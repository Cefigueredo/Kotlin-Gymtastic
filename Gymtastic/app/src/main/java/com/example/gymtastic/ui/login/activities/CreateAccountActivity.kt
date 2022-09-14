package com.example.gymtastic.ui.login.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.gymtastic.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//@Author Sofia Abadia
class CreateAccountActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_account)

        val name = findViewById<EditText>(R.id.username2)
        val email = findViewById<EditText>(R.id.username3)
        val password = findViewById<EditText>(R.id.username4)
        val conPassword = findViewById<EditText>(R.id.username5)


        val feed = findViewById<Button>(R.id.login4)
        feed.setOnClickListener {
            if (email.text.isNotEmpty() && password.text.isNotEmpty() && name.text.isNotEmpty() && conPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    email.text.toString(),
                    password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        addUser(email.text.toString(), name.text.toString())
                        val intent =
                            Intent(this@CreateAccountActivity, FeedActivity::class.java)
                        startActivity(intent)
                    } else
                        showAlert()
                }
            } else
                showNoCredentials()
        }
    }

    private fun addUser(ema: String, nam: String) {
        val db = Firebase.firestore
        val user = hashMapOf(
            "email" to ema,
            "name" to nam
        )
        db.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid).set(user)
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("An error has occurred while authenticating the user")
        builder.setPositiveButton("Accept",null)
        val dialog: AlertDialog= builder.create()
        dialog.show()
    }

    private fun showNoCredentials(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Information missing, please check")
        builder.setPositiveButton("Accept",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    /*private fun checkName(name:String): String? {
        if (name.length < 3 || name.length > 20)
            return "Name should be between 3 and 20 characters"
        return null
    }*/

}
