/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

/**
 *
 * @author Eman
 */
public class ProductEntity {
    private int productId;
    private int manufacturerId;
    private String productCode;
    private float purchaseCode;
    private int quantityOnHand;
    private float markup;
    private String description;

    public ProductEntity(int productId, int manufacturerId, String productCode, float purchaseCode, int quantityOnHand, float markup, String description) {
        this.productId = productId;
        this.manufacturerId = manufacturerId;
        this.productCode = productCode;
        this.purchaseCode = purchaseCode;
        this.quantityOnHand = quantityOnHand;
        this.markup = markup;
        this.description = description;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public float getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(float purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public float getMarkup() {
        return markup;
    }

    public void setMarkup(float markup) {
        this.markup = markup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
