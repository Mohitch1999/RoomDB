package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.Room.MyInterface;
import com.example.notes.Room.NotesDatabase;
import com.example.notes.Room.NotesTable;
import com.example.notes.databinding.ActivityUpdateBinding;

public class UpdateActivity extends AppCompatActivity {

    private ActivityUpdateBinding binding;
    NotesDatabase notesDatabase;
    MyInterface myInterface;
    EditText et_title,et_description;
    CardView back,updatebtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        et_title = binding.updatetitle;
        et_description = binding.updatedescription;
        back = binding.backbtn;
        updatebtn = binding.updatebtn;


        notesDatabase = NotesDatabase.getINSTANCE(this);
        myInterface = notesDatabase.getInterface();

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        int id = intent.getIntExtra("id",-1);



        et_title.setText(title);
        et_description.setText(description);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotesTable notesTable = new NotesTable();
                notesTable.setId(id);
                notesTable.setDescription(et_description.getText().toString());
                notesTable.setTitle(et_title.getText().toString());
                myInterface.updateNote(notesTable);
                finish();
                Toast.makeText(UpdateActivity.this, "Note Update Succesfully", Toast.LENGTH_SHORT).show();
            }
        });

    }



}