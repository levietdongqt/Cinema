package POJO;

import javax.persistence.*;

@Entity
@Table(name = "Ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketID")
    private String id;

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
    
    public Ticket(String id, Bill bill, Schedule schedule, String seatMap, boolean status) {
        this.id = id;
        this.bill = bill;
        this.schedule = schedule;
        this.seatMap = seatMap;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(String seatMap) {
        this.seatMap = seatMap;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    
    

}
