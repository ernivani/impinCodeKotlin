package fr.ernicani.impincode

import android.content.Intent
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
import fr.ernicani.impincode.utils.DatabaseManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var responseText: TextView
    private lateinit var databaseManager: DatabaseManager

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        databaseManager = DatabaseManager(this,this)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        responseText = findViewById(R.id.responseTextView)
        val textViewAppName = findViewById<TextView>(R.id.textViewAppName)
        val appName = getString(R.string.app_name)
        val spannable = SpannableString(appName).apply {
            setSpan(
                ForegroundColorSpan(Color.parseColor("#FFFFFF")),
                0,
                5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(Color.parseColor("#9990FF")),
                5,
                appName.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        textViewAppName.text = spannable

        buttonLogin.setOnClickListener {
            performAction()
            editTextUsername.clearFocus()
            editTextPassword.clearFocus()
        }
    }

    private fun performAction() {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (username.isNotBlank() && password.isNotBlank()) {
            // Launch a coroutine on the IO dispatcher
            CoroutineScope(Dispatchers.IO).launch {
                val token = databaseManager.login(username, password)
                // Switch to the Main dispatcher to update the UI
                with(Dispatchers.Main) {
                    if (token != null) {
                        navigateToLoggedActivity()
                    } else {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            getString(R.string.login_failed),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.empty_field),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun navigateToLoggedActivity() {
        val intent = Intent(this, LoggedActivity::class.java)
        startActivity(intent)
        finish()
    }
}
