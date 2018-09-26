package telran.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import telran.library.dto.*;

import telran.library.service.ILibrary;

@RestController
public class LibraryBooksController {
	@Autowired
	ILibrary library;

	@PostMapping(value = LibraryApiConstants.ADD_AUTHOR)
	public LibraryReturnCode addAuthor(@RequestBody AuthorDto author) {
		return library.addAuthor(author);
	}
	
	@PostMapping(value = LibraryApiConstants.ADD_BOOK)
	public LibraryReturnCode addBook(@RequestBody BookDto book) {
		return library.addBook(book);
	}
	
	@PostMapping(value = LibraryApiConstants.ADD_READER)
	public LibraryReturnCode addReader(@RequestBody ReaderDto reader) {
		return library.addReader(reader);
	}



}
