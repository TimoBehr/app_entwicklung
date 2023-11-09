package de.fhdw.app_entwicklung.chatgpt.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Message implements Parcelable {
    public final Date date;
    public final Author author;
    public final String message;

    public Message(Author author, String message) {
        this(new Date(), author, message);
    }

    public static final Parcelable.Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public Message(Date date, Author author, String message) {
        this.date = date;
        this.author = author;
        this.message = message;
    }

    public Message(Parcel source) {
        this.date = new Date(source.readLong());
        this.author = Author.valueOf(source.readString());
        this.message = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(date.getTime());
        dest.writeString(author.name());
        dest.writeString(message);
    }
}