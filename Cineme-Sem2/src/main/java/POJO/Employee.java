package POJO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javassist.compiler.TokenId;
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
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    private String userName;
    @Column(nullable = false)
    private String empName;

    private String password;
    @Column(nullable = false)
    private String position;
    private LocalDate birthDate;
    private LocalDate startDate;
    private String email;
  
    private boolean status;

    public Employee(String userName, String empName, String password, String position, LocalDate birthDate, LocalDate startDate, String email, boolean status, String empPhone, Set<Bill> billList, Set<WorkSession> worksessionList) {
        this.userName = userName;
        this.empName = empName;
        this.password = password;
        this.position = position;
        this.birthDate = birthDate;
        this.startDate = startDate;
        this.email = email;
        this.status = status;
        this.empPhone = empPhone;
        this.billList = billList;
        this.worksessionList = worksessionList;
    }
    private String empPhone;
    @OneToMany(mappedBy = "employee")
    private Set<Bill> billList;

    @OneToMany(mappedBy = "employee")
    private Set<WorkSession> worksessionList;

    public Employee() {
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

    public void setUserName(String userName) throws IOException {
//        if ( userName.isEmpty()) {
//            throw new IOException("Username cannot be null or empty");
//        }
        if (userName.length() > 20 || userName.length() < 3) {
            throw new IOException("Username cannot be longer than 20 characters and less than 3 characters ");
        }

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
//    public void setEmpName(String empName)throws IOException  {
//        if (empName == null) {
//            throw new IOException("Employee name cannot be null");
//        }
//        if (!Pattern.matches("[a-zA-Z]+", empName)) {
//            throw new IOException("Employee name can only contain alphabetic characters");
//        }
//        this.empName = empName;
//    }
    public void setEmpName(String empName) throws IOException {
        if (empName.isEmpty()) {
            throw new IOException("Employee name cannot be empty");
        }
        if (!empName.matches("^[a-zA-Z ]+$")) {
            throw new IOException("Employee name can only contain alphabetic characters");
        }
        if (empName.length() < 6 || empName.length() > 30) {
            throw new IOException("Employee name must be between 6 and 30 char in length");
        }
        this.empName = empName;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) throws IOException {
        if (empPhone.isEmpty()) {
            throw new IOException("Phone number cannot be empty");
        }

        if (!Pattern.matches("\\d{10}", empPhone)) {
            throw new IOException("Phone number must be 10 digits");
        }
        this.empPhone = empPhone;
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
    public void setPassword(String password) throws IOException {
        if (password == null || password.trim().isEmpty()) {
            throw new IOException("Password cannot be null or empty");
        }
        if (password.length() > 20 || password.length() < 6) {
            throw new IOException("Password cannot be longer than 20 char and less than 6 char");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            this.password = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new IOException("Error while hashing the password");
        }
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
    public void setPosition(String position) throws IOException {
        if (position.equals("Choose a position")) {
            throw new IOException("Position must be Staff or Manager ");
        }
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
    public void setBirthDate(LocalDate birthDate) throws IOException {
        LocalDate today = LocalDate.now();
        long years = birthDate.until(today, ChronoUnit.YEARS);
        if (years < 18) {
            throw new IOException("Age must be at least 18.");
        }
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
    public void setStartDate(LocalDate startDate) throws IOException {
        if (startDate == null) {
            throw new IOException("Start date cannot null");
        } 
        if (startDate.isAfter(LocalDate.now())) {
            throw new IOException("Start date cannot be in the future");
        }
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
    public void setEmail(String email) throws IOException {
        String check = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (email.matches(check)) {
            this.email = email;
        } else {
            throw new IOException("Invalid email format.");
        }

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
