package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookstoreService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void insertAuthorWithBooks() {

        Author author = new Author();
        author.setName("Alicia Tom");
        author.setAge(38);
        author.setGenre("Anthology");

        Book book = new Book();
        book.setIsbn("001-AT");
        book.setTitle("The book of swords");

        author.addBook(book); // use addBook() helper

        authorRepository.save(author);
    }

    @Transactional
    public void deleteBookOfAuthor() {

        Author author = authorRepository.fetchByName("Alicia Tom");
        Book book = author.getBooks().get(0);

        author.removeBook(book); // use removeBook() helper
    }

    // not efficient
    @Transactional
    public void deleteViaCascadeRemove() {
        Author author = authorRepository.findByName("Joana Nimar");

        authorRepository.delete(author);
    }

    // not efficient
    @Transactional
    public void deleteViaOrphanRemoval() {
        Author author = authorRepository.findByNameWithBooks("Joana Nimar");

        author.removeBooks();
        authorRepository.delete(author);
    }

    // One Author is loaded in the Persistent Context
    @Transactional
    public void deleteViaIdentifiers() {
        Author author = authorRepository.findByName("Joana Nimar");

        bookRepository.deleteByAuthorIdentifier(author.getId());
        authorRepository.deleteByIdentifier(author.getId());
        // authorRepository.deleteInBatch(List.of(author));
    }

    // One Author and the associated Book are in Persistent Context
    @Transactional
    public void deleteViaIdentifiersX() {
        Author author = authorRepository.findByNameWithBooks("Joana Nimar");

        bookRepository.deleteByAuthorIdentifier(author.getId());
        authorRepository.deleteByIdentifier(author.getId());
    }

    // More authors are loaded in the Persistent Context
    @Transactional
    public void deleteViaBulkIn() {
        List<Author> authors = authorRepository.findByAge(34);

        bookRepository.deleteBulkByAuthors(authors);
        authorRepository.deleteAllInBatch(authors);
    }

    // More Author and the associated Book are in Persistent Context
    @Transactional
    public void deleteViaBulkInX() {
        List<Author> authors = authorRepository.findByGenreWithBooks("Anthology");

        bookRepository.deleteBulkByAuthors(authors);
        authorRepository.deleteAllInBatch(authors);
    }

    // No Author or Book that should be deleted are loaded in the Persistent Context
    @Transactional
    public void deleteViaHardCodedIdentifiers() {
        bookRepository.deleteByAuthorIdentifier(4L);
        authorRepository.deleteByIdentifier(4L);
    }

    // No Author or Book that should be deleted are loaded in the Persistent Context
    @Transactional
    public void deleteViaBulkHardCodedIdentifiers() {
        List<Long> authorsIds = Arrays.asList(1L, 4L);

        bookRepository.deleteBulkByAuthorIdentifier(authorsIds);
        authorRepository.deleteBulkByIdentifier(authorsIds);
    }



}
