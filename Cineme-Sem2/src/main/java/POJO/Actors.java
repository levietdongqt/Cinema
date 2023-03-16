
package POJO;

import java.sql.Date;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Actors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int actorID;
    
    @Column(nullable = false)
    private String actorName;
    
    @Column(nullable = true)
    private Date birthDate;
    
    @Column(nullable = true)
    private String homeTown;
    
    @ManyToMany(mappedBy = "listActors")
    private Set<Film> listFilm = new HashSet<>();

    
    //Start Constructor
    public Actors() {
    }

    public Actors(int actorID, String actorName, Date birthDate, String homeTown) {
        this.actorID = actorID;
        this.actorName = actorName;
        this.birthDate = birthDate;
        this.homeTown = homeTown;
    }
    //End constructor
    /**
     * @return the actorID
     */
    public int getActorID() {
        return actorID;
    }

    /**
     * @param actorID the actorID to set
     */
    public void setActorID(int actorID) {
        this.actorID = actorID;
    }

    /**
     * @return the actorName
     */
    public String getActorName() {
        return actorName;
    }

    /**
     * @param actorName the actorName to set
     */
    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the homeTown
     */
    public String getHomeTown() {
        return homeTown;
    }

    /**
     * @param homeTown the homeTown to set
     */
    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
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
    
    
    
    
    
}
