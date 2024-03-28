// LoginActivity.kt

package fr.ernicani.impincode

import android.annotation.SuppressLint
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
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class LoggedActivity : AppCompatActivity() {

    private lateinit var buttonLogout: Button
    private lateinit var sharedPreferences: SharedPreferences
    private val client = OkHttpClient()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged)


        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        
        val token = sharedPreferences.getString("token", null)

        if (token == null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
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


        buttonLogout = findViewById(R.id.buttonLogout)
        buttonLogout.setOnClickListener {
            logout()
        }

    }

    private fun logout() {
        with(sharedPreferences.edit()) {
            remove("token")
            apply()
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkToken() {
        val token = sharedPreferences.getString("token", null)
        if (token == null) {
            logout()
        }

        val url = "https://code.impin.fr/api/check-token"

        val payload = JSONObject().apply {
            put("token", token)
        }

        val requestBody = payload.toString().toRequestBody()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

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
                val body = response.body?.string()
                val jsonObject = JSONObject(body)
                val success = jsonObject.getBoolean("success")

                if (!success) {
                    logout()
                } else {
                    val user = jsonObject.getJSONObject("user")
                    val username = user.getString("username")
                    runOnUiThread {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Welcome back $username",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }


                }
            }
        }
    }

}
