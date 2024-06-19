package fr.ernicani.impincode

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.ernicani.impincode.utils.DatabaseManager
import fr.ernicani.impincode.utils.QuestionsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LessonActivity : AppCompatActivity() {

    private lateinit var textViewLessonTitle: TextView
    private lateinit var recyclerViewQuestions: RecyclerView
    private lateinit var questionsAdapter: QuestionsAdapter
    private lateinit var databaseManager: DatabaseManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        textViewLessonTitle = findViewById(R.id.textViewLessonTitle)
        recyclerViewQuestions = findViewById(R.id.recyclerViewQuestions)
        databaseManager = DatabaseManager(this, this)

        val lessonId = intent.getIntExtra("lesson_id", -1)
        val lessonTitle = intent.getStringExtra("lesson_title")
        textViewLessonTitle.text = lessonTitle

        recyclerViewQuestions.layoutManager = LinearLayoutManager(this)
        questionsAdapter = QuestionsAdapter(emptyList())
        recyclerViewQuestions.adapter = questionsAdapter

        fetchQuestionsAndAnswers(lessonId)
    }

    private fun fetchQuestionsAndAnswers(lessonId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val questions = databaseManager.getQuestions(lessonId)
            if (questions.isNotEmpty()) {
                for (question in questions) {
                    val answers = databaseManager.getAnswers(question.getId())
                    question.setAnswers(answers)
                }
                questionsAdapter.updateData(questions)
            }
        }
    }
}
