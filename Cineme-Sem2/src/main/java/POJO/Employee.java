
package POJO;


import java.sql.Date;
import java.time.LocalDate;
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
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    private String userName;
    @Column(nullable = false)
    private  String empName;
    
    private String password;
    @Column(nullable = false)
    private String position;
    private LocalDate birthDate;
    private LocalDate startDate;
    private String email;
    private boolean status ;
    
    @OneToMany(mappedBy = "employee")
    private Set<Bill> billList;

    @OneToMany(mappedBy = "employee")
    private Set<WorkSession> worksessionList;

    public Employee() {
    }

    public Employee(String userName, String empName, String password, String position, LocalDate birthDate, LocalDate startDate, String email, boolean status, Set<Bill> billList, Set<WorkSession> worksessionList) {
        this.userName = userName;
        this.empName = empName;
        this.password = password;
        this.position = position;
        this.birthDate = birthDate;
        this.startDate = startDate;
        this.email = email;
        this.status = status;
        this.billList = billList;
        this.worksessionList = worksessionList;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the empName
     */
    public String getEmpName() {
        return empName;
    }

    /**
     * @param empName the empName to set
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the birthDate
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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

    /**
     * @return the worksessionList
     */
    public Set<WorkSession> getWorksessionList() {
        return worksessionList;
    }

    /**
     * @param worksessionList the worksessionList to set
     */
    public void setWorksessionList(Set<WorkSession> worksessionList) {
        this.worksessionList = worksessionList;
    }
    
    
    
}
