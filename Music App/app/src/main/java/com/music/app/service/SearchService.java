package com.music.app.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

import com.music.app.model.Song;

@Component
@Service
public class SearchService implements SearchInstance {

    private Connection connection;

    public SearchService() {
        String url = "jdbc:postgresql://localhost:5432/musicdb";
        String username = "postgres";
        String password = "password";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Song> getSongsByGenre(String genre) {
        List<Song> songsByGenre = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM songs WHERE genre = ?");
            statement.setString(1, genre);
            ResultSet resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                Song song = new Song();
                song.setId(resultSet.getInt("id"));
                song.setTitle(resultSet.getString("title"));
                song.setArtist(resultSet.getString("artist"));
                song.setGenre(resultSet.getString("genre"));
                song.setAlbum(resultSet.getString("album"));
                songsByGenre.add(song);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return songsByGenre;
    }
    
    public List<Song> getSongsByName(String name) {
        List<Song> songsByName = new ArrayList<>();
    
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM songs WHERE title LIKE ?");
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                Song song = new Song();
                song.setId(resultSet.getInt("id"));
                song.setTitle(resultSet.getString("title"));
                song.setArtist(resultSet.getString("artist"));
                song.setGenre(resultSet.getString("genre"));
                song.setAlbum(resultSet.getString("album"));
                songsByName.add(song);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return songsByName;
    }
    
}
