package ru.otus.spring.library.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.domain.Comment;
import ru.otus.spring.library.repository.JpaRepositoryException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName(value = "Тесты jpa репозитория для работы с комментариями")
@DataJpaTest
@Import(CommentDaoJpa.class)
class CommentDaoJpaTest {
    private static final long EXISTING_COMMENT_ID = 1L;
    private static final String EXISTING_COMMENT_TEXT = "comment_01";
    private static final long EXISTING_COMMENT_DATE_TIME = 1573318800000L;
    private static final long EXISTING_BOOK_ID = 1L;
    private static final String ID = "id";
    private static final String TEXT = "text";
    @Autowired
    CommentDaoJpa commentDaoJpa;

    @Autowired
    private TestEntityManager tem;

    @Test
    @DisplayName("Должен получать существующий комментарий")
    void getCommentById() throws JpaRepositoryException {
        Comment comment = commentDaoJpa.getCommentById(EXISTING_COMMENT_ID);
        assertEquals(comment.getId(), EXISTING_COMMENT_ID);
        assertEquals(comment.getText(), EXISTING_COMMENT_TEXT);
        assertEquals(comment.getDate().getTime(), EXISTING_COMMENT_DATE_TIME);
    }

    @Test
    @DisplayName("Должен получать существующий комментарий вместе с книгой")
    void getCommentByIdWithBook() throws JpaRepositoryException {
        Comment comment = commentDaoJpa.getCommentByIdWithBook(EXISTING_COMMENT_ID);
        assertThat(comment)
                .hasFieldOrPropertyWithValue(ID, EXISTING_COMMENT_ID)
                .hasFieldOrPropertyWithValue(TEXT, EXISTING_COMMENT_TEXT);
        assertEquals(comment.getDate().getTime(), EXISTING_COMMENT_DATE_TIME);

        Book book = comment.getBook();
        Book expectedBook = tem.find(Book.class, EXISTING_BOOK_ID);
        assertEquals(book.getId(), expectedBook.getId());
        assertEquals(book.getTitle(), expectedBook.getTitle());

        assertThat(book.getAuthors()).hasSize(2).contains(expectedBook.getAuthors().get(0), expectedBook.getAuthors().get(1));
    }
}