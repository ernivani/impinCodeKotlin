package fr.ernicani.impincode.entity

import lombok.Getter
import lombok.Setter

class Unit {

    private var id: Int = 0

    private var ordre: Int = 0

    private var title: String = ""

    private var  section: Section = Section()

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

    fun getSection(): Section {
        return section
    }

    fun setSection(section: Section) {
        this.section = section
    }

    fun getLessons(): List<Lesson> {
        return lessons
    }

    fun setLessons(lessons: List<Lesson>) {
        this.lessons = lessons
    }

    override fun toString(): String {
        return "Unit{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", section=" + section +
                ", lessons=" + lessons +
                '}'
    }
}
