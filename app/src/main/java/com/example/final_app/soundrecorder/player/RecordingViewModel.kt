package com.example.final_app.soundrecorder.player

import android.arch.lifecycle.ViewModel

class RecordingViewModel(val recordingRepository: RecordingRepository): ViewModel(){

    fun getRecordings() = recordingRepository.getRecordings()
}