package fr.ernicani.impincode.entity

import lombok.Getter
import lombok.Setter

class Question {

    @Getter
    @Setter
    private var id: Int = 0

    @Getter
    @Setter
    private var content: String = ""

    @Getter
    @Setter
    private var lesson: Lesson = Lesson()

    @Getter
    @Setter
    private var answer: Answer = Answer()
    
    constructor() {}

    constructor(id: Int, content: String, lesson: Lesson, answer: Answer) {
        this.id = id
        this.content = content
        this.lesson = lesson
        this.answer = answer
    }

    constructor(id: Int, title: String, content: String) {
        this.id = id
        this.content = content
    }
    
}
