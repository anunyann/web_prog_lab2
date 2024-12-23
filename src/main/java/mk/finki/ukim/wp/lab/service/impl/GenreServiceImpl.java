package mk.finki.ukim.wp.lab.service.impl;

import mk.finki.ukim.wp.lab.model.Genre;
import mk.finki.ukim.wp.lab.repository.jpa.GenreRepository;
import mk.finki.ukim.wp.lab.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return genreRepository.findByName(name);
    }
}


