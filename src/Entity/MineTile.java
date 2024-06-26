package Entity;

import javax.swing.JButton;

public class MineTile extends JButton {
	int r;
    int c;

    public MineTile(int r, int c) {
        this.r = r;
        this.c = c;
    }

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

    @Override
    public String toString() {
        return "MineTile [r=" + r + ", c=" + c + "]";
    }
    
	
}
