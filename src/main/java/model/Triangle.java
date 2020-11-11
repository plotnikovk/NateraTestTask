package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@ToString
@JsonAutoDetect
public class Triangle {
    private String id = "";

    private BigDecimal firstSide;
    private BigDecimal secondSide;
    private BigDecimal thirdSide;

    public Triangle() {}

    public Triangle(BigDecimal firstSide, BigDecimal secondSide, BigDecimal thirdSide){
        this.firstSide = firstSide;
        this.secondSide = secondSide;
        this.thirdSide = thirdSide;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getFirstSide() {
        return firstSide;
    }

    public void setFirstSide(BigDecimal firstSide) {
        this.firstSide = firstSide;
    }

    public BigDecimal getSecondSide() {
        return secondSide;
    }

    public void setSecondSide(BigDecimal secondSide) {
        this.secondSide = secondSide;
    }

    public BigDecimal getThirdSide() {
        return thirdSide;
    }

    public void setThirdSide(BigDecimal thirdSide) {
        this.thirdSide = thirdSide;
    }

    public BigDecimal calcPerimeter(){
        BigDecimal result = null;
        if (exists()){
            result = firstSide.add(secondSide).add(thirdSide);
        }
        return result;
    }

    public BigDecimal calcArea(){
        BigDecimal result = null;
        if (exists()) {
            BigDecimal halfPerimeter = calcPerimeter().divide(BigDecimal.valueOf(2));
            result = halfPerimeter.multiply(halfPerimeter.subtract(firstSide))
                    .multiply(halfPerimeter.subtract(secondSide))
                    .multiply(halfPerimeter.subtract(thirdSide))
                    .sqrt(new MathContext(16))
            ;
        }
        return result.setScale(15, RoundingMode.HALF_UP);
    }

    public boolean exists(){
        return (firstSide.add(secondSide).compareTo(thirdSide) == 1 &&
                secondSide.add(thirdSide).compareTo(firstSide) == 1 &&
                thirdSide.add(firstSide).compareTo(secondSide) == 1);
    }

    public String jsonPost(){
        String result = "{\"input\": \"" + firstSide.toString() + ";" + secondSide.toString() + ";" + thirdSide.toString() + "\"}";
        return result;
    }
}
