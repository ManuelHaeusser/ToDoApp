package de.dhbw.todo.domain;

/**
 * Prioritätsstufen einer Aufgabe.
 */
public enum Priority {

    LOW("Niedrig"),
    MEDIUM("Mittel"),
    HIGH("Hoch");

    private final String label;

    Priority(String label) {
        this.label = label;
    }

    /**
     * Liefert die deutschsprachige Anzeigebezeichnung.
     */
    public String getLabel() {
        return label;
    }
}
