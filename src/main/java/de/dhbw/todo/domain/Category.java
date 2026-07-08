package de.dhbw.todo.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Kategorie, der beliebig viele Aufgaben zugeordnet werden können.
 */
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Name ist Pflicht
    private String name;

    @Column(nullable = false) // Farbe ist Pflicht
    private String color;

    @Column(length = 500)  // VARCHAR(500) statt Default 255, optional (darf null sein)
    private String description;

    /**
     * Alle Aufgaben dieser Kategorie. Beim Löschen der Kategorie werden
     * die zugehörigen Aufgaben mitgelöscht.
     * orphanRemoval = true: wird ein Task aus der Liste entfernt, wird er aus der DB gelöscht.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    /** Standardkonstruktor für JPA. */
    protected Category() {
    }

    /**
     * Erstellt eine neue Kategorie.
     *
     * @param name        Name der Kategorie
     * @param color       Farbe als Hex-Wert
     * @param description optionale Beschreibung
     */
    public Category(String name, String color, String description) {
        this.name = name;
        this.color = color;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
