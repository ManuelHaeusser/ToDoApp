package de.dhbw.todo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Leitet die Startseite auf die Aufgabenübersicht um.
 */
@Controller
public class HomeController {

    /**
     * @return Weiterleitung auf die Aufgabenliste
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/tasks";
    }
}
