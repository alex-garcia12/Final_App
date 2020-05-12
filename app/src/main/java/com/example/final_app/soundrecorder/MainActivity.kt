// Moustapha Said 888907524
// Alex Garcia 802297556
// add name and CWID    ------3

package com.example.final_app.soundrecorder

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.final_app.soundrecorder.player.RecordingsActivity
import com.example.final_app.soundrecorder.recorder.RecorderViewModel
import com.example.final_app.soundrecorder.util.InjectorUtils
import com.example.final_app.soundrecorder.util.RecorderState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var viewModel: RecorderViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkNeededPermissions()        //Get permissions before doing anything
        initUI()                        // Prepare the UI

        // Start recording when recording button is pressed

        fab_start_recording.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, permissions,0)
            } else {
                startRecording()
            }
        }

        // when stop recording button is pressed

        fab_stop_recording.setOnClickListener{
            stopRecording()
        }

        // To pause the recording after starting to record
        fab_pause_recording.setOnClickListener {
            pauseRecording()
        }
        // To resume recording after pausing the record
        fab_resume_recording.setOnClickListener {
            resumeRecording()
        }

        // Takes us to a new Activity that contains the recordings

        fab_recordings.setOnClickListener {
            val intent = Intent(this, RecordingsActivity::class.java)
            startActivity(intent)
        }
        // if we are recording and we press the take us to recording, the recording stops automatically
        if(viewModel?.recorderState == RecorderState.Stopped){
            fab_stop_recording.isEnabled = false
        }
    }

    //Get permissions from user to use the microphone and access files
    private fun checkNeededPermissions() {
        println("Requesting permission")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            println("Requesting permission")
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO), 0)
        }
    }

    private fun initUI() {
        //Get the viewmodel factory
        val factory = InjectorUtils.provideRecorderViewModelFactory()

        //Getting the viewmodel
        viewModel = ViewModelProviders.of(this, factory).get(RecorderViewModel::class.java)

        addObserver()
    }

    private fun addObserver() {
        viewModel?.getRecordingTime()?.observe(this, Observer {
            textview_recording_time.text = it
        })
    }

    // Recording has begun. set the fab for stop recording to true (enabled), as
    // well as make start/resume invisible.
    @SuppressLint("RestrictedApi")
    private fun startRecording() {
        viewModel?.startRecording()

        fab_stop_recording.isEnabled = true
        fab_start_recording.visibility = View.INVISIBLE
        fab_pause_recording.visibility = View.VISIBLE
        fab_resume_recording.visibility = View.INVISIBLE
    }

    //Similar to startRecording but essentially the opposite
    @SuppressLint("RestrictedApi")
    private fun stopRecording(){
        viewModel?.stopRecording()

        fab_stop_recording.isEnabled = false
        fab_start_recording.visibility = View.VISIBLE
        fab_pause_recording.visibility = View.INVISIBLE
        fab_resume_recording.visibility = View.INVISIBLE
    }

    // Function to handle fabs when pausing recording
    @TargetApi(Build.VERSION_CODES.N)
    @SuppressLint("RestrictedApi")
    private fun pauseRecording(){
        viewModel?.pauseRecording()

        fab_stop_recording.isEnabled = true
        fab_start_recording.visibility = View.INVISIBLE
        fab_pause_recording.visibility = View.INVISIBLE
        fab_resume_recording.visibility = View.VISIBLE
    }

    // Function to handle fabs when resuming recording
    @TargetApi(Build.VERSION_CODES.N)
    @SuppressLint("RestrictedApi")
    private fun resumeRecording(){
        viewModel?.resumeRecording()
        fab_stop_recording.isEnabled = true
        fab_start_recording.visibility = View.INVISIBLE
        fab_pause_recording.visibility = View.VISIBLE
        fab_resume_recording.visibility = View.INVISIBLE
    }

}
