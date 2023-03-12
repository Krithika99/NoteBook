package com.ksas.notebook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.ksas.notebook.databinding.ActivityAddNoteBinding
import com.ksas.notebook.models.Note
import java.text.SimpleDateFormat
import java.util.*

class AddNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var note: Note
    private lateinit var oldNote: Note
    private lateinit var titleText: TextView
    private lateinit var noteText: TextView
    private var isUpdate = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        titleText = findViewById(R.id.titleNote)
        noteText = findViewById(R.id.noteDesc)

        try {
            oldNote = intent.getSerializableExtra("current_note") as Note
            binding.titleNote.setText(oldNote.title)
            binding.noteDesc.setText(oldNote.note)
            isUpdate = true
        } catch (exception: java.lang.Exception) {
            exception.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {
            val title = titleText.text.toString()
            val noteDesc = noteText.text.toString()

            if (title.isNotEmpty() || noteDesc.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyy HH:mm a")
                note = if (isUpdate) {
                    Note(
                        oldNote.id,
                        title,
                        noteDesc,
                        formatter.format(Date())
                    )
                } else {
                    Note(
                        null,
                        title,
                        noteDesc,
                        formatter.format(Date())
                    )
                }
                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@AddNote, "Please enter some data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }

    }
}