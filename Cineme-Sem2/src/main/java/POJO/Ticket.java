/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketID;
    
    private String seatMap;
    
    private boolean status;
    
    @ManyToOne
    @JoinColumn(name = "scheduleID")
    private Schedule schedule;

    public Ticket() {
    }

    public Ticket(int ticketID, String seatMap, boolean status) {
        this.ticketID = ticketID;
        this.seatMap = seatMap;
        this.status = status;
    }

    /**
     * @return the ticketID
     */
    public int getTicketID() {
        return ticketID;
    }

    /**
     * @param ticketID the ticketID to set
     */
    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
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
    
    
}
