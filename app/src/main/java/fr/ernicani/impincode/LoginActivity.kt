// LoginActivity.kt

package fr.ernicani.impincode

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    private lateinit var sharedPreferences: SharedPreferences

    private fun navigateToLoggedActivity() {
        val intent = Intent(this, LoggedActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)

        val token = sharedPreferences.getString("token", null)

        val textViewAppName =
            findViewById<TextView>(R.id.textViewAppName)
        val appName = getString(R.string.app_name)
        val spannable = SpannableString(appName)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#FFFFFF")),
            0,
            5,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#9990FF")),
            5,
            appName.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textViewAppName.text = spannable

        buttonLogin.setOnClickListener {
            if (editTextUsername.text.toString().trim() == "admin" && editTextPassword.text.toString().trim() == "admin") {


                with(sharedPreferences.edit()) {
                    putBoolean("isLogged", true)
                    val token = "admin"
                    putString("token", token)
                    apply()
                }
                navigateToLoggedActivity()
            }
            else {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.login_invalid_credentials),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }



    }



}
