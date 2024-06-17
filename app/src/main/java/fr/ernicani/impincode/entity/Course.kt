package fr.ernicani.impincode.entity

import lombok.Getter
import lombok.Setter


class Course {

    @Getter
    @Setter
    private var id: Int = 0

    @Getter
    @Setter
    private var title: String = ""

    @Getter
    @Setter
    private var description: String = ""

    @Getter
    @Setter
    private var sections: List<Section> = listOf()

    constructor(id: Int, title: String, description: String, sections: List<Section>) {
        this.id = id
        this.title = title
        this.description = description
        this.sections = sections
    }

    constructor(id: Int,title: String, description: String) {
        this.title = title
        this.description = description
        this.sections = sections
    }

    constructor() {
        
    }
}
