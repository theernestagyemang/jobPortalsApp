package com.ao.schoolerp.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class HostelFloor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;

    @Column(length = 50)
    private String status;

    @JoinColumn(name = "hostel_block_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private HostelBlock hostelBlock;

    public HostelFloor(Integer id, String name, Integer numberOfRooms, String status, HostelBlock hostelBlock) {
        this.id = id;
        this.name = name;
        this.numberOfRooms = numberOfRooms;
        this.status = status;
        this.hostelBlock = hostelBlock;
    }

    public HostelFloor() {
    }

    public Integer getId() {
        return id;
    }

    public HostelBlock getHostelBlock() {
        return hostelBlock;
    }

    public void setHostelBlock(HostelBlock hostelBlock) {
        this.hostelBlock = hostelBlock;
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

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
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
        HostelFloor that = (HostelFloor) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(numberOfRooms, that.numberOfRooms) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberOfRooms, status);
    }

    @Override
    public String toString() {
        return "HostelFloor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", status='" + status + '\'' +
                '}';
    }
}
