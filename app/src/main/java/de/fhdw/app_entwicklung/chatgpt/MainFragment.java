package de.fhdw.app_entwicklung.chatgpt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import de.fhdw.app_entwicklung.chatgpt.openai.ChatGpt;
import de.fhdw.app_entwicklung.chatgpt.speech.LaunchSpeechRecognition;
import de.fhdw.app_entwicklung.chatgpt.speech.Speak;

public class MainFragment extends Fragment {

    Speak speak;

    private final ActivityResultLauncher<LaunchSpeechRecognition.SpeechRecognitionArgs> someActivityResultLauncher = registerForActivityResult(
            new LaunchSpeechRecognition(),
            query -> {
                if (query != null) {
                    ChatGpt c = new ChatGpt(PreferenceManager
                            .getDefaultSharedPreferences(requireContext())
                            .getString("api_token", ""));
                    ((TextView)getView().findViewById(R.id.textView)).append(query);
                    Executor executor = Executors.newFixedThreadPool(1);
                    executor.execute(() -> {
                        String response = c.getChatCompletion(query);
                        ((TextView)getView().findViewById(R.id.textView)).append("\n");
                        ((TextView)getView().findViewById(R.id.textView)).append(response + "\n");
                        Speak.Ausgabe(response);

                    });
                }
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
        speak = new Speak(this.getContext());

        b.setOnClickListener(v-> someActivityResultLauncher.launch(new LaunchSpeechRecognition.SpeechRecognitionArgs()));
    }
}