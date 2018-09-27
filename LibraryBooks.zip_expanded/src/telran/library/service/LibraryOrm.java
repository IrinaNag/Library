package telran.library.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.library.dao.AuthorsRepository;
import telran.library.dao.BooksRepository;
import telran.library.dao.ReadersRepository;
import telran.library.dao.RecordsRepository;
import telran.library.dto.AuthorDto;
import telran.library.dto.BookDto;
import telran.library.dto.LibraryReturnCode;
import telran.library.dto.ReaderDto;
import telran.library.entities.Author;
import telran.library.entities.Book;
import telran.library.entities.Reader;
import telran.library.entities.Record;

@Service
public class LibraryOrm implements ILibrary {
	@Autowired
	AuthorsRepository authorsRepositiry;
	@Autowired
	BooksRepository booksRepositiry;
	@Autowired
	ReadersRepository readersRepositiry;
	@Autowired
	RecordsRepository recordsRepositiry;

	@Override
	@Transactional
	public LibraryReturnCode addAuthor(AuthorDto author) {
		if (authorsRepositiry.existsById(author.getName()))
			return LibraryReturnCode.AUTHOR_ALREADY_EXISTS;
		authorsRepositiry.save(new Author(author.getName(), author.getCountry()));
		return LibraryReturnCode.OK;
	}

	@Override
	@Transactional
	public LibraryReturnCode addBook(BookDto book) {
		// TODO To add the book to the authors
		if (booksRepositiry.existsById(book.getIsbn()))
			return LibraryReturnCode.BOOK_ALREADY_EXISTS;
		List<Author> authors = new ArrayList<>();
		for (String authorName : book.getAuthorNames()) {
			Author author = authorsRepositiry.findById(authorName).orElse(null);
			if (author == null)
				return LibraryReturnCode.NO_AUTHOR;
			authors.add(author);
		}
		Book bookForSave = new Book(book.getIsbn(), book.getAmount(), book.getTitle(), book.getCover(),
				book.getPickPeriod(), authors);
		booksRepositiry.save(bookForSave);

		/*
		 * for (Author author : authors) { List<Book> books = author.getBooks(); if
		 * (books == null) books = new ArrayList<>(); books.add(bookForSave);
		 * author.setBooks(books); authorsRepositiry.save(author); }
		 */ return LibraryReturnCode.OK;
	}

	@Override
	@Transactional
	public LibraryReturnCode pickBook(int readerId, long isbn, LocalDate pickDate) {
		// TODO To add the record to the book and to the reader
		Reader reader = readersRepositiry.findById(readerId).orElse(null);
		if (reader == null)
			return LibraryReturnCode.NO_READER;
		Book book = booksRepositiry.findById(isbn).orElse(null);
		if (book == null)
			return LibraryReturnCode.NO_BOOK;
		recordsRepositiry.save(new Record(pickDate, book, reader));
		return LibraryReturnCode.OK;
	}

	@Override
	@Transactional
	public LibraryReturnCode addReader(ReaderDto reader) {
		if (readersRepositiry.existsById(reader.getId()))
			return LibraryReturnCode.READER_ALREADY_EXISTS;
		readersRepositiry.save(new Reader(reader.getId(), reader.getName(), reader.getYear(), reader.getPhone()));
		return LibraryReturnCode.OK;
	}

	@Override
	@Transactional
	public LibraryReturnCode returnBook(int readerId, long isbn, LocalDate returnDate) {
		Reader reader = readersRepositiry.findById(readerId).orElse(null);
		if (reader == null)
			return LibraryReturnCode.NO_READER;
		Book book = booksRepositiry.findById(isbn).orElse(null);
		if (book == null)
			return LibraryReturnCode.NO_BOOK;
		Record record = recordsRepositiry.findByBookAndReaderAndReturnDateNull(book, reader);
		LocalDate mustReturnDate = record.getPickDate().plusDays(book.getPickPeriod());
		ChronoUnit chronoUnit = ChronoUnit.DAYS;
		int delayDays = (int) (returnDate.isAfter(mustReturnDate) ? chronoUnit.between(mustReturnDate, returnDate) : 0);
		record.setReturnDate(returnDate);
		record.setDelayDays(delayDays);
		return LibraryReturnCode.OK;
	}

	@Override
	public List<ReaderDto> getReadersDelayingBooks() {
		// TODO Auto-generated method stub
		List<ReaderDto> readers = new ArrayList<>();
		for (Book book : booksRepositiry.findAll()) {
			List<Record> records = book.getRecords();
			if (!records.isEmpty()) {
				int number = records.size();
				Record record = records.get(number - 1);
				if (record.getReturnDate() == null) {
					Reader reader = record.getReader();
					readers.add(new ReaderDto(reader.getId(), reader.getName(), reader.getYear(), reader.getPhone()));
				}
			}
		}
		return readers;
	}

	@Override
	public List<AuthorDto> getBookAuthors(long isbn) {
		Book book = booksRepositiry.findById(isbn).orElse(null);
		if (book == null)
			return null;
		List<AuthorDto> authors = new ArrayList<>();
		for (Author author : book.getAuthors()) {
			authors.add(new AuthorDto(author.getName(), author.getCountry()));
		}
		return authors;
	}

	@Override
	public List<BookDto> getAuthorBooks(String authorName) {
		Author author = authorsRepositiry.findById(authorName).orElse(null);
		if (author == null)
			return null;
		List<BookDto> books = new ArrayList<>();
		for (Book book : author.getBooks()) {
			books.add(mapFromBookToBookDto(book));
		}
		return books;
	}

	private BookDto mapFromBookToBookDto(Book book) {
		List<String> authorNames = new ArrayList<>();
		for (Author author : book.getAuthors()) {
			authorNames.add(author.getName());
		}
		return new BookDto(book.getIsbn(), book.getTitle(), book.getAmount(), authorNames, book.getCover(),
				book.getPickPeriod());
	}

	@Override
	public List<BookDto> getMostPopularBooks(int yearFrom, int yearTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReaderDto> getMostActiveReaders() {
		// TODO Auto-generated method stub
		return null;
	}

}
