# TODO-Webanwendung

Eine To-Do-Verwaltung mit **Java 17**, **Maven**, **Spring Boot 3.3**,
**Spring Data JPA**, **H2** (dateibasiert) und **Thymeleaf**.

## Starten

```bash
./mvnw spring-boot:run        # Linux / macOS
mvnw.cmd spring-boot:run      # Windows
```

Die Anwendung ist danach erreichbar unter:

- Anwendung:   http://localhost:8080
- H2-Konsole:  http://localhost:8080/h2-console

**H2-Konsole – Zugangsdaten:**

| Feld     | Wert                          |
|----------|-------------------------------|
| JDBC URL | `jdbc:h2:file:./data/tododb`  |
| Benutzer | `sa`                          |
| Passwort | *(leer)*                      |

Die Daten werden dateibasiert unter `./data/tododb.mv.db` gespeichert und
bleiben nach einem Neustart erhalten.

## Tests

```bash
./mvnw test
```

## Datenmodell

Zwei Tabellen mit einer 1:n-Beziehung (eine Kategorie *hat* 0..* Aufgaben):

- **Category** (`id`, `name`, `color`, `description`)
- **Task** (`id`, `title`, `description`, `priority`, `dueDate`,
  `completed`, `createdAt`, `category_id` → FK)

## Funktionsumfang (CRUD)

- Aufgaben und Kategorien anlegen, anzeigen, bearbeiten und löschen
- Aufgaben als erledigt / offen umschalten
- Filter nach Status (alle / offen / erledigt) und nach Kategorie
- Validierung der Formulareingaben
- Beispieldaten beim ersten Start

## Projektstruktur

```
src/main/java/de/dhbw/todo
├── TodoApplication.java        # Einstiegspunkt
├── domain/                     # JPA-Entitäten (Category, Task, Priority)
├── repository/                 # Spring-Data-JPA-Repositories
├── dto/                        # Records für Formular-/Transferobjekte
├── service/                    # Geschäftslogik (CRUD)
├── web/                        # MVC-Controller + Exception-Handling
└── config/                     # Beispieldaten-Seeder
```

## Umgesetzte Code-Vorgaben

- JavaDoc und Kommentare
- Keine Wildcard-Imports
- Methoden ≤ 50 Zeilen, Zeilen ≤ 120 Zeichen
- Sinnvolle Paketstruktur
- `@Override` verwendet, keine leeren `catch`-Blöcke
- Records für einfache Datentransferklassen (`TaskForm`, `CategoryForm`)
- Kein Lombok

> Hinweis: Der Maven-Wrapper nutzt den Distributionstyp `only-script`.
> Beim ersten Aufruf von `./mvnw` wird Maven automatisch heruntergeladen.
