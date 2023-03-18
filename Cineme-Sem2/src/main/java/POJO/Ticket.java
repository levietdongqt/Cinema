package POJO;

import javax.persistence.*;

@Entity
@Table(name = "Ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "billID", referencedColumnName = "billID")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "scheduleID", referencedColumnName = "scheduleID")
    private Schedule schedule;

    @Column(name = "seatMap")
    private String seatMap;

    @Column(name = "status")
    private Boolean status;
    
 
    public Ticket() {}

    public Ticket(int id, Bill bill, Schedule schedule, String seatMap, Boolean status) {
        this.id = id;
        this.bill = bill;
        this.schedule = schedule;
        this.seatMap = seatMap;
        this.status = status;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the bill
     */
    public Bill getBill() {
        return bill;
    }

    /**
     * @param bill the bill to set
     */
    public void setBill(Bill bill) {
        this.bill = bill;
    }

    /**
     * @return the schedule
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * @param schedule the schedule to set
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * @return the seatMap
     */
    public String getSeatMap() {
        return seatMap;
    }

    /**
     * @param seatMap the seatMap to set
     */
    public void setSeatMap(String seatMap) {
        this.seatMap = seatMap;
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
    
   
}
