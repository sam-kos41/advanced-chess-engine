/**
 * Professional Chess Game Implementation
 * Author: Sam Koscelny
 * 
 * A complete chess game with improved code structure, better AI,
 * and enhanced user experience. This implementation demonstrates
 * object-oriented programming principles and game development skills.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ChessGame {
    private static final int BOARD_SIZE = 9; // Using 9x9 with 1-8 indices for easier coordinate handling
    private static final String EMPTY = "";
    private static final String WHITE_PREFIX = "W";
    private static final String BLACK_PREFIX = "B";
    
    private String[][] board;
    private Scanner scanner;
    private List<String> moveHistory;
    private boolean gameOver;
    private String winner;
    
    public ChessGame() {
        board = new String[BOARD_SIZE][BOARD_SIZE];
        scanner = new Scanner(System.in);
        moveHistory = new ArrayList<>();
        gameOver = false;
        winner = null;
        initializeBoard();
    }
    
    /**
     * Main game loop
     */
    public void playGame() {
        displayWelcomeMessage();
        
        if (!confirmPlay()) {
            System.out.println("Thanks for considering my chess game!");
            return;
        }
        
        displayRules();
        
        while (!gameOver) {
            displayBoard();
            
            // Player turn
            if (playerTurn()) {
                if (checkGameEnd()) break;
            }
            
            // Computer turn
            System.out.println(YELLOW + "\n🤖 Computer is analyzing position..." + RESET);
            computerTurn();
            if (checkGameEnd()) break;
        }
        
        displayGameEnd();
    }
    
    /**
     * Initialize the chess board with pieces in starting positions
     */
    private void initializeBoard() {
        // Clear board
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
        
        // Black pieces (row 8)
        board[8][1] = board[8][8] = "BR"; // Rooks
        board[8][2] = board[8][7] = "BN"; // Knights
        board[8][3] = board[8][6] = "BB"; // Bishops
        board[8][4] = "BQ"; // Queen
        board[8][5] = "BK"; // King
        
        // Black pawns (row 7)
        for (int i = 1; i <= 8; i++) {
            board[7][i] = "BP";
        }
        
        // White pieces (row 1)
        board[1][1] = board[1][8] = "WR"; // Rooks
        board[1][2] = board[1][7] = "WN"; // Knights
        board[1][3] = board[1][6] = "WB"; // Bishops
        board[1][4] = "WK"; // King
        board[1][5] = "WQ"; // Queen
        
        // White pawns (row 2)
        for (int i = 1; i <= 8; i++) {
            board[2][i] = "WP";
        }
    }
    
    /**
     * Display welcome message
     */
    private void displayWelcomeMessage() {
        System.out.println("========================================");
        System.out.println("    PROFESSIONAL CHESS GAME v2.0");
        System.out.println("    Created by Sam Koscelny");
        System.out.println("========================================");
        System.out.println();
    }
    
    /**
     * Confirm if player wants to play
     */
    private boolean confirmPlay() {
        while (true) {
            System.out.print("Would you like to play chess? (yes/no): ");
            String response = scanner.nextLine().toLowerCase().trim();
            
            if (response.equals("yes") || response.equals("y")) {
                return true;
            } else if (response.equals("no") || response.equals("n")) {
                return false;
            } else {
                System.out.println("Please enter 'yes' or 'no'.");
            }
        }
    }
    
    /**
     * Display game rules
     */
    private void displayRules() {
        System.out.println(CYAN + BOLD + "GAME RULES:" + RESET);
        System.out.println("• You control the " + BOLD + "WHITE pieces ♔♕♖♗♘♙" + RESET + ", computer controls " + BOLD + "BLACK pieces ♚♛♜♝♞♟" + RESET);
        System.out.println("• Enter moves as: " + YELLOW + "<row> <column> <row> <column>" + RESET);
        System.out.println("• " + BOLD + "COORDINATE SYSTEM:" + RESET);
        System.out.println("  - Rows: 1-8 (numbers)");
        System.out.println("  - Columns: 1-8 (where A=1, B=2, C=3, D=4, E=5, F=6, G=7, H=8)");
        System.out.println("• " + BOLD + "Examples:" + RESET);
        System.out.println("  - " + YELLOW + "'2 2 3 2'" + RESET + " moves piece from B2 to B3");
        System.out.println("  - " + YELLOW + "'2 5 4 5'" + RESET + " moves piece from E2 to E4");
        System.out.println("• Type " + CYAN + "'quit'" + RESET + " to exit the game");
        System.out.println("• Type " + CYAN + "'history'" + RESET + " to see move history");
        System.out.println();
        
        // Display coordinate reference
        System.out.println(YELLOW + BOLD + "QUICK REFERENCE:" + RESET);
        System.out.print("Columns: ");
        for (char col = 'A'; col <= 'H'; col++) {
            int num = col - 'A' + 1;
            System.out.print(CYAN + col + "=" + num + RESET + "  ");
        }
        System.out.println();
        System.out.println();
    }
    
    // ANSI Color codes for better visual display
    private static final String RESET = "\u001B[0m";
    private static final String WHITE_BG = "\u001B[47m";
    private static final String BLACK_BG = "\u001B[40m";
    private static final String LIGHT_GRAY_BG = "\u001B[100m";
    private static final String WHITE_TEXT = "\u001B[37m";
    private static final String BLACK_TEXT = "\u001B[30m";
    private static final String BOLD = "\u001B[1m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    
    /**
     * Display the current board state with enhanced visuals
     */
    private void displayBoard() {
        System.out.println();
        System.out.println(CYAN + BOLD + "╔═══════════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + BOLD + "║                          CHESS BOARD                             ║" + RESET);
        System.out.println(CYAN + BOLD + "╚═══════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.println();
        
        // Column headers
        System.out.print("     ");
        for (char col = 'A'; col <= 'H'; col++) {
            System.out.print(YELLOW + BOLD + "   " + col + "   " + RESET);
        }
        System.out.println();
        
        // Top border
        System.out.print("   ╔");
        for (int j = 1; j <= 8; j++) {
            System.out.print("═══════");
            if (j < 8) System.out.print("╤");
        }
        System.out.println("╗");
        
        for (int i = 8; i >= 1; i--) {
            // Print pieces row
            System.out.print(YELLOW + BOLD + " " + i + " " + RESET + "║");
            
            for (int j = 1; j <= 8; j++) {
                boolean isLightSquare = (i + j) % 2 == 1;
                String piece = board[i][j];
                String pieceDisplay = getPieceUnicode(piece);
                
                if (isLightSquare) {
                    System.out.print(WHITE_BG + BLACK_TEXT + "   " + pieceDisplay + "   " + RESET);
                } else {
                    System.out.print(LIGHT_GRAY_BG + WHITE_TEXT + "   " + pieceDisplay + "   " + RESET);
                }
                
                if (j < 8) {
                    System.out.print("│");
                }
            }
            System.out.println("║ " + YELLOW + BOLD + i + RESET);
            
            // Print separator between rows (except after last row)
            if (i > 1) {
                System.out.print("   ╟");
                for (int j = 1; j <= 8; j++) {
                    System.out.print("───────");
                    if (j < 8) System.out.print("┼");
                }
                System.out.println("╢");
            }
        }
        
        // Bottom border
        System.out.print("   ╚");
        for (int j = 1; j <= 8; j++) {
            System.out.print("═══════");
            if (j < 8) System.out.print("╧");
        }
        System.out.println("╝");
        
        // Bottom column headers
        System.out.print("     ");
        for (char col = 'A'; col <= 'H'; col++) {
            System.out.print(YELLOW + BOLD + "   " + col + "   " + RESET);
        }
        System.out.println();
        System.out.println();
    }
    
    /**
     * Get Unicode chess piece symbols for better visual representation
     */
    private String getPieceUnicode(String piece) {
        if (piece.equals(EMPTY)) return " ";
        
        char color = piece.charAt(0);
        char type = piece.charAt(1);
        
        // Unicode chess pieces
        switch (type) {
            case 'K': return color == 'W' ? "♔" : "♚"; // King
            case 'Q': return color == 'W' ? "♕" : "♛"; // Queen
            case 'R': return color == 'W' ? "♖" : "♜"; // Rook
            case 'B': return color == 'W' ? "♗" : "♝"; // Bishop
            case 'N': return color == 'W' ? "♘" : "♞"; // Knight
            case 'P': return color == 'W' ? "♙" : "♟"; // Pawn
            default: return piece;
        }
    }
    
    /**
     * Handle player turn
     */
    private boolean playerTurn() {
        while (true) {
            System.out.println(CYAN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
            System.out.print(BOLD + "Your turn! " + RESET + "Enter move as " + YELLOW + "<row> <col> <row> <col>" + RESET + " (or 'quit'/'history'): ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("quit")) {
                gameOver = true;
                winner = "Game ended by player";
                return false;
            }
            
            if (input.equalsIgnoreCase("history")) {
                displayMoveHistory();
                continue;
            }
            
            int[] move = parseMove(input);
            if (move == null) {
                System.out.println("Invalid input format. Use: <start_row> <start_col> <end_row> <end_col>");
                continue;
            }
            
            if (!isValidCoordinates(move)) {
                System.out.println("Coordinates must be between 1-8.");
                continue;
            }
            
            if (!isPlayerPiece(move[0], move[1])) {
                System.out.println("You can only move white pieces (W).");
                continue;
            }
            
            if (isLegalMove(move)) {
                String moveStr = formatMove(move);
                String piece = getPieceUnicode(board[move[2]][move[3]]);
                moveHistory.add("White: " + moveStr);
                System.out.println(CYAN + "✓ Move executed: " + RESET + BOLD + piece + " " + moveStr + RESET);
                return true;
            } else {
                System.out.println(CYAN + "✗ Illegal move. Please try again." + RESET);
            }
        }
    }
    
    /**
     * Parse move input string into coordinates
     */
    private int[] parseMove(String input) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 4) return null;
        
        try {
            int[] move = new int[4];
            for (int i = 0; i < 4; i++) {
                move[i] = Integer.parseInt(parts[i]);
            }
            return move;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * Validate coordinates are within board bounds
     */
    private boolean isValidCoordinates(int[] move) {
        for (int coord : move) {
            if (coord < 1 || coord > 8) return false;
        }
        return true;
    }
    
    /**
     * Check if piece belongs to player
     */
    private boolean isPlayerPiece(int row, int col) {
        String piece = board[row][col];
        return !piece.equals(EMPTY) && piece.startsWith(WHITE_PREFIX);
    }
    
    /**
     * Format move for display
     */
    private String formatMove(int[] move) {
        char startCol = (char)('A' + move[1] - 1);
        char endCol = (char)('A' + move[3] - 1);
        return "" + startCol + move[0] + " to " + endCol + move[2];
    }
    
    /**
     * Display move history
     */
    private void displayMoveHistory() {
        System.out.println("\nMove History:");
        if (moveHistory.isEmpty()) {
            System.out.println("No moves yet.");
        } else {
            for (int i = 0; i < moveHistory.size(); i++) {
                System.out.println((i + 1) + ". " + moveHistory.get(i));
            }
        }
    }
    
    /**
     * Handle computer turn with improved AI
     */
    private void computerTurn() {
        List<int[]> possibleMoves = generateAllLegalMoves(BLACK_PREFIX);
        
        if (possibleMoves.isEmpty()) {
            gameOver = true;
            winner = "White (Computer has no legal moves)";
            return;
        }
        
        // Advanced AI: evaluate all moves and pick the best
        int[] bestMove = chooseBestMove(possibleMoves);
        int bestScore = evaluateMove(bestMove);
        
        // Show AI reasoning
        String reasoning = getAIReasoning(bestMove, bestScore);
        System.out.println(CYAN + "💭 AI Analysis: " + reasoning + RESET);
        
        isLegalMove(bestMove); // Execute the move
        
        String moveStr = formatMove(bestMove);
        String piece = getPieceUnicode(board[bestMove[2]][bestMove[3]]);
        moveHistory.add("Black: " + moveStr);
        System.out.println(YELLOW + "🤖 Computer played: " + RESET + BOLD + piece + " " + moveStr + RESET);
    }
    
    /**
     * Generate all legal moves for a given side
     */
    private List<int[]> generateAllLegalMoves(String side) {
        List<int[]> moves = new ArrayList<>();
        
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                String piece = board[row][col];
                if (!piece.equals(EMPTY) && piece.startsWith(side)) {
                    // Try all possible destination squares
                    for (int endRow = 1; endRow <= 8; endRow++) {
                        for (int endCol = 1; endCol <= 8; endCol++) {
                            int[] move = {row, col, endRow, endCol};
                            if (isLegalMoveTest(move)) {
                                moves.add(move);
                            }
                        }
                    }
                }
            }
        }
        
        return moves;
    }
    
    /**
     * Advanced AI: Choose best move using multiple strategic factors
     */
    private int[] chooseBestMove(List<int[]> moves) {
        if (moves.isEmpty()) return null;
        
        int bestScore = Integer.MIN_VALUE;
        List<int[]> bestMoves = new ArrayList<>();
        
        // Evaluate each possible move
        for (int[] move : moves) {
            int score = evaluateMove(move);
            
            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (score == bestScore) {
                bestMoves.add(move);
            }
        }
        
        // Return random move from best moves (handles ties)
        Random random = new Random();
        return bestMoves.get(random.nextInt(bestMoves.size()));
    }
    
    /**
     * Comprehensive move evaluation system
     */
    private int evaluateMove(int[] move) {
        int score = 0;
        String movingPiece = board[move[0]][move[1]];
        String targetPiece = board[move[2]][move[3]];
        
        // PRIORITY 1: CAPTURES (High Value)
        if (!targetPiece.equals(EMPTY)) {
            score += getPieceValue(targetPiece) * 10; // Capture value
            
            // Bonus for capturing high-value pieces
            if (targetPiece.equals("WQ")) score += 50; // Queen capture bonus
            if (targetPiece.equals("WK")) score += 1000; // King capture = win
        }
        
        // PRIORITY 2: PIECE SAFETY (Avoid moving into danger)
        if (isSquareUnderAttack(move[2], move[3], WHITE_PREFIX)) {
            score -= getPieceValue(movingPiece) * 5; // Penalty for moving into danger
        }
        
        // PRIORITY 3: CENTRAL CONTROL (Good positioning)
        score += getCentralControlScore(move[2], move[3]);
        
        // PRIORITY 4: PIECE DEVELOPMENT (Move pieces toward center/activity)
        score += getDevelopmentScore(move, movingPiece);
        
        // PRIORITY 5: TACTICAL OPPORTUNITIES
        score += getTacticalScore(move);
        
        // PRIORITY 6: KING SAFETY (Protect our king)
        score += getKingSafetyScore(move);
        
        return score;
    }
    
    /**
     * Get piece values for evaluation
     */
    private int getPieceValue(String piece) {
        if (piece.equals(EMPTY)) return 0;
        
        char type = piece.charAt(1);
        switch (type) {
            case 'P': return 1;    // Pawn
            case 'N': return 3;    // Knight
            case 'B': return 3;    // Bishop
            case 'R': return 5;    // Rook
            case 'Q': return 9;    // Queen
            case 'K': return 100;  // King
            default: return 0;
        }
    }
    
    /**
     * Evaluate central control (pieces in center are more powerful)
     */
    private int getCentralControlScore(int row, int col) {
        int score = 0;
        
        // Center squares (d4, d5, e4, e5) are most valuable
        if ((row == 4 || row == 5) && (col == 4 || col == 5)) {
            score += 4;
        }
        // Extended center squares
        else if (row >= 3 && row <= 6 && col >= 3 && col <= 6) {
            score += 2;
        }
        
        return score;
    }
    
    /**
     * Evaluate piece development (encourage moving pieces from starting positions)
     */
    private int getDevelopmentScore(int[] move, String piece) {
        int score = 0;
        char type = piece.charAt(1);
        
        // Encourage moving pieces from back rank
        if (move[0] == 8 && (type == 'N' || type == 'B')) {
            score += 3; // Develop knights and bishops
        }
        
        // Discourage moving same piece multiple times early
        if (type == 'Q' && move[0] == 8) {
            score -= 2; // Don't bring queen out too early
        }
        
        return score;
    }
    
    /**
     * Look for tactical opportunities (forks, pins, discovered attacks)
     */
    private int getTacticalScore(int[] move) {
        int score = 0;
        String movingPiece = board[move[0]][move[1]];
        
        // Temporarily make the move to evaluate tactics
        String originalTarget = board[move[2]][move[3]];
        board[move[2]][move[3]] = movingPiece;
        board[move[0]][move[1]] = EMPTY;
        
        // Check for forks (attacking multiple pieces)
        List<String> attackedPieces = getAttackedPieces(move[2], move[3]);
        if (attackedPieces.size() >= 2) {
            score += attackedPieces.size() * 3; // Fork bonus
        }
        
        // Check for checks (attacking enemy king)
        if (attackedPieces.contains("WK")) {
            score += 10; // Check bonus
        }
        
        // Restore board
        board[move[0]][move[1]] = movingPiece;
        board[move[2]][move[3]] = originalTarget;
        
        return score;
    }
    
    /**
     * Get list of enemy pieces attacked from a position
     */
    private List<String> getAttackedPieces(int row, int col) {
        List<String> attacked = new ArrayList<>();
        String piece = board[row][col];
        
        if (piece.equals(EMPTY)) return attacked;
        
        // Check all squares this piece can attack
        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                int[] testMove = {row, col, r, c};
                if (isLegalMoveTest(testMove)) {
                    String target = board[r][c];
                    if (!target.equals(EMPTY) && target.startsWith(WHITE_PREFIX)) {
                        attacked.add(target);
                    }
                }
            }
        }
        
        return attacked;
    }
    
    /**
     * Evaluate king safety
     */
    private int getKingSafetyScore(int[] move) {
        int score = 0;
        
        // Find our king
        int[] kingPos = findPiece("BK");
        if (kingPos == null) return 0;
        
        // Penalty if move exposes our king to attack
        String originalPiece = board[move[2]][move[3]];
        board[move[2]][move[3]] = board[move[0]][move[1]];
        board[move[0]][move[1]] = EMPTY;
        
        if (isSquareUnderAttack(kingPos[0], kingPos[1], WHITE_PREFIX)) {
            score -= 15; // Big penalty for exposing king
        }
        
        // Restore board
        board[move[0]][move[1]] = board[move[2]][move[3]];
        board[move[2]][move[3]] = originalPiece;
        
        return score;
    }
    
    /**
     * Find a specific piece on the board
     */
    private int[] findPiece(String pieceType) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (board[i][j].equals(pieceType)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
    
    /**
     * Check if a square is under attack by enemy pieces
     */
    private boolean isSquareUnderAttack(int row, int col, String enemyColor) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                String piece = board[i][j];
                if (!piece.equals(EMPTY) && piece.startsWith(enemyColor)) {
                    int[] attackMove = {i, j, row, col};
                    if (isLegalMoveTest(attackMove)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Generate human-readable AI reasoning
     */
    private String getAIReasoning(int[] move, int score) {
        String targetPiece = board[move[2]][move[3]];
        List<String> reasons = new ArrayList<>();
        
        // Check for captures
        if (!targetPiece.equals(EMPTY)) {
            reasons.add("Capturing " + getPieceUnicode(targetPiece) + " (+" + getPieceValue(targetPiece) + " points)");
        }
        
        // Check for central control
        if ((move[2] == 4 || move[2] == 5) && (move[3] == 4 || move[3] == 5)) {
            reasons.add("Controlling center");
        }
        
        // Check for checks
        String movingPiece = board[move[0]][move[1]];
        board[move[2]][move[3]] = movingPiece;
        board[move[0]][move[1]] = EMPTY;
        
        List<String> attacked = getAttackedPieces(move[2], move[3]);
        if (attacked.contains("WK")) {
            reasons.add("Check!");
        } else if (attacked.size() >= 2) {
            reasons.add("Fork attack on " + attacked.size() + " pieces");
        }
        
        // Restore board
        board[move[0]][move[1]] = movingPiece;
        board[move[2]][move[3]] = targetPiece;
        
        // Development
        if (move[0] == 8 && (movingPiece.endsWith("N") || movingPiece.endsWith("B"))) {
            reasons.add("Developing piece");
        }
        
        if (reasons.isEmpty()) {
            reasons.add("Best positional move");
        }
        
        return String.join(", ", reasons) + " (Score: " + score + ")";
    }
    
    /**
     * Check if game has ended
     */
    private boolean checkGameEnd() {
        if (!pieceExists("WK")) {
            gameOver = true;
            winner = "Black (White King captured)";
            return true;
        }
        
        if (!pieceExists("BK")) {
            gameOver = true;
            winner = "White (Black King captured)";
            return true;
        }
        
        return false;
    }
    
    /**
     * Check if a specific piece exists on board
     */
    private boolean pieceExists(String piece) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (board[i][j].equals(piece)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Display game end message
     */
    private void displayGameEnd() {
        System.out.println("\n========================================");
        System.out.println("GAME OVER!");
        System.out.println("Winner: " + winner);
        System.out.println("Total moves: " + moveHistory.size());
        System.out.println("========================================");
    }
    
    /**
     * Test if move is legal without executing it
     */
    private boolean isLegalMoveTest(int[] move) {
        // Save original state
        String originalPiece = board[move[2]][move[3]];
        
        // Test the move
        boolean result = isLegalMove(move);
        
        // Restore original state if move was executed
        if (result) {
            board[move[0]][move[1]] = board[move[2]][move[3]];
            board[move[2]][move[3]] = originalPiece;
        }
        
        return result;
    }
    
    /**
     * Main move validation and execution logic
     */
    private boolean isLegalMove(int[] move) {
        String piece = board[move[0]][move[1]];
        if (piece.equals(EMPTY)) return false;
        
        char pieceType = piece.charAt(1);
        
        switch (pieceType) {
            case 'P': return movePawn(move, piece.charAt(0));
            case 'R': return moveRook(move);
            case 'N': return moveKnight(move);
            case 'B': return moveBishop(move);
            case 'Q': return moveQueen(move);
            case 'K': return moveKing(move);
            default: return false;
        }
    }
    
    /**
     * Execute a move if legal
     */
    private boolean executeMove(int[] move) {
        String targetPiece = board[move[2]][move[3]];
        
        // Check if target square has friendly piece
        if (!targetPiece.equals(EMPTY)) {
            String movingPiece = board[move[0]][move[1]];
            if (targetPiece.charAt(0) == movingPiece.charAt(0)) {
                return false; // Can't capture own piece
            }
        }
        
        board[move[2]][move[3]] = board[move[0]][move[1]];
        board[move[0]][move[1]] = EMPTY;
        return true;
    }
    
    // Piece movement methods (simplified versions of your original logic)
    
    private boolean movePawn(int[] move, char color) {
        int direction = (color == 'W') ? 1 : -1;
        int startRow = (color == 'W') ? 2 : 7;
        
        int rowDiff = move[2] - move[0];
        int colDiff = move[3] - move[1];
        
        // Forward move
        if (colDiff == 0) {
            if (rowDiff == direction && board[move[2]][move[3]].equals(EMPTY)) {
                return executeMove(move);
            }
            // First move can be 2 squares
            if (move[0] == startRow && rowDiff == 2 * direction && 
                board[move[2]][move[3]].equals(EMPTY)) {
                return executeMove(move);
            }
        }
        // Diagonal capture
        else if (Math.abs(colDiff) == 1 && rowDiff == direction) {
            if (!board[move[2]][move[3]].equals(EMPTY)) {
                return executeMove(move);
            }
        }
        
        return false;
    }
    
    private boolean moveRook(int[] move) {
        if (move[0] != move[2] && move[1] != move[3]) return false; // Must move in line
        
        if (isPathClear(move)) {
            return executeMove(move);
        }
        return false;
    }
    
    private boolean moveKnight(int[] move) {
        int rowDiff = Math.abs(move[2] - move[0]);
        int colDiff = Math.abs(move[3] - move[1]);
        
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            return executeMove(move);
        }
        return false;
    }
    
    private boolean moveBishop(int[] move) {
        int rowDiff = Math.abs(move[2] - move[0]);
        int colDiff = Math.abs(move[3] - move[1]);
        
        if (rowDiff == colDiff && isPathClear(move)) {
            return executeMove(move);
        }
        return false;
    }
    
    private boolean moveQueen(int[] move) {
        return moveRook(move) || moveBishop(move);
    }
    
    private boolean moveKing(int[] move) {
        int rowDiff = Math.abs(move[2] - move[0]);
        int colDiff = Math.abs(move[3] - move[1]);
        
        if (rowDiff <= 1 && colDiff <= 1) {
            return executeMove(move);
        }
        return false;
    }
    
    /**
     * Check if path between two squares is clear
     */
    private boolean isPathClear(int[] move) {
        int rowStep = Integer.compare(move[2], move[0]);
        int colStep = Integer.compare(move[3], move[1]);
        
        int currentRow = move[0] + rowStep;
        int currentCol = move[1] + colStep;
        
        while (currentRow != move[2] || currentCol != move[3]) {
            if (!board[currentRow][currentCol].equals(EMPTY)) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        game.playGame();
    }
}