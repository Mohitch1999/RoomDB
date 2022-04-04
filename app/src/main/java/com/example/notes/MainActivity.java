package com.example.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.Room.MyInterface;
import com.example.notes.Room.NotesAdapterInterface;
import com.example.notes.Room.NotesDatabase;
import com.example.notes.Room.NotesTable;
import com.example.notes.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesAdapterInterface {

    CardView addnotebtn;
    RecyclerView rclview;

    ActivityMainBinding binding;
    NotesDatabase notesDatabase;
    NotesAdapter notesAdapter;
    MyInterface myInterface;
    List<NotesTable> notesTableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        rclview = binding.rclview;
        addnotebtn = binding.addnotebtn;

        notesDatabase = NotesDatabase.getINSTANCE(this);
        myInterface = notesDatabase.getInterface();
        notesTableList = new ArrayList<>();

        addnotebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent addnote = new Intent(MainActivity.this,NoteActivity.class);
                startActivity(addnote);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        notesTableList = myInterface.getAllNotes();
        notesAdapter = new NotesAdapter(this , this);
            for(int i=0;i<notesTableList.size(); i++){
                notesAdapter.addNotes(notesTableList.get(i));

            }
            notesAdapter.notifyDataSetChanged();
        rclview.setLayoutManager(new LinearLayoutManager(this));
        rclview.setAdapter(notesAdapter);


    }

    @Override
    public void Longclick(int pos, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete")
                .setMessage("are you sure want to delete the note")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notesAdapter.removeNotes(pos);
                        myInterface.deleteNotes(id);
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.show();


    }

    @Override
    public void simpleclick(int pos, int id) {
        Intent intent = new Intent(this,UpdateActivity.class);
        intent.putExtra("title",notesTableList.get(pos).getTitle());
        intent.putExtra("description",notesTableList.get(pos).getDescription());
        intent.putExtra("id",notesTableList.get(pos).getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                notesAdapter.getFilter().filter(newText);

                return false;


            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}