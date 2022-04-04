package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.Room.MyInterface;
import com.example.notes.Room.NotesDatabase;
import com.example.notes.Room.NotesTable;

import com.example.notes.databinding.ActivityNoteBinding;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    ActivityNoteBinding binding;
    EditText title,description;
    CardView btnsubmit;

    NotesDatabase notesDatabase;
    NotesAdapter notesAdapter;
    MyInterface myInterface;
    List<NotesTable> notesTableList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        title = binding.addtitle;
        description = binding.adddescription;
        btnsubmit = binding.savebtn;

        notesDatabase = NotesDatabase.getINSTANCE(this);
        myInterface = notesDatabase.getInterface();
        notesTableList = new ArrayList<>();

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NotesTable notesTable = new NotesTable();
                notesTable.setTitle(title.getText().toString());
                notesTable.setDescription(description.getText().toString());
                myInterface.createNote(notesTable);
                finish();
                Toast.makeText(NoteActivity.this, "Note Add Successfully", Toast.LENGTH_SHORT).show();

            }
        });


    }
}