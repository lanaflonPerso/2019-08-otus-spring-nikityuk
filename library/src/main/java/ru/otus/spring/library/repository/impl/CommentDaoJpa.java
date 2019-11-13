package ru.otus.spring.library.repository.impl;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import ru.otus.spring.library.domain.Comment;
import ru.otus.spring.library.repository.CommentDao;
import ru.otus.spring.library.repository.JpaRepositoryException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class CommentDaoJpa implements CommentDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment saveComment(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Comment getCommentById(long commentId) throws JpaRepositoryException {
        return Optional.ofNullable(em.find(Comment.class, commentId))
                .orElseThrow(() -> new JpaRepositoryException("Returned comment is null"));
    }

    @Override
    public Comment getCommentByIdWithBook(long commentId) throws JpaRepositoryException {
        Comment comment = this.getCommentById(commentId);
        Hibernate.initialize(comment.getBook());
        return comment;
    }

    @Override
    public void deleteCommentById(long commentId) throws JpaRepositoryException {
        em.remove(this.getCommentById(commentId));
    }

    @Override
    public List<Comment> getAllCommentsForBook(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }
}