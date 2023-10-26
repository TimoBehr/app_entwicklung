package de.fhdw.app_entwicklung.chatgpt.speech;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LaunchSpeechRecognition extends ActivityResultContract<LaunchSpeechRecognition.SpeechRecognitionArgs, String> {

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, SpeechRecognitionArgs speechRecognitionArgs) {
        return null;
    }

    @Override
    public String parseResult(int i, @Nullable Intent intent) {
        return null;
    }

    public static class SpeechRecognitionArgs
    {
        public SpeechRecognitionArgs()
        {
        }
    }
}
