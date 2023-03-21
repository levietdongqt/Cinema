
package POJO;

import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class FilmGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fGenreID;
    
    @Column(nullable = false)
    private String fGenreName;
    
    @ManyToMany(mappedBy = "listGenre")
    private Set<Film> listFilm = new HashSet<>();

    
    //Constructor
    public FilmGenre() {
    }

    public FilmGenre(int fGenreID, String fGenreName) {
        this.fGenreID = fGenreID;
        this.fGenreName = fGenreName;
    }
    //End constructor
    /**
     * @return the fGenreID
     */
    public int getfGenreID() {
        return fGenreID;
    }

    /**
     * @param fGenreID the fGenreID to set
     */
    public void setfGenreID(int fGenreID) {
        this.fGenreID = fGenreID;
    }

    /**
     * @return the fGenreName
     */
    public String getfGenreName() {
        return fGenreName;
    }

    /**
     * @param fGenreName the fGenreName to set
     */
    public void setfGenreName(String fGenreName) {
        this.fGenreName = fGenreName;
    }

    /**
     * @return the listFilm
     */
    public Set<Film> getListFilm() {
        return listFilm;
    }

    /**
     * @param listFilm the listFilm to set
     */
    public void setListFilm(Set<Film> listFilm) {
        this.listFilm = listFilm;
    }

    @Override
    public String toString() {
        return this.fGenreName;
    }
    
    
    
}
