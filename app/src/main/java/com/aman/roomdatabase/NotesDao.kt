package com.aman.roomdatabase

import androidx.room.*

@Dao
interface NotesDao{
    @Query("SELECT * FROM notes")
    fun getAll(): List<Notes>

    @Query("SELECT * FROM notes WHERE id IN (:id)")
    fun loadAllByIds(id: IntArray): List<Notes>

    @Query("SELECT * FROM notes WHERE title LIKE :title AND " +
            "description LIKE :description LIMIT 1")
    fun findByName(title: String, description: String): Notes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg notes: Notes)

    @Query("SELECT * FROM notes WHERE id = :id")
    fun findNotesByID(id: Int?): Notes?

    @Update()
    fun update(vararg notes: Notes)

    @Delete
    fun delete(note: Notes)
}