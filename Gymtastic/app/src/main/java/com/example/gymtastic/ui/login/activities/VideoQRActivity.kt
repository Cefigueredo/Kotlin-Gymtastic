package com.example.gymtastic.ui.login.activities

import android.os.Bundle
import android.widget.Toast
import com.example.gymtastic.R
import com.example.gymtastic.ui.login.QRCode
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

/**
 * Author: Carlos Figueredo
 */
class VideoQRActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    val YOUTUBE_API_KEY = "AIzaSyBiSVjU3PT4_UMm30ZQLzfJyUR-1psjuIc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_qr)


        // Get reference to the view of Video player
        //Source: https://www.geeksforgeeks.org/how-to-play-youtube-video-in-android-using-youtube-api/
        //https://stackoverflow.com/questions/29133874/android-youtube-api-an-error-occurred-while-initializing-youtube-player
        val ytPlayer = findViewById<YouTubePlayerView>(R.id.videoView)
        ytPlayer.initialize(YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {

            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {

                player?.loadVideo(QRCode().getCode())
                player?.play()
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@VideoQRActivity, "Video player Failed", Toast.LENGTH_SHORT)
                    .show()
            }
        })
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
}