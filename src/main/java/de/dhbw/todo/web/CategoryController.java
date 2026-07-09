package de.dhbw.todo.web;

import de.dhbw.todo.dto.CategoryForm;
import de.dhbw.todo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Web-Controller für die Verwaltung von Kategorien.
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * categoryService = Service für die Kategorielogik
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Zeigt die Liste aller Kategorien an.
     * model = View-Model
     * @return Name der Listen-View
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories/list";
    }

    /**
     * Zeigt das Formular zum Anlegen einer Kategorie.
     * model = View-Model
     * @return Name der Formular-View
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("categoryForm", CategoryForm.empty());
        model.addAttribute("editMode", false);
        return "categories/form";
    }

    /**
     * Verarbeitet das Anlegen einer Kategorie.
     *
     * form = Formulardaten
     * result = Validierungsergebnis
     * model = View-Model
     * redirectAttributes = Attribute für die Weiterleitung
     * @return View-Name oder Weiterleitung
     */
    @PostMapping
    public String create(@Valid @ModelAttribute("categoryForm") CategoryForm form,
                         BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("editMode", false);
            return "categories/form";
        }
        categoryService.create(form);
        redirectAttributes.addFlashAttribute("message", "Kategorie wurde angelegt.");
        return "redirect:/categories";
    }

    /**
     * Zeigt das Formular zum Bearbeiten einer Kategorie.
     *
     * id = ID der Kategorie
     * model = View-Model
     * @return Name der Formular-View
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("categoryForm", CategoryForm.from(categoryService.findById(id)));
        model.addAttribute("editMode", true);
        return "categories/form";
    }

    /**
     * Verarbeitet die Aktualisierung einer Kategorie.
     *
     * id = ID der Kategorie
     * form = Formulardaten
     * result = Validierungsergebnis
     * model = View-Model
     * redirectAttributes = Attribute für die Weiterleitung
     * @return View-Name oder Weiterleitung
     */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("categoryForm") CategoryForm form,
                         BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("editMode", true);
            return "categories/form";
        }
        categoryService.update(id, form);
        redirectAttributes.addFlashAttribute("message", "Kategorie wurde aktualisiert.");
        return "redirect:/categories";
    }

    /**
     * Löscht eine Kategorie inklusive ihrer Aufgaben.
     *
     * id = ID der Kategorie
     * redirectAttributes = Attribute für die Weiterleitung
     * @return Weiterleitung auf die Kategorienliste
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Kategorie wurde gelöscht.");
        return "redirect:/categories";
    }
}
