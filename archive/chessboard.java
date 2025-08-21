import java.util.Random;
import java.util.Scanner;

public class chessboard {
    public static void main(String[] args) {
        ////The code is kind of messy with commented code but I wanted to keep it for reference
        String[][] board = setupBoard();
        Scanner scnr = new Scanner(System.in);

        boolean playgame;
        playgame = false;
        while (playgame == false) {
            String toplay;
            System.out.println("Hello. You are about to play a chess game designed by Sam Koscelny in CS 1113. Do you want to continue? (yes/no): ");

            toplay = scnr.nextLine();

            if (toplay.equals("yes")) {
                System.out.println();
                System.out.println();
                System.out.println("Great! You, the user, are always going control the white pieces and the computer will control the black pieces.");
                System.out.println();
                System.out.println();
                System.out.println("THE RULES FOR THE CHESS PIECES: ");
                System.out.println();

                System.out.println("THE PAWN: can move up 2 tiles or 1 tile during the first move, but after the first move, pawn can only move up 1 tile ");
                System.out.println("and nowhere else. When attacking, the pawn can only attack pieces that are diagonal to it.");
                System.out.println();

                System.out.println("THE ROOK: can move upwards, downards, and sideways for as many tiles as it is not impeeded by another chesspiece. Can ");
                System.out.println("attack enemy pieces that are in its moving path.");
                System.out.println();

                System.out.println("THE KNIGHT: has 8 modes of direction from which it can attack, making it very powerful. It can move up 2 tiles and 1 tile ");
                System.out.println("to the right, move up 2 tiles and 1 tile to the left, move up 1 tile and 2 tiles to the right, move up 1 tile and 2 tiles to the left, ");
                System.out.println("move down 2 tiles and 1 tile to the right, move down 2 tiles and 1 tile to the left, move down 1 tile and 2 tiles to the ");
                System.out.println("right, move down 1 tile and 2 tiles to the left. Can attack enemy pieces that are in the destination tile of the knight.");
                System.out.println();

                System.out.println("THE BISHOP: can move diagonally for as many tiles as it is not impeeded by a chesspiece. Can attack enemy pieces that ");
                System.out.println("are in its moving path.");
                System.out.println();

                System.out.println("THE QUEEN: the most powerful piece on the board. Can move vetically, horizontally, or diagonally to any tile as long ");
                System.out.println("as it is not impeeded by another chesspiece. Can attack enemy pieces that are in its moving path.");
                System.out.println();

                System.out.println("THE KING: the most important piece on the board. Protect at all costs! Can move vertically, horizontally, or diagonally ");
                System.out.println("1 tile as long as it is not impeeded by another chesspiece on the board.");
                System.out.println();
                System.out.println();
                System.out.println("To move a chess piece, you have to correctly enter the coordinates. In my game,");
                System.out.println("I set up the coordinates in an 8x8 system");
                System.out.println("You have to identify the chess piece you want to move and enter the row and column of that ");
                System.out.println("piece and then enter the row and column");
                System.out.println("of where you want that piece to go. To help visualize: <starting row> <starting column> <ending row> <ending column>");
                System.out.println("Make sure a space is between all the cooridantes. For example:");
                System.out.println("If I want to move my pawn on the farthest left side of the board in my first move, I would enter the row,");
                System.out.println("which is 2, and the column, which is 1, and enter");
                System.out.println("where I want the pawn to go which is row 2 column 1. It will look like this:");
                System.out.println("2 1 2 2");
                System.out.println("After I enter the correct coordinates, I will press ENTER");
                System.out.println("The board will update and then I can enter more coordinates for the chess pieces.");
                System.out.println("Have fun, play smart, and remember the correct rules for the chess pieces or the board will ");
                System.out.println("not update with the coordinates you entered!");
                System.out.println();
                System.out.println("-Sam Koscelny");
                System.out.println();
                System.out.println();
                System.out.println();
                playgame = true;
                break;
            } else if (toplay.equals("no")) {
                System.out.println();
                System.out.println("I think you should consider playing. I spent a lot of time making this game.");
                System.out.println();
                playgame = false;
            } else {
                System.out.println("Please say yes or no.");
                playgame = false;


            }
        }

        while (gameIsNotOver(board)) {
            System.out.println("Please enter q to quit");
            boolean quit = false;
            while (quit == false) {
                printBoard(board);
                int[] move = getUserInput(scnr);

                /*if (move[0] == "q") {
                    quit = true;
                    break;
                }*/

                if (isLegal(board, move)) {

                } else {
                    System.out.println("That is not a legal move. Try again.");
                    continue;
                }

                if (checkForBlackKing(board) == false) {

                    System.out.println("The game is over!");
                    System.out.println("White won!");
                    break;
                }

                makeComputerMove(board);


                if (checkForWhiteKing(board) == false) {
                    System.out.println("The game is over!");
                    System.out.println("Black won!");
                    break;
                }
            }
        }
    }


