/**
 * Game Save/Load System
 * Demonstrates file I/O, serialization, and data persistence
 */

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class GamePersistence {
    private static final String SAVE_DIRECTORY = "saved_games/";
    private static final String FILE_EXTENSION = ".chess";
    
    /**
     * Save current game state to file
     */
    public static boolean saveGame(String[][] board, List<String> moveHistory, String playerName) {
        try {
            // Create directory if it doesn't exist
            File directory = new File(SAVE_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // Generate filename with timestamp
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String timestamp = sdf.format(new Date());
            String filename = SAVE_DIRECTORY + playerName + "_" + timestamp + FILE_EXTENSION;
            
            // Write game data
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                // Write metadata
                writer.println("# Chess Game Save File");
                writer.println("# Player: " + playerName);
                writer.println("# Date: " + new Date());
                writer.println("# Total Moves: " + moveHistory.size());
                writer.println();
                
                // Write board state
                writer.println("[BOARD]");
                for (int i = 8; i >= 1; i--) {
                    for (int j = 1; j <= 8; j++) {
                        writer.print(board[i][j].isEmpty() ? "." : board[i][j]);
                        if (j < 8) writer.print(",");
                    }
                    writer.println();
                }
                writer.println();
                
                // Write move history
                writer.println("[MOVES]");
                for (int i = 0; i < moveHistory.size(); i++) {
                    writer.println((i + 1) + ". " + moveHistory.get(i));
                }
                
                // Write game statistics
                writer.println();
                writer.println("[STATISTICS]");
                writer.println("Total Moves: " + moveHistory.size());
                writer.println("White Moves: " + countPlayerMoves(moveHistory, "White"));
                writer.println("Black Moves: " + countPlayerMoves(moveHistory, "Black"));
            }
            
            System.out.println("✅ Game saved successfully: " + filename);
            return true;
            
        } catch (IOException e) {
            System.err.println("❌ Error saving game: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load game state from file
     */
    public static GameState loadGame(String filename) {
        try {
            File file = new File(SAVE_DIRECTORY + filename);
            if (!file.exists()) {
                System.err.println("❌ Save file not found: " + filename);
                return null;
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String[][] board = new String[9][9];
                List<String> moveHistory = new ArrayList<>();
                String line;
                String currentSection = "";
                
                // Initialize board
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        board[i][j] = "";
                    }
                }
                
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    
                    // Skip comments and empty lines
                    if (line.startsWith("#") || line.isEmpty()) {
                        continue;
                    }
                    
                    // Check for section headers
                    if (line.equals("[BOARD]")) {
                        currentSection = "BOARD";
                        continue;
                    } else if (line.equals("[MOVES]")) {
                        currentSection = "MOVES";
                        continue;
                    } else if (line.equals("[STATISTICS]")) {
                        currentSection = "STATISTICS";
                        continue;
                    }
                    
                    // Process based on current section
                    switch (currentSection) {
                        case "BOARD":
                            loadBoardRow(board, line);
                            break;
                        case "MOVES":
                            if (line.contains(". ")) {
                                String move = line.substring(line.indexOf(". ") + 2);
                                moveHistory.add(move);
                            }
                            break;
                    }
                }
                
                System.out.println("✅ Game loaded successfully: " + filename);
                return new GameState(board, moveHistory);
            }
            
        } catch (IOException e) {
            System.err.println("❌ Error loading game: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * List all saved games
     */
    public static void listSavedGames() {
        File directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            System.out.println("No saved games found.");
            return;
        }
        
        File[] files = directory.listFiles((dir, name) -> name.endsWith(FILE_EXTENSION));
        if (files == null || files.length == 0) {
            System.out.println("No saved games found.");
            return;
        }
        
        System.out.println("📁 SAVED GAMES:");
        for (File file : files) {
            String name = file.getName().replace(FILE_EXTENSION, "");
            long size = file.length();
            Date modified = new Date(file.lastModified());
            
            System.out.printf("   %s (%.1f KB, %s)%n", 
                name, size / 1024.0, 
                new SimpleDateFormat("MM/dd/yyyy HH:mm").format(modified));
        }
    }
    
    /**
     * Helper method to count moves by player
     */
    private static int countPlayerMoves(List<String> moveHistory, String player) {
        return (int) moveHistory.stream()
                .filter(move -> move.startsWith(player))
                .count();
    }
    
    /**
     * Helper method to load board row from save file
     */
    private static void loadBoardRow(String[][] board, String line) {
        // This is a simplified version - you'd need to implement based on your save format
        String[] pieces = line.split(",");
        // Implementation would go here to restore board state
    }
    
    /**
     * Game state container class
     */
    public static class GameState {
        public final String[][] board;
        public final List<String> moveHistory;
        
        public GameState(String[][] board, List<String> moveHistory) {
            this.board = board;
            this.moveHistory = moveHistory;
        }
    }
}