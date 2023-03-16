/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.time.LocalDate;
import java.util.BitSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author DONG
 */
@Entity
@Table(name = "TimeDetails")
public class TimeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timeDetailsID;
    
    @ManyToOne
    @JoinColumn(name = "roomID")
    private Room room;
    
    @ManyToOne
    @JoinColumn(name = "sTimeID")
    private ShowTime showTime;
    
    @OneToOne(mappedBy = "timeDetail")
    private Schedule schedule;
    
    private LocalDate date;
    private boolean status;

    public TimeDetail() {
    }

    public TimeDetail(int timeDetailsID, Room room, ShowTime showTime, Schedule schedule, LocalDate date, boolean status) {
        this.timeDetailsID = timeDetailsID;
        this.room = room;
        this.showTime = showTime;
        this.schedule = schedule;
        this.date = date;
        this.status = status;
    }

    /**
     * @return the timeDetailsID
     */
    public int getTimeDetailsID() {
        return timeDetailsID;
    }

    /**
     * @param timeDetailsID the timeDetailsID to set
     */
    public void setTimeDetailsID(int timeDetailsID) {
        this.timeDetailsID = timeDetailsID;
    }

    /**
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * @return the showTime
     */
    public ShowTime getShowTime() {
        return showTime;
    }

    /**
     * @param showTime the showTime to set
     */
    public void setShowTime(ShowTime showTime) {
        this.showTime = showTime;
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
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
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
    
    

}
