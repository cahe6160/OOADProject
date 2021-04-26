package edu.colorado.mtoftheholycross;

/**
 * Destroyer class, which extends Ships
 * Destroyer differs in size, and captain location
 */
public class Destroyer extends Ship {

    public Destroyer(String head, String tail) {

        name = "Destroyer";
        this.head = head;
        this.tail = tail;


        if(this.head.substring(0, 1).equals(this.tail.substring(0, 1))) {
            if(Integer.parseInt(this.head.substring(1)) < Integer.parseInt(this.tail.substring(1))) {
                this.captainLocation = this.head.substring(0, 1) + (Integer.parseInt(this.head.substring(1)) + 1);
            } else {
                this.captainLocation = this.head.substring(0, 1) + (Integer.parseInt(this.head.substring(1)) - 1);
            }
        } else{
            if(this.head.charAt(0) < this.tail.charAt(0)) {
                this.captainLocation = (char)(this.head.charAt(0) + 1) + this.head.substring(1);
            } else {
                this.captainLocation = (char)(this.head.charAt(0) - 1) + this.head.substring(1);
            }
        }
    }
}
