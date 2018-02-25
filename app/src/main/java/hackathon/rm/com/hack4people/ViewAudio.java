package hackathon.rm.com.hack4people;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.io.IOException;
import java.util.ArrayList;

public class ViewAudio extends AppCompatActivity {
    ArrayList<AudioClass> fetchedAds;

    Query query;

    String documentID;
    ArrayList<String> documentList;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Button start;
    int flag = 0;
    MediaPlayer[] mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        start = findViewById(R.id.start);
        mediaPlayer = new MediaPlayer[1];




        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        fetchedAds = new ArrayList<AudioClass>();
        documentList = new ArrayList<String>();

//        CollectionReference audioFiles = db.collection("AudioFiles");

        query = db.collection("AudioFiles").orderBy("status", Query.Direction.DESCENDING);
//TODO sorting based on if answered by checking status




        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                Log.d("fetch success", "....");
                Integer size = documentSnapshots.size();


                for (int i = 0; i < size; i++) {
                    documentID = documentSnapshots.getDocuments().get(i).getId();
                    Log.d("fetched document", documentID);
                    documentList.add(documentID);


                    DocumentReference adsRef = db.collection("AudioFiles").document(documentID);
                    adsRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            AudioClass audioClass = documentSnapshot.toObject(AudioClass.class);
//                            Details details = documentSnapshot.get("participants");
                            Log.d("vendorId", audioClass.getuId());
//                            Details d = entries.getParticipants("");
//                            detailsList = entries.getParticipants(Details.class);
                            fetchedAds.add(audioClass);
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
        Log.d("inside trigger",".....");
        SlimAdapter.create().register(R.layout.row_layout2, new SlimInjector<AudioClass>() {
            @Override
            public void onInject(final AudioClass data, final IViewInjector injector) {

                Log.d("inside inject","...");
                injector.text(R.id.event_name,data.getuId())
                        .text(R.id.p1,data.getAnswer());
                LinearLayout ll = (LinearLayout) injector.findViewById(R.id.ll);

//                final String docId = data.getDocumentId();
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        Intent detailedIntent = new Intent(ViewAudio.this,.class);
//                        detailedIntent.putExtra("documentId",docId);
//                        startActivity(detailedIntent);



                        if (flag == 0){
                            mediaPlayer[0] = new MediaPlayer();
                            mediaPlayer[0].setAudioAttributes(new AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                    .build());
                            try {
                                mediaPlayer[0].setDataSource(data.getUrl());
                                mediaPlayer[0].prepare();
                                mediaPlayer[0].start();
                                flag =1;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else {
                            mediaPlayer[0].stop();
                            mediaPlayer[0].release();
                            flag = 0;
                        }


                    }
                });

            }
        }).attachTo(recyclerView)
                .updateData(fetchedAds);
    }
}

