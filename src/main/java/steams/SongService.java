package steams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SongService {

    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song){
        songs.add(song);
    }

    public Optional<Song> shortestSong(){
        return songs.stream()
                .min(Comparator.comparing(Song::getLength));
    }

    public List<Song> findSongByTitle(String title){
        return songs.stream()
                .filter(t-> t.getTitle().equals(title))
                .toList();
    }

    public boolean isPerformerInSong(Song s, String performer){
        return s.getPerformers().stream()
                .anyMatch(p -> p.equals(performer));
    }

    public List<String> titlesBeforeDate(LocalDate date){
        return songs.stream()
                .filter(d-> d.getRelease().isBefore(date))
                .map(Song::getTitle)
                .toList();
    }
}
