package com.ao.schoolerp.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class HostelBed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(unique = true)
    private String number;

    private BigDecimal price;

    @Column(length = 50)
    private String status;

    @Column(name = "term_of_payment")
    private String termOfPayment;

    @JoinColumn(name = "hostel_room_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private HostelRoom hostelRoom;

    public HostelBed(Integer id, String number, BigDecimal price, String status,
                     String termOfPayment, HostelRoom hostelRoom) {
        this.id = id;
        this.number = number;
        this.price = price;
        this.hostelRoom = hostelRoom;
        this.status = status;
        this.termOfPayment = termOfPayment;
    }

    public HostelBed() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HostelRoom getHostelRoom() {
        return hostelRoom;
    }

    public void setHostelRoom(HostelRoom hostelRoom) {
        this.hostelRoom = hostelRoom;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTermOfPayment() {
        return termOfPayment;
    }

    public void setTermOfPayment(String termOfPayment) {
        this.termOfPayment = termOfPayment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostelBed hostelBed = (HostelBed) o;
        return Objects.equals(id, hostelBed.id) && Objects.equals(number, hostelBed.number) && Objects.equals(price, hostelBed.price) && Objects.equals(status, hostelBed.status) && Objects.equals(termOfPayment, hostelBed.termOfPayment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, price, status, termOfPayment);
    }

    @Override
    public String toString() {
        return "HostelBed{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", termOfPayment='" + termOfPayment + '\'' +
                '}';
    }
}
