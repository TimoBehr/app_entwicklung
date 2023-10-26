package de.fhdw.app_entwicklung.chatgpt.speech;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class Speak implements TextToSpeech.OnInitListener {

    static TextToSpeech tts;

    public Speak(Context context) {
        tts = new TextToSpeech(context, this);
    }

    public static void Ausgabe(String text){
        tts.setLanguage(Locale.GERMAN);
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    public void onInit(int status) {

    }
}
