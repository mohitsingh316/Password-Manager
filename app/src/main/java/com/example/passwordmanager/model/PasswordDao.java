package com.example.passwordmanager.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PasswordDao {

    @Update
    void update(PasswordEntity password);

    @Delete
    void delete(PasswordEntity password);
    @Query("SELECT * FROM password_table")
    LiveData<List<PasswordEntity>> getAllPasswords();

    @Insert
    void insert(PasswordEntity password);
}
