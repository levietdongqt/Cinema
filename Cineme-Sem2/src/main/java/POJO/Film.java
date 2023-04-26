package POJO;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.*;
import java.util.regex.Pattern;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Film implements Serializable{

    @Id
    private String filmID;
    @Column(nullable = false)
    private String filmName;
    @Column(nullable = false)
    private int limitAge;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private Date endDate;
    @Column(nullable = false)
    private int duration;
    @Column(nullable = false)
    private String imageUrl;

    private String director;

    private int viewFilm;

    private String description;
    
    private Boolean status = true;
    @Transient
    private int countSchedule;
    @Transient
    private long sumPriceTicket;
    @Transient
    private int countTicket;

    @ManyToMany
    @JoinTable(
            name = "ActorOfFilm",
            joinColumns = {
                @JoinColumn(name = "filmID")}, //st_id là khoá ngoại ở bảng trung gian liên kết với Student
            inverseJoinColumns = {
                @JoinColumn(name = "actorID")} // course_id là khoá ngoại ở bảng trung gian

    )
    private Set<Actors> listActors = new HashSet<>();


    @ManyToMany

    @JoinTable(
            name = "FilmGenreDetails",
            joinColumns = {
                @JoinColumn(name = "filmID")}, //st_id là khoá ngoại ở bảng trung gian liên kết với Student
            inverseJoinColumns = {
                @JoinColumn(name = "fGenreID")} // course_id là khoá ngoại ở bảng trung gian

    )
    private Set<FilmGenre> listGenre = new HashSet<>();

    @OneToMany(mappedBy = "film",cascade = CascadeType.REMOVE)
    private Set<Schedule> listSchedule = new HashSet<>();

    @Override
    public String toString() {
        return filmName;
    }
    
    //Constructor
    public Film() {
    }

    public Film(String filmID, String filmName, int limitAge, Date startDate, Date endDate, int duration, String imageUrl, String director, int viewFilm, String description) {
        this.filmID = filmID;
        this.filmName = filmName;
        this.limitAge = limitAge;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.imageUrl = imageUrl;
        this.director = director;
        this.viewFilm = viewFilm;
        this.description = description;
    }

    //End Contructors
    /**
     * @return the filmID
     */
    public String getFilmID() {
        return filmID;
    }

    /**
     * @param filmID the filmID to set
     */
    public void setFilmID(String filmID) {
        this.filmID = filmID;
    }

    /**
     * @return the filmName
     */
    public String getFilmName() {
        return filmName;
    }

    /**
     * @param filmName the filmName to set
     * @throws java.lang.Exception
     */
    public void setFilmName(String filmName) throws Exception {
        if (filmName.trim().isEmpty()) {
            throw new Exception("Film name dont Empty");
        }else if(!Pattern.matches("(([\\w]+[\\s]{0,1})+[.'!&:]{0,1}[\\s]{0,1})*([\\w]+[\\s]{0,1}[!]{0,1})+", filmName)){
            throw new Exception("Film name dont special characters except [.'!&:]");
        }else if(filmName.contains("  ")){
            throw new Exception("Film name dont have two white spaces");
        }else if(filmName.length()>50){
            throw new Exception("Film name must be < 50 letters");
        }
        else {
            this.filmName = filmName;
        }
    }

    /**
     * @return the limitAge
     */
    public int getLimitAge() {
        return limitAge;
    }

    /**
     * @param limitAge the limitAge to set
     */
    public void setLimitAge(int limitAge) {
        this.limitAge = limitAge;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) throws Error, NullPointerException {
        Date patternNow = Date.valueOf(LocalDate.now());
        if (startDate.before(patternNow)) {
            throw new Error("Start Date Film must > " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        else {
            this.startDate = startDate;
        }
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) throws Error, NullPointerException {
        String startDateS = this.startDate.toString();
        LocalDate startDatePlus = LocalDate.parse(startDateS).plusDays(10);
        LocalDate startDatePlus2 = LocalDate.parse(startDateS).plusDays(30);
        Date patternEndDate = Date.valueOf(startDatePlus);
        Date patternEndDate2 = Date.valueOf(startDatePlus2);
        if (endDate.before(patternEndDate)) {
            throw new Error("End Date must be >" + startDatePlus.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }else if(endDate.after(patternEndDate2)){
            throw new Error("End Date dont have greater than Start Date 30 days");
        } 
        else {
            this.endDate = endDate;
        }

    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;

    }

    /**
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl the imageUrl to set
     */
    public void setImageUrl(String imageUrl) throws Exception {
        if (imageUrl.trim().isEmpty()) {
            throw new Exception("Image is not null!!!!");
        } else {
            this.imageUrl = imageUrl;
        }
    }

    /**
     * @return the director
     */
    public String getDirector() {
        return director;
    }

    /**
     * @param director the director to set
     */
    public void setDirector(String director) throws Error{
        if (director.trim().isEmpty()) {
            throw new Error("Director don't empty, please check again!!");
        }else if(!Pattern.matches("(([\\w]+[\\s]{0,1})+[.']{0,1}[\\s]{0,1})*([\\w]+[\\s]{0,1})+", director)){
            throw new Error("Director dont have special characters except [.']!!!");
        }else if(director.contains("  ")){
            throw new Error("Director dont have two white spaces!!!");
        }else if(director.length()>30){
            throw new Error("Director must be < 30 letters!!!");
        }
        else {
            this.director= director;
        }
    }

    /**
     * @return the viewFilm
     */
    public int getViewFilm() {
        return viewFilm;
    }

    /**
     * @param viewFilm the viewFilm to set
     */
    public void setViewFilm(int viewFilm) {
        this.viewFilm = viewFilm;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) throws Error{
        if(description.length()<=10){
            throw new Error("Description must be greater than 10 letter, please check again!!");
        }else if(description.toLowerCase().contains("fuck")|| description.toLowerCase().contains("shit")|| description.toLowerCase().contains("bitch")){
            throw new Error("Description dont contains foul language,please check again!!");
        }
        this.description=description;
    }

    /**
     * @return the listActors
     */
    public Set<Actors> getListActors() {
        return listActors;
    }

    /**
     * @param listActors the listActors to set
     */
    public void setListActors(Set<Actors> listActors) {
        this.listActors = listActors;
    }

    /**
     * @return the listGenre
     */
    public Set<FilmGenre> getListGenre() {
        return listGenre;
    }

    /**
     * @param listGenre the listGenre to set
     */
    public void setListGenre(Set<FilmGenre> listGenre) {
        this.listGenre = listGenre;
    }

    /**
     * @return the listSchedule
     */
    public Set<Schedule> getListSchedule() {
        return listSchedule;
    }

    /**
     * @param listSchedule the listSchedule to set
     */
    public void setListSchedule(Set<Schedule> listSchedule) {
        this.listSchedule = listSchedule;
    }

    /**
     * @return the status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return the countSchedule
     */
    public int getCountSchedule() {
        return countSchedule;
    }

    /**
     * @param countSchedule the countSchedule to set
     */
    public void setCountSchedule(int countSchedule) {
        this.countSchedule = countSchedule;
    }

    /**
     * @return the sumPriceTicket
     */
    public long getSumPriceTicket() {
        return sumPriceTicket;
    }

    /**
     * @param sumPriceTicket the sumPriceTicket to set
     */
    public void setSumPriceTicket(long sumPriceTicket) {
        this.sumPriceTicket = sumPriceTicket;
    }

    /**
     * @return the countTicket
     */
    public int getCountTicket() {
        return countTicket;
    }

    /**
     * @param countTicket the countTicket to set
     */
    public void setCountTicket(int countTicket) {
        this.countTicket = countTicket;
    }

}
