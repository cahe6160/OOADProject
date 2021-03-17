package edu.colorado.mtoftheholycross;

public class Submarine extends Ship {

    private String name;
    private String head;
    private String tail;
    private String captainLocation;
    private Boolean casualtyReported;

    public Submarine(String head, String tail) {
        casualtyReported = false;
        this.name = name;
        this.head = head;
        this.tail = tail;
        if(this.name.equals("Minesweeper")){
            this.captainLocation = head;
        } else if(this.name.equals("Destroyer")){
            if(this.head.substring(0, 1).equals(this.tail.substring(0, 1))) {
                this.captainLocation = this.head.substring(0, 1) + (Integer.parseInt(this.head.substring(1)) + 1);
            } else{
                this.captainLocation = (this.head.charAt(0) + 1) + this.head.substring(1);
            }
        } else {
            if(this.head.substring(0, 1).equals(this.tail.substring(0, 1))) {
                this.captainLocation = this.head.substring(0, 1) + (Integer.parseInt(this.head.substring(1)) + 2);
            } else{
                this.captainLocation = (this.head.charAt(0) + 2) + this.head.substring(1);
            }
        }
    }
}
