public class chessboard_turnin {
    public static void main(String[] args) {
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
}