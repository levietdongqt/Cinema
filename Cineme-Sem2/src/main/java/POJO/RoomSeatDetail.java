/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author DONG
 */
@Entity
@Table(name = "RoomSeatDetails")
public class RoomSeatDetail {

    @Id
    private String rsDetailsID;
    @ManyToOne
    @JoinColumn(name = "rTypeID")
    private RoomType roomType;
    @ManyToOne
    @JoinColumn(name = "sTypeID")
    private SeatType seatType;
    @ManyToOne
    @JoinColumn(name = "sMapID")
    private SeatMap seatMap;

    public RoomSeatDetail() {
    }

    public RoomSeatDetail(String rsDetailsID, RoomType roomType, SeatType seatType, SeatMap seatMap) {
        this.rsDetailsID = rsDetailsID;
        this.roomType = roomType;
        this.seatType = seatType;
        this.seatMap = seatMap;
    }

    /**
     * @return the rsDetailsID
     */
    public String getRsDetailsID() {
        return rsDetailsID;
    }

    /**
     * @param rsDetailsID the rsDetailsID to set
     */
    public void setRsDetailsID(String rsDetailsID) {
        this.rsDetailsID = rsDetailsID;
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
     * @return the seatType
     */
    public SeatType getSeatType() {
        return seatType;
    }

    /**
     * @param seatType the seatType to set
     */
    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    /**
     * @return the seatMap
     */
    public SeatMap getSeatMap() {
        return seatMap;
    }

    /**
     * @param seatMap the seatMap to set
     */
    public void setSeatMap(SeatMap seatMap) {
        this.seatMap = seatMap;
    }
    

}
