
package POJO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    private String cusPhone ;
    @Column(nullable = false)
    private String customerName;
    private String address;
    private LocalDate birthDate;
    private String email;
    private int totalPoints;
    
    @OneToMany(mappedBy = "customer")
    private Set<Bill> billList;

    public Customer() {
    }

    public Customer(String cusPhone, String customerName, String address, LocalDate birthDate, String email, int totalPoints, Set<Bill> billList) {
        this.cusPhone = cusPhone;
        this.customerName = customerName;
        this.address = address;
        this.birthDate = birthDate;
        this.email = email;
        this.totalPoints = totalPoints;
        this.billList = billList;
    }

    /**
     * @return the cusPhone
     */
    public String getCusPhone() {
        return cusPhone;
    }

    /**
     * @param cusPhone the cusPhone to set
     */
    public void setCusPhone(String cusPhone) throws Exception {
        if (cusPhone.isEmpty()) {
            throw new Exception("Phone number cannot be empty");
        }

        if (!Pattern.matches("\\d{10,}", cusPhone)) {
            throw new Exception("Phone number must be atleast 10 digits");
        }
        this.cusPhone = cusPhone;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) throws Exception {
        if (customerName.isEmpty()) {
            throw new Exception("Customer name cannot be empty");
        }
        if (!customerName.matches("^[a-zA-Z ]+$")) {
            throw new Exception("Customer name can only contain alphabetic characters");
        }
        if (customerName.length() < 6 || customerName.length() > 30) {
            throw new Exception("Customer name must be between 6 and 30 char in length");
        }
        this.customerName = customerName;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) throws Exception {
        if (address.isEmpty()) {
            throw new Exception("Address cant be empty");
        }
        if (address.length() < 6 || address.length() > 30) {
            throw new Exception("Address must be between 6 and 30 char in length");
        }
        this.address = address;
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
    public void setBirthDate(LocalDate birthDate) throws Exception {
        LocalDate today = LocalDate.now();
        
        long years = birthDate.until(today, ChronoUnit.YEARS);
        if (years < 18) {
            throw new Exception("Age must be at least 18.");
        }
        this.birthDate = birthDate;
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
    public void setEmail(String email) throws Exception {
        String check = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (email.matches(check)) {
            this.email = email;
        } else {
            throw new Exception("Invalid email format.");
        }
    }

    /**
     * @return the totalPoints
     */
    public int getTotalPoints() {
        return totalPoints;
    }

    /**
     * @param totalPoints the totalPoints to set
     */
    public void setTotalPoints(int totalPoints) throws Exception {
        if (totalPoints < 0) {
            throw new Exception("Total Points must be 0 or higer");
        }
        this.totalPoints = totalPoints;
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
