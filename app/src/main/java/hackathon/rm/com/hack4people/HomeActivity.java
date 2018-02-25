package hackathon.rm.com.hack4people;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button audioUpload,audioFetch,middleList,itemUpload,viewAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        audioUpload = findViewById(R.id.audioUpload);
        audioUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this,SoundRecording.class);
                startActivity(intent);

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
