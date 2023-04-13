package POJO;

import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "Product")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product {
    @Id
    @Column(name = "productId")
    public String productId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private int price;

    @Column(name = "status")
    private Boolean status;
    
    private String imgUrl;
    
    @OneToMany(mappedBy = "product")
    private Set<ProductBill> productBills;

    public Product() {}

    public Product(String productId, String productName, String type, int price, Boolean status, Set<ProductBill> productBills) {
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.status = status;
        this.productBills = productBills;
    }
    
    /**
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return the status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Boolean status) {
        this.status = status;
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

    /**
     * @return the imgUrl
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * @param imgUrl the imgUrl to set
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public int hashCode() {
        return 100;
    }

    @Override
    public boolean equals(Object obj) {
        Product product = (Product) obj;
        
        return this.productName.equalsIgnoreCase(product.getProductName()); 
    }
    
    

    
    
}
