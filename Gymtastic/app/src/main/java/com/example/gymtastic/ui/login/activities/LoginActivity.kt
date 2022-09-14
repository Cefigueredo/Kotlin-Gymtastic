package com.example.gymtastic.ui.login.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.gymtastic.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//@Author Sofia Abadia
class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val feed = findViewById<Button>(R.id.login)
        /*@author Sofia Abadia
         * Method with multi threading
         */
        feed.setOnClickListener {
            runBlocking{
               launch {
                   val username = findViewById<EditText>(R.id.username)
                   val password = findViewById<EditText>(R.id.password)
                   if(username.text.isNotEmpty() && password.text.isNotEmpty()) {
                       FirebaseAuth.getInstance().signInWithEmailAndPassword(
                           username.text.toString(),
                           password.text.toString()
                       ).addOnCompleteListener {
                           if (it.isSuccessful) {
                               val intent = Intent(this@LoginActivity, FeedActivity::class.java)
                               startActivity(intent)
                           } else
                           {
                               username.text.clear()
                               password.text.clear()
                               showAlert()
                           }
                       }
                   }
                   else
                       showNoCredentials()
               }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Incorrect Username or Password, please check and try again")
        builder.setPositiveButton("Accept",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showNoCredentials(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Username or Password cannot be empty")
        builder.setPositiveButton("Accept",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}