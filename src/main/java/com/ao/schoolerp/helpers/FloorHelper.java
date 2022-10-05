package com.ao.schoolerp.helpers;


public class FloorHelper {

    private Integer id;
    private String name;
    private Integer numberOfRooms;
    private String status;
    private Integer blockId;

    public FloorHelper(Integer id, String name, Integer numberOfRooms, String status, Integer blockId) {
        this.id = id;
        this.name = name;
        this.numberOfRooms = numberOfRooms;
        this.status = status;
        this.blockId = blockId;
    }

    public FloorHelper() {
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

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }
}
