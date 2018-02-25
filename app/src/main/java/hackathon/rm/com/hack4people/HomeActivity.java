package hackathon.rm.com.hack4people;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeActivity extends AppCompatActivity {

    Button audioUpload,audioFetch,middleList,itemUpload,viewAd;
    TextView id,balance;

    String uid = "farmer1";

    String amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("farmers");



        id = findViewById(R.id.id);
        balance = findViewById(R.id.balance);

        audioUpload = findViewById(R.id.audioUpload);
        audioUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this,SoundRecording.class);
                startActivity(intent);

            }
        });



        id.setText(uid);

        collectionReference.document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("uid fetch","success");

                amount = documentSnapshot.get("amount").toString();
                balance.setText(amount);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("uid fetch","error");

            }
        });




        audioFetch = findViewById(R.id.audioFetch);
        audioFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this,ViewAudio.class);
                startActivity(intent);


            }
        });


        middleList = findViewById(R.id.middleList);
        middleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this,MiddleList.class);
                startActivity(intent);


            }
        });


        itemUpload = findViewById(R.id.itemUpload);
        itemUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this,FarmerUpload.class);
                startActivity(intent);


            }
        });



        viewAd = findViewById(R.id.viewAd);
        viewAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this,Ads.class);
                startActivity(intent);


            }
        });




        
    }
}
