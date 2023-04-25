/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author DONG
 */
@Entity
@Table(name = "RoomTypeDetails")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoomTypeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rtDetailsID;
    @ManyToOne
    @JoinColumn(name = "roomID")
    private  Room room;
     @ManyToOne
    @JoinColumn(name = "rTypeID")
    private RoomType roomType;
    @OneToMany(mappedBy = "roomTypeDetail")
     private List<Schedule> scheduleList;
    private boolean status;

    public RoomTypeDetails() {
    }

    public RoomTypeDetails(int rtDetailsID, Room room, RoomType roomType) {
        this.rtDetailsID = rtDetailsID;
        this.room = room;
        this.roomType = roomType;
    }

    /**
     * @return the rtDetailsID
     */
    
    @Override
    public String toString() {
        return roomType.getrTypeName();
    }

    public int getRtDetailsID() {
        return rtDetailsID;
    }

    /**
     * @param rtDetailsID the rtDetailsID to set
     */
    public void setRtDetailsID(int rtDetailsID) {
        this.rtDetailsID = rtDetailsID;
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
     * @return the roomType
     */
    public RoomType getRoomType() {
        return roomType;
    }

    /**
     * @param roomType the roomType to set
     */
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
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
     * @return the scheduleList
     */
    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    /**
     * @param scheduleList the scheduleList to set
     */
    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    /**
     * @return the timeDetailList
     */
    
    
    
            
}
