package fr.ernicani.impincode.entity

import lombok.Getter
import lombok.Setter

class Lesson {

    private var id: Int = 0

    private var ordre: Int = 0

    private var title: String = ""

    private var questions: List<Question> = listOf()

    private var unit: Unit = Unit()

    private var completion: Int = 0

    
    constructor() {}

    constructor(id: Int, title: String, unit: Unit, questions: List<Question>) {
        this.id = id
        this.title = title
        this.unit = unit
        this.questions = questions
    }

    constructor(id: Int, title: String) {
        this.id = id
        this.title = title
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getOrdre(): Int {
        return ordre
    }

    fun setOrdre(ordre: Int) {
        this.ordre = ordre
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getQuestions(): List<Question> {
        return questions
    }

    fun setQuestions(questions: List<Question>) {
        this.questions = questions
    }

    fun getUnit(): Unit {
        return unit
    }

    fun setUnit(unit: Unit) {
        this.unit = unit
    }

    fun getCompletion(): Int {
        return completion
    }

    fun setCompletion(completion: Int) {
        this.completion = completion
    }

    override fun toString(): String {
        return "Lesson{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", unit=" + unit +
                ", questions=" + questions +
                '}'
    }


}
