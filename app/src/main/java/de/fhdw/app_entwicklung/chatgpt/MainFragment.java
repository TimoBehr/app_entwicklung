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

import java.util.List;
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

    private String userName;

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
        userName = prefs.getUserName() + ": ";
        chat.addMessage(new Message(Author.User, userName, query));
        getTextView().append(colorChange(userName, "#006400"));
        getTextView().append(query);

        MainActivity.backgroundExecutorService.execute(() -> {
            String apiToken = prefs.getApiToken();
            ChatGpt chatGpt = new ChatGpt(apiToken);
            String answer = chatGpt.getChatCompletion(chat);

            chat.addMessage(new Message(Author.Assistant,"ChatGPT: ", answer));
            requireActivity().runOnUiThread(() -> {
                getTextView().append(CHAT_SEPARATOR);
                getTextView().append(colorChange("ChatGPT: ", "#8b0000"));
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
        getAskButton().setOnClickListener(v -> {
            getPlainTextField().clearFocus();
            if (!getPlainTextField().getText().toString().trim().equals("")){
                openAiRequest(getPlainTextField().getText().toString());
                getPlainTextField().setText("");
            }else getTextFromSpeech.launch(new LaunchSpeechRecognition.SpeechRecognitionArgs(Locale.GERMAN));});

        getPlainTextField().setOnKeyListener((view1, keyCode, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                openAiRequest(getPlainTextField().getText().toString());
                getPlainTextField().setText("");
                return true;
            }
            return false;
        });
        updateTextView();
    }

    @Override
    public void onPause() {
        super.onPause();

        textToSpeech.stop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("chat", chat);
    }

    @Override
    public void onDestroy() {
        textToSpeech.destroy();
        textToSpeech = null;

        super.onDestroy();
    }

    private void updateTextView() {
        getTextView().setText("");
        List<Message> messages = chat.getMessages();
        if (!messages.isEmpty()) {
            getTextView().append(colorChange(messages.get(0).authorName, "#006400"));
            getTextView().append(toString(messages.get(0)));
            for (int i = 1; i < messages.size(); i++) {
                getTextView().append(CHAT_SEPARATOR);
                if (messages.get(i).authorName.equals("ChatGPT: ")){
                    getTextView().append(colorChange(messages.get(i).authorName, "#8b0000"));
                }else {
                    getTextView().append(colorChange(messages.get(i).authorName, "#006400"));
                }
                getTextView().append(toString(messages.get(i)));
            }
        }
    }

    public Spannable colorChange(String text, String color){
        Spannable span = new SpannableString(text);
        span.setSpan(new ForegroundColorSpan(Color.parseColor(color)), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    private CharSequence toString(Message message) {
        return message.message;
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
        return getView().findViewById(R.id.plainText);
    }

}