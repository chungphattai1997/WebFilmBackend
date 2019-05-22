package phattai.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import phattai.model.Category;
import phattai.model.Film;
import phattai.service.FilmService;

@RestController
@RequestMapping("/film")
public class FilmController {

	@Autowired
	private FilmService filmService;

//	public List<Film> getAll() {
//		System.out.println("Get list film");
//		return (List<Film>) filmService.findAll();
//	}

	// GET all film
	@GetMapping("/getall")
	public ResponseEntity<List<Film>> getAllFilm() {
		List<Film> listFilm = new ArrayList<Film>();
		listFilm = (List<Film>) filmService.findAll();
		return new ResponseEntity<List<Film>>(listFilm, HttpStatus.OK);
	}

//	// GET film by id
//	@GetMapping("/{id}")
//	public Film getById(@PathVariable("id") int id) {
//		System.out.println("Searching by id: " + id);
//		Film film = filmService.findById(id);
//		if (film == null) {
//			System.out.println("ID: " + id + " not found");
//		}
//		return film;
//	}

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

//	// POST film
//	@PostMapping("/add")
//	public Film add(@RequestBody Map<String, Object> objFilm) {
//		Category category = new Category();
//		category.setId((int) objFilm.get("id_category"));
//
//		Film film = new Film();
//		film.setId((int) objFilm.get("id"));
//		film.setTitle((String) objFilm.get("title"));
//		film.setTrailer((String) objFilm.get("trailer"));
//		film.setDetail((String) objFilm.get("detail"));
//		film.setDate_opening(new Date());
//		film.setRate((int) objFilm.get("rate"));
//		film.setImage((String) objFilm.get("image"));
//		film.setCategory(category);
//
//		filmService.save(film);
//		return film;
//	}

	// POST film
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestBody Map<String, Object> objFilm) {
		int id = (int) objFilm.get("id");
		if (filmService.findById(id) != null) {
			return new ResponseEntity<String>("ID Already Exist!", HttpStatus.CONFLICT);
		}

		Category category = new Category();
		category.setId((int) objFilm.get("id_category"));

		Film film = new Film();
		film.setId((int) objFilm.get("id"));
		film.setTitle((String) objFilm.get("title"));
		film.setTrailer((String) objFilm.get("trailer"));
		film.setDetail((String) objFilm.get("detail"));
		film.setDate_opening(new Date());
		film.setRate((int) objFilm.get("rate"));
		film.setImage((String) objFilm.get("image"));
		film.setCategory(category);

		filmService.save(film);
		return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
	}
	
	
//	// DELETE film by id
//	@DeleteMapping("/delete/{id}")
//	public void deleteById(@PathVariable("id") int id) {
//		Film film = filmService.findById(id);
//		if (film == null) {
//			System.out.println("Not exsit ID! Don't delete!");
//			return;
//		}
//		filmService.delete(id);
//		System.out.println("Deleted id: " + id);
//	}

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

//	// PUT film to update
//	@PutMapping("/update")
//	public Film update(@RequestBody Film film) {
//		Film temp = filmService.findById(film.getId());
//		if (temp != null) {
//			System.out.println("Update id: " + film.getId());
//			filmService.save(film);
//			return film;
//		}
//		System.out.println("Not exsit ID!");
//		return temp;
//	}

	// PUT film to update
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody Film film) {
		Film temp = filmService.findById(film.getId());
		if (temp != null) {
			filmService.save(film);
			return new ResponseEntity<String>("Updated!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Not Found User", HttpStatus.NO_CONTENT);
	}

}
