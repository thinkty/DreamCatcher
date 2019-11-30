package com.thinkty.myapplication.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.thinkty.myapplication.R;

public class StoriesFragment extends Fragment {

    private StoriesViewModel storiesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        storiesViewModel =
                ViewModelProviders.of(this).get(StoriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        storiesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}