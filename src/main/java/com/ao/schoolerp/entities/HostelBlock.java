package com.ao.schoolerp.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class HostelBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column(name = "number_of_floors")
    private Integer numberOfFloors;

    @Lob
    private String description;

    @Column(length = 50)
    private String status;

    @JoinColumn(name = "hostel_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private Hostel hostel;

    public HostelBlock(Integer id, String name, Integer numberOfFloors,
                       String status, Hostel hostel, String description) {
        this.id = id;
        this.name = name;
        this.hostel = hostel;
        this.numberOfFloors = numberOfFloors;
        this.status = status;
        this.description = description;
    }

    public HostelBlock() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(Integer numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostelBlock that = (HostelBlock) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(numberOfFloors, that.numberOfFloors) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberOfFloors, status);
    }

    @Override
    public String toString() {
        return "HostelBlock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberOfFloors=" + numberOfFloors +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
