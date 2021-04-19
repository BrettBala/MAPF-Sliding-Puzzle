public class Piece {

    private int value;
    private int x; //length
    private int y; //height

    public Piece() {
        value = 0;
    }

    public Piece(Piece replace) {
        value = replace.value;
        x = replace.x;
        y = replace.y;

    }

    public Piece(int value) {
        this.value = 0;
    }

    public Piece(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }


    /**Getters and Setter*/////////////////////////////////////////////////////////////////////////
    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public int getX() { return x; }

    public void setX(int x) { this.x = x; }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }
}
