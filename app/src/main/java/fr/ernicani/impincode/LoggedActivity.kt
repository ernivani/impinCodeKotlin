package fr.ernicani.impincode

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.ernicani.impincode.entity.Course
import fr.ernicani.impincode.utils.DatabaseManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoggedActivity : AppCompatActivity() {

    private lateinit var buttonLogout: Button
    private lateinit var databaseManager: DatabaseManager

    private var myCourse: Course? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged)

        databaseManager = DatabaseManager(this,this)

            CoroutineScope(Dispatchers.Main).launch {
            myCourse = databaseManager.getMyCourse()

            if (myCourse == null) {
                val intent = Intent(this@LoggedActivity, SelectCourseActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val textViewAppName = findViewById<TextView>(R.id.textViewAppName)
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
            databaseManager.logout()
        }
    }
}
