package fr.ernicani.impincode.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.ernicani.impincode.LessonActivity
import fr.ernicani.impincode.R
import fr.ernicani.impincode.entity.Lesson
import fr.ernicani.impincode.entity.Section
import fr.ernicani.impincode.entity.Unit

class CoursesAdapter(private var sections: List<Section>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SECTION = 0
        private const val VIEW_TYPE_UNIT = 1
        private const val VIEW_TYPE_LESSON = 2
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item) {
            is Section -> VIEW_TYPE_SECTION
            is Unit -> VIEW_TYPE_UNIT
            is Lesson -> VIEW_TYPE_LESSON
            else -> throw IllegalArgumentException("Unknown item type at position $position")
        }
    }

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SECTION -> SectionViewHolder(LayoutInflater.from(parent.context).inflate(R.drawable.item_section, parent, false))
            VIEW_TYPE_UNIT -> UnitViewHolder(LayoutInflater.from(parent.context).inflate(R.drawable.item_unit, parent, false))
            VIEW_TYPE_LESSON -> LessonViewHolder(LayoutInflater.from(parent.context).inflate(R.drawable.item_lesson, parent, false))
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is SectionViewHolder -> holder.bind(item as Section)
            is UnitViewHolder -> holder.bind(item as Unit)
            is LessonViewHolder -> holder.bind(item as Lesson, context)
        }
    }

    override fun getItemCount(): Int = sections.sumOf { 1 + it.getUnits().size + it.getUnits().sumOf { unit -> unit.getLessons().size } }

    private fun getItem(position: Int): Any {
        var index = position
        for (section in sections) {
            if (index == 0) return section
            index--
            for (unit in section.getUnits()) {
                if (index == 0) return unit
                index--
                for (lesson in unit.getLessons()) {
                    if (index == 0) return lesson
                    index--
                }
            }
        }
        throw IllegalArgumentException("Invalid position $position")
    }

    fun updateData(newSections: List<Section>) {
        sections = newSections
        notifyDataSetChanged()
    }

    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewSectionTitle)

        fun bind(section: Section) {
            titleTextView.text = section.getTitle()
        }
    }

    class UnitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewUnitTitle)

        fun bind(unit: Unit) {
            titleTextView.text = unit.getTitle()
        }
    }

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewLessonTitle)

        fun bind(lesson: Lesson, context: Context) {
            titleTextView.text = lesson.getTitle()
            itemView.setOnClickListener {
                val intent = Intent(context, LessonActivity::class.java).apply {
                    putExtra("lesson_id", lesson.getId())
                    putExtra("lesson_title", lesson.getTitle())
                }
                context.startActivity(intent)
            }
        }
    }

}
