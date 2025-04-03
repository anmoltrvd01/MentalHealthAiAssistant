package com.example.mentalhealthaibot

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mentalhealthaibot.roomdb.AppDatabase
import com.example.mentalhealthaibot.roomdb.UserEntity
import com.example.mentalhealthaibot.roomdb.UserRepository
import com.example.mentalhealthaibot.viewmodel.UserViewModel
import com.example.mentalhealthaibot.viewmodel.UserViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var userViewModel: UserViewModel


//    private val UserViewModel: UserViewModel by lazy {
//        val userDao = AppDatabase.getDatabase(this).userDao()
//        val repository = UserRepository(userDao)
//        val factory = UserViewModelFactory(repository)
//        ViewModelProvider(this, factory)[UserViewModel::class.java]
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Database aur Repository initialize karein
        val database = AppDatabase.getDatabase(this)
        val repository = UserRepository(database.userDao())

        // ViewModel sahi tarike se initialize karein
        userViewModel = ViewModelProvider(this, UserViewModelFactory(repository))[UserViewModel::class.java]

        val btnChat = findViewById<Button>(R.id.btnChat)
        btnChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        val btnSignIn = findViewById<Button>(R.id.btnGoogleSignIn)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // Google Sign-In Setup
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        btnSignIn.setOnClickListener {
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }
    private fun saveUserToDatabase(email: String, name: String, profileUrl: String) {
        val user = UserEntity(email, name, profileUrl)
        userViewModel.insertUser(user)
    }


    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            saveUserToDatabase(
                account.email ?: "No Email",
                account.displayName ?: "Unknown",
                account.photoUrl?.toString() ?: ""
            )
            Toast.makeText(this, "Sign-in successful!", Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {
            Toast.makeText(this, "Sign-in failed: ${e.statusCode}", Toast.LENGTH_SHORT).show()
        }
    }
}


