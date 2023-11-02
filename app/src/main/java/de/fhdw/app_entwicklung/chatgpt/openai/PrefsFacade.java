package de.fhdw.app_entwicklung.chatgpt.openai;

import android.content.Context;

import androidx.preference.PreferenceManager;

public class PrefsFacade {

    private Context context;

    public PrefsFacade(Context context){
        this.context = context;
    }

    public String getApiToken(){

        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString("api_token", "");
    }
}
