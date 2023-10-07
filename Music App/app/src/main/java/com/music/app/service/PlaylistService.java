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

import com.music.app.model.Playlist;

@Component
@Service
public class PlaylistService {

    private Connection connection;

    public PlaylistService() {
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

    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM playlists");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Playlist playlist = new Playlist();
                playlist.setId(resultSet.getInt("id"));
                playlist.setName(resultSet.getString("name"));
                playlist.setDescription(resultSet.getString("description"));
                playlists.add(playlist);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playlists;
    }

    public Playlist getPlaylistById(int id) {
        Playlist playlist = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM playlists WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                playlist = new Playlist();
                playlist.setId(resultSet.getInt("id"));
                playlist.setName(resultSet.getString("name"));
                playlist.setDescription(resultSet.getString("description"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playlist;
    }

    public Playlist createPlaylist(Playlist playlist) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO playlists (name, description) VALUES (?, ?)");
            statement.setString(1, playlist.getName());
            statement.setString(2, playlist.getDescription());
            statement.executeUpdate();

            PreparedStatement idStatement = connection.prepareStatement("SELECT lastval()");
            ResultSet resultSet = idStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                playlist.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playlist;
    }

    public Playlist updatePlaylistById(int id, Playlist updatedPlaylist) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE playlists SET name=?, description=? WHERE id=?");
            statement.setString(1, updatedPlaylist.getName());
            statement.setString(2, updatedPlaylist.getDescription());
            statement.setLong(3, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updatedPlaylist;
    }

    public boolean deletePlaylistById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM playlists WHERE id=?");
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
