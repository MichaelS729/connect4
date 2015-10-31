import java.util.ArrayList;

public class Player {
  private final char gamePiece;

  /**
   * Creates a Player with a game piece represented by SYMBOL.
   */
  public Player(char symbol) {
    gamePiece = symbol;
  }

  /**
   * Player randomly makes a move on the Gameboard.
   */
  public void play(Gameboard gb) {
    ArrayList<Integer> playableColumns = gb.getValidColumns();
    int chosenIndex = (int)(Math.random() * playableColumns.size());
    int chosenColumn = playableColumns.get(chosenIndex);
    gb.play(gamePiece, chosenColumn);
  }
}
