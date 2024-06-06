package com.example.passwordmanager.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.passwordmanager.model.PasswordEntity;
import com.example.passwordmanager.repository.PasswordRepository;

import java.util.List;

public class PasswordViewModel extends AndroidViewModel {
    private PasswordRepository repository;
    private LiveData<List<PasswordEntity>> allPasswords;

    public PasswordViewModel(@NonNull Application application) {
        super(application);
        repository = new PasswordRepository(application);
        allPasswords = repository.getAllPasswords();
    }

    public LiveData<List<PasswordEntity>> getAllPasswords() {
        return allPasswords;
    }

    public void insert(PasswordEntity password) {
        repository.insert(password);
    }

    public void update(PasswordEntity password) {
        repository.update(password);
    }

    public void delete(PasswordEntity password) {
        repository.delete(password);
    }
}
