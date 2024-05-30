package gamestates;
import java.util.Queue;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import javax.swing.*;

import Entity.MineTile;

public class Playing {

    int tileSize = 60;
    int numRows = 10;
    int numCols = numRows;
    int boardWidth = numCols * tileSize;
    int boardHeight = numRows * tileSize;
    
    JFrame frame = new JFrame("Minesweeper");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    private int mineCount;

	private MineTile[][] board = new MineTile[numRows][numCols];
    private ArrayList<MineTile> mineList;
    private Random random = new Random();

    int tilesClicked = 0; //goal is to click all tiles except the ones containing mines
<<<<<<< Updated upstream
    boolean gameOver = false;
=======
    
    boolean gameOver = false;
    boolean gameWining  = false ;
    

    int remaningUndo = 3;

    	public Playing(int mineCount, int numR, int numC, Game game) 
        {
    	gameWin = new GameWin(this, game);
    	gameOverOverlay = new GameOver(this);	
   
        this.numRows = numR;
        this.numCols = numC;
        boardWidth = numCols * tileSize;
        boardHeight = numRows * tileSize;
        isFirstClick = true;
        board = new MineTile[numRows][numCols];
>>>>>>> Stashed changes

    	public Playing(int mineCount) {
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
        boardPanel.addMouseListener(gameOverOverlay);
        boardPanel.addMouseListener( gameWin);
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
<<<<<<< Updated upstream
=======
                            //check if it the first click
                            if (isFirstClick) {     // if it is, set create mines
                                tile.setText("");
                                setMines(tile.getR(), tile.getC());
                            }

>>>>>>> Stashed changes
                            if (tile.getText() == "") {
                                if (mineList.contains(tile)) {
                                    revealMines();
                                }
                                else {
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
                    } 
                });

                boardPanel.add(tile);
                
            }
        }

        frame.setVisible(true);

        setMines();
    }
<<<<<<< Updated upstream

    void setMines() {
=======
  
    	
    	
    	public GameOver getGameOverOverlay() {
			return gameOverOverlay;
		}



		


		public GameWin getGameWin() {
			return gameWin;
		}





		public boolean isGameWining() {
			return gameWining;
		}


		public void setGameWining(boolean gameWining) {
			this.gameWining = gameWining;
		}


		public boolean isGameOver() {
			return gameOver;
		}


	public void setGameOver(boolean gameOver) {
    	this.gameOver = gameOver ;
    }
    	
    void setMines(int r, int c) {
>>>>>>> Stashed changes
        mineList = new ArrayList<MineTile>();
        int mineLeft = getMineCount();
        while (mineLeft > 0) {
            int r = random.nextInt(numRows); //0-7
            int c = random.nextInt(numCols);

            MineTile tile = board[r][c]; 
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                mineLeft -= 1;
            }
        }
    }

    void revealMines() {
        for (int i = 0; i < mineList.size(); i++) {
            MineTile tile = mineList.get(i);
            tile.setText("💣");
        }

        gameOver = true;
        textLabel.setText("Game Over!");
    }

<<<<<<< Updated upstream
    void checkMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
            return;
        }

        MineTile tile = board[r][c];
        if (!tile.isEnabled()) {
            return;
        }
        tile.setEnabled(false);
        // + the number of empty tile
        tilesClicked += 1;
=======
    
    // Using Stack => O(N)
//    private void checkMine(int r, int c) {
//        if (isOutOfBounds(r, c)) return;
//
//        Stack<int[]> stack = new Stack<>();
//        stack.push(new int[]{r, c});
//
//        boolean[][] visited = new boolean[numRows][numCols];
//
//        while (!stack.isEmpty()) {
//            int[] pos = stack.pop();
//            int currentR = pos[0];
//            int currentC = pos[1];
//
//            if (isOutOfBounds(currentR, currentC) || visited[currentR][currentC]) continue;
//
//            MineTile tile = board[currentR][currentC];
//            if (!tile.isEnabled()) continue;
//
//            tile.setEnabled(false);
//            UndoableTiles.add(tile);
//            tilesClicked++;
//            visited[currentR][currentC] = true;
//
//            int minesFound = countAdjacentMines(currentR, currentC);
//            if (minesFound > 0) {
//                tile.setText(Integer.toString(minesFound));
//            } else {
//                tile.setText("");
//                for (int[] dir : directions) {
//                    stack.push(new int[]{currentR + dir[0], currentC + dir[1]});
//                }
//            }
//
//            if (tilesClicked == numRows * numCols - mineList.size()) {
//                textLabel.setText("Mines Cleared!");
//                gameWining = true;
//                Graphics g = boardPanel.getGraphics();
//                gameWin.draw(g);
//            }
//        }
//    }
    
    
    // Using queue like Breadth-first search => O(N)
    //BFS approach is generally preferable for large-scale applications due to its robustness against stack overflow, 
    //while the recursive approach might be suitable for simpler, smaller-scale problems.
    public void checkMine(int r, int c) {
        if (isOutOfBounds(r, c)) return;

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{r, c});

        while (!queue.isEmpty()) {
            int[] position = queue.poll();
            int row = position[0];
            int col = position[1];

            if (isOutOfBounds(row, col)) continue;
>>>>>>> Stashed changes

            MineTile tile = board[row][col];
            if (!tile.isEnabled()) continue;

            tile.setEnabled(false);
            UndoableTiles.add(tile);
            tilesClicked++;

            int minesFound = countAdjacentMines(row, col);
            if (minesFound > 0) {
                tile.setText(Integer.toString(minesFound));
            } else {
                tile.setText("");

                // Add adjacent tiles to the queue for further processing
                int[][] directions = {
                    {-1, -1}, {-1, 0}, {-1, 1},
                    {0, -1}, {0, 1},
                    {1, -1}, {1, 0}, {1, 1}
                };

                for (int[] dir : directions) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];
                    if (!isOutOfBounds(newRow, newCol) && board[newRow][newCol].isEnabled()) {
                        queue.add(new int[]{newRow, newCol});
                    }
                }
            }

