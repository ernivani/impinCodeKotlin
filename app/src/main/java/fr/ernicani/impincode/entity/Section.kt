package fr.ernicani.impincode.entity

import lombok.Getter
import lombok.Setter

class Section {

    @Getter
    @Setter
    private var id: Int = 0

    @Getter
    @Setter
    private var title: String = ""

    @Getter
    @Setter
    private var  course: Course = Course()

    @Getter
    @Setter
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
    
}
