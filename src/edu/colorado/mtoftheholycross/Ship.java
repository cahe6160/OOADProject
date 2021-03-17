package edu.colorado.mtoftheholycross;
// This is the  baseclass for your ship.  Modify accordingly
// TODO: practice good OO design
public class Ship {
    protected String name;
    protected String head;
    protected String tail;
    protected String captainLocation;
    protected Boolean casualtyReported;

//    public Ship(String name, String head, String tail, String captainLocation) {
//        casualtyReported = false;
//        this.name = name;
//        this.head = head;
//        this.tail = tail;
//        if(this.name.equals("Minesweeper")){
//            this.captainLocation = head;
//        } else if(this.name.equals("Destroyer")){
//            if(this.head.substring(0, 1).equals(this.tail.substring(0, 1))) {
//                this.captainLocation = this.head.substring(0, 1) + (Integer.parseInt(this.head.substring(1)) + 1);
//            } else{
//                this.captainLocation = (this.head.charAt(0) + 1) + this.head.substring(1);
//            }
//        } else {
//            if(this.head.substring(0, 1).equals(this.tail.substring(0, 1))) {
//                this.captainLocation = this.head.substring(0, 1) + (Integer.parseInt(this.head.substring(1)) + 2);
//            } else{
//                this.captainLocation = (this.head.charAt(0) + 2) + this.head.substring(1);
//            }
//        }
//    }

    public String getName() {
        return name;
    }

    public String getHead() {
        return head;
    }

    public String getTail() {
        return tail;
    }

    public String getCaptainLocation() {
        return captainLocation;
    }


    public Boolean getCasualtyReported(){
        return casualtyReported;
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
}