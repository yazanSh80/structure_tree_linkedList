package com.example.project2;

import java.util.Date;

public class MartyrTree {
    private Date date;
    private MartyrList martyrList;
    private MartyrTree left;
    private MartyrTree right;

    public MartyrTree(Date date) {
        this.date = date;
        this.martyrList = new MartyrList();
    }

    public MartyrTree() {
        super();
    }

    public MartyrTree(Date date, MartyrList martyrList, MartyrTree left, MartyrTree right) {
        super();
        this.date = date;
        this.martyrList = martyrList;
        this.left = left;
        this.right = right;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MartyrList getMartyrList() {
        return martyrList;
    }

    public void setMartyrList(MartyrList martyrList) {
        this.martyrList = martyrList;
    }

    public MartyrTree getLeft() {
        return left;
    }

    public void setLeft(MartyrTree left) {
        this.left = left;
    }

    public MartyrTree getRight() {
        return right;
    }

    public void setRight(MartyrTree right) {
        this.right = right;
    }

    public Martyr getMartyr() {
        return getMartyr();
    }


}
