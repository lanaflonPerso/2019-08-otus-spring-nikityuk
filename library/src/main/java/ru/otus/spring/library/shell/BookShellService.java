package ru.otus.spring.library.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.library.service.BookService;
import ru.otus.spring.library.service.LibraryServiceException;
import ru.otus.spring.library.service.MessageService;

@ShellComponent
public class BookShellService {
    private final BookService bookService;
    private final MessageService ms;

    public BookShellService(BookService bookService, MessageService ms) {
        this.bookService = bookService;
        this.ms = ms;
    }

    @ShellMethod(value = "Add new book", key = {"ab", "addBook"})
    public void addBook(String title, String authorName) {
        ms.printMessage("Book added: " + bookService.addBook(title, authorName));
    }

    @ShellMethod(value = "Update existing book", key = {"ub", "updateBook"})
    public void updateBook(String id, String title, String authorName) {
        try {
            ms.printMessage("Book updated: " + bookService.updateBook(id, title, authorName));
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Get book by id", key = {"gb", "getBook"})
    public void getBookById(String id) {
        try {
            ms.printMessage("Returned book: " + bookService.getBookById(id));
        } catch (LibraryServiceException e) {
            ms.printMessage(e.getMessage());
        }
    }

    @ShellMethod(value = "Delete book by id", key = {"db", "deleteBook"})
    public void deleteBookById(String id) {
        bookService.deleteBookById(id);
        ms.printMessage("Book " + id + " is deleted");
    }

    @ShellMethod(value = "Get all books", key = {"gab", "getAllBooks"})
    public void getAllBooks() {
        ms.printMessage("All books: " + bookService.getAllBooks());
    }
}
