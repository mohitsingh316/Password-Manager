package com.example.passwordmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.passwordmanager.R;
import com.example.passwordmanager.util.BiometricPromptUtils;
import com.example.passwordmanager.model.PasswordEntity;
import com.example.passwordmanager.viewmodel.PasswordViewModel;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_ADD_PASSWORD_BOTTOM_SHEET = "add_password_bottom_sheet";

    private PasswordViewModel passwordViewModel;
    private PasswordAdapter adapter;
    private BiometricPrompt biometricPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        setupViewModel();
        setupClickListeners();

        setupBiometricAuthentication();
    }

    private void setupBiometricAuthentication() {
        biometricPrompt = BiometricPromptUtils.createBiometricPrompt(this, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON || errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                    // User cancelled the authentication
                    Toast.makeText(MainActivity.this, "Biometric authentication canceled", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                } else {
                    // Handle other errors
                    Toast.makeText(MainActivity.this, "Biometric authentication error: " + errString, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                // Biometric authentication succeeded
                // Continue with your app logic
                Toast.makeText(MainActivity.this, "Biometric authentication succeeded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                // Biometric authentication failed
                Toast.makeText(MainActivity.this, "Biometric authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = BiometricPromptUtils.createPromptInfo(this);
        biometricPrompt.authenticate(promptInfo);
    }

    private void initViews() {
        ImageButton buttonAddPassword = findViewById(R.id.button_add_password);
        buttonAddPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPasswordBottomSheet();
            }
        });
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new PasswordAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        passwordViewModel = new ViewModelProvider(this).get(PasswordViewModel.class);
        passwordViewModel.getAllPasswords().observe(this, new Observer<List<PasswordEntity>>() {
            @Override
            public void onChanged(List<PasswordEntity> passwordEntities) {
                adapter.setPasswords(passwordEntities);
            }
        });
    }

    private void setupClickListeners() {
        adapter.setOnDeleteClickListener(new PasswordAdapter.OnDeleteClickListener<PasswordEntity>() {
            @Override
            public void onDeleteClick(PasswordEntity password) {
                passwordViewModel.delete(password);
            }
        });

        adapter.setOnItemClickListener(new PasswordAdapter.OnItemClickListener<PasswordEntity>() {
            @Override
            public void onItemClick(PasswordEntity password) {
                showEditPasswordBottomSheet(password);
            }
        });
    }

    private void showAddPasswordBottomSheet() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddPasswordBottomSheet bottomSheet = new AddPasswordBottomSheet();
        bottomSheet.show(fragmentManager, TAG_ADD_PASSWORD_BOTTOM_SHEET);
    }

    private void showEditPasswordBottomSheet(PasswordEntity password) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddPasswordBottomSheet bottomSheet = AddPasswordBottomSheet.newInstance(password);
        bottomSheet.show(fragmentManager, TAG_ADD_PASSWORD_BOTTOM_SHEET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle any result if needed
    }
}
