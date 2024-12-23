package mk.finki.ukim.wp.lab.web.controller;

import com.sun.jdi.InvalidLineNumberException;
import mk.finki.ukim.wp.lab.model.Album;
import mk.finki.ukim.wp.lab.model.Genre;
import mk.finki.ukim.wp.lab.model.Song;
import mk.finki.ukim.wp.lab.service.AlbumService;
import mk.finki.ukim.wp.lab.service.GenreService;
import mk.finki.ukim.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;
    private final AlbumService albumService;

    private final GenreService genreService;

    public SongController(SongService songService, AlbumService albumService, GenreService genreService) {
        this.songService = songService;
        this.albumService = albumService;
        this.genreService = genreService;
    }


    @GetMapping
    public String getSongsPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("songs", songService.listSongs());
        model.addAttribute("albums", albumService.findAll());
        model.addAttribute("error", error);
        return "songs";
    }

    @PostMapping({"/add", "/add"})
    public String saveSong(@RequestParam String title, @RequestParam String genre,
                           @RequestParam String releaseYear, @RequestParam Long albumId) {
        if (title == null || title.isEmpty() || genre == null || releaseYear == null || albumId == null) {
            return "redirect:/songs?error=MissingRequiredFields";
        }

        Album album = albumService.findById(albumId);
        if (album == null) {
            return "redirect:/songs?error=AlbumNotFound";
        }

        // Find or create the Genre
        Optional<Genre> genreOptional = genreService.findByName(genre);
        Genre genreEntity = genreOptional.orElseGet(() -> {
            Genre newGenre = new Genre();
            newGenre.setName(genre);
            return genreService.save(newGenre);
        });

        int year;
        try {
            year = Integer.parseInt(releaseYear);
        } catch (NumberFormatException e) {
            return "redirect:/songs?error=InvalidReleaseYear";
        }

        Song song = new Song(title, genreEntity, year, new ArrayList<>());
        song.setAlbum(album);
        songService.save(song);
        return "redirect:/songs";
    }



    @GetMapping("/edit/{songId}")
    public String showEditForm(@PathVariable Long songId, Model model) {
        Song song = songService.findById(songId);
        model.addAttribute("song", song);
        return "redirect:/artists?songId=" + songId;
    }

    @GetMapping("/edit_song/{songId}")
    public String showEditSongForm(@PathVariable Long songId, Model model) {
        Song song = songService.findById(songId);
        model.addAttribute("song", song);
        model.addAttribute("albums", albumService.findAll());
        return "editSong";
    }

    @PostMapping("/edit")
    public String editSong(@RequestParam Long id, @RequestParam String title,
                           @RequestParam Long genreId, @RequestParam String releaseYear,
                           @RequestParam Long albumId) {
        Song song = songService.findById(id);
        if (song == null) {
            return "redirect:/songs?error=SongNotFound";
        }
        Genre genre = genreService.findById(genreId).orElseThrow(InvalidLineNumberException::new);
        Album album = albumService.findById(albumId);
        song.setTitle(title);
        song.setGenre(genre);
        song.setReleaseYear(Integer.parseInt(releaseYear));
        song.setAlbum(album);
        songService.save(song);
        return "redirect:/songs";
    }


    @GetMapping("/delete/{id}")
    public String deleteSong(@PathVariable Long id) {
        songService.delete(id);
        return "redirect:/songs";
    }
}
