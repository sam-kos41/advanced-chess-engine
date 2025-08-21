/**
 * Chess Game Analysis System
 * Advanced analytics and game review capabilities
 * Demonstrates data analysis and algorithmic thinking
 */

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameAnalyzer {
    private List<String> moveHistory;
    private Map<String, Integer> pieceMoveCounts;
    private Map<String, Integer> captureStats;
    private int totalMoves;
    private int checksGiven;
    private int checksReceived;
    
    public GameAnalyzer(List<String> moveHistory) {
        this.moveHistory = new ArrayList<>(moveHistory);
        this.pieceMoveCounts = new HashMap<>();
        this.captureStats = new HashMap<>();
        this.totalMoves = moveHistory.size();
        this.checksGiven = 0;
        this.checksReceived = 0;
        analyzeGame();
    }
    
    /**
     * Comprehensive game analysis
     */
    private void analyzeGame() {
        for (String move : moveHistory) {
            analyzeSingleMove(move);
        }
    }
    
    /**
     * Analyze individual move for patterns
     */
    private void analyzeSingleMove(String move) {
        // Count piece movements
        if (move.contains("Pawn")) {
            pieceMoveCounts.put("Pawn", pieceMoveCounts.getOrDefault("Pawn", 0) + 1);
        } else if (move.contains("Knight")) {
            pieceMoveCounts.put("Knight", pieceMoveCounts.getOrDefault("Knight", 0) + 1);
        } else if (move.contains("Bishop")) {
            pieceMoveCounts.put("Bishop", pieceMoveCounts.getOrDefault("Bishop", 0) + 1);
        } else if (move.contains("Rook")) {
            pieceMoveCounts.put("Rook", pieceMoveCounts.getOrDefault("Rook", 0) + 1);
        } else if (move.contains("Queen")) {
            pieceMoveCounts.put("Queen", pieceMoveCounts.getOrDefault("Queen", 0) + 1);
        } else if (move.contains("King")) {
            pieceMoveCounts.put("King", pieceMoveCounts.getOrDefault("King", 0) + 1);
        }
        
        // Count captures and checks
        if (move.contains("captures")) {
            String[] parts = move.split(" ");
            for (String part : parts) {
                if (part.contains("♕") || part.contains("♛")) {
                    captureStats.put("Queen", captureStats.getOrDefault("Queen", 0) + 1);
                } else if (part.contains("♖") || part.contains("♜")) {
                    captureStats.put("Rook", captureStats.getOrDefault("Rook", 0) + 1);
                }
                // Add other pieces as needed
            }
        }
        
        if (move.contains("Check")) {
            if (move.startsWith("White")) {
                checksGiven++;
            } else {
                checksReceived++;
            }
        }
    }
    
    /**
     * Generate comprehensive game report
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        
        report.append("╔═══════════════════════════════════════════════════════════════════╗\n");
        report.append("║                          GAME ANALYSIS REPORT                    ║\n");
        report.append("╚═══════════════════════════════════════════════════════════════════╝\n\n");
        
        // Basic stats
        report.append("📊 GAME STATISTICS:\n");
        report.append("   Total Moves: ").append(totalMoves).append("\n");
        report.append("   Checks Given: ").append(checksGiven).append("\n");
        report.append("   Checks Received: ").append(checksReceived).append("\n");
        report.append("   Game Length: ").append(getGameLength()).append("\n\n");
        
        // Piece usage analysis
        report.append("♟️ PIECE MOVEMENT ANALYSIS:\n");
        for (Map.Entry<String, Integer> entry : pieceMoveCounts.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalMoves;
            report.append("   ").append(entry.getKey()).append(": ")
                  .append(entry.getValue()).append(" moves (")
                  .append(String.format("%.1f", percentage)).append("%)\n");
        }
        report.append("\n");
        
        // Performance rating
        report.append("🏆 PERFORMANCE EVALUATION:\n");
        report.append("   Playing Style: ").append(analyzePlayingStyle()).append("\n");
        report.append("   Tactical Awareness: ").append(analyzeTacticalAwareness()).append("\n");
        report.append("   Overall Rating: ").append(calculateOverallRating()).append("/10\n\n");
        
        // Recommendations
        report.append("💡 IMPROVEMENT SUGGESTIONS:\n");
        for (String suggestion : generateSuggestions()) {
            report.append("   • ").append(suggestion).append("\n");
        }
        
        return report.toString();
    }
    
    private String getGameLength() {
        if (totalMoves < 10) return "Quick game";
        else if (totalMoves < 30) return "Medium game";
        else return "Long game";
    }
    
    private String analyzePlayingStyle() {
        int pawnMoves = pieceMoveCounts.getOrDefault("Pawn", 0);
        int pieceMoves = totalMoves - pawnMoves;
        
        if (pawnMoves > pieceMoves) return "Positional Player";
        else return "Tactical Player";
    }
    
    private String analyzeTacticalAwareness() {
        if (checksGiven > 2) return "High";
        else if (checksGiven > 0) return "Medium";
        else return "Developing";
    }
    
    private double calculateOverallRating() {
        double rating = 5.0; // Base rating
        
        // Bonus for tactical play
        rating += checksGiven * 0.5;
        rating += Math.min(captureStats.size() * 0.3, 1.5);
        
        // Bonus for game length (shows endurance)
        if (totalMoves > 20) rating += 1.0;
        
        return Math.min(rating, 10.0);
    }
    
    private List<String> generateSuggestions() {
        List<String> suggestions = new ArrayList<>();
        
        if (checksGiven == 0) {
            suggestions.add("Practice tactical combinations to create more threatening positions");
        }
        
        if (pieceMoveCounts.getOrDefault("Knight", 0) < 2) {
            suggestions.add("Develop knights early for better piece coordination");
        }
        
        if (totalMoves < 10) {
            suggestions.add("Try playing longer games to practice endgame techniques");
        }
        
        suggestions.add("Study opening principles to improve early game development");
        suggestions.add("Practice recognizing tactical patterns like forks and pins");
        
        return suggestions;
    }
}