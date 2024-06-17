package fr.ernicani.impincode.entity

import lombok.Getter
import lombok.Setter

class Unit {

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
    private var  section: Section = Section()

    @Getter
    @Setter
    private var lessons: List<Lesson> = listOf()

    
    constructor() {}

    constructor(id: Int, title: String, section: Section, lessons: List<Lesson>) {
        this.id = id
        this.title = title
        this.section = section
        this.lessons = lessons
    }

    constructor(id: Int, title: String) {
        this.id = id
        this.title = title
    }
}
