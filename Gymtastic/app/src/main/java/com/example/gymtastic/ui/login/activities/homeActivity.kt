package com.example.gymtastic.ui.login.activities



import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.*
import com.example.gymtastic.R


//@Author Miguel Parra
class homeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        val login = findViewById<Button>(R.id.LoginButtom)
        val createAccount = findViewById<Button>(R.id.SignUpButtom)

        login.setOnClickListener{
            /*@author Sofia Abadia
            * Method with eventual connectivity
            * If there's no network, the user can't login
            */
            val connectionManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            if (isConnected) {
                val intent = Intent(this@homeActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error")
                builder.setMessage("There is no network connection. Please check and try again")
                builder.setPositiveButton("Accept",null)
                val dialog: AlertDialog= builder.create()
                dialog.show()
            }
        }

        createAccount.setOnClickListener {
            /*@author Sofia Abadia
            * Method with eventual connectivity
            * If there's no network, the user can't login
            */
            val connectionManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
                if (isConnected) {
                    val intent = Intent(this@homeActivity, CreateAccountActivity::class.java)
                    startActivity(intent)
                }
                else{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error")
                    builder.setMessage("There is no network connection. Please check and try again")
                    builder.setPositiveButton("Accept",null)
                    val dialog: AlertDialog= builder.create()
                    dialog.show()
                }
        }
    }
}

