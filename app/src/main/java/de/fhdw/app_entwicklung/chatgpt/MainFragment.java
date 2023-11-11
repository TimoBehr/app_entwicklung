package de.fhdw.app_entwicklung.chatgpt;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import de.fhdw.app_entwicklung.chatgpt.model.Author;
import de.fhdw.app_entwicklung.chatgpt.model.Chat;
import de.fhdw.app_entwicklung.chatgpt.model.Message;
import de.fhdw.app_entwicklung.chatgpt.openai.ChatGpt;
import de.fhdw.app_entwicklung.chatgpt.openai.PrefsFacade;
import de.fhdw.app_entwicklung.chatgpt.speech.LaunchSpeechRecognition;
import de.fhdw.app_entwicklung.chatgpt.speech.TextToSpeechTool;

public class MainFragment extends Fragment {

    private static final String CHAT_SEPARATOR = "\n\n";

    private PrefsFacade prefs;
    private TextToSpeechTool textToSpeech;
    private Chat chat;

    private final ActivityResultLauncher<LaunchSpeechRecognition.SpeechRecognitionArgs> getTextFromSpeech = registerForActivityResult(
            new LaunchSpeechRecognition(),
            query -> {
                if(query != null){
                    openAiRequest(query);
                }
            });

    public MainFragment() {

    }

    public void openAiRequest(String query){
        chat.addMessage(new Message(Author.User, query));
        String name = "User" + ": ";
        Spannable user = new SpannableString(name);
        user.setSpan(new ForegroundColorSpan(Color.parseColor("#006400")), 0, user.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getTextView().append(user);
        getTextView().append(query);

        MainActivity.backgroundExecutorService.execute(() -> {
            String apiToken = prefs.getApiToken();
            ChatGpt chatGpt = new ChatGpt(apiToken);
            String answer = chatGpt.getChatCompletion(chat);

            chat.addMessage(new Message(Author.Assistant, answer));
            requireActivity().runOnUiThread(() -> {
                getTextView().append(CHAT_SEPARATOR);
                Spannable text = new SpannableString("ChatGPT: ");
                text.setSpan(new ForegroundColorSpan(Color.parseColor("#8b0000")), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                getTextView().append(text);
                getTextView().append(answer);
                textToSpeech.speak(answer);
                getTextView().append(CHAT_SEPARATOR);
            });
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null){
            chat = savedInstanceState.getParcelable("chat");
        }else {
            chat = new Chat();
        }
        prefs = new PrefsFacade(requireContext());
        textToSpeech = new TextToSpeechTool(requireContext(), Locale.GERMAN);
        getAskButton().setOnClickListener(v ->
                getTextFromSpeech.launch(new LaunchSpeechRecognition.SpeechRecognitionArgs(Locale.GERMAN)));

        getPlainTextField().setOnKeyListener((view1, i, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                    (i == KeyEvent.KEYCODE_ENTER)) {
                openAiRequest(getPlainTextField().getText().toString());
                getPlainTextField().setText("");
                return true;
            }
            return false;
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        textToSpeech.stop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("chat", chat);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        textToSpeech.destroy();
        textToSpeech = null;

        super.onDestroy();
    }

    private TextView getTextView() {
        //noinspection ConstantConditions
        return getView().findViewById(R.id.ausgabeView);
    }

    private Button getAskButton() {
        //noinspection ConstantConditions
        return getView().findViewById(R.id.button_ask);
    }

    private EditText getPlainTextField() {
        //noinspection ConstantConditions
        return getView().findViewById(R.id.plainText);
    }

}