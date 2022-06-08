package com.aman.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    lateinit var userRoomDatabase: UserRoomDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userRoomDatabase = UserRoomDatabase.getDatabase(this)

        Thread {

            userRoomDatabase.wordDao().insertAll(User( 0, "first two ",  "test 2"
            ))
        }.start()

        Thread {
            var users = userRoomDatabase.wordDao().getAll()
            Log.e("TAG", " users $users")
        }.start()

    }
}