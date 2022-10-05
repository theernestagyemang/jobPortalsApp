package com.ao.schoolerp.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
@Entity
public class HostelTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column(name = "term_of_payment")
    private String termOfPayment;
    
    @Column(length = 50, name = "payment_status")
    private String paymentStatus;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "allotment_date")
    private Date allotmentDate;
    @Column(name = "end_date")
    private Date endDate;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private AppUser enteredBy;

    @Column(name = "student_email",length = 100)
    private String studentEmail;

    @JoinColumn(name = "hostel_payment_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private HostelPayment hostelPayment;

    public HostelTransaction(
            Integer id, String name, String termOfPayment, String paymentStatus, String studentEmail, Date startDate,
            Date endDate, Date allotmentDate, AppUser enteredBy, HostelPayment hostelPayment) {
        this.id = id;
        this.name = name;
        this.termOfPayment = termOfPayment;
        this.paymentStatus = paymentStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hostelPayment = hostelPayment;
        this.studentEmail = studentEmail;
        this.enteredBy = enteredBy;
        this.allotmentDate = allotmentDate;
    }

    public HostelTransaction() {
    }

    public Date getAllotmentDate() {
        return allotmentDate;
    }

    public void setAllotmentDate(Date allotmentDate) {
        this.allotmentDate = allotmentDate;
    }


    public AppUser getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(AppUser enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public HostelPayment getHostelPayment() {
        return hostelPayment;
    }

    public void setHostelPayment(HostelPayment hostelPayment) {
        this.hostelPayment = hostelPayment;
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

    public String getTermOfPayment() {
        return termOfPayment;
    }

    public void setTermOfPayment(String termOfPayment) {
        this.termOfPayment = termOfPayment;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostelTransaction that = (HostelTransaction) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(termOfPayment, that.termOfPayment) && Objects.equals(paymentStatus, that.paymentStatus) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, termOfPayment, paymentStatus, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", termOfPayment='" + termOfPayment + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
