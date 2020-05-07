package com.example.final_app.soundrecorder.player

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class RecordingViewModelProvider(val recordingRepository: RecordingRepository): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecordingViewModel(recordingRepository) as T
    }
}