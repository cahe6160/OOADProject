package edu.colorado.mtoftheholycross;

public class Destroyer extends Ship {

    public Destroyer(String head, String tail) {
        casualtyReported = false;
        name = "Destroyer";
        this.head = head;
        this.tail = tail;
        if(this.head.substring(0, 1).equals(this.tail.substring(0, 1))) {
            this.captainLocation = this.head.substring(0, 1) + (Integer.parseInt(this.head.substring(1)) + 1);
        } else{
            this.captainLocation = (this.head.charAt(0) + 1) + this.head.substring(1);
        }
    }
}
