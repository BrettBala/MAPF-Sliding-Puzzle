import java.util.ArrayList;

public class Logic {

    public int[][] goal = new int[][]{ {7,8,0}, {4,5,6}, {1,2,3} };
    public static int n;
    public static boolean endSearch = false;
    public int count = 0;
    public Boardv2 board;




    /**No args constructor*/
    Logic() {

    }


    /////////////////////////////////////////////////////////////////////////////////

    /**Generate the goal array to compare against*/
    public int[][] generateGoal (int n, int numBlanks) {
        this.n = n;
        int[][] temp = new int[n][n];


        ArrayList<Integer> tempArray = new ArrayList<Integer>();

        //Add numbers
        for (int i = 0; i < (n * n) - numBlanks; i++) {
            tempArray.add(i + 1);
        }

        //Add blanks
        for (int i = 0; i < numBlanks; i++) {
            tempArray.add(0);
        }



        for(int y = n - 1; y >= 0; y--) {
            for(int x = 0; x < n; x++) {
                temp[x][y] = tempArray.get(0);
                tempArray.remove(0);
            }
        }

        goal = temp;

        return temp;
    }

    /**See if a the top row is complete, if so decrement the height*/
    public boolean isRowDone (Boardv2 currentBoard) {

        int height = currentBoard.getHeight();
        int length = currentBoard.getLength();
        boolean isItDone = true;

        //See if the top most row is correct
        while (length >= 1 && isItDone) {
            if (currentBoard.board[n - length][height - 1] != goal[n - length][height - 1]) {isItDone = false;}
            length--;
        }

        //The row is complete
        if (isItDone) {
            currentBoard.setHeight(currentBoard.getHeight() - 1);
            return true;
        }



        //It is not done
        return false;
    }

    /**See if a the left col is complete, if so decrement the length*/
    public boolean isColDone (Boardv2 currentBoard) {

        int height = currentBoard.getHeight();
        int length = currentBoard.getLength();
        boolean isItDone = true;

        //See if the top most row is correct
        while (height >= 1 && isItDone) {
            System.out.println(currentBoard.board[n - length][height - 1] + " != " + goal[n - length][height - 1]);
            if (currentBoard.board[n - length][height - 1] != goal[n - length][height - 1]) {isItDone = false;}
            height--;
        }

        //The row is complete
        if (isItDone) {
            currentBoard.setLength(currentBoard.getLength() - 1);
            return true;
        }



        //It is not done
        return false;
    }


    /**Row Only Depth Search*/
    public void rowDepthSearch(int depth, Boardv2 currentBoard) {
        if(endSearch) {return;} //End search if we are told to

        //See if the board is done
        if(isRowDone(currentBoard)) {

            endSearch = true;

            currentBoard.displayBoard();

            System.out.println("The top row is complete");

            board = currentBoard;

        }

        if (depth == 0) { return; } //Search has maxed out

        ArrayList<Boardv2> temp = currentBoard.possibleMoves();

        for(int i = 0; i < temp.size(); i++) {
            rowDepthSearch(depth-1, temp.get(i));
        }

        return;

    }


    /**Col Only Depth Search*/
    public void colDepthSearch(int depth, Boardv2 currentBoard) {
        if(endSearch) {return;} //End search if we are told to

        //See if the board is done
        if(isColDone(currentBoard)) {

            endSearch = true;

            currentBoard.displayBoard();

            System.out.println("The left col is complete");

            board = currentBoard;


        }

        if (depth == 0) { return; } //Search has maxed out

        ArrayList<Boardv2> temp = currentBoard.possibleMoves();

        for(int i = 0; i < temp.size(); i++) {
            colDepthSearch(depth-1, temp.get(i));
        }

        return;

    }

    /**Depth Search*/
    public void depthSearch(int depth, Boardv2 currentBoard) {

        if(endSearch) {return;}

        boolean shortCircuit = true;

        for(int y = n - 1; y >= 0 && shortCircuit; y--) { //See if we are solved / Pruning time
            for (int x = 0; x < n && shortCircuit; x++) {

                if (currentBoard.board[x][y] != goal[x][y])
                    shortCircuit = false;
            }
        }

        if(shortCircuit) {currentBoard.displayBoard(); System.out.println("Solved"); endSearch = true; count++; return;} //Solved board

        if (depth == 0) { return; } //Base return statement / Pruning time

        ArrayList<Boardv2> temp = currentBoard.possibleMoves();

        for(int i = 0; i < temp.size(); i++) {
            depthSearch(depth-1, temp.get(i));
        }

        return;


    }



    public static void main(String[] args) {




        Logic logicTest = new Logic();
        int[][] goal = logicTest.generateGoal(5,1);
        Boardv2 boardTest = new Boardv2(5);

        boardTest.displayBoard();


        while(boardTest.getHeight() > 2 && boardTest.getLength() > 2) {
            if (boardTest.getHeight() == boardTest.getLength()) {
                logicTest.rowDepthSearch(30, boardTest);
            }
            else {
                logicTest.colDepthSearch(30, boardTest);
            }

            boardTest = logicTest.board;
            logicTest.endSearch = false;


        }

        logicTest.endSearch = false;
        logicTest.depthSearch(30, boardTest);



//        for(int i = 0; i < 10; i++) {
//            Boardv2 boardTest = new Boardv2(3);
//            boardTest.displayBoard();
//            logicTest.depthSearch(30, boardTest);
//            System.out.println(logicTest.count);
//            logicTest.endSearch = false;
//        }
//
//        System.out.print("FINAL COUNT: " + logicTest.count);

    }




}
