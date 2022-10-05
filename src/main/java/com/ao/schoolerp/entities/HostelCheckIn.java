package com.ao.schoolerp.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "hostel_check_in")
public class HostelCheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "checkin_date")
    private Date checkInDate;

    @Column(name = "checkin_time")
    private Time checkInTime;

    @Column(name = "checkout_date")
    private Date checkOutDate;

    @Column(name = "checkout_time")
    private Time checkOutTime;

    @Lob
    private String remarks;

    @JoinColumn(name = "hostel_bed_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private HostelBed hostelBed;

    @JoinColumn(name = "hostel_transaction_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private HostelTransaction hostelTransaction;

    @JoinColumn(name = "hostel_payment_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private HostelPayment hostelPayment;

    public HostelCheckIn(Integer id, Date checkInDate, Time checkInTime, Date checkOutDate,
                         Time checkOutTime, String remarks, HostelPayment hostelPayment,
                         HostelBed hostelBed, HostelTransaction hostelTransaction) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkOutDate = checkOutDate;
        this.checkOutTime = checkOutTime;
        this.hostelPayment = hostelPayment;
        this.hostelBed = hostelBed;
        this.hostelTransaction = hostelTransaction;
        this.remarks = remarks;
    }

    public HostelCheckIn() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HostelBed getHostelBed() {
        return hostelBed;
    }

    public void setHostelBed(HostelBed hostelBed) {
        this.hostelBed = hostelBed;
    }

    public HostelTransaction getHostelTransaction() {
        return hostelTransaction;
    }

    public void setHostelTransaction(HostelTransaction hostelTransaction) {
        this.hostelTransaction = hostelTransaction;
    }

    public HostelPayment getHostelPayment() {
        return hostelPayment;
    }

    public void setHostelPayment(HostelPayment hostelPayment) {
        this.hostelPayment = hostelPayment;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Time getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Time checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Time getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Time checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostelCheckIn that = (HostelCheckIn) o;
        return Objects.equals(id, that.id) && Objects.equals(checkInDate, that.checkInDate) && Objects.equals(checkInTime, that.checkInTime) && Objects.equals(checkOutDate, that.checkOutDate) && Objects.equals(checkOutTime, that.checkOutTime) && Objects.equals(remarks, that.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checkInDate, checkInTime, checkOutDate, checkOutTime, remarks);
    }


    @Override
    public String toString() {
        return "HostelCheckIn{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkInTime=" + checkInTime +
                ", checkOutDate=" + checkOutDate +
                ", checkOutTime=" + checkOutTime +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
