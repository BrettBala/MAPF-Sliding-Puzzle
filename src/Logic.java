import java.util.ArrayList;

public class Logic {

    public int topRowScore = 100000;

//    public int[][] goal = new int[][]{ {7,8,0}, {4,5,6}, {1,2,3} };
    public int[][] goal;
    public static int n;
    public static boolean endSearch = false;
    public int count = 0;
    public Boardv2 board;
    public Boardv2 bestBoard;
    public int totalMoves = 0;




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



        for(int x = n - 1; x >= 0; x--) {
            for(int y = 0; y < n; y++) {
                temp[x][y] = tempArray.get(0);
                //System.out.println("(" + x + ", " + y+ "): " + temp[x][y] + "    goal: " +goal[x][y]);
                tempArray.remove(0);
            }
        }

        goal = temp;

        return temp;
    }



    /**Looks through all possible moves, sets best board as the global var*/
    public void rowPointsSearch(int depth, Boardv2 currentBoard) {


        int topRow = currentBoard.getHeight() - 1;

        //If the board's top row has the best score
        //System.out.println(topRowScore + " > " + currentBoard.rowVal(topRow, goal));
        if(currentBoard.rowVal(topRow, goal) < topRowScore) {
            topRowScore = currentBoard.rowVal(topRow, goal);
            bestBoard = currentBoard;
        }

        //If this board is tied with the best, does it have less parents?
        if((currentBoard.rowVal(topRow, goal) == topRowScore) && (currentBoard.numParents < bestBoard.numParents)) {
            bestBoard = currentBoard;
        }

        //If depth hits 0 or if the board is too far off from the best
        if (depth == 0 || currentBoard.rowVal(topRow, goal) > topRowScore + 2) {return;}



        ArrayList<Boardv2> temp = currentBoard.possibleMoves();

        for(int i = 0; i < temp.size(); i++) {
            rowPointsSearch(depth-1, temp.get(i));
        }

        return;
    }

    /**Looks through all possible moves, sets best board as the global var*/
    public void colPointsSearch(int depth, Boardv2 currentBoard) {


        int leftCol = n - currentBoard.getLength();

        //If the board's top row has the best score
        //System.out.println(topRowScore + " > " + currentBoard.colVal(leftCol, goal));
        if(currentBoard.colVal(leftCol, goal) < topRowScore) {
            topRowScore = currentBoard.colVal(leftCol, goal);
            bestBoard = currentBoard;
        }

        //If this board is tied with the best, does it have less parents?
        if((currentBoard.colVal(leftCol, goal) == topRowScore) && (currentBoard.numParents < bestBoard.numParents)) {
            bestBoard = currentBoard;
        }

        //If depth hits 0 or if the board is too far off from the best
        if (depth == 0 || currentBoard.colVal(leftCol, goal) > topRowScore + 2) {return;}





        ArrayList<Boardv2> temp = currentBoard.possibleMoves();

        for(int i = 0; i < temp.size(); i++) {
            colPointsSearch(depth-1, temp.get(i));
        }

        return;
    }



    /**Depth Search*/
    public void depthSearch(int depth, Boardv2 currentBoard) {

        if(endSearch) {return;}



        if(currentBoard.boardVal(goal) == 0) {bestBoard = currentBoard; endSearch = true; return;} //Solved board

        if (depth == 0) { return; } //Base return statement / Pruning time

        ArrayList<Boardv2> temp = currentBoard.possibleMoves();

        for(int i = 0; i < temp.size(); i++) {
            depthSearch(depth-1, temp.get(i));
        }

        return;


    }

    /**Display all boards*/
    public void displayAllBoards (Boardv2 currentBoard) {

        ArrayList<Boardv2> temp = new ArrayList<Boardv2>();

        while (currentBoard != null) {

            temp.add(currentBoard);
            currentBoard = currentBoard.parentBoard;

        }

        temp.remove(0); //Gets rid of first board, since it will the newest parent board

        totalMoves += temp.size();

        for (int i = temp.size() - 1; i >= 0; i--) {
            temp.get(i).displayBoard();

//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        return;
    }



    public static void main(String[] args) {


        int NUM = 5;
        int depth = 16;

        Logic logicTest = new Logic();
        int[][] goal = logicTest.generateGoal(NUM,1);
        Boardv2 boardTest = new Boardv2(NUM);



        int topRow = boardTest.getHeight() - 1;
        int leftCol = n - boardTest.getLength();

        while(boardTest.getHeight() > 1 && boardTest.getLength() > 1) {

            logicTest.topRowScore = 100000;
            topRow = boardTest.getHeight() - 1;
            leftCol = n - boardTest.getLength();

            while (boardTest.rowVal(topRow, goal) != 0) {
                logicTest.rowPointsSearch(depth, boardTest);
                ////////////////////////////////
                boardTest = logicTest.bestBoard;
                logicTest.displayAllBoards(boardTest);
                boardTest.parentBoard=null;
                boardTest.numParents=0;
                //////////////////////////////////
//                System.out.println("Row: " + boardTest.rowVal(topRow, goal));
//                boardTest.displayBoard();

            }
            boardTest.setHeight(boardTest.getHeight() - 1);

            logicTest.topRowScore = 100000;

            while (boardTest.colVal(leftCol, goal) != 0) {
                logicTest.colPointsSearch(depth, boardTest);
                ///////////////////////////////
                boardTest = logicTest.bestBoard;
                logicTest.displayAllBoards(boardTest);
                boardTest.parentBoard=null;
                boardTest.numParents=0;
                //////////////////////////////////
//                System.out.println("Col: " + boardTest.colVal(leftCol, goal));
//                boardTest.displayBoard();

            }
            boardTest.setLength(boardTest.getLength() - 1);

        }

        logicTest.topRowScore = 100000;
        topRow = boardTest.getHeight() - 1;

        while (boardTest.rowVal(topRow, goal) != 0) {
            logicTest.rowPointsSearch(depth, boardTest);
            boardTest = logicTest.bestBoard;


        }




        ////////////////////////////////
        boardTest = logicTest.bestBoard;
        logicTest.displayAllBoards(boardTest);
        boardTest.parentBoard=null;
        boardTest.numParents=0;
        ///////////////////////////////////

        boardTest.displayBoard();

        System.out.println("----------------------");



        System.out.println("Total Moves: " + logicTest.totalMoves);





    }




}
