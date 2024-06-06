package com.example.passwordmanager.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.passwordmanager.model.AppDatabase;
import com.example.passwordmanager.model.PasswordDao;
import com.example.passwordmanager.model.PasswordEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasswordRepository {
    private PasswordDao passwordDao;
    private LiveData<List<PasswordEntity>> allPasswords;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public PasswordRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        passwordDao = db.passwordDao();
        allPasswords = passwordDao.getAllPasswords();
    }

    public LiveData<List<PasswordEntity>> getAllPasswords() {
        return allPasswords;
    }

    public void insert(PasswordEntity password) {
        executorService.execute(() -> passwordDao.insert(password));
    }

    public void update(PasswordEntity password) {
        executorService.execute(() -> passwordDao.update(password));
    }

    public void delete(PasswordEntity password) {
        executorService.execute(() -> passwordDao.delete(password));
    }
}
