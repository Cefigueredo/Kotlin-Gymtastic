package com.example.gymtastic.ui.login



import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.*
import com.example.gymtastic.R
import com.google.firebase.auth.FirebaseAuth


//@Author Miguel Parra
class homeActivity : AppCompatActivity() {
    //@Author: Carlos Figueredo
    //Necessary for QR feature
    /* private lateinit var codeScanner: CodeScanner

    //Provider de ubicacion @Author Miguel Parra
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    // Id de permisos para la ubicacion @Author Miguel Parra
    val PERMISSION_ID = 42

    //Controlador
    var myGym : Gymtastic= Gymtastic()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home);

        val login = findViewById<Button>(R.id.LoginButtom);
        val createAcount = findViewById<Button>(R.id.SignUpButtom);

        login.setOnClickListener{
            setContentView(R.layout.activity_login);
            //@Author Sofia Abadia
            /* When the user clicks the button "Lets go!!" the app verifies that th username and the password are not empty and are part of the registered users
             If its not a registered user it shows an error
             If username or password are empty is shows an error*/
            val feed = findViewById<Button>(R.id.login);
            feed.setOnClickListener {
                val username = findViewById<EditText>(R.id.username)
                val password = findViewById<EditText>(R.id.password)
                if(username.text.isNotEmpty() && password.text.isNotEmpty()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        username.text.toString(),
                        password.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            leerubicacionactual();
                            setContentView(R.layout.activity_feed);

                            //@Author: Carlos Figueredo
                            val r1 = findViewById<Button>(R.id.recomended_btn)
                            r1.setOnClickListener{

                                setContentView(R.layout.activity_detail);

                                val weight = findViewById<EditText>(R.id.editTextWeight)
                                val set = findViewById<EditText>(R.id.editTextSet)
                                val exerciseGym = ExerciseGym("Press Chest","https://www.youtube.com/watch?v=vthMCtgVtFw", 20, 20)

                                //Putting the video of the exercise and playing it
                                val video = findViewById<VideoView>(R.id.videoView)
                                val uri = Uri.parse(exerciseGym.getUrl())
                                video.setVideoURI(uri)
                                video.start()

                                val submitButton = findViewById<Button>(R.id.buttonSubmitWeightSet)
                                submitButton.setOnClickListener{
                                    setContentView(R.layout.activity_feed)
                                    //Logic where the Weight and sets are added to the exercise
                                    exerciseGym.addSets(
                                        Set(
                                            set.text.toString().toInt(),
                                            weight.text.toString().toInt()
                                        )
                                    )
                                    Toast.makeText(this,"Weight added", Toast.LENGTH_SHORT).show()
                                }

                                val returnButton = findViewById<ImageButton>(R.id.returnButton)
                                returnButton.setOnClickListener{
                                    setContentView(R.layout.activity_feed)
                                }

                            }
                            //@Author: Carlos Figueredo. Feature QR
                            val qrButton = findViewById<Button>(R.id.QR)
                            qrButton.setOnClickListener{
                                setContentView(R.layout.activity_qr)
                                if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),123)
                                }
                                else{
                                    startScanning()
                                }
                            }
                        }
                        else
                            showAlert();
                    }
                }
                else
                    showNoCredentials();


            }
        }

        createAcount.setOnClickListener{
            setContentView(R.layout.activity_create_account);
            //@Author Sofia Abadia
            val feed = findViewById<Button>(R.id.login4);
            feed.setOnClickListener {
                leerubicacionactual();
                setContentView(R.layout.activity_feed);

                //@Author: Carlos Figueredo
                val r1 = findViewById<Button>(R.id.recomended_btn)
                r1.setOnClickListener{

                    setContentView(R.layout.activity_detail);

                    val weight = findViewById<EditText>(R.id.editTextWeight)
                    val set = findViewById<EditText>(R.id.editTextSet)
                    val exerciseGym = ExerciseGym("Press Chest","https://www.youtube.com/watch?v=vthMCtgVtFw", 20, 20)

                    //Putting the video of the exercise and playing it
                    val video = findViewById<VideoView>(R.id.videoView)
                    val uri = Uri.parse(exerciseGym.getUrl())
                    video.setVideoURI(uri)
                    video.start()

                    val submitButton = findViewById<Button>(R.id.buttonSubmitWeightSet)
                    submitButton.setOnClickListener{
                        setContentView(R.layout.activity_feed)
                        //Logic where the Weight and sets are added to the exercise
                        exerciseGym.addSets(
                            Set(
                                set.text.toString().toInt(),
                                weight.text.toString().toInt()
                            )
                        )
                        Toast.makeText(this,"Weight added", Toast.LENGTH_SHORT).show()
                    }

                    val returnButton = findViewById<ImageButton>(R.id.returnButton)
                    returnButton.setOnClickListener{
                        setContentView(R.layout.activity_feed)
                    }

                }
                //@Author: Carlos Figueredo. Feature QR
                val qrButton = findViewById<Button>(R.id.QR)
                qrButton.setOnClickListener{
                    setContentView(R.layout.activity_qr)
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),123)
                    }
                    else{
                        startScanning()
                    }
                }
            }
        }





        // Metodo que verifica todos los permisos garantisados @author Miguel Parra
        //Preguntar si se tiene permisos.
        if (allPermissionsGrantedGPS()){
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        } else {
            // Si no hay permisos solicitarlos al usuario.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
        }


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
        builder.setMessage("Username or Password cannot be empty")
        builder.setPositiveButton("Accept",null)
        val dialog: AlertDialog= builder.create()
        dialog.show()
    }

    //LOGICA GPS @MIGUEL PARRA REVISA TODOS LOS PERMISOS
    private fun allPermissionsGrantedGPS() = REQUIRED_PERMISSIONS_GPS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    //Metodo que lee las ubicaciones
    private fun leerubicacionactual(){
        if (checkPermissions()){
            if (isLocationEnabled()){
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.lastLocation.addOnCompleteListener(this){ task ->
                        var location: Location? = task.result
                        if (location == null){
                            requestNewLocationData()
                        } else {

                            val texto = myGym.determineRecomendedRoutine(location.latitude.toString(),location.longitude.toString())
                            val  recomended = findViewById<Button>(R.id.recomended_btn);
                            recomended.setText(texto)

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
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
        }
    }
    //REQUIERE MAS PERMISOS DE GPS
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(){
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper())
    }

    private val mLocationCallBack = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation : Location = locationResult.lastLocation
           // binding.lbllatitud.text = "LATITUD = " + mLastLocation.latitude.toString()
            //binding.lbllongitud.text = "LONGITUD = "+ mLastLocation.longitude.toString()
        }
    }
    //Revisa si el permiso de ubicacion funciona
    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    //@Author: Carlos Figueredo.
    private fun startScanning() {
        val scannerView: CodeScannerView = findViewById<CodeScannerView>(R.id.scanner_view)

        codeScanner = CodeScanner(this,scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread{
                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_SHORT).show()
            }
        }

        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread{
                Toast.makeText(this, "Camera initialization error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        scannerView.setOnClickListener{
            codeScanner.startPreview()
        }
    }

    //@Author: Carlos Figueredo.
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Camera permission granted", Toast.LENGTH_SHORT).show()
                startScanning()
            }else{
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //@Author: Carlos Figueredo.
    override fun onResume() {
        super.onResume()
        if(::codeScanner.isInitialized){
            codeScanner?.startPreview()
        }
    }

    //@Author: Carlos Figueredo.
    override fun onPause() {
        super.onPause()
        if(::codeScanner.isInitialized){
            codeScanner?.releaseResources()
        }
    }


    //Metodo que verifica y solicita permisos de ubicacion @Author Miguel Parra

    companion object {
        private val REQUIRED_PERMISSIONS_GPS= arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    }
    }*/
}

