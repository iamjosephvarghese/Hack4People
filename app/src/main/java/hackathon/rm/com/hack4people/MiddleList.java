package hackathon.rm.com.hack4people;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;

public class MiddleList extends AppCompatActivity {

    ArrayList<MiddleClass> fetchedMiddle;
    ArrayList<String> documentList;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    String documentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_list);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        fetchedMiddle = new ArrayList<MiddleClass>();
        documentList = new ArrayList<String>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


        CollectionReference collectionReference = db.collection("MiddleMen");
        collectionReference.orderBy("rating", Query.Direction.DESCENDING);
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {

                Log.d("fetch success", "....");
                Integer size = documentSnapshots.size();




                for (int i = 0; i < size; i++) {
                    documentID = documentSnapshots.getDocuments().get(i).getId();
                    Log.d("fetched document", documentID);
                    documentList.add(documentID);


                    DocumentReference adsRef = db.collection("MiddleMen").document(documentID);
                    adsRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            MiddleClass middleClass = documentSnapshot.toObject(MiddleClass.class);
                            Log.d("uid", middleClass.getUid());
                            fetchedMiddle.add(middleClass);
                            Log.d("fetched size",Integer.toString(fetchedMiddle.size()));



                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            Log.d("onComplete","here");
                            trigger();
                        }
                    });

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Main Fail",".....");
            }
        });


    }


    public void trigger(){
        Log.d("inide trigger",".....");
        SlimAdapter.create().register(R.layout.row_layout2, new SlimInjector<MiddleClass>() {
            @Override
            public void onInject(MiddleClass data, IViewInjector injector) {

                Log.d("inside inject","...");
                injector.text(R.id.event_name,data.getUid())
                        .text(R.id.p1,data.getRating().toString());
                LinearLayout ll = (LinearLayout) injector.findViewById(R.id.ll);

                final String middleId = data.getUid();
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent middleMenIntent = new Intent(MiddleList.this,MiddleDetailed.class);
                        middleMenIntent.putExtra("middleId",middleId);
                        startActivity(middleMenIntent);
                    }
                });

            }
        }).attachTo(recyclerView)
                .updateData(fetchedMiddle);
    }
}
