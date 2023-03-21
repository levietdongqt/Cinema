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
@Table(name = "SeatMap")
public class SeatMap {
    @Id
    private String sMapID;
//    private String seatRow;
//    private int seatNum;
    @OneToMany(mappedBy = "seatMap")
    private Set<RoomSeatDetail> roomSeatDetailList;

    public SeatMap() {
    }

    public SeatMap(String sMapID, String seatRow, int seatNum, Set<RoomSeatDetail> roomSeatDetailList) {
        this.sMapID = sMapID;
//        this.seatRow = seatRow;
//        this.seatNum = seatNum;
        this.roomSeatDetailList = roomSeatDetailList;
    }

    /**
     * @return the sMapID
     */
    public String getsMapID() {
        return sMapID;
    }

    /**
     * @param sMapID the sMapID to set
     */
    public void setsMapID(String sMapID) {
        this.sMapID = sMapID;
    }

    /**
     * @return the seatRow
     */
//    public String getSeatRow() {
//        return seatRow;
//    }
//
//    /**
//     * @param seatRow the seatRow to set
//     */
//    public void setSeatRow(String seatRow) {
//        this.seatRow = seatRow;
//    }
//
//    /**
//     * @return the seatNum
//     */
//    public int getSeatNum() {
//        return seatNum;
//    }
//
//    /**
//     * @param seatNum the seatNum to set
//     */
//    public void setSeatNum(int seatNum) {
//        this.seatNum = seatNum;
//    }

    /**
     * @return the roomSeatDetailList
     */
    public Set<RoomSeatDetail> getRoomSeatDetailList() {
        return roomSeatDetailList;
    }

    /**
     * @param roomSeatDetailList the roomSeatDetailList to set
     */
    public void setRoomSeatDetailList(Set<RoomSeatDetail> roomSeatDetailList) {
        this.roomSeatDetailList = roomSeatDetailList;
    }

    
}
