package de.fhdw.app_entwicklung.chatgpt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import de.fhdw.app_entwicklung.chatgpt.openai.ChatGpt;
import de.fhdw.app_entwicklung.chatgpt.speech.LaunchSpeechRecognition;

public class MainFragment extends Fragment {

    private final ActivityResultLauncher<LaunchSpeechRecognition.SpeechRecognitionArgs> someActivityResultLauncher = registerForActivityResult(
            new LaunchSpeechRecognition(),
            query -> {
                ((TextView)getView().findViewById(R.id.textView)).append(query);
                ChatGpt c = new ChatGpt("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");
                Executor executor = Executors.newFixedThreadPool(1);
                executor.execute(new Runnable(){
                    @Override
                    public void run() {
                        String response = c.getChatCompletion(query);
                        ((TextView)getView().findViewById(R.id.textView)).append("\n");
                        ((TextView)getView().findViewById(R.id.textView)).append(response + "\n");
                    }
                });
            });

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button b = view.findViewById(R.id.button);

        b.setOnClickListener(v->{
                    someActivityResultLauncher.launch(new LaunchSpeechRecognition.SpeechRecognitionArgs());
        });
    }
}