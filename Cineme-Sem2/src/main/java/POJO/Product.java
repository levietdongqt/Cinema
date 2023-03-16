/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    private String productID;
    private String productName;
    private String type;
    private Float cost;
    private Boolean status;
    
    @OneToMany(mappedBy = "ProductDetail")
    private Set<productDetail> productDetailList;
    
    public Product(){
    
    }

    public Product(String productID, String productName, String type, Float cost, Boolean status, Set<productDetail> productDetailList) {
        this.productID = productID;
        this.productName = productName;
        this.type = type;
        this.cost = cost;
        this.status = status;
        this.productDetailList = productDetailList;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<productDetail> getProductDetailList() {
        return productDetailList;
    }

    public void setProductDetailList(Set<productDetail> productDetailList) {
        this.productDetailList = productDetailList;
    }
    
    
}
