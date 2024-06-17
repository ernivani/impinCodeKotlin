package fr.ernicani.impincode.entity

import lombok.Getter
import lombok.Setter

class Lesson {

    @Getter
    @Setter
    private var id: Int = 0

    @Getter
    @Setter
    private var ordre: Int = 0

    @Getter
    @Setter
    private var title: String = ""

    @Getter
    @Setter
    private var questions: List<Question> = listOf()

    @Getter
    @Setter
    private var unit: Unit = Unit()

    @Getter
    @Setter
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
}
