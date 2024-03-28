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
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
class LoginActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    private lateinit var responseText: TextView

    private lateinit var sharedPreferences: SharedPreferences

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)

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
            performAction("https://code.impin.fr/api/login")
        }
    }

    private fun performAction(url: String) {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (username.isNotBlank() && password.isNotBlank()) {
            sendRequest(url, username, password)
        } else {
            Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.login_empty_field),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun sendRequest(url: String, email: String, password: String) {
        val payload = JSONObject().apply {
            put("username", email)
            put("password", password)
        }

        val requestBody = payload.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder().url(url).post(requestBody).build()
        client.newCall(request).enqueue(getCallbackForApiResponse())
    }

    private fun getCallbackForApiResponse(): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.network_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        val jsonObject = JSONObject(responseBody)
                        val token = jsonObject.getString("token")

                        sharedPreferences.edit().putString("token", token).apply()

                        navigateToLoggedActivity()
                    } else {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            getString(R.string.login_invalid_credentials),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun navigateToLoggedActivity() {
        val intent = Intent(this, LoggedActivity::class.java)
        startActivity(intent)
        finish()
    }

}