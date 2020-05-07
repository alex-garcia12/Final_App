package com.example.final_app.soundrecorder.item

import android.content.Context
import com.example.final_app.soundrecorder.R
import com.example.final_app.soundrecorder.player.RecordingRepository
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.recording_layout.view.*

class Recording(val title: String, val context: Context): Item(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.recording_title_textview.text = title
        viewHolder.itemView.recording_image.setOnClickListener {
            RecordingRepository.playRecording(context, title )
        }
    }

    override fun getLayout(): Int {
        return R.layout.recording_layout
    }
//comment
}