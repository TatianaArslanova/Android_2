package com.example.ama.android2_lesson01.ui.details

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note

class DetailsNoteActivity : AppCompatActivity(),
        DetailsNoteFragment.OnSaveNoteClickListener {

    companion object {
        const val DETAILS_NOTE_FRAGMENT = "details_note_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_note)
        initFragments()
    }

    private fun initFragments() {
        val targetNote: Note? = intent.extras?.getParcelable(DetailsNoteFragment.TARGET_NOTE)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_details_note_fragment,
                        if (supportFragmentManager.findFragmentByTag(DETAILS_NOTE_FRAGMENT) == null)
                            DetailsNoteFragment.newInstance(targetNote)
                        else supportFragmentManager.findFragmentByTag(DETAILS_NOTE_FRAGMENT),
                        DETAILS_NOTE_FRAGMENT)
                .commit()
    }

    override fun sendResult() {
        setResult(Activity.RESULT_OK)
        finish()
    }
}