package com.thinkty.myapplication.ui.write;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WriteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WriteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }
}