package fr.ernicani.impincode.entity

class Section {

    private var id: Int = 0

    private var title: String = ""

    private var  course: Course = Course()

    private var units: List<Unit> = listOf()

    constructor() {}

    constructor(id: Int, title: String, course: Course, units: List<Unit>) {
        this.id = id
        this.title = title
        this.course = course
        this.units = units
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

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getCourse(): Course {
        return course
    }

    fun setCourse(course: Course) {
        this.course = course
    }

    fun getUnits(): List<Unit> {
        return units
    }

    fun setUnits(units: List<Unit>) {
        this.units = units
    }

    override fun toString(): String {
        return "Section{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", course=" + course +
                ", units=" + units +
                '}'
    }

}
