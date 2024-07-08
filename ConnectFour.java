import java.util.Scanner;

public class ConnectFour {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final char EMPTY_SLOT = '-';
    private static final char PLAYER_ONE = 'X';
    private static final char PLAYER_TWO = 'O';

    private static char[][] board = new char[ROWS][COLUMNS];
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeBoard();
        boolean playerOneTurn = true;
        boolean gameWon = false;

        while (!gameWon && !isBoardFull()) {
            printBoard();
            if (playerOneTurn) {
                System.out.println("Player 1 (X), choose a row (0-5) and a column (0-6): ");
            } else {
                System.out.println("Player 2 (O), choose a row (0-5) and a column (0-6): ");
            }

            int row = scanner.nextInt();
            int column = scanner.nextInt();
            if (row < 0 || row >= ROWS || column < 0 || column >= COLUMNS || !isPositionValid(row, column)) {
                System.out.println("Invalid row or column. Try again.");
                continue;
            }

            placeDisc(playerOneTurn ? PLAYER_ONE : PLAYER_TWO, row, column);
            if (checkWin(row, column)) {
                gameWon = true;
                printBoard();
                System.out.println("Player " + (playerOneTurn ? "1 (X)" : "2 (O)") + " wins!");
            } else {
                playerOneTurn = !playerOneTurn;
            }
        }

        if (!gameWon) {
            System.out.println("The game is a draw!");
        }
    }

    private static void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = EMPTY_SLOT;
            }
        }
    }

    private static void printBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean isPositionValid(int row, int column) {
        return board[row][column] == EMPTY_SLOT;
    }

    private static void placeDisc(char disc, int row, int column) {
        board[row][column] = disc;
    }

    private static boolean isBoardFull() {
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                if (board[row][col] == EMPTY_SLOT) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkWin(int row, int column) {
        char disc = board[row][column];
        return checkDirection(row, column, disc, 1, 0) // Horizontal
            || checkDirection(row, column, disc, 0, 1) // Vertical
            || checkDirection(row, column, disc, 1, 1) // Diagonal /
            || checkDirection(row, column, disc, 1, -1); // Diagonal \
    }

    private static boolean checkDirection(int row, int column, char disc, int rowDir, int colDir) {
        int count = 0;
        for (int i = -3; i <= 3; i++) {
            int r = row + i * rowDir;
            int c = column + i * colDir;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS && board[r][c] == disc) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }
}
