package com.ao.schoolerp.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class HostelRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column(name = "number_of_beds")
    private Integer numberOfBeds;

    private String roomNumber;
    @Column(length = 50)
    private String status;

    @JoinColumn(name = "hostel_floor_id", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private HostelFloor hostelFloor;

    public HostelRoom(Integer id, String name, Integer numberOfBeds, String roomNumber,
                      String status, HostelFloor hostelFloor) {
        this.id = id;
        this.name = name;
        this.hostelFloor = hostelFloor;
        this.roomNumber = roomNumber;
        this.numberOfBeds = numberOfBeds;
        this.status = status;
    }

    public HostelRoom() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HostelFloor getHostelFloor() {
        return hostelFloor;
    }

    public void setHostelFloor(HostelFloor hostelFloor) {
        this.hostelFloor = hostelFloor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
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
        HostelRoom that = (HostelRoom) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(numberOfBeds, that.numberOfBeds) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberOfBeds, status);
    }

    @Override
    public String toString() {
        return "HostelRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberOfBeds=" + numberOfBeds +
                ", status='" + status + '\'' +
                '}';
    }
}
