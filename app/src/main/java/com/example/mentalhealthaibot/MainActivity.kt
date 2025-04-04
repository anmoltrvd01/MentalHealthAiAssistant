package com.example.mentalhealthaibot

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Database aur Repository initialize karein
        val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
//        val database = AppDatabase.getDatabase(this)
        val repository = UserRepository(database.userDao())
        // UI references
        val tvName = findViewById<TextView>(R.id.tvGreeting)
        val ivProfile = findViewById<ImageView>(R.id.ivProfile)
        val tvDate = findViewById<TextView>(R.id.tvDate)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val userEmail = account?.email
        val userDao = AppDatabase.getDatabase(applicationContext).userDao()
        val factory = UserViewModelFactory(repository)

        // Set current Date and Time
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        tvDate.text = dateFormat.format(currentTime)

        // ViewModel sahi tarike se initialize karein
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        userEmail?.let { email ->
            userViewModel.getUserByEmail(email) { user ->
                user?.let {
                    tvName.text = it.name
                    tvEmail.text = it.email
                    Glide.with(this).load(it.profileImageUrl).into(ivProfile)
                }
            }
        }

        val btnChat = findViewById<Button>(R.id.btnChat)
        btnChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        val btnSignIn = findViewById<Button>(R.id.btnGoogleSignIn)

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
        val user = UserEntity(id = 0, name = name, email = email, profileImageUrl = profileUrl)
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


