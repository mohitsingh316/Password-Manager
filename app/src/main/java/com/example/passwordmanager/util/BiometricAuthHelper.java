package com.example.passwordmanager.util;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.passwordmanager.ui.MainActivity;

import java.util.concurrent.Executor;

public class BiometricAuthHelper {

    private final Context context;
    private final BiometricPrompt.AuthenticationCallback callback;

    public BiometricAuthHelper(Context context, BiometricPrompt.AuthenticationCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean isBiometricSupported() {
        BiometricManager biometricManager = BiometricManager.from(context);
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS;
    }

    public void authenticate() {
        Executor executor = ContextCompat.getMainExecutor(context);
        BiometricPrompt biometricPrompt = new BiometricPrompt((MainActivity) context, executor, callback);
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Authenticate using your biometric credential")
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}
