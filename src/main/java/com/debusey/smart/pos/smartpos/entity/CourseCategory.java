/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.entity;


/**
 * @author AlhassanHussein
 */


import javax.persistence.*;
import java.io.Serializable;

/**
 * @author SL002
 */
@Entity
@Table(name = "course_category", uniqueConstraints = {@UniqueConstraint(columnNames = {"transaction_id"})})
public class CourseCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    @Column(name = "transaction_id")
    private String transactionId;
    @Lob
    private String description;
    @Column(columnDefinition = "varchar(50) default 'team.jpg'")
    private String fileName;
    private Boolean showPic;
    private String status;

    public CourseCategory() {
    }

    public CourseCategory(Integer id, String name, String transactionId, String description, String fileName, Boolean showPic, String status) {
        this.id = id;
        this.name = name;
        this.transactionId = transactionId;
        this.description = description;
        this.fileName = fileName;
        this.showPic = showPic;
        this.status = status;
    }


    public CourseCategory(Integer id) {
        this.id = id;
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


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getShowPic() {
        return showPic;
    }

    public void setShowPic(Boolean showPic) {
        this.showPic = showPic;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Course)) {
            return false;
        }
        CourseCategory other = (CourseCategory) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
