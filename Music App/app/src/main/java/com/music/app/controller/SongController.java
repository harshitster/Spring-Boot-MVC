package com.music.app.controller;

import com.music.app.model.Song;
import com.music.app.service.SongService;
import com.music.app.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SearchService searchService;
    private SongService songService;

    @GetMapping("/home")  //working
    public List<Song> home(Model model) {
        List<Song> songs = songService.getAllSongs();
        return songs;
    }

    @GetMapping("/{id}")  //working
    public Song getSongById(@PathVariable("id") int id, Model model) {
        Song song = songService.getSongById(id);
        return song;
    }

    @GetMapping("/genre/{genre}")  //working
    public ResponseEntity<?> getSongsByGenre(@PathVariable String genre, Model model) {
        List<Song> songs = searchService.getSongsByGenre(genre);
        if (songs.isEmpty()) {
            System.out.println("Song not found for genre: "+genre);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found for genre: "+genre);
        }
        else {
            System.out.println("Song found for genre:"+genre);
            return ResponseEntity.status(HttpStatus.OK).body(songs);
        }
    }

    @GetMapping("/name/{name}")   //working
    public ResponseEntity<?> getSongsByName(@PathVariable String name, Model model) {
        List<Song> songs = searchService.getSongsByName(name);
        if (songs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No songs found for given name: " + name);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(songs);
        }
    }

    @PostMapping("/search/{name}")  //working
    public ResponseEntity<?> searchSongs(@PathVariable String name, Model model) {
        if(searchService.getSongsByName(name).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No songs found for given name: " + name);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Songs found for name: " + name);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSong(@RequestBody Song song) {
        Song newSong = songService.createSong(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSong);
    }

    @PostMapping("/delete/{id}")  //working
    public ResponseEntity<?> deleteSong(@PathVariable int id) {
        songService.deleteSongById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Song deleted successfully");
    }

    @GetMapping("/{id}/play")  //working
    public ResponseEntity<?> playSong(@PathVariable int id) {
        // TODO: Implement playSong logic
        return ResponseEntity.status(HttpStatus.OK).body("Song playing successfully");
    }

    @GetMapping("/{id}/pause")  //working
    public ResponseEntity<?> pauseSong(@PathVariable int id) {
        // TODO: Implement pauseSong logic
        return ResponseEntity.status(HttpStatus.OK).body("Song paused successfully");
    }
}