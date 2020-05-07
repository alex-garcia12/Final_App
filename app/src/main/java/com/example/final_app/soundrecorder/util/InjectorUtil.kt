package com.example.final_app.soundrecorder.util

import com.example.final_app.soundrecorder.player.RecordingRepository
import com.example.final_app.soundrecorder.player.RecordingViewModelProvider
import com.example.final_app.soundrecorder.recorder.RecorderRepository
import com.example.final_app.soundrecorder.recorder.RecorderViewModelProvider

object InjectorUtils {
    fun provideRecorderViewModelFactory(): RecorderViewModelProvider {
        val recorderRepository = RecorderRepository.getInstance()
        return RecorderViewModelProvider(recorderRepository)
    }

    fun provideRecordingViewModelFactory(): RecordingViewModelProvider {
        val recordingRepository = RecordingRepository.getInstance()
        return RecordingViewModelProvider(recordingRepository)
    }
}