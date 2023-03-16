/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package POJO;

import java.time.LocalTime;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author DONG
 */
@Entity
@Table(name = "ShowTime")
public class ShowTime {
    @Id
    private String sTimeID;
    private LocalTime startTime;
     @OneToMany(mappedBy = "showTime")
    private Set<TimeDetail> timeDetailList;

     
     
    public ShowTime() {
    }

    public ShowTime(String sTimeID, LocalTime startTime, Set<TimeDetail> timeDetailList) {
        this.sTimeID = sTimeID;
        this.startTime = startTime;
        this.timeDetailList = timeDetailList;
    }

    /**
     * @return the sTimeID
     */
    public String getsTimeID() {
        return sTimeID;
    }

    /**
     * @param sTimeID the sTimeID to set
     */
    public void setsTimeID(String sTimeID) {
        this.sTimeID = sTimeID;
    }

    /**
     * @return the startTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the timeDetailList
     */
    public Set<TimeDetail> getTimeDetailList() {
        return timeDetailList;
    }

    /**
     * @param timeDetailList the timeDetailList to set
     */
    public void setTimeDetailList(Set<TimeDetail> timeDetailList) {
        this.timeDetailList = timeDetailList;
    }
    
}
