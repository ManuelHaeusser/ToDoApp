package de.dhbw.todo.repository;

import de.dhbw.todo.domain.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring-Data-JPA-Repository für Category-Entitäten.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Liefert die Kategorien alphabetisch nach Namen sortiert.
     * @return sortierte Liste aller Kategorien
     */
    List<Category> findAllByOrderByNameAsc();
}
