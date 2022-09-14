package com.example.gymtastic.ui.login.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.provider.MediaStore.Video.Thumbnails.VIDEO_ID
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.example.gymtastic.R
import com.example.gymtastic.ui.login.QRCode
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.coroutines.*

/**
 * @Author: Carlos Figueredo
 * Class with the activities of the qr view
 */
class QRActivity : AppCompatActivity() {
    var code = ""
    private lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                123
            )
        } else {
            //Connectivity
            val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            if(isConnected){
                startScanning()
            }
            else{
                Toast.makeText(this@QRActivity, "There is no connectivity", Toast.LENGTH_SHORT).show()

            }
        }

    }

    //@Author: Carlos Figueredo.
    private fun startScanning() {
        val scannerView: CodeScannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            /**
             * runOnUiThread runs the specified action on the UI thread.
             * If the current thread is the UI thread, then the action
             * is executed immediately. If the current thread is not the UI thread,
             * the action is posted to the event queue of the UI thread.
             */
            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                runOnUiThread {
                    Toast.makeText(this@QRActivity, "Scan result: ${it.text}", Toast.LENGTH_SHORT).show()
                    code = it.text
                    searchVideo()
                }
            }
        }

        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(
                    this,
                    "Camera initialization error: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    fun searchVideo() {
        if (code.startsWith("https://www.youtube.com/watch?v") ) {
            QRCode().setCode(code.split("=")[1])
            val intent = Intent(this@QRActivity, VideoQRActivity::class.java)
            startActivity(intent)
        }
        else if(code.startsWith("https://youtu.be/")){
            val idVideo = code.split("/")[3].dropLast(1)
            QRCode().setCode(idVideo)

            val intent = Intent(this@QRActivity, VideoQRActivity::class.java)
            startActivity(intent)
        }
        else {
            Toast.makeText(this, "Not a youtube video", Toast.LENGTH_SHORT).show()
        }
    }

    //@Author: Carlos Figueredo.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
                startScanning()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //@Author: Carlos Figueredo.
    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner?.startPreview()
        }
    }

    //@Author: Carlos Figueredo.
    override fun onPause() {
        super.onPause()
        if (::codeScanner.isInitialized) {
            codeScanner?.releaseResources()
        }
    }
}