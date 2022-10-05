package com.ao.schoolerp.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "hostel_payment")
public class HostelPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column(name = "amount_paid")
    private BigDecimal amountPaid;

    @Column(name = "payment_date")
    private Date paymentDate;

    private String studentEmail;
    @Lob
    private String description;

    @JoinColumn(name = "hostel_bed_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private HostelBed hostelBed;


    public HostelPayment(Integer id, String name, BigDecimal amountPaid, Date paymentDate,
                         String description, HostelBed hostelBed, String studentEmail) {
        this.id = id;
        this.name = name;
        this.amountPaid = amountPaid;
        this.hostelBed = hostelBed;
        this.studentEmail = studentEmail;
        this.paymentDate = paymentDate;
        this.description = description;
    }

    public HostelPayment() {
    }

    public HostelBed getHostelBed() {
        return hostelBed;
    }

    public void setHostelBed(HostelBed hostelBed) {
        this.hostelBed = hostelBed;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
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

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostelPayment that = (HostelPayment) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(amountPaid, that.amountPaid) && Objects.equals(paymentDate, that.paymentDate) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amountPaid, paymentDate, description);
    }

    @Override
    public String toString() {
        return "HostelPayment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amountPaid=" + amountPaid +
                ", paymentDate=" + paymentDate +
                ", description='" + description + '\'' +
                '}';
    }
}
