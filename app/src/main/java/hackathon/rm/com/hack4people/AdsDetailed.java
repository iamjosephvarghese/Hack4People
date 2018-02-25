package hackathon.rm.com.hack4people;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdsDetailed extends AppCompatActivity {

    AdsClass detailedFetch;


    MaterialDialog.Builder builder1;
    MaterialDialog dialog;


    TextView vendorId,desc;
    ImageView img;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_detailed);


        vendorId = findViewById(R.id.vendorId);
        desc = findViewById(R.id.desc);
        img = findViewById(R.id.img);
        btn = findViewById(R.id.btn);
        btn.setVisibility(View.INVISIBLE);


        Intent getDoc = getIntent();
        String documentId = getDoc.getStringExtra("documentId");
        Log.d("docsId",documentId);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        builder1 = new MaterialDialog.Builder(AdsDetailed.this)
                .title("Loading Data")
                .content("Please Wait")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .cancelable(false);

        dialog = builder1.build();

        dialog.show();


        DocumentReference documentReference = db.collection("Ads").document(documentId);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("fetching doc again",".........");

                detailedFetch = documentSnapshot.toObject(AdsClass.class);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("fetch doc again error",".......");
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(detailedFetch != null){
                    trigger();
                }else{
                    Log.d("trigger check false","....");
                }
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO open link in chrome   detailedfetch.getLin
            }
        });


    }




    public void trigger(){

        dialog.dismiss();


        Glide.with(this)
                .load(detailedFetch.getUrl())
//                .placeholder(R.drawable.placeholder)
                .crossFade()
                .override(500,500)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(img);

        vendorId.setText(detailedFetch.getVendorId());
        desc.setText(detailedFetch.getDessc());

        btn.setVisibility(View.VISIBLE);


    }


}
