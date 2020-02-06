package com.example.shareandcare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class splashScreenActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static int SPLASH_TIME_OUT = 3000;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        tts = new TextToSpeech(this, this);

        speak();

        new Handler().postDelayed(new Runnable() {

           @Override
           public void run() {
               Intent i = new Intent(splashScreenActivity.this, mainActivity.class);
               startActivity(i);
               finish();
           }
        },SPLASH_TIME_OUT);
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.UK);

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This language is not supported!");
            } else {
                speak();
            }
        } else {
            Log.e("TTS", "Initialization Failed!");
        }

    }

    private void speak() {
        String msg = "Share and Care";
        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
    }
}
