package com.aman.roomdatabase

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notes(
    @PrimaryKey (autoGenerate = true)
    var id: Int=0,
    @ColumnInfo(name = "title") var title: String?= null,
    @ColumnInfo(name = "description") var description: String?= null,
    @ColumnInfo(name = "created_date") var date: String?= null,
    @ColumnInfo(name = "is_completed") var isCompleted: Boolean? = false,
    @ColumnInfo(name = "completed_date") var completedDate: String?= null
)