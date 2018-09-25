package telran.library.service;

import java.time.LocalDate;
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
	public LibraryReturnCode addBook(BookDto book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LibraryReturnCode pickBook(int readerId, long isbn, LocalDate pickDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LibraryReturnCode addReader(ReaderDto reader) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
