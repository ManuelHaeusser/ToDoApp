package de.dhbw.todo.config;

import de.dhbw.todo.domain.Category;
import de.dhbw.todo.domain.Priority;
import de.dhbw.todo.domain.Task;
import de.dhbw.todo.repository.CategoryRepository;
import de.dhbw.todo.repository.TaskRepository;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Legt beim ersten Start Beispieldaten an, sofern die Datenbank leer ist.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;

    /**
     * categoryRepository = Repository für Kategorien
     * taskRepository = Repository für Aufgaben
     */
    public DataSeeder(CategoryRepository categoryRepository, TaskRepository taskRepository) {
        this.categoryRepository = categoryRepository;
        this.taskRepository = taskRepository;
    }

    /**
     * Erstellt Standardkategorien und Beispielaufgaben bei leerer Datenbank.
     *
     * args = Startargumente (nicht verwendet)
     */
    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) {
            return;
        }
        Category studium = categoryRepository.save(
                new Category("Studium", "#0d6efd", "Aufgaben rund um das DHBW-Studium"));
        Category privat = categoryRepository.save(
                new Category("Privat", "#198754", "Persönliche Erledigungen"));

        taskRepository.save(new Task("Portfolio abgeben", "Portfolio hochladen",
                Priority.HIGH, LocalDate.now().plusDays(3), studium));
        taskRepository.save(new Task("Wocheneinkauf", "Einkaufsliste abarbeiten",
                Priority.LOW, LocalDate.now().plusDays(1), privat));
    }
}
