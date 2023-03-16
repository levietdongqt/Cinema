
package POJO;
import java.sql.Date;
import java.time.LocalTime;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="Promotion")
public class Promotion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int promoID;
    private String promoName;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean status;
    @OneToMany(mappedBy = "promotion")
    private Set<Bill> billList;

    public Promotion(int promoID, String promoName, LocalTime startTime, LocalTime endTime, boolean status, Set<Bill> billList) {
        this.promoID = promoID;
        this.promoName = promoName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.billList = billList;
    }

    public Promotion() {
    }

    /**
     * @return the promoID
     */
    public int getPromoID() {
        return promoID;
    }

    /**
     * @param promoID the promoID to set
     */
    public void setPromoID(int promoID) {
        this.promoID = promoID;
    }

    /**
     * @return the promoName
     */
    public String getPromoName() {
        return promoName;
    }

    /**
     * @param promoName the promoName to set
     */
    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    /**
     * @return the startTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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
     * @return the billList
     */
    public Set<Bill> getBillList() {
        return billList;
    }

    /**
     * @param billList the billList to set
     */
    public void setBillList(Set<Bill> billList) {
        this.billList = billList;
    }
    
    
    
}
