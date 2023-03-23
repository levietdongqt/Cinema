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
    private boolean status;
    @OneToMany(mappedBy = "roomType")
    private Set <RoomTypeDetails> roomTypeDetailsList;
    @OneToMany(mappedBy = "roomType")
    private Set <RoomSeatDetail> roomSeatDetailList;

    public RoomType() {
    }

    public RoomType(String rTypeID, String rTypeName, String Description,  Set<RoomTypeDetails> roomTypeDetailsList, Set<RoomSeatDetail> roomSeatDetailList) {
        this.rTypeID = rTypeID;
        this.rTypeName = rTypeName;
        this.Description = Description;
        this.roomTypeDetailsList = roomTypeDetailsList;
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
     * @return the roomTypeDetailsList
     */
    public Set <RoomTypeDetails> getRoomTypeDetailsList() {
        return roomTypeDetailsList;
    }

    /**
     * @param roomTypeDetailsList the roomTypeDetailsList to set
     */
    public void setRoomTypeDetailsList(Set <RoomTypeDetails> roomTypeDetailsList) {
        this.roomTypeDetailsList = roomTypeDetailsList;
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
