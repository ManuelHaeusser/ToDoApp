package de.dhbw.todo.service;

/**
 * Wird geworfen, wenn eine angeforderte Entität nicht existiert.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Erstellt eine neue Ausnahme.
     * @param message beschreibende Fehlermeldung
     */
    public NotFoundException(String message) {
        super(message);
    }
}
