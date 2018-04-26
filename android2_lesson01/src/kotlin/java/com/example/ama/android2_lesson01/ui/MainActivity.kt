package com.example.ama.android2_lesson01.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.details.DetailsNoteActivity
import com.example.ama.android2_lesson01.ui.details.DetailsNoteFragment
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesHolder

class MainActivity : AppCompatActivity(),
        ListOfNotesHolder.OnNoteClickListener {

    companion object {
        const val LIST_OF_NOTES_FRAGMENT = "list_of_notes_fragment"
        const val NOTE_EDITED_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragments()
    }

    private fun initFragments() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container_list_of_notes,
                        if (supportFragmentManager.findFragmentByTag(LIST_OF_NOTES_FRAGMENT) == null)
                            ListOfNotesFragment.newInstance()
                        else supportFragmentManager.findFragmentByTag(LIST_OF_NOTES_FRAGMENT),
                        LIST_OF_NOTES_FRAGMENT)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.mi_add_note) {
            openEditNote(null)
            return true
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == NOTE_EDITED_REQUEST && resultCode == Activity.RESULT_OK) {
            (supportFragmentManager.findFragmentByTag(LIST_OF_NOTES_FRAGMENT) as ListOfNotesFragment).refresh()
        }
    }

    override fun onNoteClick(note: Note) {
        //TODO: edit this note
    }

    override fun onDeleteNoteClick(note: Note) {
        (supportFragmentManager.findFragmentByTag(LIST_OF_NOTES_FRAGMENT) as ListOfNotesFragment).deleteNote(note)
    }

    fun openEditNote(note: Note?) {
        val intent = Intent(this, DetailsNoteActivity::class.java)
        intent.putExtra(DetailsNoteFragment.TARGET_NOTE, note)
        startActivityForResult(intent, NOTE_EDITED_REQUEST)
    }
}
