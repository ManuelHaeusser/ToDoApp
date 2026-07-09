package de.dhbw.todo.service;

import de.dhbw.todo.domain.Category;
import de.dhbw.todo.dto.CategoryForm;
import de.dhbw.todo.repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Geschäftslogik für Kategorien mit CRUD-Operationen.
 */
@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * categoryRepository = Repository für Kategorien
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * @return alle Kategorien, alphabetisch sortiert
     */
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAllByOrderByNameAsc();
    }

    /**
     *id = ID der gesuchten Kategorie
     * @return die gefundene Kategorie
     * @throws NotFoundException falls keine Kategorie mit der ID existiert
     */
    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Kategorie mit ID " + id + " wurde nicht gefunden."));
    }

    /**
     * Legt eine neue Kategorie an.
     * form = Formulardaten
     * @return die gespeicherte Kategorie
     */
    public Category create(CategoryForm form) {
        Category category = new Category(form.name(), form.color(), form.description());
        return categoryRepository.save(category);
    }

    /**
     * Aktualisiert eine bestehende Kategorie.
     * id = ID der zu ändernden Kategorie
     * form = neue Formulardaten
     * @return die aktualisierte Kategorie
     */
    public Category update(Long id, CategoryForm form) {
        Category category = findById(id);
        category.setName(form.name());
        category.setColor(form.color());
        category.setDescription(form.description());
        return categoryRepository.save(category);
    }

    /**
     * Löscht eine Kategorie inklusive ihrer Aufgaben
     * id = ID der zu löschenden Kategorie
     */
    public void delete(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}
