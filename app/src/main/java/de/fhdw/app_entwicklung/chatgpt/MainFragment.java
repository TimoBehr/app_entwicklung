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
import androidx.fragment.app.Fragment;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import de.fhdw.app_entwicklung.chatgpt.openai.ChatGpt;
import de.fhdw.app_entwicklung.chatgpt.speech.LaunchSpeechRecognition;

public class MainFragment extends Fragment {

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button b = view.findViewById(R.id.button);
        ChatGpt c = new ChatGpt("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");
        Executor executor = Executors.newFixedThreadPool(1);

        b.setOnClickListener(v->{
            executor.execute(new Runnable(){

                @Override
                public void run() {
                    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                            new ActivityResultContracts.StartActivityForResult(),
                            new ActivityResultCallback<ActivityResult>() {
                                @Override
                                public void onActivityResult(ActivityResult result) {
                                    if (result.getResultCode() == Activity.RESULT_OK) {
                                        // Here, no request code
                                        Intent data = result.getData();
                                        String response = c.getChatCompletion(data.getDataString());
                                        ((TextView)getView().findViewById(R.id.textView)).append(response + "\n");
                                    }
                                }
                            });
                }
            });
        });
    }
}