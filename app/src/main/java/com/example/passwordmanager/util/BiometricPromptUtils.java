package com.example.passwordmanager.util;

import android.content.Context;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.passwordmanager.R;

import java.util.concurrent.Executor;

public class BiometricPromptUtils {

    public static BiometricPrompt createBiometricPrompt(FragmentActivity activity, BiometricPrompt.AuthenticationCallback callback) {
        Executor executor = ContextCompat.getMainExecutor(activity);
        return new BiometricPrompt(activity, executor, callback);
    }

    public static BiometricPrompt.PromptInfo createPromptInfo(Context context) {
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle(context.getString(R.string.biometric_prompt_title))
                .setSubtitle(context.getString(R.string.biometric_prompt_subtitle))
                .setDescription(context.getString(R.string.biometric_prompt_description))
                .setNegativeButtonText(context.getString(R.string.biometric_prompt_cancel))
                .build();
    }

    public static boolean isBiometricAvailable(Context context) {
        BiometricManager biometricManager = BiometricManager.from(context);
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS;
    }
}
