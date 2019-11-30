package com.thinkty.myapplication.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.thinkty.myapplication.R;

import java.util.Objects;

public class WriteFragment extends Fragment {

    private WriteViewModel writeViewModel;
    private EditText newNoteTitle;
    private EditText newNoteText;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        writeViewModel = ViewModelProviders.of(this).get(WriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_write, container, false);

        newNoteTitle = root.findViewById(R.id.new_note_title);
        newNoteText = root.findViewById(R.id.new_note_content);
        ImageButton createNewNoteButton = root.findViewById(R.id.create_new_note_button);

        createNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add a new note to the collection
                final String title = newNoteTitle.getText().toString();
                newNoteTitle.setText("");
                final String content = newNoteText.getText().toString();
                newNoteText.setText("");

                // Get unique id for the new file
                SharedPreferences preferences = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
                int newNoteIndex = preferences.getInt("newNoteIndex", 0);

                // TODO: save to the device with newNoteIndex as an unique index for later on

                // Update the newNoteIndex
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("newNoteIndex", newNoteIndex);
                editor.apply();

                Toast.makeText(getContext(), "Note Saved", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Fill in the fields if there are any temporary values
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        String newNoteTitle = preferences.getString("newNoteTitle", "");
        if (newNoteTitle != null && !newNoteTitle.isEmpty()) {
            this.newNoteTitle.setText(newNoteTitle);
        }
        String newNoteText = preferences.getString("newNoteText", "");
        if (newNoteText != null && !newNoteText.isEmpty()) {
            this.newNoteText.setText(newNoteText);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // Save the current writings on title and text
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("newNoteTitle", newNoteTitle.getText().toString());
        editor.putString("newNoteText", newNoteText.getText().toString());
        editor.apply();
    }
}