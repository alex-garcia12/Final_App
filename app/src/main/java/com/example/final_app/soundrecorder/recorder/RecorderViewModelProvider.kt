package com.example.final_app.soundrecorder.recorder

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class RecorderViewModelProvider(val recorderRepository: RecorderRepository): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecorderViewModel(recorderRepository) as T
    }
}