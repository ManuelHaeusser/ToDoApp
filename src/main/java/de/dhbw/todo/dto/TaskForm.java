package de.dhbw.todo.dto;

import de.dhbw.todo.domain.Priority;
import de.dhbw.todo.domain.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Datentransfer- und Formularobjekt zum Anlegen bzw. Bearbeiten einer Aufgabe.
 * id = technische ID
 * title = Titel der Aufgabe
 * description = Beschreibung der Aufgabe
 * priority = Priorität
 * dueDate = Fälligkeitsdatum (optional)
 * categoryId =  ID der zugeordneten Kategorie
 */
public record TaskForm(

        Long id,

        @NotBlank(message = "Titel darf nicht leer sein")
        @Size(max = 150, message = "Titel darf höchstens 150 Zeichen lang sein")
        String title,

        @Size(max = 1000, message = "Beschreibung darf höchstens 1000 Zeichen lang sein")
        String description,

        @NotNull(message = "Bitte eine Priorität wählen")
        Priority priority,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dueDate,

        @NotNull(message = "Bitte eine Kategorie wählen")
        Long categoryId) {

    /**
     * Erzeugt ein leeres Formular mit Standardpriorität MEDIUM.
     * @return leeres Aufgabenformular
     */
    public static TaskForm empty() {
        return new TaskForm(null, "", "", Priority.MEDIUM, null, null);
    }

    /**
     * Erzeugt ein Formular aus einer bestehenden Aufgabe.
     * task = Quellaufgabe
     * @return befülltes Formular
     */
    public static TaskForm from(Task task) {
        return new TaskForm(task.getId(), task.getTitle(), task.getDescription(),
                task.getPriority(), task.getDueDate(), task.getCategory().getId());
    }
}
