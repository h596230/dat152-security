/**
 * 
 */
package no.hvl.dat152.rest.ws.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.service.AuthorService;

/**
 * 
 */
@RestController
@RequestMapping("/elibrary/api/v1")
public class AuthorController {

	@Autowired
	private AuthorService authorService;
	
	@GetMapping("/authors/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Author> getAuthor(@PathVariable("id") Long id) throws AuthorNotFoundException {
		Author author = authorService.findById(id);
		if(author == null) {
			throw new AuthorNotFoundException("Author with that id not found");
		}
		
		return new ResponseEntity<>(author, HttpStatus.OK);		
	}
	@GetMapping("/authors")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Object> getAllAuthors(){
		List<Author> authors = authorService.findAll();
		if(authors.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(authors, HttpStatus.OK);
	}

	@PutMapping("/authors/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> updateAuthor(@PathVariable("id") long id,@RequestBody Author author) throws Exception {
		if(author == null){
			throw new Exception("Author with that id not found");
		}
		Author newAuthor =  authorService.updateAuthor(id,author);

		return new ResponseEntity<>(newAuthor,HttpStatus.OK);
	}
	@GetMapping("/authors/{id}/books")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Object> getAuthorBooks(@PathVariable long id) throws AuthorNotFoundException {
		Author author = authorService.findById(id);
		Set<Book> books = author.getBooks();

		return new ResponseEntity<>(books,HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/authors")
	public ResponseEntity<Object> addAuthor(@RequestBody Author author){
		authorService.saveAuthor(author);
		List<Author> authors = authorService.findAll();
		return new ResponseEntity<>(authors,HttpStatus.CREATED);
	}


}
