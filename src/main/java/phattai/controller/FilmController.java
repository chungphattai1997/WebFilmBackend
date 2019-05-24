package phattai.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import phattai.dto.FilmDTO;
import phattai.model.Film;
import phattai.service.FilmService;

@RestController
@RequestMapping("/film")
public class FilmController {

	@Autowired
	private FilmService filmService;

	// GET all film
	@GetMapping("/getall")
	public ResponseEntity<List<Film>> getAll() {
		List<Film> listFilm = new ArrayList<Film>();
		listFilm = (List<Film>) filmService.findAll();
		return new ResponseEntity<List<Film>>(listFilm, HttpStatus.OK);
	}

	// GET film by id
	@GetMapping("/{id}")
	public ResponseEntity<Film> getById(@PathVariable("id") int id) {
		System.out.println("Searching by id: " + id);
		Film film = filmService.findById(id);
		if (film == null) {
			System.out.println("ID: " + id + " not found");
			return new ResponseEntity<Film>(HttpStatus.NOT_FOUND);
		}
		System.out.println(film);
		return new ResponseEntity<Film>(film, HttpStatus.OK);
	}

	// POST film
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestBody FilmDTO filmDTO) {
		int id = filmDTO.getId();
		if (filmService.findById(id) != null) {
			return new ResponseEntity<String>("ID Already Exist!", HttpStatus.CONFLICT);
		}
		Film film = new Film();
		film = filmService.covert(filmDTO);
		filmService.save(film);
		return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
	}

	// DELETE film by id
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id) {
		Film film = filmService.findById(id);
		if (film == null) {
			return new ResponseEntity<String>("Not Found Film", HttpStatus.NOT_FOUND);
		}
		filmService.delete(id);
		return new ResponseEntity<String>("Deleted!", HttpStatus.OK);
	}

	// PUT film to update
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody FilmDTO filmDTO) {
		int id = filmDTO.getId();
		if (filmService.findById(id) == null) {
			return new ResponseEntity<String>("Not Found ID", HttpStatus.NOT_FOUND);
		}
		Film film = new Film();
		film = filmService.covert(filmDTO);
		filmService.save(film);
		return new ResponseEntity<String>("Updated!", HttpStatus.OK);
	}
}
