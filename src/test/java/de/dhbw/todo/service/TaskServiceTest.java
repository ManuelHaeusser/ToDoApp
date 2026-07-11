package de.dhbw.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.dhbw.todo.domain.Category;
import de.dhbw.todo.domain.Priority;
import de.dhbw.todo.domain.Task;
import de.dhbw.todo.dto.TaskForm;
import de.dhbw.todo.repository.TaskRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit-Tests für TaskService mit Mockito.
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private TaskService taskService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category("Studium", "#0d6efd", "Testkategorie");
    }

    @Test
    void createResolvesCategoryAndSavesTask() {
        TaskForm form = new TaskForm(null, "Lernen", "Klausur",
                Priority.HIGH, LocalDate.now(), 1L);
        when(categoryService.findById(1L)).thenReturn(category);
        when(taskRepository.save(any(Task.class))).thenAnswer(call -> call.getArgument(0));

        Task saved = taskService.create(form);

        assertThat(saved.getTitle()).isEqualTo("Lernen");
        assertThat(saved.getCategory()).isSameAs(category);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void toggleCompletedInvertsStatus() {
        Task task = new Task("Lernen", "Klausur", Priority.LOW, null, category);
        when(taskRepository.findById(5L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenAnswer(call -> call.getArgument(0));

        taskService.toggleCompleted(5L);

        assertThat(task.isCompleted()).isTrue();
    }

    @Test
    void findByIdThrowsWhenMissing() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findById(99L))
                .isInstanceOf(NotFoundException.class);
    }
}
