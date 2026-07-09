package de.dhbw.todo.dto;

import de.dhbw.todo.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Objekt zum Anlegen bzw. Bearbeiten einer Kategorie.
 * id =  ID (bei Neuanlage  null)
 * name = Name der Kategorie
 * color = Farbe als Hex-Wert
 * description = optionale Beschreibung
 */
public record CategoryForm(

        Long id,

        @NotBlank(message = "Name darf nicht leer sein")
        @Size(max = 100, message = "Name darf höchstens 100 Zeichen lang sein")
        String name,

        @NotBlank(message = "Bitte eine Farbe wählen")
        String color,

        @Size(max = 500, message = "Beschreibung darf höchstens 500 Zeichen lang sein")
        String description) {

    /**
     * Erzeugt ein leeres Formular mit sinnvoller Standardfarbe.
     * @return leeres Kategorieformular
     */
    public static CategoryForm empty() {
        return new CategoryForm(null, "", "#0d6efd", "");
    }

    /**
     * Erzeugt ein Formular aus einer bestehenden Kategorie.
     * category = Quellkategorie
     * @return befülltes Formular
     */
    public static CategoryForm from(Category category) {
        return new CategoryForm(category.getId(), category.getName(),
                category.getColor(), category.getDescription());
    }
}
