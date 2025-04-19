package com.example.project2;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Stack {
    private int size;
    private DistrictNode Front;

    public Stack() {
        Front = null;
        this.size = size;

    }

    public void push(Object object) {
        DistrictNode newNode;
        newNode = new DistrictNode();
        newNode.setNext(Front);
        Front = newNode;
        size++;/// add one increment in the size
    }

    public Object pop() {
        if (!isEmpty()) {
            DistrictNode top = Front;
            Front = Front.getNext();//
            size--;
            return top.getItem();
        } else
            return null;
    }

    public Object peek() {
        if (!isEmpty())
            return Front.getItem();
        else
            return null;
    }

    public int Size() {
        return size;
    }

    public boolean isEmpty() {
        return (Front == null);
    }

    public void Display() {

        DistrictNode current = Front;
        while (current != null) {
            System.out.println(current.getItem() + " ");
            current = current.getNext();

        }
        System.out.println();

    }

    public void clear(TextField nextTextField, TextField prevTextField, TextArea messageArea) {
        nextTextField.clear();
        prevTextField.clear();
        messageArea.clear();
    }

}

