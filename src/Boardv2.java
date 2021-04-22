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
    private int lastX = -1;
    private int lastY = -1;

    public int[][] board; //This is the board

    /**No arg constructor*/
    public Boardv2() {
        this.length = 3;
        this.height = 3;
        this.n = 3;
        board = new int[n][n];

        ArrayList<Integer> tempBoard = new ArrayList<Integer>(Arrays.asList( //Default board
                0, 1, 2,
                5, 6, 3,
                4, 7, 8
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

        int temp = 1;

        while (temp % 2 == 1) {
            setupBoard();
            temp = getInversionCount();
        }


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
            System.out.print("-----"); //Bring top row
        }

        System.out.println();

        for (int y = n - 1; y >= 0; y--) {

            System.out.print("|");
            for (int i = 0; i < n; i++) {
                System.out.print("    |");
            }

            System.out.println();

            System.out.print("|");
            for (int x = 0; x < n; x++) {
                System.out.print(" " + (board[x][y] == 0 ? "  " : (board[x][y] > 9 ? board[x][y] : (" " + board[x][y]))) + " |");
            }

            System.out.println();

            System.out.print("|");
            for (int i = 0; i < n; i++) {
                System.out.print("    |");
            }

            System.out.println();

            for (int i = 0; i < n; i++) {
                System.out.print("-----"); //Bring top row
            }

            System.out.println();

        }
    }

    /**Swap two pieces on the board*/
    public void swapPieces(int newX, int newY) {

        lastX = blankX;
        lastY = blankY;
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
        if (blankX != 0 && (lastX != blankX-1)) {
            temp.add(potentialSwap(blankX-1, blankY));
        }

        //If not on the right
        if (blankX != length-1 && (lastX != blankX+1)) {
            temp.add(potentialSwap(blankX+1, blankY));
        }

        //If not on bottom
        if (blankY != 0 && (lastY != blankY-1)) {
            temp.add(potentialSwap(blankX, blankY-1));
        }

        //If not on top
        if (blankY != height-1 && (lastY != blankY+1)) {
            temp.add(potentialSwap(blankX, blankY+1));
        }



        return temp;

    }

    /**Gets inversion count to see if sovlable*/
    public int getInversionCount() {

        int count = 0;
        int temp, temp2;

        for(int y = n - 1; y >= 0; y--) {
            for (int x = 0; x < n; x++) {

                temp = board[x][y];
                temp2 = x;

                for(int i = y; i >= 0; i--) {
                    for (int j = temp2; j < n; j++) {

                        if(temp > board[j][i] && board[j][i] != 0) {count++;}
                    }
                    temp2 = 0;
                }

            }
        }

        return count;
    }

    public static void test(int depth, Boardv2 currentBoard) {

        if (depth == 0) { return; }

        for(int i = 0; i < currentBoard.possibleMoves().size(); i++) {

            if (currentBoard.board[0][2] == 1 && currentBoard.board[1][2] == 2 && currentBoard.board[2][2] == 3
            && currentBoard.board[0][1] == 4 && currentBoard.board[1][1] == 5 && currentBoard.board[2][1] == 6
            && currentBoard.board[0][0] == 7 && currentBoard.board[1][0] == 8){
                currentBoard.displayBoard();
            }


            test(depth-1, currentBoard.possibleMoves().get(i));
        }

        return;


    }


    public static void main(String[] args) {
        Boardv2 test = new Boardv2(3);

        test.displayBoard();

        test(25, test);

        System.out.println("All done");

        int[][] goal = new int[][]{ {7,8,0}, {4,5,6}, {1,2,3} };


//
//        ArrayList<Boardv2> possibilities = test.possibleMoves();
//
//        possibilities.get(0).displayBoard();
//
//
//        for(int i = 0; i < possibilities.get(0).possibleMoves().size(); i++) {
//            possibilities.get(0).possibleMoves().get(i).displayBoard();
//        }
//        //possibilities.get(0).possibleMoves().get(0).displayBoard();
//
//        test.displayBoard();








    }
}
