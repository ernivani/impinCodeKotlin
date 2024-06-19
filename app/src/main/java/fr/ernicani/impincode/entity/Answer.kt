package fr.ernicani.impincode.entity

import lombok.Getter
import lombok.Setter

class Answer {

    private var id: Int = 0

    private var content: String = ""

    private var question: Question = Question()

    private var isCorrect: Boolean = false

    constructor() {}

    constructor(id: Int, content: String, question: Question, isCorrect: Boolean) {
        this.id = id
        this.content = content
        this.question = question
        this.isCorrect = isCorrect
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

    fun getQuestion(): Question {
        return question
    }

    fun setQuestion(question: Question) {
        this.question = question
    }

    fun getIsCorrect(): Boolean {
        return isCorrect
    }

    fun setIsCorrect(isCorrect: Boolean) {
        this.isCorrect = isCorrect
    }

    override fun toString(): String {
        return "Answer(id=$id, content='$content', question=$question, isCorrect=$isCorrect)"
    }

    
}
