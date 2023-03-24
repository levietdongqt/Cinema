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
    
    @OneToMany(mappedBy = "room")
    private Set<RoomTypeDetails> roomTypeDetailList;

    public Room() {
    }

    public Room(String roomID, String roomName, String description, Set<RoomTypeDetails> roomTypeDetailList) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.description = description;
        this.roomTypeDetailList = roomTypeDetailList;
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
     * @return the roomTypeDetailList
     */
    public Set<RoomTypeDetails> getRoomTypeDetailList() {
        return roomTypeDetailList;
    }

    /**
     * @param roomTypeDetailList the roomTypeDetailList to set
     */
    public void setRoomTypeDetailList(Set<RoomTypeDetails> roomTypeDetailList) {
        this.roomTypeDetailList = roomTypeDetailList;
    }

    
}
