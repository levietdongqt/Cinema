
package POJO;

import java.io.Serializable;
import java.sql.Date;
import java.util.*;
import java.util.regex.Pattern;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Actors implements Serializable{
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
     * @throws java.lang.Exception
     */
    public void setActorName(String actorName) throws Exception {
        if(actorName.trim().isEmpty()){
            throw new Exception("Actors name don't empty!!");
        }else if(!Pattern.matches("(([\\w]+[\\s]{0,1})+[.']{0,1}[\\s]{0,1})*([\\w]+[\\s]{0,1})+", actorName)){
            throw new Exception("Actors dont have special characters except [.']!!");
        }else if(actorName.length()>30){
            throw new Exception("Actors must be <=30 letters");
        }else if(actorName.contains("  ")){
            throw new Exception("Actors dont have two white spaces");
        }
        else{
            this.actorName = actorName;
        }
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     * @throws java.lang.Exception
     */
    public void setBirthDate(Date birthDate) throws Exception {
        Date patternBD1 = Date.valueOf("1933-01-01");
        Date patternBD2 = Date.valueOf("2010-01-01");
        if(birthDate.before(patternBD1) || birthDate.after(patternBD2)){
            throw new Exception("BirthDay must 1933<yyyy<2010");
        }else{
            this.birthDate = birthDate;
        }
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

    @Override
    public String toString() {
        return this.actorName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        final Actors other = (Actors) obj;
        if(!Objects.equals(this.actorName, other.actorName)) return false;
        if(!Objects.equals(this.birthDate, other.birthDate)) return false;
        if(!Objects.equals(this.homeTown, other.homeTown)) return false;
        if(this.actorID != other.getActorID()) return false;
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash +Objects.hashCode(this.actorName);
        hash = 31 * hash +Objects.hashCode(this.birthDate);
        hash = 31 * hash +Objects.hashCode(this.homeTown);
        hash = 31 * hash +this.actorID;
        return hash;   
    }

   
    
    
    
    
    
}
