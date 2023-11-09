package de.fhdw.app_entwicklung.chatgpt.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chat implements Parcelable {

    private final List<Message> messages = new ArrayList<>();
    public static final Parcelable.Creator<Chat> CREATOR = new Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel source) {
            return new Chat(source);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };

    public Chat() {
    }

    public Chat(Parcel source) {
       source.readList(this.messages, Message.class.getClassLoader());
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeList(messages);
    }
}