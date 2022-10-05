package com.ao.schoolerp.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Hostel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(unique = true)
    private String name;

    private String location;

    private String address;

    private String telephone;

    private String status;

    @Lob
    private String description;

    public Hostel(Integer id, String name, String location, String address,
                  String telephone, String status, String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.address = address;
        this.telephone = telephone;
        this.status = status;
        this.description = description;
    }


    public Hostel() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hostel hostel = (Hostel) o;
        return Objects.equals(id, hostel.id) && Objects.equals(name, hostel.name) && Objects.equals(location, hostel.location) && Objects.equals(address, hostel.address) && Objects.equals(telephone, hostel.telephone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, address, telephone);
    }

    @Override
    public String toString() {
        return "Hostel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}