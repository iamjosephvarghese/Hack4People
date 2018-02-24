package hackathon.rm.com.hack4people;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent homeIntent = new Intent(Splash.this,HomeActivity.class);
                startActivity(homeIntent);

            }
        },2000);


    }
}
