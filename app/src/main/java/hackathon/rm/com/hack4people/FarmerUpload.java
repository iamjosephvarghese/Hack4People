package hackathon.rm.com.hack4people;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

public class FarmerUpload extends AppCompatActivity {

    EditText item,qty;
    Button button;

    Integer quantity;



    private StorageReference mStorageRef;
    private StorageMetadata metadata;


    String itemS,qtyS,farmerId = "abcd";
//    TODO shared prefrences

    MaterialDialog.Builder builder;
    MaterialDialog uploadDialog;

    String downloadUrl;

    private int STORAGE_PERMISSION_CODE = 23;
    private int CAMERA_PERMISSION_CODE = 24;

    private int LOAD_IMAGE = 1;
    private int TAKE_CAMERA = 2;
    private int CHECK_IMAGE = 3;
    Uri uploadUri;
    private Bitmap bitmap;

    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_upload);


        db = FirebaseFirestore.getInstance();

        item = findViewById(R.id.item);
        qty = findViewById(R.id.qty);

        button = findViewById(R.id.button);




        mStorageRef = FirebaseStorage.getInstance().getReference();

        StrictMode.VmPolicy.Builder builderStrict = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builderStrict.build());


        builder = new MaterialDialog.Builder(FarmerUpload.this)
                .title("Uploading Image")
                .content("Please Wait")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .cancelable(false);


        uploadDialog = builder.build();





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemS = item.getText().toString();
                qtyS = qty.getText().toString();


                if(!itemS.equals("") && !qtyS.equals("")){

                    metadata = new StorageMetadata.Builder()
                            .setContentType("image/jpg")
                            .setCustomMetadata("Timestamp",new Date().toString())
                            .build();

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                        if (isReadStorageAllowed()) {
                            showFileChooser();
                            return;
                        }

                        requestStoragePermission();

                    }else{
                        showFileChooser();
                    }

                }
            }
        });





    }


    private void showFileChooser(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),LOAD_IMAGE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            uploadUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                writeToFile(BitMapToString(bitmap),FarmerUpload.this);
                Intent showImage = new Intent(FarmerUpload.this,ShowSelected.class);
                startActivityForResult(showImage,CHECK_IMAGE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (requestCode == CHECK_IMAGE){
            if (resultCode == RESULT_OK){
                uploadImage();

            }else if(resultCode == RESULT_CANCELED){

            }

        }


    }



    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }



    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){

        }

        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            } else {


            }
        }
    }



    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    public  void uploadImage(){


        Log.d("Reached","uploadImage");



        db = FirebaseFirestore.getInstance();

        final DocumentReference documentReference = db.collection("FarmerItems").document();

        final StorageReference sRef = mStorageRef.child("FarmerItems/" +  new Date().toString());

        uploadDialog.show();


        sRef.putFile(uploadUri,metadata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloadUrl = taskSnapshot.getDownloadUrl().toString();
                Log.d("Uploading image",".......");



                documentReference.set(new FarmerUploadClass(downloadUrl,farmerId,itemS,Integer.parseInt(qtyS))).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("push to db farmer item","success");
                        uploadDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("push to db farmer item","error");
                        uploadDialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("image upload error","....");
                Log.d("exception",e.toString());
                uploadDialog.dismiss();
            }
        });



    }




}



