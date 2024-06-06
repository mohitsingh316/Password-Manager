package com.example.passwordmanager.model;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "password_table")
public class PasswordEntity implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Constructors
    public PasswordEntity(int id, String title, String username, String password) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.password = password;
    }

    @Ignore
    public PasswordEntity(String title, String username, String password) {
        this.title = title;
        this.username = username;
        this.password = password;
    }

    // Parcelable implementation
    protected PasswordEntity(Parcel in) {
        id = in.readInt();
        title = in.readString();
        username = in.readString();
        password = in.readString();
    }

    public static final Creator<PasswordEntity> CREATOR = new Creator<PasswordEntity>() {
        @Override
        public PasswordEntity createFromParcel(Parcel in) {
            return new PasswordEntity(in);
        }

        @Override
        public PasswordEntity[] newArray(int size) {
            return new PasswordEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(username);
        dest.writeString(password);
    }
}
