
package POJO;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Schedule {
    @Id
    private String scheduleID;
    
    private int ticketQuality;
    
    private boolean status;
    
    @ManyToOne
    @JoinColumn(name = "filmID")
    private Film film;
    
    @OneToOne
    @JoinColumn(name = "timeDetailsID")
    private TimeDetail timeDetail;
    
    @OneToMany(mappedBy = "schedule")
    private Set<Ticket> listTicket;
    //Constructor
    public Schedule() {
    }

    public Schedule(String scheduleID, int ticketQuality, boolean status) {
        this.scheduleID = scheduleID;
        this.ticketQuality = ticketQuality;
        this.status = status;
    }
    
    //End constructor
    
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
     * @return the ticketQuality
     */
    public int getTicketQuality() {
        return ticketQuality;
    }

    /**
     * @param ticketQuality the ticketQuality to set
     */
    public void setTicketQuality(int ticketQuality) {
        this.ticketQuality = ticketQuality;
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
     * @return the timeDetail
     */
    public TimeDetail getTimeDetail() {
        return timeDetail;
    }

    /**
     * @param timeDetail the timeDetail to set
     */
    public void setTimeDetail(TimeDetail timeDetail) {
        this.timeDetail = timeDetail;
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
    
    
}
