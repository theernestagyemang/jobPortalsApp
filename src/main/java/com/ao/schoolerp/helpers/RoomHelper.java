package com.ao.schoolerp.helpers;

public class RoomHelper {

    private Integer id;
    private String name;
    private Integer numberOfBeds;

    private String roomNumber;
    private String status;
    private Integer floorId;

    public RoomHelper(Integer id, String name, Integer numberOfBeds, String roomNumber, String status, Integer floorId) {
        this.id = id;
        this.name = name;
        this.numberOfBeds = numberOfBeds;
        this.roomNumber = roomNumber;
        this.status = status;
        this.floorId = floorId;
    }

    public RoomHelper() {
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

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }
}
