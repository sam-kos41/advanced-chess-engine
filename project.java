public class Board {

	//global variables
	private Piece[][] board = new Piece[8][8]; //to show game status in text based version
	private ArrayList<Piece> pieces = new ArrayList<Piece>(32); //create 32 pieces, 16 for both players
	
	/**
	 * Constructor.
	 */
	public Board() {
		initBoard(); //places pieces on the board 
	}
	

Shows the current game state which is 8x8 board with pieces on it.
//	 * For text based version.
//	 */
//	public void showGameState() {
//		System.out.println();
//		System.out.println("###########################");
//		for (int i=0; i<=7; i++) {
//			System.out.print("#|"); //all rows begin with a divider
//			for (int j=0; j<=7; j++) {
//				if (board[i][j] == null) { //if empty square
//					System.out.print("  "); //print empty string
//					System.out.print("|"); //print divider
//				} else {
//					System.out.print(board[i][j].getAcronym()); //print acronym
//					System.out.print("|"); //print divider
//				}
//			}
//			System.out.println("#"); //next row
//			if (i!=7) {
//				System.out.println("#|-----------------------|#"); //line break between rows
//			}
//		}
//		System.out.println("###########################");
//		System.out.println();
//	}
	
//	/**
//	 * Shows the current game state which is 8x8 board with pieces on it.
//	 * Shows notation on each side.
//	 * For text based version.
//	 */
//	public void showGameStateWithNotation() {
//		System.out.println();
//		System.out.println("     a  b  c  d  e  f  g  h"); //alphabetic notation
//		System.out.println("  ###########################");
//		for (int i=0; i<=7; i++) { //rows
//			System.out.print(8-i+" #|"); //all rows begin with a divider
//			for (int j=0; j<=7; j++) { //columns
//				if (board[i][j] == null) { //if empty square
//					System.out.print("  "); //print empty string
//					System.out.print("|"); //print divider
//				} else {
//					System.out.print(board[i][j].getAcronym()); //print acronym
//					System.out.print("|"); //print divider
//				}
//			}
//			System.out.println("# " +(8-i)); //next row
//			if (i!=7) {
//				System.out.println("  #|-----------------------|#"); //linebreak between rows
//			}
//		}
//		System.out.println("  ###########################");
//		System.out.println("     a  b  c  d  e  f  g  h"); //alphabetic notation
//		System.out.println();