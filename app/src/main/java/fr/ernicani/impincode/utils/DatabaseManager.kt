package fr.ernicani.impincode.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import fr.ernicani.impincode.MainActivity
import fr.ernicani.impincode.SelectCourseActivity
import fr.ernicani.impincode.entity.Course
import fr.ernicani.impincode.entity.Section
import fr.ernicani.impincode.entity.Unit
import fr.ernicani.impincode.entity.Lesson
import fr.ernicani.impincode.entity.Question
import fr.ernicani.impincode.entity.Answer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class DatabaseManager(private val context: Context, private val activity: Activity) {
    private val client = OkHttpClient()
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    private val API_URL = "https://code.impin.fr/api"

    suspend fun login(email: String, password: String): String? = withContext(Dispatchers.IO) {
        val url = "$API_URL/login"
        val payload = JSONObject().apply {
            put("username", email)
            put("password", password)
        }
        val body = payload.toString().toRequestBody()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            if (responseBody != null) {
                val json = JSONObject(responseBody)
                val token = json.getString("token")
                setLoggedUserToken(token)
                return@withContext token
            }
        }
        return@withContext null
    }

    suspend fun getCourses(): MutableList<Course>? = withContext(Dispatchers.IO) {
        
        if (!isUserLogged()) {
            logout()
            return@withContext null
        }

        val url = "$API_URL/courses"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val json = responseBody?.let { JSONObject(it) }
            val courses = json?.getJSONArray("courses")
            val coursesList = mutableListOf<Course>()
            if (courses != null) {
                for (i in 0 until courses.length()) {
                    val jsonCourse = courses.getJSONObject(i)
                    val id = jsonCourse.getInt("id")
                    val name = jsonCourse.getString("name")
                    val description = jsonCourse.getString("description")
                    val course = Course(id, name, description)
                    coursesList.add(course)
                }
            }
            return@withContext coursesList
        }
        return@withContext null
    }

    suspend fun getMyCourse(): Course? = withContext(Dispatchers.IO) {

        if (!isUserLogged()) {
            logout()
            return@withContext null
        }

        val url = "$API_URL/my-course"
        val payload = JSONObject().apply {
            put("token", sharedPreferences.getString("token", null))
        }

        val body = payload.toString().toRequestBody()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        // if the reponse is 401 it mean the token is not valid
        if (response.code == 401) {
            return@withContext null
        }
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val json = responseBody?.let { JSONObject(it) }
            if (json != null) {
                val id = json.getInt("id")
                val name = json.getString("name")
                val description = json.getString("description")
                return@withContext Course(id, name, description)
            }
        }
        return@withContext null
    }

    private fun isTokenValid(token: String): Boolean {
        val url = "$API_URL/check-token"
        val payload = JSONObject().apply {
            put("token", token)
        }
        val body = payload.toString().toRequestBody()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val json = responseBody?.let { JSONObject(it) }
            return json?.getBoolean("success") ?: false
        }
        return false
    }

    fun logout() {
        with(sharedPreferences.edit()) {
            remove("token")
            apply()
        }
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
        activity.finish()
    }

    fun isUserLogged(): Boolean {
        val token = sharedPreferences.getString("token", null)
        return token != null && isTokenValid(token)
    }

    private fun setLoggedUserToken(token: String) {
        with(sharedPreferences.edit()) {
            putString("token", token)
            apply()
        }
    }

    suspend fun getSections(courseId: Int): List<Section> = withContext(Dispatchers.IO) {
        val url = "$API_URL/course/$courseId/sections"
        val payload = JSONObject().apply {
            put("token", sharedPreferences.getString("token", null))
        }
        val body = payload.toString().toRequestBody()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val json = JSONObject(responseBody)
            val sections = json.getJSONArray("sections")
            val sectionsList = mutableListOf<Section>()
            for (i in 0 until sections.length()) {
                val jsonSection = sections.getJSONObject(i)
                val id = jsonSection.getInt("id")
                val title = jsonSection.getString("title")
                val section = Section(id, title)
                sectionsList.add(section)
            }
            return@withContext sectionsList
        }
        return@withContext emptyList<Section>()
    }

    suspend fun getUnits(sectionId: Int): List<Unit> = withContext(Dispatchers.IO) {
        val url = "$API_URL/section/$sectionId/units"
        val payload = JSONObject().apply {
            put("token", sharedPreferences.getString("token", null))
        }
        val body = payload.toString().toRequestBody()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val json = JSONObject(responseBody)
            val units = json.getJSONArray("units")
            val unitsList = mutableListOf<Unit>()
            for (i in 0 until units.length()) {
                val jsonUnit = units.getJSONObject(i)
                val id = jsonUnit.getInt("id")
                val title = jsonUnit.getString("title")
                val unit = Unit(id, title)
                unitsList.add(unit)
            }
            return@withContext unitsList
        }
        return@withContext emptyList<Unit>()
    }

    suspend fun getLessons(unitId: Int): List<Lesson> = withContext(Dispatchers.IO) {
        val url = "$API_URL/unit/$unitId/lessons"
        val payload = JSONObject().apply {
            put("token", sharedPreferences.getString("token", null))
        }
        val body = payload.toString().toRequestBody()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val json = JSONObject(responseBody)
            val lessons = json.getJSONArray("lessons")
            val lessonsList = mutableListOf<Lesson>()
            for (i in 0 until lessons.length()) {
                val jsonLesson = lessons.getJSONObject(i)
                val id = jsonLesson.getInt("id")
                val title = jsonLesson.getString("title")
                val lesson = Lesson(id, title)
                lessonsList.add(lesson)
            }
            return@withContext lessonsList
        }
        return@withContext emptyList<Lesson>()
    }

    suspend fun getQuestions(lessonId: Int): List<Question> = withContext(Dispatchers.IO) {
        val url = "$API_URL/lesson/$lessonId/questions"
        val payload = JSONObject().apply {
            put("token", sharedPreferences.getString("token", null))
        }
        val body = payload.toString().toRequestBody()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val json = JSONObject(responseBody)
            val questions = json.getJSONArray("questions")
            val questionsList = mutableListOf<Question>()
            for (i in 0 until questions.length()) {
                val jsonQuestion = questions.getJSONObject(i)
                val id = jsonQuestion.getInt("id")
                val title = jsonQuestion.getString("title")
                val content = jsonQuestion.getString("content")
                val question = Question(id, title, content)
                questionsList.add(question)
            }
            return@withContext questionsList
        }
        return@withContext emptyList<Question>()
    }

    suspend fun getAnswers(questionId: Int): List<Answer> = withContext(Dispatchers.IO) {
        val url = "$API_URL/question/$questionId/answers"
        val payload = JSONObject().apply {
            put("token", sharedPreferences.getString("token", null))
        }
        val body = payload.toString().toRequestBody()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val json = JSONObject(responseBody)
            val answers = json.getJSONArray("answers")
            val answersList = mutableListOf<Answer>()
            for (i in 0 until answers.length()) {
                val jsonAnswer = answers.getJSONObject(i)
                val id = jsonAnswer.getInt("id")
                val content = jsonAnswer.getString("content")
                val answer = Answer(id, content)
                answersList.add(answer)
            }
            return@withContext answersList
        }
        return@withContext emptyList<Answer>()
    }
}
