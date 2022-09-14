package com.example.gymtastic.ui.login.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.util.LruCache
import android.widget.*
import androidx.annotation.IntegerRes

import com.example.gymtastic.R
import com.example.gymtastic.ui.login.ExerciseGym
import com.example.gymtastic.ui.login.Gymtastic
import com.example.gymtastic.ui.login.Set
import com.example.gymtastic.ui.login.User
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author: Carlos Figueredo
 *
 * Class with the activities of the detail View
 */
class DetailActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    val YOUTUBE_API_KEY = "AIzaSyBiSVjU3PT4_UMm30ZQLzfJyUR-1psjuIc"
    private lateinit var memoryCache: LruCache<String, String>
    //Autor Miguel Parra
    val gym = Gymtastic()
    //Archivo shared preferences
    private val sharedPrefFile = "kotlinsharedpreference"
    //TODO: It needs to get the link from DB according to the routine
    //Get link of the video of the routine
    var youtubeLink = "https://www.youtube.com/watch?v=KTqgEvqYZtI"
    //Take the id from the link
    val youtubeVideoid = youtubeLink.split("=")[1]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val weight = findViewById<EditText>(R.id.editTextWeight)
        val set = findViewById<EditText>(R.id.editTextSet)


        //Putting the video of the exercise and playing it
        val exerciseGym =
            ExerciseGym("Press Chest", "https://www.youtube.com/watch?v=vthMCtgVtFw", 20, 20)

        //Autor Miguel Parra
        var newset = gym.predictNextSet(exerciseGym)
       // Log.d("Weigth",""+newset.getWeigth())
        weight.setText(newset.getWeigth().toString())
        set.setText(newset.getRep().toString())

        // Get reference to the view of Video player
        //Source: https://www.geeksforgeeks.org/how-to-play-youtube-video-in-android-using-youtube-api/
        //https://stackoverflow.com/questions/29133874/android-youtube-api-an-error-occurred-while-initializing-youtube-player
        val ytPlayer = findViewById<YouTubePlayerView>(R.id.videoView)

        //@Author: Carlos Figueredo
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        // Use 1/8th of the available memory for this memory cache.
        val cacheSize = maxMemory / 8

        memoryCache = object : LruCache<String, String>(cacheSize){}

        ytPlayer.initialize(YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {

            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                player?.loadVideo(youtubeVideoid)
                player?.play()
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@DetailActivity, "Video player Failed", Toast.LENGTH_SHORT)
                    .show()
            }
        })



        val submitButton = findViewById<Button>(R.id.buttonSubmitWeightSet)
        //Event of click in the button
        submitButton.setOnClickListener {

            val connectionManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            //There is connectivity---------------
            if (isConnected) {
                //Busines Question Miguel Parra Sprint 3
                updateMuslelGroup()
                if (weight.text.toString().isNullOrEmpty() or set.text.toString().isNullOrEmpty()) {
                    //Error if a field is empty
                    Toast.makeText(this, "A value is empty", Toast.LENGTH_SHORT).show()
                } else if ((weight.text.toString().toInt() < 1) or (set.text.toString().toInt() < 1)) {
                    //Error if a field is lower than one
                    Toast.makeText(this, "Values should be positive", Toast.LENGTH_SHORT).show()
                } else {
                    finish()
                    //Logic where the Weight and sets are added to the exercise
                    exerciseGym.addSets(
                        Set(
                            set.text.toString().toInt(),
                            weight.text.toString().toInt()
                        )
                    )
                    Toast.makeText(this, "Weight added", Toast.LENGTH_SHORT).show()
                }
            }
            //In case of lost conectivity-------------------------
            else{

                val db = Firebase.firestore
                val actualId = FirebaseAuth.getInstance().currentUser?.uid
                //First  ask in cache if there is the data
                val lastwk =  memoryCache.get(actualId.toString())
                if(lastwk != null)
                {
                    val text = findViewById<TextView>(R.id.lastWk)
                    text.setText("Your last workout was in "+lastwk.toString())
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Last connection retrieve from ")
                    builder.setMessage("local cache")
                    builder.setPositiveButton("Accept",null)
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
                else
                {
                    //Try to ask data from disk
                    val prefs: SharedPreferences = getSharedPreferences(sharedPrefFile, 0)

                    val lastwk =prefs.getString(actualId.toString(),null)
                    if(lastwk!=null)
                    {
                        val text = findViewById<TextView>(R.id.lastWk)
                        text.setText("Your last workout was in "+lastwk.toString())
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Last conection retrive from ")
                        builder.setMessage("local disk")
                        builder.setPositiveButton("Accept",null)
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                    }
                    else
                    {
                        //return a sorry message
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Sorry  ")
                        builder.setMessage("There is no connection, please try again with conectivity")
                        builder.setPositiveButton("Accept",null)
                        val dialog: AlertDialog = builder.create()
                        dialog.show()

                    }


                }

            }



        }

    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {

    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {

    }

    //Busines Question Miguel Parra Sprint 3
    fun updateMuslelGroup()
    {
        val db = Firebase.firestore


        val ref= db.collection("MuscularGroup").document("Onp5rnhDfLSK6FgiPPDf")
        ref.get().addOnSuccessListener { document ->

                try{

                    var count = document.data?.get("count")

                    if(count != null)
                    {

                        val countInt = count.toString().toInt() +1 ;

                       ref.update("count", countInt.toString())

                    }



                }
                catch(e: Exception)
                {

                }

            }
    }
}