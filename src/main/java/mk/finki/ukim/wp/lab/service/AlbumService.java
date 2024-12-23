package mk.finki.ukim.wp.lab.service;

import mk.finki.ukim.wp.lab.model.Album;

import java.util.List;

public interface AlbumService {
    List<Album> findAll();
    Album findById(Long id);
}
