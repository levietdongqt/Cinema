package POJO;

import java.util.Set;
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
    private String roomName;
    private int seatQuanlity;
    
    @ManyToOne
    @JoinColumn(name = "rTypeID")
    private RoomType roomType;
    
    @OneToMany(mappedBy = "room")
    private Set<TimeDetail> timeDetailList;

    public Room() {
    }

    public Room(String roomID, String roomName, int seatQuanlity, RoomType roomType, Set<TimeDetail> timeDetails) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.seatQuanlity = seatQuanlity;
        this.roomType = roomType;
        this.timeDetailList = timeDetails;
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
     * @return the seatQuanlity
     */
    public int getSeatQuanlity() {
        return seatQuanlity;
    }

    /**
     * @param seatQuanlity the seatQuanlity to set
     */
    public void setSeatQuanlity(int seatQuanlity) {
        this.seatQuanlity = seatQuanlity;
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
     * @return the timeDetails
     */
    public Set<TimeDetail> getTimeDetails() {
        return timeDetailList;
    }

    /**
     * @param timeDetails the timeDetails to set
     */
    public void setTimeDetails(Set<TimeDetail> timeDetails) {
        this.timeDetailList = timeDetails;
    }

   
}
