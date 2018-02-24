package hackathon.rm.com.hack4people;

import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class SoundRecording extends AppCompatActivity {

    private String outputFile = null;
    private MediaRecorder myAudioRecorder;
    int flag = 0;

    private StorageReference mStorageRef;
    private StorageMetadata metadata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_recording);



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

                }



            }
        });




    }
}
