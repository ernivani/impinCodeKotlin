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
        this.id = id
        this.title = title
        this.description = description
    }

    constructor() {
        
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

    fun getDescription(): String {
        return description
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getSections(): List<Section> {
        return sections
    }

    fun setSections(sections: List<Section>) {
        this.sections = sections
    }

    override fun toString(): String {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", sections=" + sections +
                '}'
    }

}
