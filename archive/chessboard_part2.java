import java.util.Scanner;

public class chessboard {
    public static void main(String[] args) {
        String[][] board = setupBoard();
        Scanner scnr = new Scanner(System.in);

        while (gameIsNotOver(board)) {
            printBoard(board);
            int[] move = getUserInput(scnr);
            if (isLegal(board, move)) {

            }
            else {
                System.out.println("That is not a legal move. Try again.");
                continue;
            }
            makeComputerMove(board);
        }
        System.out.println("The game is over!");
    }
    public static String[][] setupBoard() {

        String[][] board = new String [9][9];
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

        for (int i = board.length - 1; i >=1; i--) {
            System.out.print(i + "  " );

            for (int j = 1; j < board[i].length; j++) {
                if (isWhite) {
                    System.out.print(emptyWhite);
                }
                else {
                    System.out.print(emptyBlack);

                }
                isWhite = !isWhite;

            }

            System.out.println();
            System.out.print("   " );

            for (int j = 1; j < board[i].length; j++, isWhite = !isWhite)
                if (isWhite)
                    if (board[i][j].equals(""))
                        System.out.print(emptyWhite);
                    else
                        System.out.print(" " + board[i][j] + " ");
                else
                if (board[i][j].equals(""))
                    System.out.print(emptyBlack);
                else
                    System.out.print("*" + board[i][j]+ "*");
            System.out.println();
            isWhite = !isWhite;

        }
        char name = 'A';

        for (int j = 0 ; j < 8; j++)
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

    public static boolean movePawn(String[][] board, int[] move) {
        System.out.println(board[move[0] + 1][move[1]]);
            if (board[move[0]+1][move[1]].equals("")) {
                if ((move[2] - move[0]) == 1) {
                board[move[2]][move[3]] = board[move[0]][move[1]];
                board[move[0]][move[1]] = "";
                return true;
        }
    }
        return false;
    }

    public static boolean moveQueen(String[][] board, int [] move) {
        // vertical movement
        boolean isLegal = true;
        for (int i = 1; i <= move[2] - move[0]; i++) {
            if (!board[move[0] + i][move[1]].equals("")) {
                isLegal = false;
                break;
            }
        }
        for (int i = 1; i >= move[2] - move[0]; i--) {
            if (!board[move[0] - i][move[1]].equals("")) {
                isLegal = false;
                break;
            }
        }
        if(isLegal) {
            board[move[2]][move[3]] = board[move[0]][move[1]];
            board[move[0]][move[1]] = "";
            return true;
        }

//        for (int i = 0; i < move[3] - move[1]; i++) {
//
//            if (board[move[0]][move[1+i]].contains("****") || board[move[0]][move[1] + i].contains("    ")) {
//                board[move[2]][move[3]] = board[move[0]][move[1]];
//                board[move[0]][move[1]] = "";
//                return true;
//            }
//        }



        return false;
    }
    public static boolean isLegal(String[][] board, int[] move) {
        // Figure out which function to call
        if(board[move[0]][move[1]].equals("WP"))
        movePawn(board, move);
        if(board[move[0]][move[1]].equals("WQ"))
            moveQueen(board, move);
        /*if ((0 <= board[move]] && board[move[]] <= 8) && (0 <= board[move]] && board[move]] <= 8)){*/
        return true;
    }

        /*public static void updateBoard(String[][] board, int[] move) {
            board[move[2]][move[3]] = board[move[0]][move[1]];
            board[move[0]][move[1]] = "";

        }*/

    public static void makeComputerMove(String[][] board) {
    }
}