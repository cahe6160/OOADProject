package edu.colorado.mtoftheholycross;

public class Cell {
    private String surface;
    private String underwater;

    Cell() {

    }

    Cell(String surface, String underwater) {
        this.surface = surface;
        this.underwater = underwater;
    }

    public String getSurface() {
        return surface;
    }

    public String getUnderwater() {
        return underwater;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public void setUnderwater(String underwater) {
        this.underwater = underwater;
    }
}
