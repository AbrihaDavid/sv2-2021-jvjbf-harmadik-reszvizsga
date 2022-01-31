package movietheatres;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;

public class MovieTheatreService {

    private Map<String, List<Movie>> shows = new LinkedHashMap<>();

    public Map<String, List<Movie>> getShows() {
        return shows;
    }

    public void readFromFile(Path path){
        try(BufferedReader br = Files.newBufferedReader(path)){
            String line;
            while((line= br.readLine())!=null){
                String[] temp = line.split("-");
                String theatre = temp[0];
                String movieTitle = temp[1].split(";")[0];
                LocalTime movieTime = LocalTime.parse(temp[1].split(";")[1]);
                if(!shows.containsKey(temp[0])){
                    shows.put(theatre,new ArrayList<>());
                }
                List<Movie> movies = shows.get(theatre);
                movies.add(new Movie(movieTitle,movieTime));
                movies.sort(new Comparator<Movie>() {
                    @Override
                    public int compare(Movie o1, Movie o2) {
                        return o1.getStartTime().getHour()*60+o1.getStartTime().getMinute() - o2.getStartTime().getHour()*60+o2.getStartTime().getMinute();
                    }
                });


            }
        } catch (IOException ioe){
            throw new IllegalStateException("File missing",ioe);
        }
    }

    public List<String> findMovie(String title) {

        List<String> result = new ArrayList<>();
        for (Map.Entry<String, List<Movie>> entry : shows.entrySet()) {
            if (entry.getValue().stream().anyMatch(t -> t.getTitle().equals(title))) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

}
