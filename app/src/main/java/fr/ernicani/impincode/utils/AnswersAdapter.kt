package fr.ernicani.impincode.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.ernicani.impincode.R
import fr.ernicani.impincode.entity.Answer

class AnswersAdapter(private var answers: List<Answer>) : RecyclerView.Adapter<AnswersAdapter.AnswerViewHolder>() {

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.drawable.item_answer, parent, false)
        return AnswerViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bind(answers[position])
    }

    override fun getItemCount(): Int = answers.size

    fun updateData(newAnswers: List<Answer>) {
        answers = newAnswers
        notifyDataSetChanged()
    }

    class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewAnswerContent: TextView = itemView.findViewById(R.id.textViewAnswerContent)

        fun bind(answer: Answer) {
            textViewAnswerContent.text = answer.getContent()
        }
    }
}