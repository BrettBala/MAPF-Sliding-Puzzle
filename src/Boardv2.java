import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//TODO: ISSUE, OBJECTS ARE COPIED BY REFERENCE IN JAVA, NOT VALUE
public class Boardv2 {

    /*
    Board is set as Board[x][y] with (0,0) being the bottom left corner

    No Args Default Board is as follows:

                {5, 3, 7},
                {1, 4, 8},
                {2, 6, 0}



     */




    private int length; //Length of board, shrinks as we solve
    private int height; //Height of board, shrinks as we solve
    private int n; //Total n by n size of board
    private int numBlanks = 1; //Number of blanks, currently dealing with 1
    //private ArrayList<Piece> Blanks; //Stores a copies of all blank pieces, and thus their location
    private int blankX;
    private int blankY;
    private int Blank; //Will be using this since we will only have 1 blank for the time being

    public int[][] board;

    /**No arg constructor*/
    public Boardv2() {
        this.length = 3;
        this.height = 3;
        this.n = 3;
        board = new int[n][n];

        ArrayList<Integer> tempBoard = new ArrayList<Integer>(Arrays.asList( //Default board
                5, 3, 7,
                1, 4, 8,
                2, 6, 0
        ));

        Piece temp; //temp piece to store newest piece to check if it is 0

        //Set the board and blank
        for (int y = 2; y >= 0; y--) {
            for (int x = 0; x < 3; x++) {
                board[x][y] = (tempBoard.get(0));
                if (board[x][y] == 0) { //If the temp is 0, set blank to that
                    blankX = x;
                    blankY = y;
                }
                tempBoard.remove(0);
            }
        }

        return;
    }


    /**Constructor*/
    public Boardv2(int num) {
        n = num;
        length = num;
        height = num;
        board = new int[n][n];

        setupBoard();


        return;
    }

    /**Getters and Setters*//////////////////////////////////////////////////////////
    public int getLength() { return length; }

    public int getHeight() { return height; }

    public int getN() { return n; }

    public void setLength(int length) { this.length = length; }

    public void setHeight(int height) { this.height = height; }

    public int getBlankX() { return blankX; }

    public void setBlankX(int blankX) { this.blankX = blankX; }

    public int getBlankY() { return blankY; }

    public void setBlankY(int blankY) { this.blankY = blankY; }

    /**Methods*////////////////////////////////////////////////////////////////////

    /**This inputs the number of blank spaces (default is 1) and creates the board*/
    public void setupBoard () {

        //Make a temp 1D array to hold all values and shuffle with
        ArrayList<Integer> tempArray = new ArrayList<Integer>();

        //Add all blank spots
        for (int i = 0; i < numBlanks; i++) {
            tempArray.add(0);
        }

        //Add remaining numbers
        for (int i = 0; i < (n * n) - numBlanks; i++) {
            tempArray.add(i + 1);
        }

        //Shuffle numbers
        Collections.shuffle(tempArray);

        //Populate board and note the co-ords of any blank
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                board[x][y] = (tempArray.get(0));
                if (board[x][y] == 0) { //If the temp is 0, set blank to that
                    blankX = x;
                    blankY = y;
                }
                tempArray.remove(0);
            }

        }


    }

    /**Display board*/
    public void displayBoard() {
        for (int i = 0; i < n; i++) {
            System.out.print("----"); //Bring top row
        }

        System.out.println();

        for (int y = n - 1; y >= 0; y--) {

            System.out.print("|");
            for (int i = 0; i < n; i++) {
                System.out.print("   |");
            }

            System.out.println();

            System.out.print("|");
            for (int x = 0; x < n; x++) {
                System.out.print(" " + (board[x][y] == 0 ? " " : board[x][y]) + " |");
            }

            System.out.println();

            System.out.print("|");
            for (int i = 0; i < n; i++) {
                System.out.print("   |");
            }

            System.out.println();

            for (int i = 0; i < n; i++) {
                System.out.print("----"); //Bring top row
            }

            System.out.println();

        }
    }

    /**Swap two pieces on the board*/
    public void swapPieces(int newX, int newY) {

        board[blankX][blankY] = board[newX][newY];
        board[newX][newY] = 0;
        blankX = newX;
        blankY = newY;


    }

    /**Returns board after a potential swap*/
    public Boardv2 potentialSwap (int newX, int newY) {
        Boardv2 temp = this.clone();
        temp.swapPieces(newX, newY);

        return temp;
    }

    /**Returns Deep Copy of Board*/
    public Boardv2 clone () {
        Boardv2 temp = new Boardv2(n);

        temp.setLength(length);
        temp.setHeight(height);

        for (int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++){
                temp.board[x][y] = board[x][y];
            }
        }

        temp.setBlankX(blankX);
        temp.setBlankY(blankY);

        return temp;
    }


    /**Returns ArrayList of possible new Board movements*/
    public ArrayList<Boardv2> possibleMoves() {

        ArrayList<Boardv2> temp = new ArrayList<Boardv2>();


        //If not on left
        if (blankX != 0) {
            temp.add(potentialSwap(blankX-1, blankY));
        }

        //If not on the right
        if (blankX != length-1) {
            temp.add(potentialSwap(blankX+1, blankY));
        }

        //If not on bottom
        if (blankY != 0) {
            temp.add(potentialSwap(blankX, blankY-1));
        }

        //If not on top
        if (blankY != height-1) {
            temp.add(potentialSwap(blankX, blankY+1));
        }



        return temp;

    }


    public static void main(String[] args) {
        Boardv2 test = new Boardv2();

        test.displayBoard();

        ArrayList<Boardv2> possibilities = test.possibleMoves();

        possibilities.get(0).displayBoard();
        possibilities.get(1).displayBoard();
        test.displayBoard();








    }
}
