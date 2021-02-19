package edu.colorado.mtoftheholycross;
// This is the  baseclass for your ship.  Modify accordingly
// TODO: practice good OO design
public class Ship {
    private
        String name;
        String head;
        String tail;

    public Ship(String name, String head, String tail) {
        this.name = name;
        this.head = head;
        this.tail = tail;
    }

    public String getName() {
        return name;
    }

    public String getHead() {
        return head;
    }

    public String getTail() {
        return tail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }
}
