package com.aman.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Notes::class), version = 1, exportSchema = false)
public abstract class NotesRoomDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        private var instance: NotesRoomDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): NotesRoomDatabase {
            // if the instance is not null, then return it,
            // if it is, then create the database
            if(instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesRoomDatabase::class.java,
                    "notes_database"
                ).build()
            }
            return instance!!
        }
    }
}