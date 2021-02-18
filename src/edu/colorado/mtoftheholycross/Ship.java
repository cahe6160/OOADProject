package edu.colorado.mtoftheholycross;
// This is the  baseclass for your ship.  Modify accordingly
// TODO: practice good OO design
public class Ship {
    private String name;
    private int shipLength;
    String[] positionsHeld;
    private String Head;
    private String Tail;

    public Ship() { }

    public Ship(String name, int shipLength, String Head, String Tail) {
        this.name = name;
        this.shipLength = shipLength;
        this.Head = Head;
        this.Tail = Tail;
    }

    public String getName() {
        return name;
    }

    public int getShipLength() {
        return shipLength;
    }

    public String getHead() {
        return Head;
    }

    public String getTail() {
        return Tail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShipLength(int shipLength) {
        this.shipLength = shipLength;
    }

    public void setHead(String Head) {
        this.Head = Head;
    }

    public void setTail(String tail) {
        this.Tail = tail;
    }

    //Team mtoftheholycross, pair 1 was here
    // Team mtoftheholycross, pair 2 was here

    public  void show()     {     // dunno why this is here maybe it is just an example method
        System.out.println("IF you can't see this then something is severely wrong!!");
    }
}
