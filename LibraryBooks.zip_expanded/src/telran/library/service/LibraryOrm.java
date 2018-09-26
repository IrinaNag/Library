package telran.library.service;

import java.time.LocalDate;
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
		if(booksRepositiry.existsById(book.getIsbn()))
			return LibraryReturnCode.BOOK_ALREADY_EXISTS;
		List<String> authorNames=book.getAuthorNames();
		List<Author> authors=new ArrayList<>();
		for (String authorName : authorNames) {
			Author author = authorsRepositiry.findById(authorName).orElse(null);
			if(author==null) 
				return LibraryReturnCode.NO_AUTHOR;
			authors.add(author);
			booksRepositiry.save(new Book(book.getIsbn(), book.getAmount()
					, book.getTitle(), book.getCover(), book.getPickPeriod(), authors));
		}
		return LibraryReturnCode.OK;
	}

	@Override
	public LibraryReturnCode pickBook(int readerId, long isbn, LocalDate pickDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LibraryReturnCode addReader(ReaderDto reader) {
		if(readersRepositiry.existsById(reader.getId()))
			return LibraryReturnCode.READER_ALREADY_EXISTS;
		readersRepositiry.save(new Reader(reader.getId(), reader.getName()
				, reader.getYear(), reader.getPhone()));
		return LibraryReturnCode.OK;
	}

	@Override
	public LibraryReturnCode returnBook(int readerId, long isbn, LocalDate returnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReaderDto> getReadersDelayingBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	
	public List<AuthorDto> getBookAuthors(long isbn) {
		Book book=booksRepositiry.findById(isbn).orElse(null);
		if(book==null)
			return null;
		List<AuthorDto>authors=new ArrayList<>();
		for (Author author : book.getAuthors()) {
			authors.add(new AuthorDto(author.getName(), author.getCountry()));
		}
		return authors;
	}

	@Override
	public List<BookDto> getAuthorBooks(String authorName) {
		// TODO Auto-generated method stub
		return null;
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
