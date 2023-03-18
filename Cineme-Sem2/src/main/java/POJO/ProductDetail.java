package POJO;

import javax.persistence.*;

@Entity
@Table(name = "productDetail")
public class ProductDetail {

    @Id
    @Column(name = "detailCode")
    private String detailCode;

    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "billCode")
    private Bill bill;

    @Column(name = "quantity")
    private Integer quantity;

    public ProductDetail() {
        
    }

    public ProductDetail(String detailCode, Product product, Bill bill, Integer quantity) {
        this.detailCode = detailCode;
        this.product = product;
        this.bill = bill;
        this.quantity = quantity;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    
    
}
