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
@Table(name = "RoomType")
public class RoomType {
    @Id
    private String rTypeID;
    private String rTypeName;
    private String Description;
    
    @OneToMany(mappedBy = "roomType")
    private Set <Room> roomList;
    @OneToMany(mappedBy = "roomSeatDetail")
    private Set <RoomSeatDetail> roomSeatDetailList;

    public RoomType() {
    }

    public RoomType(String rTypeID, String rTypeName, String Description, Set<Room> roomList, Set<RoomSeatDetail> roomSeatDetailList) {
        this.rTypeID = rTypeID;
        this.rTypeName = rTypeName;
        this.Description = Description;
        this.roomList = roomList;
        this.roomSeatDetailList = roomSeatDetailList;
    }

    /**
     * @return the rTypeID
     */
    public String getrTypeID() {
        return rTypeID;
    }

    /**
     * @param rTypeID the rTypeID to set
     */
    public void setrTypeID(String rTypeID) {
        this.rTypeID = rTypeID;
    }

    /**
     * @return the rTypeName
     */
    public String getrTypeName() {
        return rTypeName;
    }

    /**
     * @param rTypeName the rTypeName to set
     */
    public void setrTypeName(String rTypeName) {
        this.rTypeName = rTypeName;
    }

    /**
     * @return the Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param Description the Description to set
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * @return the roomList
     */
    public Set <Room> getRoomList() {
        return roomList;
    }

    /**
     * @param roomList the roomList to set
     */
    public void setRoomList(Set <Room> roomList) {
        this.roomList = roomList;
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
