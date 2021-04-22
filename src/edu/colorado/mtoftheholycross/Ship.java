package edu.colorado.mtoftheholycross;

public class Ship {

    protected String name;
    protected String head;
    protected String tail;
    protected String captainLocation;
    protected boolean casualtyReported = false;
    protected boolean submerged = false;

    public String getName() {
        return name;
    }

    public String getHead() { return head; }

    public String getTail() { return tail; }

    public String getCaptainLocation() {
        return captainLocation;
    }

    public boolean getCasualtyReported(){
        return casualtyReported;
    }

    public boolean getSubmerged() {
        return submerged;
    }

    public void setCasualtyReported(Boolean casualtyReported) {
        this.casualtyReported = casualtyReported;
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

    public void setCaptainLocation(String captainLocation) {
        this.captainLocation = captainLocation;
    }

    public void setSubmerged(boolean submerged) {
        this.submerged = submerged;
    }
}