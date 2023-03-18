package POJO;

import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @Column(name = "productid")
    private String productId;

    @Column(name = "productname")
    private String productName;

    @Column(name = "type")
    private String type;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "product")
    private Set<ProductDetail> productDetails;

    public Product() {}

    public Product(String productId, String productName, String type, BigDecimal cost, Boolean status, Set<ProductDetail> productDetails) {
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.cost = cost;
        this.status = status;
        this.productDetails = productDetails;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(Set<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }
    
}
