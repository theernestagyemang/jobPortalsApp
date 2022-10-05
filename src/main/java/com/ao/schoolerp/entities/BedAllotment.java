package com.ao.schoolerp.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class BedAllotment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Date allotmentDate;

    @JoinColumn(name = "hostel_bed_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private HostelBed hostelBed;

    public BedAllotment(Integer id, Date allotmentDate) {
        this.id = id;
        this.allotmentDate = allotmentDate;
    }

    public BedAllotment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAllotmentDate() {
        return allotmentDate;
    }

    public void setAllotmentDate(Date allotmentDate) {
        this.allotmentDate = allotmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BedAllotment that = (BedAllotment) o;
        return Objects.equals(id, that.id) && Objects.equals(allotmentDate, that.allotmentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, allotmentDate);
    }

    @Override
    public String toString() {
        return "BedAllotment{" +
                "id=" + id +
                ", allotmentDate=" + allotmentDate +
                '}';
    }
}
