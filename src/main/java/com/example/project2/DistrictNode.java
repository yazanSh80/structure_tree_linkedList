package com.example.project2;

import java.util.Date;

public class DistrictNode {
    private String district;
    private DistrictNode left;
    private DistrictNode right;
    private Object item;
    private DistrictNode next;
    private LocationBinaryTree locatoinList;
    public int martyrs;

    public LocationBinaryTree getLocatoinList() {
        return locatoinList;
    }

    public DistrictNode() {
        super();
    }

    public DistrictNode(String district, DistrictNode left, DistrictNode right, Object item, DistrictNode next,
                        LocationBinaryTree locatoinList) {
        super();
        this.district = district;
        this.left = left;
        this.right = right;
        this.item = item;
        this.next = next;
        this.locatoinList = locatoinList;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public DistrictNode getNext() {
        return next;
    }

    public void setNext(DistrictNode next) {
        this.next = next;
    }

    public void setLocatoinList(LocationBinaryTree locatoinList) {
        this.locatoinList = locatoinList;
    }

    public DistrictNode(String district) {
        this.district = district;
        this.left = null;
        this.right = null;
    }

    // Getters and setters for the node
    public String getDistrict() {
        return district;
    }

    public DistrictNode getLeft() {
        return left;
    }

    public void setLeft(DistrictNode left) {
        this.left = left;
    }

    public DistrictNode getRight() {
        return right;
    }

    public void setRight(DistrictNode right) {
        this.right = right;
    }

    public void setDistrict(String district2) {

    }

    public Date getData() {
        return null;
    }

}

