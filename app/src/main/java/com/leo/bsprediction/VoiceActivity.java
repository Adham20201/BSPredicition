package com.leo.bsprediction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class VoiceActivity extends AppCompatActivity {

    ImageButton btnRec;

    GifImageView gifView;

    SpeechRecognizer speechRecognizer;

    ImageView practiseState;
    TextView practiseNumber;

    int num_practice, num_trails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_voice);

        practiseState = findViewById(R.id.practice_state);
        practiseNumber = findViewById(R.id.practice_number);

        num_practice = 0;
        num_trails = 0;

        btnRec = findViewById(R.id.btnRec);
        gifView = findViewById(R.id.gifView);


        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
            }

            @Override
            public void onBeginningOfSpeech() {
                gifView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {
                gifView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(int error) {}

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> result = new ArrayList<>();
                result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (result.get(0).matches("the quick brown fox jumps over the lazy dog")){
                    num_trails++ ;
                    num_practice++;
                    practiseState.setImageResource(R.drawable.ic_basel_true);
                    practiseNumber.setText(num_practice + "/6");
                } else {
                    num_trails++ ;
                    practiseState.setImageResource(R.drawable.ic_basel_false);
                }
                if (num_trails >= 6){
                    if (num_practice <= 5){
                        SendMessage();
                        Intent intent = new Intent(VoiceActivity.this, UserProfileActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(VoiceActivity.this, UserProfileActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {}

            @Override
            public void onEvent(int eventType, Bundle params) {}
        });



        btnRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechRecognizer.startListening(intent);
            }
        });

    }

    private void SendMessage() {
        try {
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage("01115585967",null,"Help, Brain stroke predicted!\n" + "\n" +
                    "Location: Gharbiya STEM School, El Galaa Educational Complex, Tanta, Gharbiya.",null,null);
            Toast.makeText(getApplicationContext(),"Message Sent",Toast.LENGTH_LONG).show();
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Some fields is Empty",Toast.LENGTH_LONG).show();
        }
    }

}