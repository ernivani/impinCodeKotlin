package fr.ernicani.impincode.entity

import lombok.Getter
import lombok.Setter

class Question {

    private var id: Int = 0

    private var content: String = ""

    private var lesson: Lesson = Lesson()

    private var answers: List<Answer> = ArrayList()
    
    constructor() {}

    constructor(id: Int, content: String, lesson: Lesson, answer: List<Answer>) {
        this.id = id
        this.content = content
        this.lesson = lesson
        this.answers = answer
    }

    constructor(id: Int, content: String) {
        this.id = id
        this.content = content
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getContent(): String {
        return content
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun getLesson(): Lesson {
        return lesson
    }

    fun setLesson(lesson: Lesson) {
        this.lesson = lesson
    }

    fun getAnswers(): List<Answer> {
        return answers
    }

    fun setAnswers(answers: List<Answer>) {
        this.answers = answers
    }


    override fun toString(): String {
        return "Question{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", lesson=" + lesson +
                ", answers=" + answers +
                '}'
    }

}
