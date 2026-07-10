package de.dhbw.todo.service;

import de.dhbw.todo.domain.Category;
import de.dhbw.todo.domain.Task;
import de.dhbw.todo.dto.TaskForm;
import de.dhbw.todo.repository.TaskRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Geschäftslogik für Aufgaben mit CRUD-Operationen.
 */
@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryService categoryService;

    /**
     * taskRepository = Repository für Aufgaben
     * categoryService = Service zum Auflösen der Kategorie
     */
    public TaskService(TaskRepository taskRepository, CategoryService categoryService) {
        this.taskRepository = taskRepository;
        this.categoryService = categoryService;
    }

    /**
     * return alle Aufgaben, nach Fälligkeitsdatum sortiert
     */
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        return taskRepository.findAllByOrderByDueDateAsc();
    }

    /**
     * categoryId = ID der Kategorie
     * return Aufgaben der angegebenen Kategorie
     */
    @Transactional(readOnly = true)
    public List<Task> findByCategory(Long categoryId) {
        return taskRepository.findByCategoryIdOrderByDueDateAsc(categoryId);
    }

    /**
     * completed = gewünschter Erledigt-Status
     * @return Aufgaben mit dem angegebenen Status
     */
    @Transactional(readOnly = true)
    public List<Task> findByCompleted(boolean completed) {
        return taskRepository.findByCompletedOrderByDueDateAsc(completed);
    }

    /**
     * id = ID der gesuchten Aufgabe
     * return die gefundene Aufgabe
     * throws NotFoundException falls keine Aufgabe mit der ID existiert
     */
    @Transactional(readOnly = true)
    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Aufgabe mit ID " + id + " wurde nicht gefunden."));
    }

    /**
     * Legt eine neue Aufgabe an.
     *
     * form = Formulardaten
     * return die gespeicherte Aufgabe
     */
    public Task create(TaskForm form) {
        Category category = categoryService.findById(form.categoryId());
        Task task = new Task(form.title(), form.description(),
                form.priority(), form.dueDate(), category);
        return taskRepository.save(task);
    }

    /**
     * Aktualisiert eine bestehende Aufgabe. Der Erledigt-Status bleibt erhalten.
     *
     * id = ID der zu ändernden Aufgabe
     * form = neue Formulardaten
     * return die aktualisierte Aufgabe
     */
    public Task update(Long id, TaskForm form) {
        Task task = findById(id);
        Category category = categoryService.findById(form.categoryId());
        task.setTitle(form.title());
        task.setDescription(form.description());
        task.setPriority(form.priority());
        task.setDueDate(form.dueDate());
        task.setCategory(category);
        return taskRepository.save(task);
    }

    /**
     * Schaltet den Erledigt-Status einer Aufgabe um.
     * id = ID der Aufgabe
     */
    public void toggleCompleted(Long id) {
        Task task = findById(id);
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    /**
     * Löscht eine Aufgabe.
     * id = ID der zu löschenden Aufgabe
     */
    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }
}
