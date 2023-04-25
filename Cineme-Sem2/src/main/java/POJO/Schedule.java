
package POJO;

import DAO.ScheduleDAO;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Schedule {
    @Id
    private String scheduleID;
    
//    private int ticketQuality;
    private LocalDateTime startTime;
    private LocalDateTime  endTime;
    private boolean status;
    private String note= "";
    @ManyToOne
    @JoinColumn(name = "filmID")
    private Film film;
    @ManyToOne
    @JoinColumn(name = "rtDetailsID")
    private RoomTypeDetails roomTypeDetail;
    
    @OneToMany(mappedBy = "schedule")
    private Set<Ticket> listTicket;

    public Schedule() {
    }
    public Schedule(String scheduleID, LocalDateTime startTime, LocalDateTime endTime, boolean status, Film film, RoomTypeDetails roomTypeDetail, Set<Ticket> listTicket) {
        this.scheduleID = scheduleID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.film = film;
        this.roomTypeDetail = roomTypeDetail;
        this.listTicket = listTicket;
    }
    
    /**
     * @return the scheduleID
     */
    public String getScheduleID() {
        return scheduleID;
    }

    /**
     * @param scheduleID the scheduleID to set
     */
    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    /**
     * @return the startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the film
     */
    public Film getFilm() {
        return film;
    }

    /**
     * @param film the film to set
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * @return the roomTypeDetail
     */
    public RoomTypeDetails getRoomTypeDetail() {
        return roomTypeDetail;
    }

    /**
     * @param roomTypeDetail the roomTypeDetail to set
     */
    public void setRoomTypeDetail(RoomTypeDetails roomTypeDetail) {
        this.roomTypeDetail = roomTypeDetail;
    }

    /**
     * @return the listTicket
     */
    public Set<Ticket> getListTicket() {
        return listTicket;
    }

    /**
     * @param listTicket the listTicket to set
     */
    public void setListTicket(Set<Ticket> listTicket) {
        this.listTicket = listTicket;
    }

    @Override
    public String toString() {
        return this.getStartTime().toLocalTime().toString()+" "+this.note;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }
    
    
    
}
