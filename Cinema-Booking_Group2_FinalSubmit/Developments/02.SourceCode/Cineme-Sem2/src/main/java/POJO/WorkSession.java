
package POJO;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "WorkSession")
public class WorkSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sessionID ;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    @ManyToOne
    @JoinColumn(name = "userName")
    private Employee employee ;
    
     @Transient
     private double totalWorkTime;
    
     public WorkSession() {
    }

    public WorkSession(int sessionID, LocalDateTime startTime, LocalDateTime endTime, Employee employee , double totalWorkTime ) {
        this.sessionID = sessionID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee;
        this.totalWorkTime = totalWorkTime;
    }
    
    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

   public double getTotalWorkTime() {
        return totalWorkTime;
    }

    public void setTotalWorkTime(double totalWorkTime) {
     this.totalWorkTime = totalWorkTime;
    }

   
}
