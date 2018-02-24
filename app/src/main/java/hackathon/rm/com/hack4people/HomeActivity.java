package hackathon.rm.com.hack4people;

import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.security.MessageDigest;

import br.com.safety.audio_recorder.AudioListener;
import br.com.safety.audio_recorder.AudioRecordButton;
import br.com.safety.audio_recorder.AudioRecording;
import br.com.safety.audio_recorder.RecordingItem;

public class HomeActivity extends AppCompatActivity {

    private AudioRecordButton audioRecordButton;
    String outputfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        audioRecordButton = findViewById(R.id.audio_record_button);

        final String completePath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC).getPath();

        outputfile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/file.ogg";


        audioRecordButton.setOnAudioListener(new AudioListener() {
            @Override
            public void onStop(RecordingItem recordingItem) {
                Toast.makeText(getBaseContext(), "Audio...", Toast.LENGTH_SHORT).show();
                new AudioRecording(getBaseContext()).play(recordingItem);
                Log.d("filepath",recordingItem.getFilePath());

//                recordingItem.setFilePath(completePath+"/file.ogg");
                recordingItem.setFilePath(outputfile);
                Log.d("filedescription",Integer.toString(recordingItem.describeContents()));
                Log.d("filepath",recordingItem.getFilePath());

            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Log.d("MainActivity", "Error: " + e.getMessage());
            }
        });



    }
}
