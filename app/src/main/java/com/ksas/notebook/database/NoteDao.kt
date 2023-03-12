package com.ksas.notebook.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ksas.notebook.models.Note


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("select * from notes order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("UPDATE notes set title= :title, note = :note where id = :id")
    suspend fun update(id: Int?, title: String?, note: String?)


}