package POJO;

import javax.persistence.*;

@Entity
@Table(name = "productDetail")
public class productDetail {
    @Id
    private String detailCode;
    private String productCode;
    private String billCode;
    private Integer quantity;
    
    @ManyToOne
    @JoinColumn(name = "productID")
    private productDetail ProductDetail;

//    @ManyToOne
//    @JoinColumn(name = "billID")
    
    public productDetail(){}
    
    public productDetail(String detailCode, String productCode, String billCode, Integer quantity, productDetail ProductDetail) {
        this.detailCode = detailCode;
        this.productCode = productCode;
        this.billCode = billCode;
        this.quantity = quantity;
        this.ProductDetail = ProductDetail;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public productDetail getProductDetail() {
        return ProductDetail;
    }

    public void setProductDetail(productDetail ProductDetail) {
        this.ProductDetail = ProductDetail;
    }
    
    
}
