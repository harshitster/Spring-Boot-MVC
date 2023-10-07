// handle play/pause button clicks
const playPauseButtons = document.querySelectorAll(".play-pause-btn");
playPauseButtons.forEach(btn => {
    btn.addEventListener("click", () => {
        const audioPlayer = btn.closest(".card-body").querySelector("audio");
        if (audioPlayer.paused) {
            audioPlayer.play();
            btn.innerHTML = '<i class="fa fa-pause"></i>';
        } else {
            audioPlayer.pause();
            btn.innerHTML = '<i class="fa fa-play"></i>';
        }
    });
});

// handle adding songs to playlist
const addToPlaylistButtons = document.querySelectorAll(".add-to-playlist-btn");
addToPlaylistButtons.forEach(btn => {
    btn.addEventListener("click", () => {
        const songId = btn.dataset.songId;
        const playlistId = document.querySelector("#playlist-select").value;
        fetch(`/playlists/${playlistId}/addSong/${songId}`, {
            method: "POST"
        }).then(response => {
            if (response.ok) {
                alert("Song added to playlist!");
            } else {
                alert("Error adding song to playlist.");
            }
        }).catch(error => {
            alert("Error adding song to playlist.");
        });
    });
});

// handle removing songs from playlist
const removeFromPlaylistButtons = document.querySelectorAll(".remove-from-playlist-btn");
removeFromPlaylistButtons.forEach(btn => {
    btn.addEventListener("click", () => {
        const songId = btn.dataset.songId;
        const playlistId = btn.closest(".playlist-item").dataset.playlistId;
        fetch(`/playlists/${playlistId}/removeSong/${songId}`, {
            method: "DELETE"
        }).then(response => {
            if (response.ok) {
                const songItem = btn.closest(".song-item");
                songItem.parentNode.removeChild(songItem);
            } else {
                alert("Error removing song from playlist.");
            }
        }).catch(error => {
            alert("Error removing song from playlist.");
        });
    });
});

// handle recommending songs
const recommendButton = document.querySelector("#recommend-btn");
recommendButton.addEventListener("click", () => {
    const genre = recommendButton.dataset.genre;
    fetch(`/songs/recommend/${genre}`, {
        method: "GET"
    }).then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("Error getting recommended songs.");
        }
    }).then(songs => {
        const recommendedSongsList = document.querySelector("#recommended-songs");
        recommendedSongsList.innerHTML = "";
        songs.forEach(song => {
            recommendedSongsList.innerHTML += `
                <div class="card mb-3">
                    <div class="card-body">
                        <h5 class="card-title">${song.name}</h5>
                        <audio controls src="${song.url}"></audio>
                        <button class="btn btn-primary add-to-playlist-btn" data-song-id="${song.id}">Add to Playlist</button>
                    </div>
                </div>
            `;
        });
    }).catch(error => {
        alert("Error getting recommended songs.");
    });
});
