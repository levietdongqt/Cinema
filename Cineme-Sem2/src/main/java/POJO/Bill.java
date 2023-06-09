
package POJO;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billID;
    
    private LocalDateTime printDate;
    
    private float exchangePoints;
    
    @ManyToOne
    @JoinColumn(name="promoID")  //promoID là tên khoá ngoại của bảng Bill trong database
    private Promotion promotion;
    
    @ManyToOne
    @JoinColumn(name = "cusPhone")
    private Customer customer ;
    
    @ManyToOne
    @JoinColumn(name = "userName")
    private Employee employee ;
    @OneToMany(mappedBy = "bill")
    private Set<Ticket> tickets;
    
    @OneToMany(mappedBy = "bill")
    private Set<ProductBill> productBills;
    
    public Bill() {
    }

    public Bill(int billID, LocalDateTime printDate, float exchangePoints, BigDecimal finalTotal, Promotion promotion, Customer customer, Employee employee) {
        this.billID = billID;
        this.printDate = printDate;
        this.exchangePoints = exchangePoints;
        this.promotion = promotion;
        this.customer = customer;
        this.employee = employee;
    }

    /**
     * @return the billID
     */
    public int getBillID() {
        return billID;
    }

    /**
     * @param billID the billID to set
     */
    public void setBillID(int billID) {
        this.billID = billID;
    }

    /**
     * @return the printDate
     */
    public LocalDateTime getPrintDate() {
        return printDate;
    }

    /**
     * @param printDate the printDate to set
     */
    public void setPrintDate(LocalDateTime printDate) {
        this.printDate = printDate;
    }

    /**
     * @return the exchangePoints
     */
    public float getExchangePoints() {
        return exchangePoints;
    }

    /**
     * @param exchangePoints the exchangePoints to set
     */
    public void setExchangePoints(float exchangePoints) {
        this.exchangePoints = exchangePoints;
    }

    /**
     * @return the finalTotal
     */
    /**
     * @return the promotion
     */
    public Promotion getPromotion() {
        return promotion;
    }

    /**
     * @param promotion the promotion to set
     */
    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * @return the tickets
     */
    public Set<Ticket> getTickets() {
        return tickets;
    }

    /**
     * @param tickets the tickets to set
     */
    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    /**
     * @return the productBills
     */
    public Set<ProductBill> getProductBills() {
        return productBills;
    }

    /**
     * @param productBills the productBills to set
     */
    public void setProductBills(Set<ProductBill> productBills) {
        this.productBills = productBills;
    }
    
    
}
    