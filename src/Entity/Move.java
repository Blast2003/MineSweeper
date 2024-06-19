package Entity;

public class Move {
	public MineTile tile;
    public String previousText;
    public boolean wasEnabled;
    public int previousTilesClicked;

    public Move(MineTile tile, String previousText, boolean wasEnabled, int previousTilesClicked) {
        this.tile = tile;
        this.previousText = previousText;
        this.wasEnabled = wasEnabled;
        this.previousTilesClicked = previousTilesClicked;
    }
}
