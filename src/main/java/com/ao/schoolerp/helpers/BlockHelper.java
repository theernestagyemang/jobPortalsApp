package com.ao.schoolerp.helpers;

import com.ao.schoolerp.entities.Hostel;

public class BlockHelper {

    private Integer id;

    private String name;

    private Integer numberOfFloors;

    private String description;

    private String status;

    private Integer hostelId;

    public BlockHelper(Integer id, String name, Integer numberOfFloors,
                       String description, String status, Integer hostelId) {
        this.id = id;
        this.name = name;
        this.numberOfFloors = numberOfFloors;
        this.description = description;
        this.status = status;
        this.hostelId = hostelId;
    }

    public BlockHelper() {
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

    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(Integer numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getHostelId() {
        return hostelId;
    }

    public void setHostelId(Integer hostelId) {
        this.hostelId = hostelId;
    }
}
