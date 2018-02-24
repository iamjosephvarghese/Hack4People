package hackathon.rm.com.hack4people;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MiddleDetailed extends AppCompatActivity {

    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_detailed);

        Intent middleGet = getIntent();
        String middleId = middleGet.getStringExtra("middleId");

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        query = db.collection("MiddleMen").




    }
}
