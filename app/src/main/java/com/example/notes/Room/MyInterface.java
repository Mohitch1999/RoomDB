package com.example.notes.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyInterface {

    @Insert
    void createNote(NotesTable notesTable);

    @Update
    void updateNote(NotesTable notesTable);

    @Query("select * from notes")
    List<NotesTable> getAllNotes();

    @Query("delete from notes where id=:id")
    void deleteNotes(int id);
}
