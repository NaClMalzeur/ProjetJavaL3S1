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
public class MicroMarketEntity {
    private String zipCode;
    private float radius;
    private float areaLength;
    private float areaWidth;

    public MicroMarketEntity(String zipCode, float radius, float areaLength, float areaWidth) {
        this.zipCode = zipCode;
        this.radius = radius;
        this.areaLength = areaLength;
        this.areaWidth = areaWidth;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getAreaLength() {
        return areaLength;
    }

    public void setAreaLength(float areaLength) {
        this.areaLength = areaLength;
    }

    public float getAreaWidth() {
        return areaWidth;
    }

    public void setAreaWidth(float areaWidth) {
        this.areaWidth = areaWidth;
    }
    
    
}
