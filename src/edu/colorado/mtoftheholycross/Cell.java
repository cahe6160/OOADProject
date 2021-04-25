package edu.colorado.mtoftheholycross;

/**
 * The cell class allows us to make every grid position contina two values:
 * The surface status, and the underwater status.
 */
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
