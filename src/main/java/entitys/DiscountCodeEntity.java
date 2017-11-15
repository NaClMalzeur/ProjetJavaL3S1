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
public class DiscountCodeEntity {
    
    private String discountCode;
    private float rate;

    public DiscountCodeEntity(String discountCode, float rate) {
        this.discountCode = discountCode;
        this.rate = rate;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
    
    
    
    
}
