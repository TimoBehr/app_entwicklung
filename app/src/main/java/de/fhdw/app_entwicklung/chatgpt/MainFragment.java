package de.fhdw.app_entwicklung.chatgpt;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
                    String response = c.getChatCompletion("Stelle mir eine Rechenaufgabe");
                    ((TextView)getView().findViewById(R.id.textView)).append(response + "\n");
                }
            });
        });
    }
}