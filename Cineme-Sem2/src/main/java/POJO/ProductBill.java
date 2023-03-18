package POJO;

import javax.persistence.*;

@Entity
@Table(name = "productBill")
public class ProductBill {

    @Id
    @Column(name = "proBillID")
    private String proBillID;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "billID")
    private Bill bill;

    @Column(name = "quantity")
    private Integer quantity;

    public ProductBill() {
    }

    public ProductBill(String proBillID, Product product, Bill bill, Integer quantity) {
        this.proBillID = proBillID;
        this.product = product;
        this.bill = bill;
        this.quantity = quantity;
    }

    /**
     * @return the proBillID
     */
    public String getProBillID() {
        return proBillID;
    }

    /**
     * @param proBillID the proBillID to set
     */
    public void setProBillID(String proBillID) {
        this.proBillID = proBillID;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the bill
     */
    public Bill getBill() {
        return bill;
    }

    /**
     * @param bill the bill to set
     */
    public void setBill(Bill bill) {
        this.bill = bill;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    
}
