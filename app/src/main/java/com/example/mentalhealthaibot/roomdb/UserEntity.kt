package com.example.mentalhealthaibot.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val email: String,  // Primary key (har user ka unique email hoga)
    val name: String,
    val profilePictureUrl: String
)
