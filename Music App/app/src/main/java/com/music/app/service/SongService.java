package com.music.app.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.music.app.model.Song;

@Component
@Service
public class SongService {

    private Connection connection;

    public SongService() {
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

    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM songs");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Song song = new Song();
                song.setId(resultSet.getInt("id"));
                song.setTitle(resultSet.getString("title"));
                song.setArtist(resultSet.getString("artist"));
                song.setGenre(resultSet.getString("genre"));
                song.setAlbum(resultSet.getString("album"));
                songs.add(song);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songs;
    }

    public Song getSongById(int id) {
        Song song = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM songs WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                song = new Song();
                song.setId(resultSet.getInt("id"));
                song.setTitle(resultSet.getString("title"));
                song.setArtist(resultSet.getString("artist"));
                song.setGenre(resultSet.getString("genre"));
                song.setAlbum(resultSet.getString("album"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return song;
    }

    public Song createSong(Song song) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO songs (title, artist, genre, album) VALUES (?, ?, ?, ?)");
            statement.setString(1, song.getTitle());
            statement.setString(2, song.getArtist());
            statement.setString(3, song.getGenre());
            statement.setString(4, song.getAlbum());
            statement.executeUpdate();

            PreparedStatement idStatement = connection.prepareStatement("SELECT lastval()");
            ResultSet resultSet = idStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                song.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return song;
    }

    public Song updateSongById(int id, Song updatedSong) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE songs SET title=?, artist=?, genre=?, album=? WHERE id=?");
            statement.setString(1, updatedSong.getTitle());
            statement.setString(2, updatedSong.getArtist());
            statement.setString(3, updatedSong.getGenre());
            statement.setString(4, updatedSong.getAlbum());
            statement.setInt(5, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updatedSong;
    }

    public boolean deleteSongById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM songs WHERE id=?");
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}    

