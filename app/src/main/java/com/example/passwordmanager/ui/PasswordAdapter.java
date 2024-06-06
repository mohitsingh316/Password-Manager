package com.example.passwordmanager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordmanager.R;
import com.example.passwordmanager.model.PasswordEntity;

import java.util.ArrayList;
import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordHolder> {

    private List<PasswordEntity> passwords = new ArrayList<>();
    private OnItemClickListener<PasswordEntity> onItemClickListener;
    private OnDeleteClickListener<PasswordEntity> onDeleteClickListener; // Listener for delete clicks

    public void setPasswords(List<PasswordEntity> passwords) {
        this.passwords = passwords;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<PasswordEntity> listener) {
        this.onItemClickListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener<PasswordEntity> listener) {
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public PasswordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.password_item, parent, false);
        return new PasswordHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordHolder holder, int position) {
        PasswordEntity currentPassword = passwords.get(position);
        holder.textViewTitle.setText(currentPassword.getTitle());
        holder.textViewUsername.setText(currentPassword.getUsername());
    }

    @Override
    public int getItemCount() {
        return passwords.size();
    }

    class PasswordHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewUsername;
        private View deleteButton; // Button or icon for delete

        public PasswordHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewUsername = itemView.findViewById(R.id.text_view_username);
            deleteButton = itemView.findViewById(R.id.delete_button); // Initialize delete button or icon

            // Set click listener for the entire item view
            itemView.setOnClickListener(view -> {
                if (onItemClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(passwords.get(getAdapterPosition()));
                }
            });

            // Set click listener for delete button or icon
            deleteButton.setOnClickListener(view -> {
                if (onDeleteClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onDeleteClickListener.onDeleteClick(passwords.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item);
    }

    // Interface for delete click listener
    public interface OnDeleteClickListener<T> {
        void onDeleteClick(T item);
    }
}
