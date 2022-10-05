package com.debusey.smart.pos.smartpos.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "package_subscription")
public class PackageSubscribe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;
    private String comment1;
    private String comment2;
    private String comment3;
    private String comment4;
    private String comment5;
    private String comment6;

    public PackageSubscribe(Integer id, String name, Double price, String comment1, String comment2,
                            String comment3, String comment4, String comment5, String comment6) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.comment1 = comment1;
        this.comment2 = comment2;
        this.comment3 = comment3;
        this.comment4 = comment4;
        this.comment5 = comment5;
        this.comment6 = comment6;
    }

    public PackageSubscribe() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public String getComment2() {
        return comment2;
    }

    public void setComment2(String comment2) {
        this.comment2 = comment2;
    }

    public String getComment3() {
        return comment3;
    }

    public void setComment3(String comment3) {
        this.comment3 = comment3;
    }

    public String getComment4() {
        return comment4;
    }

    public void setComment4(String comment4) {
        this.comment4 = comment4;
    }

    public String getComment5() {
        return comment5;
    }

    public void setComment5(String comment5) {
        this.comment5 = comment5;
    }

    public String getComment6() {
        return comment6;
    }

    public void setComment6(String comment6) {
        this.comment6 = comment6;
    }
}
