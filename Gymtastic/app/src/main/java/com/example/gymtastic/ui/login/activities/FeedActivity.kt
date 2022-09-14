package com.example.gymtastic.ui.login.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Looper
import android.preference.PreferenceManager
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.gymtastic.R
import com.example.gymtastic.ui.login.Gymtastic
import com.google.android.gms.location.*
import android.util.Log
import android.util.LruCache
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.*

class FeedActivity : AppCompatActivity() {

    //Provider de ubicacion @Author Miguel Parra
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    // Id de permisos para la ubicacion @Author Miguel Parra
    val PERMISSION_ID = 42

    //Archivo shared preferences
    private val sharedPrefFile = "kotlinsharedpreference"

    //Controlador
    var myGym: Gymtastic = Gymtastic()

    //LRU Miguel Parra
    private lateinit var memoryCache: LruCache<String, String>

    var placeRou = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed);


        //Caching LRU para last connection @Autor Miguel Parra
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        // Use 1/8th of the available memory for this memory cache.
        val cacheSize = maxMemory / 8

        memoryCache = object : LruCache<String, String>(cacheSize) {}


        // Metodo que verifica todos los permisos garantisados @author Miguel Parra
        //Preguntar si se tiene permisos.
        if (allPermissionsGrantedGPS()) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        } else {
            // Si no hay permisos solicitarlos al usuario.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_ID
            )
        }

        //Features Miguel
        leerubicacionactual()
        val refresh = findViewById<Button>(R.id.refresh)
        refresh.setOnClickListener {
            whereWasLastWork()
            //In case of wifi conection request data from firebase directly
            val connectionManager: ConnectivityManager =
                this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            if (isConnected) {

                updateLastWork()
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Last conection retrive from ")
                builder.setMessage("Online database")
                builder.setPositiveButton("Accept", null)
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            //In case of lost conectivity
            else {

                val db = Firebase.firestore
                val actualId = FirebaseAuth.getInstance().currentUser?.uid
                //First  ask in cache if there is the data
                val lastwk = memoryCache.get(actualId.toString())
                if (lastwk != null) {
                    val text = findViewById<TextView>(R.id.lastWk)
                    text.setText("Your last workout was in " + lastwk.toString())
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Last conection retrive from ")
                    builder.setMessage("local cache")
                    builder.setPositiveButton("Accept", null)
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                } else {
                    //Try to ask data from disk
                    val prefs: SharedPreferences = getSharedPreferences(sharedPrefFile, 0)

                    val lastwk = prefs.getString(actualId.toString(), null)
                    if (lastwk != null) {
                        val text = findViewById<TextView>(R.id.lastWk)
                        text.setText("Your last workout was in " + lastwk.toString())
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Last conection retrive from ")
                        builder.setMessage("local disk")
                        builder.setPositiveButton("Accept", null)
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                    } else {
                        //return a sorry message
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Sorry  ")
                        builder.setMessage("You are in a jungle please try again with conectivity")
                        builder.setPositiveButton("Accept", null)
                        val dialog: AlertDialog = builder.create()
                        dialog.show()

                    }


                }

            }
        }


        //@Author: Carlos Figueredo. Detail View
        val r1 = findViewById<Button>(R.id.recomended_btn)
        r1.setOnClickListener {

            val intent = Intent(this@FeedActivity, DetailActivity::class.java)
            startActivity(intent)
        }
        //@Author: Carlos Figueredo. Feature QR
        val qrButton = findViewById<Button>(R.id.QR)
        qrButton.setOnClickListener {
            val intent = Intent(this@FeedActivity, QRActivity::class.java)
            startActivity(intent)
        }


    }

    //REQUIERE MAS PERMISOS DE GPS
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallBack,
            Looper.myLooper()
        )
    }

    private val mLocationCallBack = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            // binding.lbllatitud.text = "LATITUD = " + mLastLocation.latitude.toString()
            //binding.lbllongitud.text = "LONGITUD = "+ mLastLocation.longitude.toString()
        }
    }

    //Revisa si el permiso de ubicacion funciona
    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }


    //Metodo que verifica y solicita permisos de ubicacion @Author Miguel Parra

    companion object {
        private val REQUIRED_PERMISSIONS_GPS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    //LOGICA GPS @MIGUEL PARRA REVISA TODOS LOS PERMISOS
    private fun allPermissionsGrantedGPS() = REQUIRED_PERMISSIONS_GPS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    //Metodo que lee las ubicaciones
    private fun leerubicacionactual() {

        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                        var location: Location? = task.result
                        if (location == null) {
                            requestNewLocationData()
                        } else {

                            val texto = myGym.determineRecomendedRoutine(
                                location.latitude.toString(),
                                location.longitude.toString()
                            )
                            val recomended = findViewById<Button>(R.id.recomended_btn);
                            recomended.setText(texto)
                            placeRou = texto

                        }
                    }
                }
            } else {
                Toast.makeText(this, "Activar ubicación", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
                this.finish()
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_ID
            )
        }
    }

    //@Autor Miguel Parra Busines Question Miguel Parra Sprint 2
    //Also this method implement a coroutine
    //Update Last Workout in DB
    fun updateLastWork() = runBlocking {
        launch {
            val db = Firebase.firestore
            val actualId = FirebaseAuth.getInstance().currentUser?.uid
            if (actualId != null) {
                val userRef = db.collection("users").document(actualId)
                userRef.get().addOnSuccessListener { document ->

                    try {
                        val date = document.data?.get("last_workout")
                        if (date != null) {
                            val text = findViewById<TextView>(R.id.lastWk)
                            text.setText("Your last workout was in " + date.toString())
                            //Save on cache the lastWK
                            memoryCache.put(actualId.toString(), date.toString())
                            //Save on disk
                            val prefs: SharedPreferences = getSharedPreferences(sharedPrefFile, 0)

                            prefs.edit().putString(actualId.toString(), date.toString()).apply()
                        } else {
                            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                            val currentDate = sdf.format(Date())
                            userRef.update("last_workout", currentDate)

                        }


                    } catch (e: Exception) {

                    }
                }


            }

        }
    }


    /*
     * @Author: Sofia Abadia
     * business question type 2: my last workout was from the gym or from home?
     */
    fun whereWasLastWork() {
        val db = Firebase.firestore
        val actualId = FirebaseAuth.getInstance().currentUser?.uid
        if (actualId != null) {
            val userRef = db.collection("users").document(actualId)
            userRef.get().addOnSuccessListener { document ->

                try {
                    val place = document.data?.get("last_Routine")
                    if (place != null) {
                        val text = findViewById<TextView>(R.id.place)
                        text.setText("Your last workout was: " + place.toString())
                        userRef.update("last_Routine", placeRou)
                        //Save on cache the lastWK
                        //memoryCache.put(actualId.toString(),date.toString())
                        //Save on disk
                        //val prefs: SharedPreferences = getSharedPreferences(sharedPrefFile, 0)

                        //prefs.edit().putString(actualId.toString(), date.toString()).apply()
                    } else {
                        userRef.update("last_Routine", placeRou)

                    }


                } catch (e: Exception) {

                }


            }


        }
    }
}