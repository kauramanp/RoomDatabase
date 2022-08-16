package com.aman.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var id: String?="id"
    lateinit var userRoomDatabase: NotesRoomDatabase
    public var dateFormat = SimpleDateFormat("dd MMM yyyy")
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userRoomDatabase = NotesRoomDatabase.getDatabase(this)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.navController)

        Thread {

            userRoomDatabase.notesDao().insertAll(Notes( 0, "first two ",  "test 2", dateFormat.format(Calendar.getInstance().time)
            ))
        }.start()

        Thread {
            var users = userRoomDatabase.notesDao().getAll()
            Log.e("TAG", " users $users")
        }.start()

    }
}