import java.util.ArrayList;

public class Gameboard {
  private static final int DEFAULT_HEIGHT = 6;
  private static final int DEFAULT_WIDTH = 7;

  private char[][] board;
  private int height;
  private int width;

  // For each column, keep track of the height left.
  private int[] heightLeft;

  /**
   * Creates a Connect 4 game board with the default width and height.
   */
  public Gameboard() {
    this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
  }

  /**
   * Creates a Connect 4 game board with a custom width and height.
   *
   * @param height - the number of rows in the board
   * @param width - the number of columns in the board
   */
  public Gameboard(int height, int width) {
    this.height = height;
    this.width = width;
    board = new char[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = '*';
      }
    }
    heightLeft = new int[width];
    for (int j = 0; j < width; j++) {
      heightLeft[j] = height;
    }
  }

  public ArrayList<Integer> getValidColumns() {
    ArrayList<Integer> validColumns = new ArrayList<Integer>();
    for (int j = 0; j < width; j++) {
      if (heightLeft[j] > 0) {
        validColumns.add(j);
      }
    }
    return validColumns;
  }

  /**
   * Plays a game piece.
   *
   * @param piece - the game piece of a player
   * @param chosenColumn - the column in which the piece falls into
   *
   * @return true when the move was valid, false otherwise
   */
  public void play(char piece, int chosenColumn) {
    board[heightLeft[chosenColumn] - 1][chosenColumn] = piece;
    heightLeft[chosenColumn]--;
  }

  /**
   * Check if the board has a winning position for either player.
   */
  public boolean hasWin() {
    return hasHorizontal() || hasDiagonal() || hasVertical();
  }

  private boolean hasHorizontal() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width - 3; j++) {
        if (board[i][j] != '*'
            && board[i][j] == board[i][j+1]
            && board[i][j+1] == board[i][j+2]
            && board[i][j+2] == board[i][j+3]) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean hasVertical() {
    for (int i = 0; i < height-3; i++) {
      for (int j = 0; j < width; j++) {
        if (board[i][j] != '*'
            && board[i][j] == board[i+1][j]
            && board[i+1][j] == board[i+2][j]
            && board[i+2][j] == board[i+3][j]) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean hasDiagonal() {
    for (int i = 0; i < height-3; i++) {
      for (int j = 0; j < width-3; j++) {
        if (board[i][j] != '*'
            && board[i][j] == board[i+1][j+1]
            && board[i+1][j+1] == board[i+2][j+2]
            && board[i+2][j+2] == board[i+3][j+3]) {
          return true;
        }
      }
    }

    for (int i = height-1; i >= 3; i--) {
      for (int j = 0; j < width-3; j++) {
        if (board[i][j] != '*'
            && board[i][j] == board[i-1][j+1]
            && board[i-1][j+1] == board[i-2][j+2]
            && board[i-2][j+2] == board[i-3][j+3]) {
          return true;
        }
      }
    }
    return false;
  }


  /**
   * Check if the board is full without a winner.
   */
  public boolean hasTie() {
    for (int i = 0; i < width; i++) {
      if (heightLeft[i] != 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Prints the current state of the board.
   */
  public void printBoard() {
    for (int i = 0; i < height; i++) {
      System.out.println(new String(board[i]));
    }
  }

  /**
   * Simulates a game of Connect 4.
   */
  public static void main(String[] args) {
    Player p1 = new Player('O');
    Player p2 = new Player('x');
    Gameboard gb = new Gameboard();
    Player nextPlayer = p1;
    while (!(gb.hasWin() || gb.hasTie())) {
      nextPlayer.play(gb);
      if (nextPlayer == p1) {
        nextPlayer = p2;
      } else {
        nextPlayer = p1;
      }
      gb.printBoard();
      System.out.println();
    }

    // Winner is the person who played last.
    if (gb.hasTie()) {
      System.out.println("Game ended in a tie.");
    } else {
      if (nextPlayer == p1) {
        System.out.println("P2 (x) is the winner!");
      } else {
        System.out.println("P1 (O) is the winner!");
      }
    }
  }
}
