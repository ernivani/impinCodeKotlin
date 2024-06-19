package fr.ernicani.impincode

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.ernicani.impincode.entity.Course
import fr.ernicani.impincode.utils.CoursesAdapter
import fr.ernicani.impincode.utils.DatabaseManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoggedActivity : AppCompatActivity() {

    private lateinit var buttonLogout: Button
    private lateinit var databaseManager: DatabaseManager
    private lateinit var recyclerViewCourses: RecyclerView
    private lateinit var coursesAdapter: CoursesAdapter

    private var myCourse: Course? = null

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged)

        databaseManager = DatabaseManager(this, this)

        val textViewAppName = findViewById<TextView>(R.id.textViewAppName)
        val textViewCourseTitle = findViewById<TextView>(R.id.textViewCourseTitle)
        val textViewCourseDescription = findViewById<TextView>(R.id.textViewCourseDescription)
        buttonLogout = findViewById(R.id.buttonLogout)
        recyclerViewCourses = findViewById(R.id.recyclerViewCourses)

        buttonLogout.setOnClickListener {
            databaseManager.logout()
        }

        recyclerViewCourses.layoutManager = LinearLayoutManager(this)
        coursesAdapter = CoursesAdapter(emptyList(), this)
        recyclerViewCourses.adapter = coursesAdapter

        CoroutineScope(Dispatchers.Main).launch {
            myCourse = databaseManager.getMyCourse()
            Log.d("LoggedActivity", "My course: $myCourse")

            if (myCourse == null) {
                val intent = Intent(this@LoggedActivity, SelectCourseActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                myCourse = databaseManager.getCourseDetails(myCourse!!)
                textViewCourseTitle.text = myCourse?.getTitle()
                textViewCourseDescription.text = myCourse?.getDescription()
                // Populate the RecyclerView with course details
                val sections = myCourse?.getSections() ?: emptyList()
                Log.d("LoggedActivity", "Sections: $sections")
                coursesAdapter.updateData(sections)
            }
        }

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
    }
}
