package com.example.gymtastic.ui.login

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*

import com.example.gymtastic.R

/**
 * @Author: Carlos Figueredo
 *
 * Class with the activities of the detail View
 */
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        val editTextSet = findViewById<EditText>(R.id.editTextSet)
        val editTextWeight = findViewById<EditText>(R.id.editTextWeight)
        val videoView = findViewById<VideoView>(R.id.videoView)

    }

    /**
     * Get the array of sets in ExerciseGym
     */
    fun getSets() {
        findViewById<EditText>(R.id.editTextSet).text
    }

    fun getWeight(){
        findViewById<EditText>(R.id.editTextWeight).text
    }
    /**
     * If a field isn't filled, it shows an error.
     */
    private fun showNoValues() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Sets or Weight cannot be empty")
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}