/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

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
@Table(name = "SeatType")
public class SeatType {
    @Id
    private String sTypeID;
    private String sTypeName;
    private int seatPrice;
    @OneToMany(mappedBy = "seatType")
    private Set <RoomSeatDetail> roomSeatDetailList;

    public SeatType() {
    }

    public SeatType(String sTypeID, String sTypeName, int seatPrice, Set<RoomSeatDetail> roomSeatDetailList) {
        this.sTypeID = sTypeID;
        this.sTypeName = sTypeName;
        this.seatPrice = seatPrice;
        this.roomSeatDetailList = roomSeatDetailList;
    }

    /**
     * @return the sTypeID
     */
    public String getsTypeID() {
        return sTypeID;
    }

    /**
     * @param sTypeID the sTypeID to set
     */
    public void setsTypeID(String sTypeID) {
        this.sTypeID = sTypeID;
    }

    /**
     * @return the sTypeName
     */
    public String getsTypeName() {
        return sTypeName;
    }

    /**
     * @param sTypeName the sTypeName to set
     */
    public void setsTypeName(String sTypeName) {
        this.sTypeName = sTypeName;
    }

    /**
     * @return the seatPrice
     */
    public int getSeatPrice() {
        return seatPrice;
    }

    /**
     * @param seatPrice the seatPrice to set
     */
    public void setSeatPrice(int seatPrice) {
        this.seatPrice = seatPrice;
    }

    /**
     * @return the roomSeatDetailList
     */
    public Set <RoomSeatDetail> getRoomSeatDetailList() {
        return roomSeatDetailList;
    }

    /**
     * @param roomSeatDetailList the roomSeatDetailList to set
     */
    public void setRoomSeatDetailList(Set <RoomSeatDetail> roomSeatDetailList) {
        this.roomSeatDetailList = roomSeatDetailList;
    }

    
}
