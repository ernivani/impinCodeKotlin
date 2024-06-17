package fr.ernicani.impincode.entity

import lombok.Getter
import lombok.Setter

class Answer {

    @Getter
    @Setter
    private var id: Int = 0

    @Getter
    @Setter
    private var content: String = ""

    @Getter
    @Setter
    private var question: Question = Question()

    @Getter
    @Setter
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
    
}
