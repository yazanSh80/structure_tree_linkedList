package com.example.project2;
public class LocationNode {
    private String location;
    private LocationNode left;
    private LocationNode right;
    private int martyrList;

    public LocationNode() {
        super();
    }

    LocationNode(String location) {
        this.location = location;
        this.left = null;
        this.right = null;
    }

    public int getMartyrList() {
        return martyrList;
    }

    public void setMartyrList(int martyrList) {
        this.martyrList = martyrList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocationNode getLeft() {
        return left;
    }

    public void setLeft(LocationNode left) {
        this.left = left;
    }

    public LocationNode getRight() {
        return right;
    }

    public void setRight(LocationNode right) {
        this.right = right;
    }

}


