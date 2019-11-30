package com.thinkty.myapplication.ui.stories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.thinkty.myapplication.R;

public class StoriesFragment extends Fragment {

    private StoriesViewModel storiesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        storiesViewModel = ViewModelProviders.of(this).get(StoriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notes, container, false);









        return root;
    }
}