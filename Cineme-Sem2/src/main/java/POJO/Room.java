    package POJO;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Room")
public class Room {

    @Id
    private String roomID;
    @Column(nullable = false,unique = true)
    private String roomName;
//    @Column(nullable = true)
//    private int seatQuanlity;
    private String description;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "rTypeID")
    private RoomType roomType;
    
    @OneToMany(mappedBy = "room")
    private Set<TimeDetail> timeDetailList;

    public Room() {
    }

    public Room(String roomID, String roomName, String description, boolean status, RoomType roomType, Set<TimeDetail> timeDetailList) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.description = description;
        this.status = status;
        this.roomType = roomType;
        this.timeDetailList = timeDetailList;
    }

    /**
     * @return the roomID
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * @param roomID the roomID to set
     */
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    /**
     * @return the roomName
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * @param roomName the roomName to set
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
