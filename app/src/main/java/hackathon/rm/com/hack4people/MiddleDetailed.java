package hackathon.rm.com.hack4people;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MiddleDetailed extends AppCompatActivity {

    Query query;

    TextView name,rating,address,contact;
    Button call,connect;

    MaterialDialog.Builder builder1;
    MaterialDialog dialog;

    MiddleClass middleClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_detailed);

        name = findViewById(R.id.name);
        rating = findViewById(R.id.rating);
        address = findViewById(R.id.address);
        contact = findViewById(R.id.contact);

        call = findViewById(R.id.call);
        connect = findViewById(R.id.connect);

        call.setVisibility(View.INVISIBLE);
        connect.setVisibility(View.INVISIBLE);

        Intent middleGet = getIntent();
        String middleId = middleGet.getStringExtra("middleId");


        builder1 = new MaterialDialog.Builder(MiddleDetailed.this)
                .title("Loading Data")
                .content("Please Wait")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .cancelable(false);

        dialog = builder1.build();

        dialog.show();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        query = db.collection("MiddleMen").whereEqualTo("uid",middleId);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {

                middleClass = documentSnapshots.toObjects(MiddleClass.class).get(0);
                Log.d("id",documentSnapshots.getDocuments().get(0).getId());


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                dialog.dismiss();

                if (middleClass != null){
                    trigger();
                }else{
                    Log.d("trigger check false","....");
                }

            }
        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO add call code
            }
        });




    }



    public void trigger(){
        name.setText(middleClass.getName());
        address.setText(middleClass.getAddress());
//        contact.setText(middleClass.getContactNo());
//        rating.setText(middleClass.getRating());

        call.setVisibility(View.VISIBLE);
        connect.setVisibility(View.VISIBLE);
    }
}
