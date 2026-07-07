package de.dhbw.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Einstiegspunkt der TODO-Webanwendung.
 */
@SpringBootApplication
public class TodoApplication {

    /**
     * Startet die Spring-Boot-Anwendung.
     *
     * @param args Kommandozeilenargumente
     */
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }
}
