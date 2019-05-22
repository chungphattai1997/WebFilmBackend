package phattai.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phattai.model.Film;
import phattai.repository.FilmRepository;

@Service
public class FilmService {
	
	@Autowired
	FilmRepository filmRepository;
	
	//Find all film
	public Iterable<Film> findAll() {
		return filmRepository.findAll();
	}
	
	//Find film by id
	public Film findById(int id) {
		Optional<Film> op = filmRepository.findById(id);
		if (!op.isPresent()) {
			System.out.println("Not exist ID: " + id);
			return null;
		}
		return op.get();
	}
	
	//Add or update film
	public void save(Film film) {
		filmRepository.save(film);
	}
	
	//Delete film
	public void delete(int id) {
		filmRepository.deleteById(id);
	}
}
