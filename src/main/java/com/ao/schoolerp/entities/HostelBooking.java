package com.ao.schoolerp.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class HostelBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String Type;

    private Date bookingDate;

    @JoinColumn(name = "hostel_bed_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private HostelBed hostelBed;

    public HostelBooking(Integer id, String type, Date bookingDate, HostelBed hostelBed) {
        this.id = id;
        Type = type;
        this.hostelBed = hostelBed;
        this.bookingDate = bookingDate;
    }

    public HostelBooking() {
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostelBooking that = (HostelBooking) o;
        return Objects.equals(id, that.id) && Objects.equals(Type, that.Type) && Objects.equals(bookingDate, that.bookingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Type, bookingDate);
    }

    @Override
    public String toString() {
        return "HostelBooking{" +
                "id=" + id +
                ", Type='" + Type + '\'' +
                ", bookingDate=" + bookingDate +
                '}';
    }
}
