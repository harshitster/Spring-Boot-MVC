package com.music.app.controller;

import com.music.app.model.Playlist;
import com.music.app.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    // Get all playlists
    @PostMapping("/all")
    public ResponseEntity<List<Playlist>> getAllPlaylists(@RequestBody Playlist playlist) {
        List<Playlist> playlists = playlistService.getAllPlaylists();
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    // Get a playlist by id
    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable("id") int id) {
        Playlist playlist = playlistService.getPlaylistById(id);
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }

    // Create a new playlist
    @PostMapping("/create")
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
        Playlist newPlaylist = playlistService.createPlaylist(playlist);
        return new ResponseEntity<>(newPlaylist, HttpStatus.CREATED);
    }

    // Update a playlist by id
    @PutMapping("/{id}")
    public ResponseEntity<Playlist> updatePlaylistById(@PathVariable("id") int id, @RequestBody Playlist playlist) {
        Playlist updatedPlaylist = playlistService.updatePlaylistById(id, playlist);
        return new ResponseEntity<>(updatedPlaylist, HttpStatus.OK);
    }

    // Delete a playlist by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylistById(@PathVariable("id") int id) {
        playlistService.deletePlaylistById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
