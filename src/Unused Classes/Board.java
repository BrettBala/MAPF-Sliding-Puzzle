import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//TODO: ISSUE, OBJECTS ARE COPIED BY REFERENCE IN JAVA, NOT VALUE
public class Board {

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
    private ArrayList<Piece> Blanks; //Stores a copies of all blank pieces, and thus their location
    private Piece Blank; //Will be using this since we will only have 1 blank for the time being

    public Piece[][] board;

    /**No arg constructor*/
    public Board() {
        this.length = 3;
        this.height = 3;
        this.n = 3;
        board = new Piece[n][n];

        ArrayList<Integer> tempBoard = new ArrayList<Integer>(Arrays.asList( //Default board
                5, 3, 7,
                1, 4, 8,
                2, 6, 0
        ));

        Piece temp; //temp piece to store newest piece to check if it is 0

        //Set the board and blank
        for (int y = 2; y >= 0; y--) {
            for (int x = 0; x < 3; x++) {
                temp =  new Piece(tempBoard.get(0), x, y);
                board[x][y] = temp;
                tempBoard.remove(0);
                if (temp.getValue() == 0) { //If the temp is 0, set blank to that
                    Blank = temp;
                }
            }
        }

        return;
    }

    public Board(Board replace){
        length = replace.length;
        height = replace.height;
        n = replace.n;
        numBlanks = replace.numBlanks;
        Blanks = replace.Blanks;
        Blank = replace.Blank;
        board = replace.board;
    }

    /**Constructor*/
    public Board(int num) {
        n = num;
        length = num;
        height = num;
        board = new Piece[n][n];

        setupBoard();


        return;
    }

    /**Getters and Setters*//////////////////////////////////////////////////////////
    public int getLength() { return length; }

    public int getHeight() { return height; }

    public int getN() { return n; }

    public void setLength(int length) { this.length = length; }

    public void setHeight(int height) { this.height = height; }


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
        Piece temp;
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                temp =  new Piece(tempArray.get(0), x, y);
                board[x][y] = temp;
                tempArray.remove(0);
                if (temp.getValue() == 0) { //If the temp is 0, set blank to that
                    Blank = temp;
                }
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
                System.out.print(" " + (board[x][y].getValue() == 0 ? " " : board[x][y].getValue()) + " |");
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
    public void swapPieces(Board temp, Piece oldBlank, Piece newBlank) {

        temp.board[oldBlank.getX()][oldBlank.getY()].setValue(newBlank.getValue());
        temp.board[newBlank.getX()][newBlank.getY()].setValue(0);
        Blank = temp.board[newBlank.getX()][newBlank.getY()];

    }

    /**Returns board after a potential swap*/
    public Board potentialSwap (Piece oldBlank, Piece newBlank) {
        Board temp = new Board(this);
        temp.swapPieces(temp, new Piece(oldBlank), new Piece(newBlank));
        temp.displayBoard();
        displayBoard();
        return temp;
    }


    /**Returns ArrayList of possible new Board movements*/
    public ArrayList<Board> possibleMoves() {
        int x = Blank.getX();
        int y = Blank.getY();
        ArrayList<Board> temp = new ArrayList<Board>();


        //If not on left
        if (x != 0) {
            temp.add(potentialSwap(board[x][y], board[x-1][y]));
        }

        //If not on the right
        if (x != length-1) {
            temp.add(potentialSwap(board[x][y], board[x+1][y]));
        }

        //If not on bottom
        if (y != 0) {
            temp.add(potentialSwap(board[x][y], board[x][y-1]));
        }

        //If not on top
        if (y != height-1) {
            temp.add(potentialSwap(board[x][y], board[x][y+1]));
        }



        return temp;

    }


    public static void main(String[] args) {
        Board test = new Board();
        test.displayBoard();
        ArrayList<Board> temp = test.possibleMoves();

//        for(int i = 0; i < temp.size(); i++) {
//            temp.get(i).displayBoard();
//        }
//        test.displayBoard();



    }
}
