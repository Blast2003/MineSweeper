package gamestates;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.Graphics;

import Entity.MineTile;
import main.Game;
import ui.GameOver;
import ui.GameWin;

public class Playing implements Statemethods{

    int tileSize = 60;
    int numRows, numCols, boardWidth, boardHeight;
    
    JFrame frame = new JFrame("Minesweeper");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    private int mineCount;

    private boolean isFirstClick;
    
    private int bombFound = 0;
	private MineTile[][] board;
    private ArrayList<MineTile> mineList;
    private Random random = new Random();
    private ArrayList<MineTile> UndoableTiles = new ArrayList<MineTile>();
    private GameOver gameOverOverlay;
    private GameWin gameWin;
    int tilesClicked = 0; //goal is to click all tiles except the ones containing mines

    boolean gameOver = false;
    boolean gameWining  = false ;
    

    int remaningUndo = 3;

    	public Playing(int mineCount, int numR, int numC) 
        {
    	gameWin = new GameWin(this);
    	gameOverOverlay = new GameOver(this);	
    	//boardPanel.addMouseListener( gameOverOverlay);
        boardPanel.addMouseListener( gameWin);
        this.numRows = numR;
        this.numCols = numC;
        boardWidth = numCols * tileSize;
        boardHeight = numRows * tileSize;
        isFirstClick = true;
        board = new MineTile[numRows][numCols];

    	setMineCount(mineCount);
        // frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Minesweeper: " + Integer.toString(getMineCount()));
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(numRows, numCols)); //8x8
        // boardPanel.setBackground(Color.green);
        frame.add(boardPanel);
       

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                MineTile tile = new MineTile(r, c);
                board[r][c] = tile;

                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
                // tile.setText("💣");
                tile.addMouseListener(new MouseAdapter() {
                    
                    @Override
                    public void mousePressed(MouseEvent e) {
                        
                        if (gameOver) {
                            return;
                        }

                        MineTile tile = (MineTile) e.getSource();
                        //left click
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            //check if it the first click
                            if (isFirstClick) {     // if it is, set creat mines
                                tile.setText("");
                                setMines(tile.getR(), tile.getC());
                            }

                            if (tile.getText() == "") {
                                if (mineList.contains(tile)) {
                                    if (bombFound < remaningUndo) {
                                    	bombFound++;
                                        tile.setText("💣");
                                        tile.setEnabled(false);
                                        UndoableTiles.clear(); // clear old list
                                        UndoableTiles.add(tile); // add tile to list to undo
                                    }
                                    else revealMines();
                                }
                                else {
                                    UndoableTiles.clear(); // clear old list
                                    checkMine(tile.getR(), tile.getC());
                                }
                            }
                        }
                        //right click
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (tile.getText() == "" && tile.isEnabled()) {
                                tile.setText("🚩");
                            }
                            else if (tile.getText() == "🚩") {
                                tile.setText("");
                            }
                        }

                        else if (e.getButton() == MouseEvent.BUTTON2) { // Dùng tạm Button 2 (Nút giữa) cho redo
                            if (remaningUndo > 0) {
                                undoLastClick();
                            }
                        }
                    } 
                });

                boardPanel.add(tile);
                
            }
        }

        frame.setVisible(true);

        // setMines();
    }
  
    	
    public void setGameOver(boolean gameOver) {
    	this.gameOver = gameOver ;
    }
    	
    void setMines(int r, int c) {
        mineList = new ArrayList<MineTile>();
        int mineLeft = getMineCount();
        while (mineLeft > 0) {
            int randR = random.nextInt(numRows); //0-7
            int randC = random.nextInt(numCols);

            while (!(randC < c-1 || randC > c+1) && !(randR < r-1 || randR > r +1)) {  
                //Guarantee first tile and all tiles near it will be empty
                randR = random.nextInt(numRows); 
                randC = random.nextInt(numCols);
            }

            MineTile tile = board[randR][randC]; 
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                mineLeft -= 1;
            }
        }
        isFirstClick = false; // set first click to false to start the game
    }

    void revealMines() {
        for (int i = 0; i < mineList.size(); i++) {
            MineTile tile = mineList.get(i);
            tile.setText("💣");
        }

        gameOver = true;
        textLabel.setText("Game Over!");
        Graphics g = boardPanel.getGraphics();
        gameOverOverlay.draw(g);
    }


	void checkMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
            return;
        }

        MineTile tile = board[r][c];
        if (!tile.isEnabled()) {
            return;
        }
        //Disable mine and add mine to list for undo operation
        tile.setEnabled(false);
        UndoableTiles.add(tile);

        // + the number of empty tile
        tilesClicked += 1;

        int minesFound = 0;

        //top 3
        minesFound += countMine(r-1, c-1);  //top left
        minesFound += countMine(r-1, c);    //top
        minesFound += countMine(r-1, c+1);  //top right

        //left and right
        minesFound += countMine(r, c-1);    //left
        minesFound += countMine(r, c+1);    //right

        //bottom 3
        minesFound += countMine(r+1, c-1);  //bottom left
        minesFound += countMine(r+1, c);    //bottom
        minesFound += countMine(r+1, c+1);  //bottom right

        if (minesFound > 0) {
            tile.setText(Integer.toString(minesFound));
        }
        else {
            tile.setText("");
            
            //top 3
            checkMine(r-1, c-1);    //top left
            checkMine(r-1, c);      //top
            checkMine(r-1, c+1);    //top right

            //left and right
            checkMine(r, c-1);      //left
            checkMine(r, c+1);      //right

            //bottom 3
            checkMine(r+1, c-1);    //bottom left
            checkMine(r+1, c);      //bottom
            checkMine(r+1, c+1);    //bottom right
        }

        if (tilesClicked == numRows * numCols - mineList.size()) {
           // gameOver = true;
            textLabel.setText("Mines Cleared!");
            Graphics g = boardPanel.getGraphics();
            gameWin.draw(g);
            gameWining = true ;
        }
    }

    int countMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
            return 0;
        }
        if (mineList.contains(board[r][c])) {
            return 1;
        }
        return 0;
    }
    
    public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}

	public int getMineCount() {
		return mineCount;
	}

    public void undoLastClick() {
            remaningUndo--;
            if(bombFound > 0) bombFound--;
            for (MineTile t : UndoableTiles) {

                if (t.getText() == "💣" ) t.setText(""); // mine : hides it
                else  tilesClicked--; // not mine: reduces the tileClicked counter
                t.setEnabled(true);
            }
    
            boolean over = false;
            for (int y = 0; y < numCols; y++) {
                for (int x = 0; x < numCols; x++) {
                    if (board[x][y].getText() == "💣") {
                        over = true;
                    }
                }
            }
    
            if (over) revealMines();
    }
    
   public void resetLose() {
	   for (int r = 0; r < numRows; r++) {
	        for (int c = 0; c < numCols; c++) {
	            board[r][c].setText(""); // Clear any text on the tiles
	            board[r][c].setEnabled(true); // Re-enable all tiles
	        }
	    }
   }
    
   public void resetWin() {
	   for (int r = 0; r < numRows; r++) {
	        for (int c = 0; c < numCols; c++) {
	            board[r][c].setText(""); // Clear any text on the tiles
	            board[r][c].setEnabled(true); // Re-enable all tiles
	        }
	    }
   } 
   @Override
   public void update() {
	   if(gameOver) {
		   gameOverOverlay.update();
	   }else if (gameWining) {
		   gameWin.update();
	   }
	   
   }
@Override
public void draw(Graphics g) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent e) {
	if(gameOver) {
		gameOverOverlay.mousePressed(e);
	}else if(gameWining) {
		gameWin.mousePressed(e);
	}
	
}
@Override
public void mouseReleased(MouseEvent e) {
	if(gameOver) {
		gameOverOverlay.mouseReleased(e);
	}else if(gameWining) {
		gameWin.mouseReleased(e);
	}
	
}
@Override
public void mouseMoved(MouseEvent e) {
	if(gameOver) {
		gameOverOverlay.mouseMoved(e);
	}else if(gameWining) {
		gameWin.mouseMoved(e);
	}
	
}
@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
    
    }