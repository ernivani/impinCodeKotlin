package fr.ernicani.impincode

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var loginRedirect: MaterialButton
    private lateinit var registerRedirect: MaterialButton
    private lateinit var sharedPreferences: SharedPreferences


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)

        val token = sharedPreferences.getString("token", null)


        if (token != null) {
            navigateToLoggedActivity()
            finish()
            return
        }
        val textViewAppName =
            findViewById<TextView>(R.id.textViewAppName)
        val appName = getString(R.string.app_name)
        val spannable = SpannableString(appName)

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

        val myCustomButton = findViewById<AppCompatButton>(R.id.myCustomButton)
        myCustomButton.setOnClickListener {
            Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show()
        }

        val existingAccountTextView = findViewById<TextView>(R.id.existingAccountTextView)
        val fullText = existingAccountTextView.text.toString()
        val spannableString = SpannableString(fullText)
        val textColor = Color.parseColor("#9990FF")
        val startIndex = fullText.indexOf("Connecte toi !")
        val endIndex = startIndex + "Connecte toi !".length

        spannableString.setSpan(
            ForegroundColorSpan(textColor),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        existingAccountTextView.text = spannableString

        existingAccountTextView.setOnClickListener {
//           todo: change Activity to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        if (intent.getBooleanExtra("logged_out", false)) {
            Snackbar.make(
                findViewById(android.R.id.content),
                "You have been logged out",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        val token = sharedPreferences.getString("token", null)
        if (token != null) {
            navigateToLoggedActivity()
            finish()
        }
    }
        // Récupérez le token et le statut de connexion


//        loginRedirect = findViewById(R.id.LoginRedirect)
//        registerRedirect = findViewById(R.id.RegisterRedirect)
//
//        loginRedirect.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
//
//        registerRedirect.setOnClickListener {
//            val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
//        }
//
//    }
//
    private fun navigateToLoggedActivity() {
        val intent = Intent(this, LoggedActivity::class.java)
        startActivity(intent)
        finish()
    }


}