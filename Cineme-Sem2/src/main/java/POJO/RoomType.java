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
    private Set <Room> rooms;
    @OneToMany(mappedBy = "roomSeatDetail")
    private Set <RoomSeatDetail> roomSeatDetails;

    public RoomType() {
    }

    public RoomType(String rTypeID, String rTypeName, String Description, Set<Room> rooms, Set<RoomSeatDetail> roomSeatDetails) {
        this.rTypeID = rTypeID;
        this.rTypeName = rTypeName;
        this.Description = Description;
        this.rooms = rooms;
        this.roomSeatDetails = roomSeatDetails;
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
     * @return the rooms
     */
    public Set <Room> getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(Set <Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * @return the roomSeatDetails
     */
    public Set <RoomSeatDetail> getRoomSeatDetails() {
        return roomSeatDetails;
    }

    /**
     * @param roomSeatDetails the roomSeatDetails to set
     */
    public void setRoomSeatDetails(Set <RoomSeatDetail> roomSeatDetails) {
        this.roomSeatDetails = roomSeatDetails;
    }
    
    
    
}
