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
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import fr.ernicani.impincode.entity.Course
import fr.ernicani.impincode.utils.DatabaseManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class SelectCourseActivity : AppCompatActivity() {

    private lateinit var databaseManager: DatabaseManager
    private var courses: List<Course>? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_course)

        databaseManager = DatabaseManager(this,this)
        CoroutineScope(Dispatchers.Main).launch {
            courses = databaseManager.getCourses()
        }

        Log.d("Courses", courses.toString())

//        val textViewAppName =
//            findViewById<TextView>(R.id.textViewAppName)
//        val appName = getString(R.string.app_name)
//        val spannable = SpannableString(appName)
//        spannable.setSpan(
//            ForegroundColorSpan(Color.parseColor("#FFFFFF")),
//            0,
//            5,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//        spannable.setSpan(
//            ForegroundColorSpan(Color.parseColor("#9990FF")),
//            5,
//            appName.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//
//        textViewAppName.text = spannable

    }


}
