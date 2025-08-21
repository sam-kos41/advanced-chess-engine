#!/bin/bash

# Chess Engine Portfolio Setup Script
# This script sets up the project for Git and creates proper structure

echo "🚀 Setting up Chess Engine Portfolio..."

# Create project structure
mkdir -p src/main/java
mkdir -p src/test/java
mkdir -p docs
mkdir -p demos
mkdir -p saved_games

# Move Java files to proper structure
mv ChessGame.java src/main/java/
mv GameAnalyzer.java src/main/java/
mv GamePersistence.java src/main/java/

# Create documentation
mv PORTFOLIO_README.md README.md
mv README.md docs/TECHNICAL_README.md
cp docs/TECHNICAL_README.md README.md

# Create .gitignore
cat > .gitignore << 'EOF'
# Compiled class files
*.class

# BlueJ files
*.ctxt

# Mobile Tools for Java (J2ME)
.mtj.tmp/

# Package Files
*.jar
*.war
*.nar
*.ear
*.zip
*.tar.gz
*.rar

# Virtual machine crash logs
hs_err_pid*

# IDE files
.idea/
*.iml
.vscode/
.eclipse/

# OS generated files
.DS_Store
.DS_Store?
._*
.Spotlight-V100
.Trashes
ehthumbs.db
Thumbs.db

# Saved games (optional - you might want to include examples)
saved_games/*.chess

# Compiled output
out/
build/
target/
EOF

# Create demo script
cat > demos/demo.sh << 'EOF'
#!/bin/bash
echo "🎮 Chess Engine Demo"
echo "Compiling chess engine..."
cd ../src/main/java
javac *.java
echo "Starting chess game..."
java ChessGame
EOF

chmod +x demos/demo.sh

# Initialize Git repository
git init
git add .
git commit -m "Initial commit: Advanced Chess Engine Portfolio Project

Features:
- Intelligent AI opponent with multi-factor evaluation
- Professional UI with Unicode chess pieces
- Game analysis and performance metrics
- Save/load functionality
- Comprehensive documentation

Technical Skills Demonstrated:
- Advanced Java programming
- AI algorithm development
- Software architecture design
- User experience optimization"

echo "✅ Portfolio setup complete!"
echo ""
echo "Next steps:"
echo "1. Create GitHub repository: 'advanced-chess-engine'"
echo "2. Add remote: git remote add origin https://github.com/yourusername/advanced-chess-engine.git"
echo "3. Push to GitHub: git push -u origin main"
echo ""
echo "📁 Project structure:"
tree -I 'node_modules|.git' || ls -la