<<<<<<< Updated upstream
            //left and right
            checkMine(r, c-1);      //left
            checkMine(r, c+1);      //right

            //bottom 3
            checkMine(r+1, c-1);    //bottom left
            checkMine(r+1, c);      //bottom
            checkMine(r+1, c+1);    //bottom right
        }

        if (tilesClicked == numRows * numCols - mineList.size()) {
            gameOver = true;
            textLabel.setText("Mines Cleared!");
=======
            if (tilesClicked == numRows * numCols - mineList.size()) {
                textLabel.setText("Mines Cleared!");
                gameWining = true;
                Graphics g = boardPanel.getGraphics();
                gameWin.draw(g);
            }
>>>>>>> Stashed changes
        }
    }

    private boolean isOutOfBounds(int r, int c) {
        return r < 0 || r >= numRows || c < 0 || c >= numCols;
    }

    private int countAdjacentMines(int r, int c) {
        int minesFound = 0;
        for (int[] dir : directions) {
            minesFound += countMine(r + dir[0], c + dir[1]);
        }
        return minesFound;
    }

    private int countMine(int r, int c) {
        if (isOutOfBounds(r, c)) return 0;
        if (mineList.contains(board[r][c])) return 1;
        return 0;
    }

    private static final int[][] directions = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1}, {0, 1},
        {1, -1}, {1, 0}, {1, 1}
    };
    
    
    
	// Time Complexity: worst case: O(n) with n = total number of titles (column * row)
    // Space Complexity:worst case: O(n) with n = total number of titles (column * row) => the recursion depth proportional to n
//	void checkMine(int r, int c) {
//        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
//            return;
//        }
//
//        MineTile tile = board[r][c];
//        if (!tile.isEnabled()) {
//            return;
//        }
//        //Disable mine and add mine to list for undo operation
//        tile.setEnabled(false);
//        UndoableTiles.add(tile);
//
//        // + the number of empty tile
//        tilesClicked += 1;
//
//        int minesFound = 0;
//
//        //top 3
//        minesFound += countMine(r-1, c-1);  //top left
//        minesFound += countMine(r-1, c);    //top
//        minesFound += countMine(r-1, c+1);  //top right
//
//        //left and right
//        minesFound += countMine(r, c-1);    //left
//        minesFound += countMine(r, c+1);    //right
//
//        //bottom 3
//        minesFound += countMine(r+1, c-1);  //bottom left
//        minesFound += countMine(r+1, c);    //bottom
//        minesFound += countMine(r+1, c+1);  //bottom right
//
//        if (minesFound > 0) {
//            tile.setText(Integer.toString(minesFound));
//        }
//        else {
//            tile.setText("");
//            
//            //top 3
//            checkMine(r-1, c-1);    //top left
//            checkMine(r-1, c);      //top
//            checkMine(r-1, c+1);    //top right
//
//            //left and right
//            checkMine(r, c-1);      //left
//            checkMine(r, c+1);      //right
//
//            //bottom 3
//            checkMine(r+1, c-1);    //bottom left
//            checkMine(r+1, c);      //bottom
//            checkMine(r+1, c+1);    //bottom right
//        }
//
//        if (tilesClicked == numRows * numCols - mineList.size()) {
//           
//            textLabel.setText("Mines Cleared!");
//            Graphics g = boardPanel.getGraphics();
//            gameWin.draw(g);
//            gameWining = true ;
//        }
//    }



    
	
	
    
    public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}

	public int getMineCount() {
		return mineCount;
	}
    
<<<<<<< Updated upstream
=======
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
	public void setVisible(boolean b) {
        frame.setVisible(b);
    }
>>>>>>> Stashed changes
}