    public static String[][] setupBoard() {

        String[][] board = new String[9][9];
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                board[i][j] = "";
            }
        }
        board[8][1] = board[8][8] = "BR";
        board[8][2] = board[8][7] = "BN";
        board[8][3] = board[8][6] = "BB";
        board[8][4] = "BQ";
        board[8][5] = "BK";

        for (int i = 0; i < board[7].length; ++i) {
            board[7][i] = "BP";
        }

        board[1][1] = board[1][8] = "WR";
        board[1][2] = board[1][7] = "WN";
        board[1][3] = board[1][6] = "WB";
        board[1][4] = "WK";
        board[1][5] = "WQ";

        for (int i = 0; i < board[2].length; ++i) {
            board[2][i] = "WP";
        }
        return board;
    }


    public static void printBoard(String[][] board) {
        boolean isWhite = true;
        String emptyWhite = "    ";
        String emptyBlack = "****";

        for (int i = board.length - 1; i >= 1; i--) {
            System.out.print(i + "  ");

            for (int j = 1; j < board[i].length; j++) {
                if (isWhite) {
                    System.out.print(emptyWhite);
                } else {
                    System.out.print(emptyBlack);

                }
                isWhite = !isWhite;

            }

            System.out.println();
            System.out.print("   ");

            for (int j = 1; j < board[i].length; j++, isWhite = !isWhite)
                if (isWhite)
                    if (board[i][j].equals(""))
                        System.out.print(emptyWhite);
                    else
                        System.out.print(" " + board[i][j] + " ");
                else if (board[i][j].equals(""))
                    System.out.print(emptyBlack);
                else
                    System.out.print("*" + board[i][j] + "*");
            System.out.println();
            isWhite = !isWhite;

        }
        char name = 'A';

        for (int j = 0; j < 8; j++)
            System.out.printf("%4c", name++);


    }

    public static boolean gameIsNotOver(String[][] board) {


        return true;
    }

    public static int[] getUserInput(Scanner scnr) {
        System.out.println();
        System.out.print("Enter move <start row> <start col> <end row> <end col>:");

        int[] retVal = new int[4];
        for (int i = 0; i < retVal.length; i++) {
            retVal[i] = scnr.nextInt();

        }
        return retVal;

    }

    public static boolean movePawnWhite(String[][] board, int[] move) {
        ///start

        //To accomplish first move of pawn to be of magnitude 2 or 1
        if ((move[0] == 2)) {
            for (int i = 1; i < 9; ++i) {
                if (move[1] == i) {
                    if (move[2] - move[0] > 0) {
                        if (move[3] - move[1] == 0) {
                            if (board[move[0] + 1][move[1]].equals("")) {
                                if (board[move[0] + 2][move[1]].equals("")) {
                                    if (((move[2] - move[0]) == 1) || ((move[2] - move[0]) == 2)) {
                                        board[move[2]][move[3]] = board[move[0]][move[1]];
                                        board[move[0]][move[1]] = "";
                                        return true;

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    /*if ((move[2] - move[0]) == 1) {
        if (move[3] - move[1] == 1) {
        if (!board[move[0]+1][move[1]].equals("")) {
        board[move[2]][move[3]] = board[move[0]][move[1]];
        board[move[0]][move[1]] = "";
        return true;
        }
    }
}

if ((move[2] - move[0]) == 1) {
    if (move[3] - move[1] == -1) {
    if (!board[move[0]+1][move[1]].equals("")) {
    board[move[2]][move[3]] = board[move[0]][move[1]];
    board[move[0]][move[1]] = "";
    return true;
    }
}
}*/
        //To accomplish anywhere on the board for pawn to move up 1
        if (board[move[0] + 1][move[1]].equals("")) {
            if ((move[2] - move[0]) == 1) {
                if ((move[3] - move[1]) == 0) {
                    board[move[2]][move[3]] = board[move[0]][move[1]];
                    board[move[0]][move[1]] = "";
                    return true;
                }
            }
        }

//for diagonal attack NE for pawn
        boolean isLegal;
        isLegal = false;
        if (move[2] - move[0] == 1) {
            if (move[3] - move[1] == 1) {
                if ((Math.abs(move[2] - move[0])) == Math.abs((move[3] - move[1]))) {
                    if (!board[move[0] + 1][move[1] + 1].equals("")) {
                        isLegal = true;
                    }
                }
            }

        }


///for diagonal attack NW for pawn
        if (move[2] - move[0] == 1) {
            if (move[3] - move[1] == -1) {
                if (Math.abs((move[2] - move[0])) == Math.abs((move[3] - move[1]))) {
                    if (!board[move[0] + 1][move[1] - 1].equals("")) {
                        isLegal = true;
                    }
                }
            }
        }

        if (isLegal) {
              if (board[move[2]][move[3]] == "") {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;

            }
            else if (board[move[2]][move[3]] != ""
            && ((board[move[2]][move[3]].charAt(0) == 'B' && board[move[0]][move[1]].charAt(0) == 'W') || (board[move[2]][move[3]].charAt(0) == 'W' && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;
            }
            }

        return false;

    }

    public static boolean movePawnBlack(String[][] board, int[] move) {
        ///start

        //To accomplish first move of pawn to be of magnitude 2 or 1
        if ((move[0] == 7)) {
            for (int i = 1; i < 9; ++i) {
                if (move[1] == i) {
                    if (move[0] - move[2] > 0) {
                        if (move[3] - move[1] == 0) {
                            if (board[move[0] - 1][move[1]].equals("")) {
                                if (board[move[0] - 2][move[1]].equals("")) {
                                    if (((move[0] - move[2]) == 1) || ((move[0] - move[2]) == 2)) {
                                        board[move[2]][move[3]] = board[move[0]][move[1]];
                                        board[move[0]][move[1]] = "";
                                        System.out.println("313");

                                        return true;

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    /*if ((move[2] - move[0]) == 1) {
        if (move[3] - move[1] == 1) {
        if (!board[move[0]+1][move[1]].equals("")) {
        board[move[2]][move[3]] = board[move[0]][move[1]];
        board[move[0]][move[1]] = "";
        return true;
        }
    }
}

if ((move[2] - move[0]) == 1) {
    if (move[3] - move[1] == -1) {
    if (!board[move[0]+1][move[1]].equals("")) {
    board[move[2]][move[3]] = board[move[0]][move[1]];
    board[move[0]][move[1]] = "";
    return true;
    }
}
}*/
        //To accomplish anywhere on the board for pawn to move up 1
        if (board[move[0] - 1][move[1]].equals("")) {
            if ((move[0] - move[2]) == 1) {
                if ((move[3] - move[1]) == 0) {
                    board[move[2]][move[3]] = board[move[0]][move[1]];
                    board[move[0]][move[1]] = "";
                    System.out.println("348");
                    return true;

                }
            }
        }

//for diagonal attack NE for pawn
        boolean isLegal;
        isLegal = false;
        if (move[0] - move[2] == 1) {
            if (move[3] - move[1] == 1) {
                if ((Math.abs(move[2] - move[0])) == Math.abs((move[3] - move[1]))) {
                    if (!board[move[0] - 1][move[1] + 1].equals("")) {
                        isLegal = true;
                        System.out.println("361");
                    }
                }
            }

        }


///for diagonal attack NW for pawn
        if (move[0] - move[2] == 1) {
            if (move[3] - move[1] == -1) {
                if (Math.abs((move[2] - move[0])) == Math.abs((move[3] - move[1]))) {
                    if (!board[move[0] - 1][move[1] - 1].equals("")) {
                        System.out.println("377");

                        isLegal = true;
                    }
                }
            }
        }

        if (isLegal) {
              if (board[move[2]][move[3]] == "") {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;

            }
            else if (board[move[2]][move[3]] != ""
            && ((board[move[2]][move[3]].charAt(0) == 'B' && board[move[0]][move[1]].charAt(0) == 'W') || (board[move[2]][move[3]].charAt(0) == 'W' && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;
            }
            }


        return false;

    }
        /*if (move[0] == 7) {
            for (int i = 1; i < 9; ++i) {
                if (move[1] == i) {
                    if (move[0] - move[2] > 0) {
                        if (move[3] - move[1] == 0) {
                if (board[move[0]-1][move[1]].equals("")) {
                if (board[move[0]-2][move[1]].equals("")) {
                if (((move[0] - move[2]) == 1) || ((move[0] - move[2]) == 2)) {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;

                }
            }
        }
    }
}
        }
    }
}


 //To accomplish anywhere on the board for pawn to move down 1
 if (board[move[0]-1][move[1]].equals("")) {
    if ((move[0] - move[2]) == 1) {
        if ((move[3] - move[1]) == 0) {
    board[move[2]][move[3]] = board[move[0]][move[1]];
    board[move[0]][move[1]] = "";
    return true;
        }
}
}

//for diagonal attack SE for pawn
boolean isLegal;
isLegal = false;
if (move[0] - move[2] == 1) {
    if (move[3] - move[1] == 1) {
            if ((Math.abs(move[2]-move[0])) == Math.abs((move[3]-move[1]))) {
            if (!board[move[0] + 1][move[1] + 1].equals("")) {
                isLegal = true;
            }
            }
        }

    }

///for diagonal attack SW for pawn
if (move[0] - move[2] == 1) {
    if (move[3] - move[1] == -1) {
    if (Math.abs((move[2]-move[0])) == Math.abs((move[3]-move[1]))) {
    if (!board[move[0] + 1][move[1] - 1].equals("")) {
        isLegal = true;
    }
    }
}
}

if(isLegal) {
    board[move[2]][move[3]] = board[move[0]][move[1]];
    board[move[0]][move[1]] = "";
    return true;
}


        /*if ((move[0] == 2) || (move[0] == 1)) {
            for (int i = 1; i < 9; ++i) {
                if (move[1] == i) {
                    if (move[2] - move[0] > 0) {
                        if (move[3] - move[1] == 0) {
                if (board[move[0]+1][move[1]].equals("")) {
                if (board[move[0]+2][move[1]].equals("")) {
                if (((move[2] - move[0]) == 1) || ((move[2] - move[0]) == 2)) {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;

                }
            }
        }
    }
}
        }
    }
}
        System.out.println(board[move[0] - 1][move[1]]);
        if (board[move[0]-1][move[1]].equals("")) {
            if (Math.abs((move[2] - move[0])) == 1) {
            board[move[2]][move[3]] = board[move[0]][move[1]];
            board[move[0]][move[1]] = "";
            return true;
    }
}
    return false;
    }*/

    public static boolean moveKing(String[][] board, int[] move) {
        //move forward
       /* if (move[2] - move[0] == 1) { 
        if (move[3] - move[1] == 0) {
        if ((board[move[0] + 1][move[1]].equals("")) 
        || ((board[move[2]][move[3]].charAt(0) == 'B' 
        && board[move[0]][move[1]].charAt(0) == 'W') 
        || (board[move[2]][move[3]].charAt(0) == 'W' 
        && (board[move[0]][move[1]].charAt(0) == 'B')))) {
        if ((move[2] - move[0]) == 1) {
            board[move[2]][move[3]] = board[move[0]][move[1]];
            board[move[0]][move[1]] = "";
            return true;
            }
        }
    }
}

        //move to the right

        if (move[2] - move[0] == 0) { 
        if (move[3] - move[1] == 1) {
        if ((board[move[0]][move[1] + 1].equals(""))
        || ((board[move[2]][move[3]].charAt(0) == 'B' 
        && board[move[0]][move[1]].charAt(0) == 'W') 
        || (board[move[2]][move[3]].charAt(0) == 'W' 
        && (board[move[0]][move[1]].charAt(0) == 'B')))) {
            if ((move[3] - move[1]) == 1) {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;
            }
        }
    }
        }
        //move backward
        if (move[2] - move[0] == -1) { 
        if (move[3] - move[1] == 0) {
        if ((board[move[0] - 1][move[1]].equals("")) 
        || ((board[move[2]][move[3]].charAt(0) == 'B' 
        && board[move[0]][move[1]].charAt(0) == 'W') 
        || (board[move[2]][move[3]].charAt(0) == 'W' 
        && (board[move[0]][move[1]].charAt(0) == 'B')))) {
            if (Math.abs((move[0] - move[2])) == 1) {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;
            }
        }
    }
}

        //move to the left
        if (move[2] - move[0] == 0) { 
        if (move[3] - move[1] == -1) {
        if ((board[move[0]][move[1] - 1].equals("")) 
        || ((board[move[2]][move[3]].charAt(0) == 'B' 
        && board[move[0]][move[1]].charAt(0) == 'W') 
        || (board[move[2]][move[3]].charAt(0) == 'W' 
        && (board[move[0]][move[1]].charAt(0) == 'B')))) {
            if ((move[1] - move[3]) == 1) {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;
            }
        }
    }
}*/

                   /* if (move[2] - move[0] == 1) {
                        if (move[3] - move[1] == 0) {
                            for (int i = 1; i <= Math.abs(move[2] - move[0]); i++) {
                                if ((Math.abs(move[2]-move[1])) == Math.abs((move[3]-move[1]))) {
                                if (board[move[0] + i][move[1] + i].equals("")) {
                                    isLegal = true;
                                    break;
                                }
                                }
                            }

                        }
                    }


                    if (move[2] - move[0] == 1) {
                        if (move[3] - move[1] == 1) {
                        for (int i = 1; i <= Math.abs(move[2] - move[0]); i++) {
                        if (Math.abs((move[2]-move[1])) == Math.abs((move[3]-move[1]))) {
                        if (board[move[0] + i][move[1] - i].equals("")) {
                            isLegal = true;
                            break;
                        }
                        }
                    }
                }
            }


                    if (move[2] - move[0] == 1) {
                        if (move[3] - move[1] == 1) {
                        for (int i = 1; i <= Math.abs(move[2] - move[0]); i++) {
                        if (Math.abs((move[2]-move[1])) == Math.abs((move[3]-move[1]))) {

                        if (board[move[0] - i][move[1] - i].equals("")) {
                            isLegal = true;
                                    break;
                                }
                        }
                            }
                        }
                    }

                    if (move[2] - move[0] == 1) {
                        if (move[3] - move[1] == 1) {
                        for (int i = 1; i <= Math.abs(move[2] - move[0]); i++) {
                        if (Math.abs((move[2]-move[1])) == Math.abs((move[3]-move[1]))) {

                        if (board[move[0] - i][move[1] + i].equals("")) {
                            isLegal = true;
                            break;
                        }
                        }
                    }
                }
                }*/

    Boolean isLegal = false;

    if (move[2] - move[0] == 1) { 
        if (move[3] - move[1] == 0) {
        if (board[move[0] + 1][move[1]].equals("")) {
        isLegal = true;
            }
        }
    }

        //move to the right

        if (move[2] - move[0] == 0) { 
        if (move[3] - move[1] == 1) {
        if (board[move[0]][move[1] + 1].equals("")) {
        isLegal = true;
            }
        }
    }

        //move backward
        if (move[2] - move[0] == -1) { 
        if (move[3] - move[1] == 0) {
        if (board[move[0] - 1][move[1]].equals("")) {

        isLegal = true;
            }
        }
    
}

        //move to the left
        if (move[2] - move[0] == 0) { 
        if (move[3] - move[1] == -1) {
        if (board[move[0]][move[1] - 1].equals("")) {
            isLegal = true;
        }
    }
}

    if ((move[2] - move[0]) == 1 && (move[3] - move[1]) == 0) {
            
            isLegal = true;
        }

        if ((move[2] - move[0]) == -1 && (move[3] - move[1]) == 0) {
            
            isLegal = true;
        }

        if ((move[3] - move[1]) == 1 && (move[2] - move[0]) == 0) {
            
            isLegal = true;
        }

        if ((move[3] - move[1]) == -1 && (move[2] - move[0]) == 0) {
            
            isLegal = true;
        }



        /************************************************************* */

        if ((move[2] - move[0]) == 1 && (move[3] - move[1]) == 1) {
            
            isLegal = true;
        }

        if ((move[2] - move[0]) == -1 && (move[3] - move[1]) == 1) {
            
            isLegal = true;
        }

        if ((move[2] - move[0]) == 1 && (move[3] - move[1]) == -1) {
            
            isLegal = true;
        }

        if ((move[2] - move[0]) == -1 && (move[3] - move[1]) == -1) {
            
            isLegal = true;
        }
            
            
  


        if (isLegal) {
              if (board[move[2]][move[3]] == "") {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;

            }
            else if (board[move[2]][move[3]] != ""
            && ((board[move[2]][move[3]].charAt(0) == 'B' && board[move[0]][move[1]].charAt(0) == 'W') || (board[move[2]][move[3]].charAt(0) == 'W' && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;
            }
            }
            
        
        return false;
    }     



    ///end
            /*boolean isLegal = false;
        if (!board[move[0] + i][move[1]].equals("")) {*/

            /*if (board[move[0][move]])

            for (i = 0; i < 9; i++) {
                if (board[move[0][move[1]] ==   + 1][move[1]].equals("")) {

                isLegal = true;
            }
            if (isLegal) {

                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;
            }
            return false;
        }*/


    public static boolean moveQueen(String[][] board, int[] move) {
        // vertical movement
        boolean isLegal = false;

       /* for (int i = 1; i <= move[2] - move[0]; i++) {
            if ((move[3] - move[1]) == 0) {
                if (board[move[0] + i][move[1]].equals("")) {
                    isLegal = true;
                    break;
                }
            }
        }  */


        if (move[2] - move[0] > 0) { 
        if (move[3] - move[1] == 0) {
        for (int i = 1; i < move[2] - move[0]; i++) {

            if (board[move[0] + i][move[1]].equals("")) {
                isLegal = true;
            }
            else {
                return false;
            }
        }
    }
}

        if (move[2] - move[0] < 0) { 
        if (move[3] - move[1] == 0) {
        for (int i = 1; i < move[0] - move[2]; ++i) {
        /*for (int i = move[0] - move[2]; i > 1; i--) {*/
            if (board[move[0] - i][move[1]].equals("")) {
                isLegal = true;
            }
            else {
                return false;
            }
        }
    }
}
        ///horizontal movement

        if (move[2] - move[0] == 0) { 
        if (move[3] - move[1] > 1) {
        for (int i = 1; i < move[3] - move[1]; i++) {
            if (board[move[0]][move[1] + i].equals("")) {
                isLegal = true;
        }
            else {

                return false;
            }
        }
    }
}

        if (move[2] - move[0] == 0) { 
        if (move[3] - move[1] < 1) {
        for (int i = 1; i < move[1] - move[3]; ++i) {
        /*for (int i = move[1] - move[3]; i > 1; i--) {*/
            if (board[move[0]][move[1] - i].equals("")) {
                isLegal = true;
            }
            else {
                return false;
            }
        }
    }
}



        /*for (int i = move[0] - move[2]; i >= 1; i--) {
            if ((move[3] - move[1]) == 0) {
                if (board[move[0] - i][move[1]].equals("")) {
                    isLegal = true;
                    break;
                }
            }
        }
        ///horizontal movement
        for (int i = 1; i <= move[3] - move[1]; i++) {
            if ((move[2] - move[0]) == 0) {
                if (board[move[0]][move[1] + i].equals("")) {
                    isLegal = true;
                    break;
                }
            }
        }

        for (int i = move[1] - move[3]; i >= 1; i--) {
            if ((move[2] - move[0]) == 0) {
                if (board[move[0]][move[1] - i].equals("")) {
                    isLegal = true;
                    break;
                }
            }
        }*/


        ////diagonal movement
        
        



         //move northeast
         if (move[2] - move[0] > 0) {
            if (move[3] - move[1] > 0) {
                if ((Math.abs(move[2] - move[0]) == Math.abs((move[3] - move[1])))) {
                    for (int i = 1; i < Math.abs(move[2] - move[0]); i++) {
                        if (board[move[0] + i][move[1] + i].equals("")) {
                            isLegal = true;
                        
                        }

                        else {
                            return false;
                        }
                    }
            }
        }
    }
        //move north west
        if (move[2] - move[0] > 0) {
            if (move[3] - move[1] < 0) {
                if ((Math.abs((move[2] - move[0])) == Math.abs((move[3] - move[1])))) {

                for (int i = 1; i < Math.abs(move[2] - move[0]); i++) {
                        if (board[move[0] + i][move[1] - i].equals("")) {
                            isLegal = true;
                            
                        }
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        

        //move south west
        if (move[2] - move[0] < 0) {
            if (move[3] - move[1] < 0) {
                if ((Math.abs((move[2] - move[0])) == Math.abs((move[3] - move[1])))) {
                for (int i = 1; i < Math.abs(move[2] - move[0]); i++) {
                        if (board[move[0] - i][move[1] - i].equals("")) {
                            isLegal = true;
                            
                        }
                        else {
                            return false;
                        }
                    }
                }
            }
        }

        //move south east
        if (move[2] - move[0] < 0) {
            if (move[3] - move[1] > 0) {
                if ((Math.abs((move[2] - move[0])) == Math.abs((move[3] - move[1])))) {
                for (int i = 1; i < Math.abs(move[2] - move[0]); i++) {
                        if (board[move[0] - i][move[1] + i].equals("")) {
                            isLegal = true;
                        }
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        


        

        /*if (move[2] - move[0] > 0) {
            if (move[3] - move[1] > 0) {
                for (int i = 1; i <= Math.abs(move[2] - move[0]); i++) {
                    if ((Math.abs(move[2] - move[0])) == Math.abs((move[3] - move[1]))) {
                        if (!board[move[0] + i][move[1] + i].equals("")) {
                            isLegal = false;
                            break;
                        }
                    }
                }

            }
        


        if (move[2] - move[0] > 0) {
            if (move[3] - move[1] < 0) {
                for (int i = 1; i <= Math.abs(move[2] - move[0]); i++) {
                    if (Math.abs((move[2] - move[0])) == Math.abs((move[3] - move[1]))) {
                        if (!board[move[0] + i][move[1] - i].equals("")) {
                            isLegal = false;
                            break;
                        }
                    }
                }
            }
        
        if (move[2] - move[0] < 0) {
            if (move[3] - move[1] < 0) {
                for (int i = 1; i <= Math.abs(move[2] - move[0]); i++) {
                    if (Math.abs((move[2] - move[0])) == Math.abs((move[3] - move[1]))) {

                        if (!board[move[0] - i][move[1] - i].equals("")) {
                            isLegal = false;
                            break;
                        }
                    }
                }
            }
        

        if (move[2] - move[0] < 0) {
            if (move[3] - move[1] > 0) {
                for (int i = 1; i <= Math.abs(move[2] - move[0]); i++) {
                   if (Math.abs((move[2] - move[0])) == Math.abs((move[3] - move[1]))) {

                        if (!board[move[0] - i][move[1] + i].equals("")) {
                            isLegal = false;
                            break;
                        }
                    }
                }
            }*/
        



        /*for (int i = 1; i <= move[2] - move[0]; i++) {
            if (!board[move[0] + i][move[1] + i].equals("")) {
                isLegal = false;
                break;
            }
        }*/
              

        if ((move[2] - move[0]) == 1 && (move[3] - move[1]) == 0) {
            
            isLegal = true;
        }

        if ((move[2] - move[0]) == -1 && (move[3] - move[1]) == 0) {
            
            isLegal = true;
        }

        if ((move[3] - move[1]) == 1 && (move[2] - move[0]) == 0) {
            
            isLegal = true;
        }

        if ((move[3] - move[1]) == -1 && (move[2] - move[0]) == 0) {
            
            isLegal = true;
        }



        /************************************************************* */

        if ((move[2] - move[0]) == 1 && (move[3] - move[1]) == 1) {
            
            isLegal = true;
        }

        if ((move[2] - move[0]) == -1 && (move[3] - move[1]) == 1) {
            
            isLegal = true;
        }

        if ((move[2] - move[0]) == 1 && (move[3] - move[1]) == -1) {
            
            isLegal = true;
        }

        if ((move[2] - move[0]) == -1 && (move[3] - move[1]) == -1) {
            
            isLegal = true;
        }
            
            
  


        if (isLegal) {
              if (board[move[2]][move[3]] == "") {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;

            }
            else if (board[move[2]][move[3]] != ""
            && ((board[move[2]][move[3]].charAt(0) == 'B' && board[move[0]][move[1]].charAt(0) == 'W') || (board[move[2]][move[3]].charAt(0) == 'W' && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;
            }
            }
            
        
        return false;
    }

    public static boolean moveBishop(String[][] board, int[] move) {
        boolean isLegal = false;

        if ((move[2] - move[0] == 0) || (move[3] - move[1] == 0)) {
            isLegal = false;

            return false;
        }
        

       


        //move northeast
        if (move[2] - move[0] > 0) {
            if (move[3] - move[1] > 0) {
                if ((Math.abs(move[2] - move[0]) == Math.abs((move[3] - move[1])))) {
                    for (int i = 1; i < Math.abs(move[2] - move[0]); i++) {
                        if (board[move[0] + i][move[1] + i].equals("")) {
                            isLegal = true;
                        
                        }

                        else {
                            return false;
                        }
                    }
            }
        }
    }
        //move north west
        if (move[2] - move[0] > 0) {
            if (move[3] - move[1] < 0) {
                if ((Math.abs((move[2] - move[0])) == Math.abs((move[3] - move[1])))) {

                for (int i = 1; i < Math.abs(move[2] - move[0]); i++) {
                        if (board[move[0] + i][move[1] - i].equals("")) {
                            isLegal = true;
                            
                        }
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        

        //move south west
        if (move[2] - move[0] < 0) {
            if (move[3] - move[1] < 0) {
                if ((Math.abs((move[2] - move[0])) == Math.abs((move[3] - move[1])))) {
                for (int i = 1; i < Math.abs(move[2] - move[0]); i++) {
                        if (board[move[0] - i][move[1] - i].equals("")) {
                            isLegal = true;
                            
                        }
                        else {
                            return false;
                        }
                    }
                }
            }
        }

        //move south east
        if (move[2] - move[0] < 0) {
            if (move[3] - move[1] > 0) {
                if ((Math.abs((move[2] - move[0])) == Math.abs((move[3] - move[1])))) {
                for (int i = 1; i < Math.abs(move[2] - move[0]); i++) {
                        if (board[move[0] - i][move[1] + i].equals("")) {
                            isLegal = true;
                        }
                        else {
                            return false;
                        }
                    }
                }
            }
        }



        if ((move[2] - move[0]) == 1 && (move[3] - move[1]) == 1) {
            
            isLegal = true;
        }

        if ((move[2] - move[0]) == -1 && (move[3] - move[1]) == 1) {
            
            isLegal = true;
        }

        if ((move[2] - move[0]) == 1 && (move[3] - move[1]) == -1) {
            
            isLegal = true;
        }

        if ((move[2] - move[0]) == -1 && (move[3] - move[1]) == -1) {
            
            isLegal = true;
        }

        if (isLegal) {
              if (board[move[2]][move[3]] == "") {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;

            }
            else if (board[move[2]][move[3]] != ""
            && ((board[move[2]][move[3]].charAt(0) == 'B' && board[move[0]][move[1]].charAt(0) == 'W') || (board[move[2]][move[3]].charAt(0) == 'W' && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;
            }

            }
        return false;
    }


    public static boolean moveRook(String[][] board, int[] move) {
        boolean isLegal = false;

        if ((move[2] - move[0] != 0) && (move[3] - move[1] != 0)) {
            isLegal = false;

            return false;
        }

        //move vertical



        if (move[2] - move[0] > 0) { 
        if (move[3] - move[1] == 0) {
        for (int i = 1; i < move[2] - move[0]; i++) {
            if (board[move[0] + i][move[1]].equals("")) {
                isLegal = true;
               
            }
            else {
                return false;
            }
        }
        }
        }

        if (move[2] - move[0] < 0) { 
        if (move[3] - move[1] == 0) {
        
        for (int i = 1; i < move[0] - move[2]; ++i) {
        /*for (int i = move[0] - move[2]; i > 1; i--) {*/
            if (board[move[0] - i][move[1]].equals("")) {
                isLegal = true;
            }
            else {
                return false;
            }
        }
        }
        }
        ///horizontal movement

        if (move[2] - move[0] == 0) { 
        if (move[3] - move[1] > 0) {
        for (int i = 1; i < move[3] - move[1]; i++) {
            if (board[move[0]][move[1] + i].equals("")) {
                isLegal = true;
            }
            else {
                return false;
            }
        }
        }
        }



        if (move[2] - move[0] == 0) { 
        if (move[3] - move[1] < 0) {
        for (int i = 1; i < move[1] - move[3]; ++i) {
            if (board[move[0]][move[1] - i].equals("")) {
                isLegal = true;
            }
            else {
                return false;
            }
        }
        }
        }

        if ((move[2] - move[0]) == 1 && (move[3] - move[1]) == 0) {
            
            isLegal = true;
        }

        if ((move[2] - move[0]) == -1 && (move[3] - move[1]) == 0) {
            
            isLegal = true;
        }

        if ((move[3] - move[1]) == 1 && (move[2] - move[0]) == 0) {
            
            isLegal = true;
        }

        if ((move[3] - move[1]) == -1 && (move[2] - move[0]) == 0) {
            
            isLegal = true;
        }
            


         if (isLegal) {
              if (board[move[2]][move[3]] == "") {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;

            }
            else if (board[move[2]][move[3]] != ""
            && ((board[move[2]][move[3]].charAt(0) == 'B' && board[move[0]][move[1]].charAt(0) == 'W') || (board[move[2]][move[3]].charAt(0) == 'W' && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;
            }

           


            }

        return false;
    }

    public static boolean moveKnight(String[][] board, int[] move) {
        boolean isLegal = false;

        ///all knight moves with positive y direction

        if ((move[2] - move[0]) == 2) {
            if ((move[3] - move[1]) == 1) {
                if ((board[move[0] + 2][move[1] + 1].equals("")) 
                || ((board[move[2]][move[3]].charAt(0) == 'B' 
                && board[move[0]][move[1]].charAt(0) == 'W') 
                || (board[move[2]][move[3]].charAt(0) == 'W' 
                && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                    isLegal = true;
                }
            }
        }

        if ((move[2] - move[0]) == 2) {
            if ((move[3] - move[1]) == -1) {
                if ((board[move[0] + 2][move[1] - 1].equals("")) 
                || ((board[move[2]][move[3]].charAt(0) == 'B' 
                && board[move[0]][move[1]].charAt(0) == 'W') 
                || (board[move[2]][move[3]].charAt(0) == 'W' 
                && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                    isLegal = true;
                }
            }
        }

        if ((move[2] - move[0]) == 1) {
            if ((move[3] - move[1]) == 2) {
            if ((board[move[0] + 1][move[1] + 2].equals("")) 
                || ((board[move[2]][move[3]].charAt(0) == 'B' 
                && board[move[0]][move[1]].charAt(0) == 'W') 
                || (board[move[2]][move[3]].charAt(0) == 'W' 
                && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                    isLegal = true;
                }
            }
        }

        if ((move[2] - move[0]) == 1) {
            if ((move[3] - move[1]) == -2) {
            if ((board[move[0] + 1][move[1] - 2].equals("")) 
                || ((board[move[2]][move[3]].charAt(0) == 'B' 
                && board[move[0]][move[1]].charAt(0) == 'W') 
                || (board[move[2]][move[3]].charAt(0) == 'W' 
                && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                    isLegal = true;
                }
            }
        }

        if ((move[2] - move[0]) == -2) {
            if ((move[3] - move[1]) == 1) {
             if ((board[move[0] - 2][move[1] + 1].equals("")) 
                || ((board[move[2]][move[3]].charAt(0) == 'B' 
                && board[move[0]][move[1]].charAt(0) == 'W') 
                || (board[move[2]][move[3]].charAt(0) == 'W' 
                && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                    isLegal = true;
                }
            }
        }

        if ((move[2] - move[0]) == -2) {
            if ((move[3] - move[1]) == -1) {
            if ((board[move[0] - 2][move[1] - 1].equals("")) 
                || ((board[move[2]][move[3]].charAt(0) == 'B' 
                && board[move[0]][move[1]].charAt(0) == 'W') 
                || (board[move[2]][move[3]].charAt(0) == 'W' 
                && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                    isLegal = true;
                }
            }
        }

        if ((move[2] - move[0]) == -1) {
            if ((move[3] - move[1]) == 2) {
            if ((board[move[0] - 1][move[1] + 2].equals("")) 
                || ((board[move[2]][move[3]].charAt(0) == 'B' 
                && board[move[0]][move[1]].charAt(0) == 'W') 
                || (board[move[2]][move[3]].charAt(0) == 'W' 
                && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                    isLegal = true;
                }
            }
        }

        if ((move[2] - move[0]) == -1) {
            if ((move[3] - move[1]) == -2) {
             if ((board[move[0] - 1][move[1] - 2].equals("")) 
                || ((board[move[2]][move[3]].charAt(0) == 'B' 
                && board[move[0]][move[1]].charAt(0) == 'W') 
                || (board[move[2]][move[3]].charAt(0) == 'W' 
                && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                    isLegal = true;
                }
            }
        }


            /*if (!board[move[0] + 1][move[1] - 2].equals("")) {
                isLegal = false;
                }
             /*if (!board[move[0] + 1 ][move[1] - 2].equals("")) {
                isLegal = false;
                }

        /*///all knight moves with negative y direction

            /*if (!board[move[0] - 3][move[1] + 1].equals("")) {
                isLegal = false;
                }
            if (!board[move[0] - 1][move[1] + 3].equals("")) {
                isLegal = false;
                }
             if (!board[move[0] - 3][move[1] - 1].equals("")) {
                isLegal = false;
                }

            if (!board[move[0] - 1 ][move[1] - 3].equals("")) {
                isLegal = false;
                }*/


      if (isLegal) {
              if (board[move[2]][move[3]] == "") {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;

            }
            else if (board[move[2]][move[3]] != ""
            && ((board[move[2]][move[3]].charAt(0) == 'B' && board[move[0]][move[1]].charAt(0) == 'W') || (board[move[2]][move[3]].charAt(0) == 'W' && (board[move[0]][move[1]].charAt(0) == 'B')))) {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;
            }
            }
      

        return false;
    }


//        for (int i = 0; i < move[3] - move[1]; i++) {
//
//            if (board[move[0]][move[1+i]].contains("****") || board[move[0]][move[1] + i].contains("    ")) {
//                board[move[2]][move[3]] = board[move[0]][move[1]];
//                board[move[0]][move[1]] = "";
//                return true;
//            }
//        }

    public static boolean isLegal(String[][] board, int[] move) {
        // Figure out which function to call
        //white pieces
        if (board[move[0]][move[1]].equals("WP"))
            return movePawnWhite(board, move);
        if (board[move[0]][move[1]].equals("WQ"))
            return moveQueen(board, move);
        /*if ((0 <= board[move]] && board[move[]] <= 8) && (0 <= board[move]] && board[move]] <= 8)){*/
        if (board[move[0]][move[1]].equals("WR"))
            return moveRook(board, move);
        if (board[move[0]][move[1]].equals("WN"))
            return moveKnight(board, move);
        if (board[move[0]][move[1]].equals("WK"))
            return moveKing(board, move);
        if (board[move[0]][move[1]].equals("WB"))
            return moveBishop(board, move);
        ///black pieces
        if (board[move[0]][move[1]].equals("BP"))
            return movePawnBlack(board, move);
        if (board[move[0]][move[1]].equals("BQ"))
            return moveQueen(board, move);
        /*if ((0 <= board[move]] && board[move[]] <= 8) && (0 <= board[move]] && board[move]] <= 8)){*/
        if (board[move[0]][move[1]].equals("BR"))
            return moveRook(board, move);
        if (board[move[0]][move[1]].equals("BN"))
            return moveKnight(board, move);
        if (board[move[0]][move[1]].equals("BK"))
            return moveKing(board, move);
        if (board[move[0]][move[1]].equals("BB"))
            return moveBishop(board, move);
        return false;
    }


        /*public static void updateBoard(String[][] board, int[] move) {
            board[move[2]][move[3]] = board[move[0]][move[1]];
            board[move[0]][move[1]] = "";

        }*/

    public static void makeComputerMove(String[][] board) {

        int i;
        Random rand = new Random();


        int[] retVal = new int[4];

        while (true) {
            for (i = 0; i < retVal.length; i++) {
                int max = 8;
                int min = 1;
                retVal[i] = rand.nextInt((max - min)+ 1) + min;
                System.out.println(retVal[i]);
            }
            if (!board[retVal[0]][retVal[1]].equals("") && (board[retVal[0]][retVal[1]].charAt(0)) == ('B') && (isLegal(board, retVal))) {
                break;
            }


        }


    }


    public static Boolean checkForBlackKing(String[][] board) {
        for (int i = 1; i < 9; ++i) {
            for (int j = 1; j < 9; ++j) {
                if (board[i][j].equals("BK")) {
                    return true;

                }
            }
        }
                return false;
        }


    public static Boolean checkForWhiteKing(String[][] board) {
        for (int i = 1; i < 9; ++i) {
            for (int j = 1; j < 9; ++j) {
                if (board[i][j].equals("WK")) {
                    return true;

                }
            }
        }
                return false;
        }


    }


        /*for (i = 0; i < board.length; i++) {
            for (j = 0; j < board.length; i++) {


            }
        }


        int[] move = */


