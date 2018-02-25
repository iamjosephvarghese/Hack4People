package hackathon.rm.com.hack4people;

import android.graphics.Color;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;

public class    SoundRecording extends AppCompatActivity {

    private String outputFile = null;
    private MediaRecorder myAudioRecorder;
    int flag = 0;

    private StorageReference mStorageRef;
    private StorageMetadata metadata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_recording);


        StrictMode.VmPolicy.Builder builderStrict = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builderStrict.build());


        mStorageRef = FirebaseStorage.getInstance().getReference();

        final Button start;


        start = findViewById(R.id.start);


        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.mp3";


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    try {
                        myAudioRecorder = new MediaRecorder();
                        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                        myAudioRecorder.setOutputFile(outputFile);
                        myAudioRecorder.prepare();
                        myAudioRecorder.start();
                        flag = 1;
                        start.setBackgroundColor(Color.RED);
                        start.setText("STOP");
                        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                        Log.d("outputfile",outputFile);
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else {
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    myAudioRecorder = null;
                    flag = 0;
                    start.setText("START");
                    start.setBackgroundColor(Color.GREEN);
                    Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
                    Log.d("starting uplaod",".........");

                    upload();


                }



            }
        });



    }

    public void upload(){


        StorageReference sRef = mStorageRef.child("AudioFiles").child(new Date().toString());

        metadata = new StorageMetadata.Builder()
                .setContentType("audio/mpeg")
                .setCustomMetadata("timestamp",new Date().toString())
                .build();


        mStorageRef = FirebaseStorage.getInstance().getReference();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final DocumentReference audioFiles = db.collection("AudioFiles").document();

        Uri url = Uri.parse("file:///"+outputFile);
        Log.d("url",url.toString());

        sRef.putFile(url,metadata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                audioFiles.set(new AudioClass(taskSnapshot.getDownloadUrl().toString(),"user1",false," ")).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("audio push","success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("audio push","error");
                    }
                });
            }
        });




    }
}
