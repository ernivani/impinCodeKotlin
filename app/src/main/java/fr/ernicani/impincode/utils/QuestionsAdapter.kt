package fr.ernicani.impincode.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.ernicani.impincode.R
import fr.ernicani.impincode.entity.Answer
import fr.ernicani.impincode.entity.Question

class QuestionsAdapter(private var questions: List<Question>) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.drawable.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount(): Int = questions.size

    fun updateData(newQuestions: List<Question>) {
        questions = newQuestions
        notifyDataSetChanged()
    }

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewQuestionContent: TextView = itemView.findViewById(R.id.textViewQuestionContent)
        private val recyclerViewAnswers: RecyclerView = itemView.findViewById(R.id.recyclerViewAnswers)

        fun bind(question: Question) {
            textViewQuestionContent.text = question.getContent()
            recyclerViewAnswers.layoutManager = LinearLayoutManager(itemView.context)
            recyclerViewAnswers.adapter = AnswersAdapter(question.getAnswers())
        }
    }
}
