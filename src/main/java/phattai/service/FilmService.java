package phattai.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import phattai.dto.FilmDTO;
import phattai.model.Category;
import phattai.model.Film;
import phattai.repository.FilmRepository;

@Service
public class FilmService {

	@Autowired
	FilmRepository filmRepository;

	// Find all film
	public Iterable<Film> findAll() {
		return filmRepository.findAll();
	}

	// Find film by id
	public Film findById(int id) {
		Optional<Film> film = filmRepository.findById(id);
		if (!film.isPresent()) {
			return null;
		}
		return film.get();
	}

	// Add or update film
	public void save(Film film) {
		filmRepository.save(film);
	}

	// Delete film
	public void delete(int id) {
		filmRepository.deleteById(id);
	}

	// Covert FilmDTO to Film
	public Film covert(FilmDTO filmDTO) {
		Film film = new Film();
		Category category = new Category();
		category.setId(filmDTO.getId_category());
		filmDTO.setCategory(category);
		BeanUtils.copyProperties(filmDTO, film);
		return film;
	}

}
