package mk.finki.ukim.wp.lab.service;
import mk.finki.ukim.wp.lab.model.Genre;

import java.util.Optional;

public interface GenreService {
    Optional<Genre> findById(Long id);
    Genre save(Genre genre);

    Optional<Genre> findByName(String name);
}
