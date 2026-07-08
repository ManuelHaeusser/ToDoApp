package de.dhbw.todo.repository;

import de.dhbw.todo.domain.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring-Data-JPA-Repository für Task-Entitäten.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * @return alle Aufgaben, aufsteigend nach Fälligkeitsdatum sortiert
     */
    List<Task> findAllByOrderByDueDateAsc();

    /**
     * @param categoryId ID der Kategorie
     * @return Aufgaben der Kategorie, aufsteigend nach Fälligkeitsdatum
     */
    List<Task> findByCategoryIdOrderByDueDateAsc(Long categoryId);

    /**
     * @param completed Erledigt-Status
     * @return Aufgaben mit dem gewünschten Status, nach Fälligkeitsdatum
     */
    List<Task> findByCompletedOrderByDueDateAsc(boolean completed);
}
