package com.example.bookstore.repository;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByTitle(String title);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Book b WHERE b.author.id=?1")
    public int deleteByAuthorIdentifier(Long id);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Book b WHERE b.author.id IN ?1")
    public int deleteBulkByAuthorIdentifier(List<Long> id);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Book b WHERE b.author IN ?1")
    public int deleteBulkByAuthors(List<Author> authors);
}
