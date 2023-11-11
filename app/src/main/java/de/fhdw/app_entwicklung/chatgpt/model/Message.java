package de.fhdw.app_entwicklung.chatgpt.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Message implements Parcelable {
    public final Date date;
    public final Author author;

    public final String authorName;
    public final String message;

    public Message(Author author,String authorName, String message) {
        this(new Date(), author, authorName, message);
    }

    public Message(Date date, Author author, String authorName, String message) {
        this.date = date;
        this.author = author;
        this.authorName = authorName;
        this.message = message;
    }

    public Message(Parcel source) {
        this.date = new Date(source.readLong());
        this.author = Author.valueOf(source.readString());
        this.authorName = source.readString();
        this.message = source.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(date.getTime());
        dest.writeString(author.name());
        dest.writeString(authorName);
        dest.writeString(message);
    }
}