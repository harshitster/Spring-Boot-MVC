package com.music.app.service;

import java.util.List;
import com.music.app.model.Song;

public interface SearchInstance {

    List<Song> getSongsByGenre(String genre);

    List<Song> getSongsByName(String name);
    
}