#!/usr/bin/env python3
"""
Generate a high-quality PDF image of a chess board with Unicode pieces.
"""

from reportlab.lib.pagesizes import letter, A4
from reportlab.pdfgen import canvas
from reportlab.lib.colors import HexColor, black, white
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
import os

def create_chessboard_pdf(filename="chessboard_portfolio.pdf"):
    """Create a professional chess board PDF for portfolio use."""
    
    # Create canvas with custom size for square board
    board_size = 600  # Size of the board in points
    margin = 50
    page_width = board_size + 2 * margin
    page_height = board_size + 2 * margin + 100  # Extra space for title
    
    c = canvas.Canvas(filename, pagesize=(page_width, page_height))
    
    # Colors
    dark_square = HexColor('#8B7355')  # Darker brown
    light_square = HexColor('#F0D9B5')  # Light tan
    border_color = HexColor('#2C1810')  # Dark brown border
    
    # Board parameters
    square_size = board_size / 8
    start_x = margin
    start_y = margin + 50  # Extra space at bottom for labels
    
    # Draw title
    c.setFont("Helvetica-Bold", 24)
    c.setFillColor(black)
    title = "Advanced Chess Engine & AI System"
    c.drawCentredString(page_width/2, page_height - 40, title)
    
    # Draw border around board
    c.setStrokeColor(border_color)
    c.setLineWidth(3)
    c.rect(start_x - 5, start_y - 5, board_size + 10, board_size + 10)
    
    # Draw the chess board squares
    for row in range(8):
        for col in range(8):
            x = start_x + col * square_size
            y = start_y + row * square_size
            
            # Alternate colors
            if (row + col) % 2 == 0:
                c.setFillColor(light_square)
            else:
                c.setFillColor(dark_square)
            
            c.rect(x, y, square_size, square_size, fill=1, stroke=0)
    
    # Chess pieces in Unicode
    pieces = {
        'K': '♔', 'Q': '♕', 'R': '♖', 'B': '♗', 'N': '♘', 'P': '♙',  # White
        'k': '♚', 'q': '♛', 'r': '♜', 'b': '♝', 'n': '♞', 'p': '♟'   # Black
    }
    
    # Initial board setup (from bottom to top, left to right)
    board = [
        ['R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'],  # Row 1 (White)
        ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'],  # Row 2 (White)
        [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '],  # Row 3
        [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '],  # Row 4
        [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '],  # Row 5
        [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '],  # Row 6
        ['p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'],  # Row 7 (Black)
        ['r', 'n', 'b', 'q', 'k', 'b', 'n', 'r']   # Row 8 (Black)
    ]
    
    # Draw pieces
    c.setFont("Helvetica", 48)
    for row in range(8):
        for col in range(8):
            piece = board[row][col]
            if piece != ' ':
                x = start_x + col * square_size + square_size/2
                y = start_y + row * square_size + square_size/2 - 15
                
                # Set color based on piece case
                if piece.isupper():
                    c.setFillColor(white)
                    c.setStrokeColor(black)
                    c.setLineWidth(1)
                else:
                    c.setFillColor(black)
                
                # Draw the piece
                piece_char = pieces.get(piece, piece)
                c.drawCentredString(x, y, piece_char)
    
    # Draw file labels (a-h)
    c.setFont("Helvetica-Bold", 14)
    c.setFillColor(black)
    files = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h']
    for i, file_label in enumerate(files):
        x = start_x + i * square_size + square_size/2
        c.drawCentredString(x, start_y - 20, file_label)
        c.drawCentredString(x, start_y + board_size + 10, file_label)
    
    # Draw rank labels (1-8)
    ranks = ['1', '2', '3', '4', '5', '6', '7', '8']
    for i, rank_label in enumerate(ranks):
        y = start_y + i * square_size + square_size/2
        c.drawCentredString(start_x - 20, y, rank_label)
        c.drawCentredString(start_x + board_size + 20, y, rank_label)
    
    # Add footer with project info
    c.setFont("Helvetica", 10)
    c.setFillColor(HexColor('#666666'))
    footer_text = "Java Chess Engine with AI | Multi-factor Evaluation | Tactical Pattern Recognition"
    c.drawCentredString(page_width/2, 25, footer_text)
    
    # Save the PDF
    c.save()
    print(f"✅ Chess board PDF created: {filename}")
    return filename

def create_simplified_board(filename="chessboard_simple.pdf"):
    """Create a simplified, clean chess board design."""
    
    # Smaller, more web-friendly size
    board_size = 400
    margin = 30
    page_width = board_size + 2 * margin
    page_height = board_size + 2 * margin
    
    c = canvas.Canvas(filename, pagesize=(page_width, page_height))
    
    # Modern colors
    dark_square = HexColor('#769656')  # Green
    light_square = HexColor('#EEEED2')  # Light cream
    
    square_size = board_size / 8
    start_x = margin
    start_y = margin
    
    # Draw board
    for row in range(8):
        for col in range(8):
            x = start_x + col * square_size
            y = start_y + row * square_size
            
            if (row + col) % 2 == 0:
                c.setFillColor(light_square)
            else:
                c.setFillColor(dark_square)
            
            c.rect(x, y, square_size, square_size, fill=1, stroke=0)
    
    # Initial position pieces (simplified)
    board = [
        ['♖', '♘', '♗', '♕', '♔', '♗', '♘', '♖'],
        ['♙', '♙', '♙', '♙', '♙', '♙', '♙', '♙'],
        [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '],
        [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '],
        [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '],
        [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '],
        ['♟', '♟', '♟', '♟', '♟', '♟', '♟', '♟'],
        ['♜', '♞', '♝', '♛', '♚', '♝', '♞', '♜']
    ]
    
    # Draw pieces
    c.setFont("Helvetica", 32)
    for row in range(8):
        for col in range(8):
            piece = board[row][col]
            if piece != ' ':
                x = start_x + col * square_size + square_size/2
                y = start_y + row * square_size + square_size/2 - 10
                
                # All pieces in black for simplicity
                c.setFillColor(black)
                c.drawCentredString(x, y, piece)
    
    c.save()
    print(f"✅ Simplified chess board PDF created: {filename}")
    return filename

if __name__ == "__main__":
    # Create both versions
    create_chessboard_pdf()
    create_simplified_board()
    
    print("\n📄 Generated PDFs:")
    print("  1. chessboard_portfolio.pdf - Full featured board with title")
    print("  2. chessboard_simple.pdf - Clean, minimal design")
    print("\nUse these for your portfolio website!")