package com.example.gymtastic.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.example.gymtastic.R

/**
 * Author: Carlos Figueredo
 */
class Form1Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_account)

        val name = findViewById<EditText>(R.id.editTextName)
        val lastName = findViewById<EditText>(R.id.editTextLastName)
        val age = findViewById<EditText>(R.id.editTextAge)
        val gender = findViewById<EditText>(R.id.editTextGender)
    }
}