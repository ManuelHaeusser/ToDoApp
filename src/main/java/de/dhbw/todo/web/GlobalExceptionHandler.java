package de.dhbw.todo.web;

import de.dhbw.todo.service.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Zentraler ExceptionHandler für alle Web-Controller.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Rendert eine Fehlerseite, wenn eine Entität nicht gefunden wurde.
     *
     * exception = die ausgelöste Ausnahme
     * model = View-Model der Fehlerseite
     * return Name der Fehler-View
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NotFoundException exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }
}
