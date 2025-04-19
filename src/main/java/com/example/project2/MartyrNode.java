package com.example.project2;

public class MartyrNode {
    private Martyr martyr;
    private MartyrNode next;

    public MartyrNode(Martyr martyr) {
        this.martyr = martyr;
        this.next = null;
    }

    public Martyr getMartyr() {
        return martyr;
    }

    public void setMartyr(Martyr martyr) {
        this.martyr = martyr;
    }

    public MartyrNode getNext() {
        return next;
    }

    public void setNext(MartyrNode next) {
        this.next = next;
    }
}
