package hackathon.rm.com.hack4people;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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


public class Ads extends AppCompatActivity {

    ArrayList<AdsClass> fetchedAds;

    Query query;

    String documentID;
    ArrayList<String> documentList;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);




        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        fetchedAds = new ArrayList<AdsClass>();
        documentList = new ArrayList<String>();

//        CollectionReference audioFiles = db.collection("AudioFiles");

        query = db.collection("Ads");




        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                Log.d("fetch success", "....");
                Integer size = documentSnapshots.size();


                for (int i = 0; i < size; i++) {
                    documentID = documentSnapshots.getDocuments().get(i).getId();
                    Log.d("fetched document", documentID);
                    documentList.add(documentID);


                    DocumentReference adsRef = db.collection("Ads").document(documentID);
                    adsRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            AdsClass adsClass = documentSnapshot.toObject(AdsClass.class);
//                            Details details = documentSnapshot.get("participants");
                            Log.d("vendorId", adsClass.getVendorId());
//                            Details d = entries.getParticipants("");
//                            detailsList = entries.getParticipants(Details.class);
                            fetchedAds.add(adsClass);
                            Log.d("fetched size",Integer.toString(fetchedAds.size()));



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
        SlimAdapter.create().register(R.layout.row_layout2, new SlimInjector<AdsClass>() {
            @Override
            public void onInject(AdsClass data, IViewInjector injector) {

                Log.d("inside inject","...");
                injector.text(R.id.event_name,data.getVendorId())
                        .text(R.id.p1,data.getDessc());
                LinearLayout ll = (LinearLayout) injector.findViewById(R.id.ll);

                final String docId = data.getDocumentId();
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent detailedIntent = new Intent(Ads.this,AdsDetailed.class);
                        detailedIntent.putExtra("documentId",docId);
                        Log.d("docId",docId);
                        startActivity(detailedIntent);
                    }
                });
                
            }
        }).attachTo(recyclerView)
                .updateData(fetchedAds);
    }
}

