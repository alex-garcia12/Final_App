package com.example.final_app.soundrecorder.player

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import java.io.File

class RecordingRepository{
    companion object {
        @Volatile
        private var instance: RecordingRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: RecordingRepository().also { instance = it }
            }


        fun playRecording(context: Context, title: String){
            val path = Uri.parse(Environment.getExternalStorageDirectory().absolutePath+"/soundrecorder/$title")

            // to show a toast it we try to press and play a recording while another one is being played
            val manager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            if(manager.isMusicActive) {
                Toast.makeText(context, "Another recording is just playing! Wait until it's finished!", Toast.LENGTH_SHORT).show()
            }else{
                val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setDataSource(context, path)
                    prepare()
                    start()
                }
            }

        }
    }
    // initiate a variable for the externam storage to bs used for saving the recordings
    private val recorderDirectory = File(Environment.getExternalStorageDirectory().absolutePath+"/soundrecorder/")
    // array list to save the recordings in
    private var file : ArrayList<String>? = null

    init {
        file = ArrayList<String>()
        getRecording()
    }
    // returns back the recording pressed
    private fun getRecording(){
        val files: Array<out File>? = recorderDirectory.listFiles()
        for(i in files!!){
            println(i.name)
            file?.add(i.name)
        }
    }

    fun getRecordings() = file

}