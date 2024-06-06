package com.example.passwordmanager.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.passwordmanager.R;
import com.example.passwordmanager.model.PasswordEntity;
import com.example.passwordmanager.viewmodel.PasswordViewModel;

import java.util.Random;

public class AddPasswordBottomSheet extends DialogFragment {

    private EditText editTextTitle;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonSave;
    private Button buttonGeneratePassword;

    private PasswordViewModel passwordViewModel;

    public static AddPasswordBottomSheet newInstance(PasswordEntity passwordEntity) {
        AddPasswordBottomSheet fragment = new AddPasswordBottomSheet();
        Bundle args = new Bundle();
        args.putParcelable("password_entity", passwordEntity);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_add_password, container, false);

        // Initialize UI elements
        editTextTitle = view.findViewById(R.id.edit_text_title);
        editTextUsername = view.findViewById(R.id.edit_text_username);
        editTextPassword = view.findViewById(R.id.edit_text_password);
        buttonSave = view.findViewById(R.id.button_save);
        buttonGeneratePassword = view.findViewById(R.id.button_generate_password);

        // Retrieve PasswordEntity from arguments bundle
        Bundle args = getArguments();
        if (args != null) {
            PasswordEntity passwordEntity = args.getParcelable("password_entity");
            if (passwordEntity != null) {
                // Set values of EditText fields
                editTextTitle.setText(passwordEntity.getTitle());
                editTextUsername.setText(passwordEntity.getUsername());
                editTextPassword.setText(passwordEntity.getPassword());
            }
        }

        // Initialize ViewModel
        passwordViewModel = new ViewModelProvider(this).get(PasswordViewModel.class);

        // Set click listener for save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle save button click
                String title = editTextTitle.getText().toString();
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Perform save operation
                savePassword(title, username, password);

                // Close the bottom sheet after saving
                dismiss();
            }
        });

        // Set click listener for generate password button
        buttonGeneratePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generate a password and set it in the password EditText
                String generatedPassword = generateRandomPassword();
                editTextPassword.setText(generatedPassword);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Adjust the size and position of the bottom sheet
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        // Set the height to a certain percentage of the screen height
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenHeight = displayMetrics.heightPixels;
        params.height = (int) (screenHeight * 0.4); // Adjust the percentage as needed

        getDialog().getWindow().setAttributes(params);
        // Set the window background to null to remove the default white background
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    // Method to generate a random password
    private String generateRandomPassword() {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        int passwordLength = 12; // You can adjust the length as needed
        StringBuilder sb = new StringBuilder(passwordLength);
        Random random = new Random();
        for (int i = 0; i < passwordLength; i++) {
            sb.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        return sb.toString();
    }

    // Method to save the password
    private void savePassword(String title, String username, String password) {
        // Retrieve the PasswordEntity from the arguments bundle
        Bundle args = getArguments();
        if (args != null) {
            PasswordEntity passwordEntity = args.getParcelable("password_entity");
            if (passwordEntity != null) {
                // Update the existing passwordEntity with new values
                passwordEntity.setTitle(title);
                passwordEntity.setUsername(username);
                passwordEntity.setPassword(password);
                // Update the password in the database
                passwordViewModel.update(passwordEntity);
                // Show a message to indicate that the password has been updated
                Toast.makeText(getContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                // Close the bottom sheet after updating
                dismiss();
                return; // Exit the method after updating
            }
        }
        // If there is no existing passwordEntity, create a new one and insert it into the database
        PasswordEntity newPasswordEntity = new PasswordEntity(title, username, password);
        passwordViewModel.insert(newPasswordEntity);
        // Show a message to indicate that the password has been saved
        Toast.makeText(getContext(), "Password saved successfully", Toast.LENGTH_SHORT).show();
        // Close the bottom sheet after saving
        dismiss();
    }
}
