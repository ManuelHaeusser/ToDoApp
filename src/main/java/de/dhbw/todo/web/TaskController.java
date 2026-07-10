package de.dhbw.todo.web;

import de.dhbw.todo.domain.Priority;
import de.dhbw.todo.domain.Task;
import de.dhbw.todo.dto.TaskForm;
import de.dhbw.todo.service.CategoryService;
import de.dhbw.todo.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Web-Controller für die Verwaltung von Aufgaben.
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final CategoryService categoryService;

    /**
     * taskService = Service für die Aufgabenlogik
     * categoryService = Service für die Kategorienauswahl
     */
    public TaskController(TaskService taskService, CategoryService categoryService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
    }

    /**
     * Zeigt die Aufgabenliste, optional gefiltert nach Kategorie oder Status.
     * categoryId = optionaler Kategoriefilter
     * status = optionaler Statusfilter ({@code open} oder {@code done})
     * model = View-Model
     * return Name der Listen-View
     */
    @GetMapping
    public String list(@RequestParam(required = false) Long categoryId,
                       @RequestParam(required = false) String status, Model model) {
        model.addAttribute("tasks", resolveTasks(categoryId, status));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("selectedStatus", status);
        return "tasks/list";
    }

    /**
     * Wählt die passende Aufgabenliste anhand der aktiven Filter.
     * categoryId = Kategoriefilter (kann null sein)
     * status = Statusfilter (kann null sein)
     * return gefilterte Aufgabenliste
     */
    private List<Task> resolveTasks(Long categoryId, String status) {
        if (categoryId != null) {
            return taskService.findByCategory(categoryId);
        }
        if ("open".equals(status)) {
            return taskService.findByCompleted(false);
        }
        if ("done".equals(status)) {
            return taskService.findByCompleted(true);
        }
        return taskService.findAll();
    }

    /**
     * Zeigt das Formular zum Anlegen einer Aufgabe.
     * model = View-Model
     * return Name der Formular-View
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        prepareForm(model, TaskForm.empty(), false);
        return "tasks/form";
    }

    /**
     * Verarbeitet das Anlegen einer Aufgabe.
     * form = Formulardaten
     * result = Validierungsergebnis
     * model = View-Model
     * redirectAttributes = Attribute für die Weiterleitung
     * return View-Name oder Weiterleitung
     */
    @PostMapping
    public String create(@Valid @ModelAttribute("taskForm") TaskForm form,
                         BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            prepareForm(model, form, false);
            return "tasks/form";
        }
        taskService.create(form);
        redirectAttributes.addFlashAttribute("message", "Aufgabe wurde angelegt.");
        return "redirect:/tasks";
    }

    /**
     * Zeigt das Formular zum Bearbeiten einer Aufgabe.
     * id = ID der Aufgabe
     * model = View-Model
     * return Name der Formular-View
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        prepareForm(model, TaskForm.from(taskService.findById(id)), true);
        return "tasks/form";
    }

    /**
     * Verarbeitet die Aktualisierung einer Aufgabe.
     * id = ID der Aufgabe
     * form = Formulardaten
     * result = Validierungsergebnis
     * model = View-Model
     * redirectAttributes = Attribute für die Weiterleitung
     * return View-Name oder Weiterleitung
     */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("taskForm") TaskForm form,
                         BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            prepareForm(model, form, true);
            return "tasks/form";
        }
        taskService.update(id, form);
        redirectAttributes.addFlashAttribute("message", "Aufgabe wurde aktualisiert.");
        return "redirect:/tasks";
    }

    /**
     * Schaltet den Erledigt-Status einer Aufgabe um.
     * id = ID der Aufgabe
     * return Weiterleitung auf die Aufgabenliste
     */
    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id) {
        taskService.toggleCompleted(id);
        return "redirect:/tasks";
    }

    /**
     * Löscht eine Aufgabe.
     * id = ID der Aufgabe
     * redirectAttributes = Attribute für die Weiterleitung
     * return Weiterleitung auf die Aufgabenliste
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        taskService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Aufgabe wurde gelöscht.");
        return "redirect:/tasks";
    }

    /**
     * Befüllt das Model mit allen für das Aufgabenformular nötigen Daten.
     * model = View-Model
     * form = anzuzeigendes Formular
     * editMode = true im Bearbeiten-Modus
     */
    private void prepareForm(Model model, TaskForm form, boolean editMode) {
        model.addAttribute("taskForm", form);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("editMode", editMode);
    }
}
