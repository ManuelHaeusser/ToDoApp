package de.dhbw.todo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Einzelne Aufgabe, die genau einer Category zugeordnet ist.
 */
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority = Priority.MEDIUM;

    private LocalDate dueDate;

    @Column(nullable = false)
    private boolean completed = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /** Standardkonstruktor für JPA. */
    protected Task() {
    }

    /**
     * Erstellt eine neue Aufgabe.
     *  title = Titel der Aufgabe
     *  description = Beschreibung der Aufgabe
     *  priority = Priorität der Aufgabe
     *  dueDate = Fälligkeitsdatum (optional)
     *  category = zugeordnete Kategorie
     */
    public Task(String title, String description, Priority priority,
                LocalDate dueDate, Category category) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.category = category;
    }

    /** Setzt den Erstellungszeitpunkt automatisch beim ersten Speichern. */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
