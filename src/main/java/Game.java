import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Game {
    Table table = new Table();
    String currentPlayer = "black";
    String mode;
    String difficulty;

    public Game(String mode, String difficulty) {
        this.mode = mode;
        this.difficulty = difficulty;
    }

    void Play() {
        int cnt = 0;
        while (table.getNumberOfFilledSquares() < 64) {
            try {
                makeMove();
            } catch (RuntimeException ex) {
                System.out.println(currentPlayer + " skips a turn");
                if (currentPlayer.equals("black")) {
                    currentPlayer = "white";
                } else {
                    currentPlayer = "black";
                }
                List<PairOfPairs> possibleSquares = checkPossibleSquares();
                if (possibleSquares.isEmpty()) {
                    break;
                }
            }
        }
        if (table.getNumberOfBlack() > table.getNumberOfWhite()) {
            System.out.println("Black won!");
        } else if (table.getNumberOfBlack() < table.getNumberOfWhite()) {
            System.out.println("White won!");
        } else {
            System.out.println("Draw!");
        }
    }

    private void makeMove() throws RuntimeException {
        System.out.println("\n\n\n\n\n\n" + table);
        List<PairOfPairs> possibleSquares;
        double rPoints;
        double maxRPoints;
        possibleSquares = checkPossibleSquares();
        if (possibleSquares.isEmpty()) {
            throw new RuntimeException();
        } else {
            PairOfPairs maxRPair = null;
            if (currentPlayer.equals("white")) {
                maxRPoints = 0;
                for (PairOfPairs pairOfPairs :
                        possibleSquares) {
                    rPoints = R(pairOfPairs);
                    if (rPoints > maxRPoints) {
                        maxRPoints = rPoints;
                        maxRPair = pairOfPairs;
                    }
                }
            } else {
                Pair pair = getCoordinates();
                for (PairOfPairs p :
                        possibleSquares) {
                    if (p.first.equals(pair)) {
                        maxRPair = p;
                    }
                }
            }
            changeColor(maxRPair);
        }
        System.out.println("\n\n\n\n\n\n" + table);
        if (currentPlayer.equals("black")) {
            currentPlayer = "white";
        } else {
            currentPlayer = "black";
        }
    }

    private Pair getCoordinates() {
        Scanner in = new Scanner(System.in);
        System.out.println("введите координаты");
        int row = in.nextInt();
        int column = in.nextInt();
        return new Pair(row, column);
    }

    private void changeColor(PairOfPairs pairOfPairs) {
        int row = pairOfPairs.second.getFirst();
        int column = pairOfPairs.second.getSecond();
        int rowChange = 0;
        int columnChange = 0;
        int numberOfColored = 0;

        if (row < pairOfPairs.first.getFirst()) {
            rowChange = 1;
        } else if (row > pairOfPairs.first.getFirst()) {
            rowChange = -1;
        }
        if (column < pairOfPairs.first.getSecond()) {
            columnChange = 1;
        } else if (column > pairOfPairs.first.getSecond()) {
            columnChange = -1;
        }
        row += rowChange;
        column += columnChange;
        while (row != pairOfPairs.first.getFirst() || column != pairOfPairs.first.getSecond()) {
            table.table[row][column].color = currentPlayer;
            numberOfColored += 1;
            row += rowChange;
            column += columnChange;
        }
        table.table[pairOfPairs.first.getFirst()][pairOfPairs.first.getSecond()] = new Disk(currentPlayer, row, column);
        if (currentPlayer.equals("black")) {
            table.setNumberOfBlack(table.getNumberOfBlack() + numberOfColored + 1);
            table.setNumberOfWhite(table.getNumberOfWhite() - numberOfColored);
        } else {
            table.setNumberOfBlack(table.getNumberOfBlack() - numberOfColored);
            table.setNumberOfWhite(table.getNumberOfWhite() + numberOfColored + 1);
        }
    }

    private double R(PairOfPairs pairOfPairs) {
        double ss = 0;
        double s;
        if (pairOfPairs.first.getFirst() == 0 && pairOfPairs.first.getSecond() == 0 ||
                pairOfPairs.first.getFirst() == 0 && pairOfPairs.first.getSecond() == 7 ||
                pairOfPairs.first.getFirst() == 7 && pairOfPairs.first.getSecond() == 0 ||
                pairOfPairs.first.getFirst() == 7 && pairOfPairs.first.getSecond() == 7) {
            ss = 0.8;
        } else if (pairOfPairs.first.getFirst() == 0 || pairOfPairs.first.getSecond() == 7 ||
                pairOfPairs.first.getFirst() == 7 || pairOfPairs.first.getSecond() == 0) {
            ss = 0.4;
        }

        int row = pairOfPairs.second.getFirst();
        int column = pairOfPairs.second.getSecond();
        int rowChange = 0;
        int columnChange = 0;

        if (row < pairOfPairs.first.getFirst()) {
            rowChange = 1;
        } else if (row > pairOfPairs.first.getFirst()) {
            rowChange = -1;
        }
        if (column < pairOfPairs.first.getSecond()) {
            columnChange = 1;
        } else if (column > pairOfPairs.first.getSecond()) {
            columnChange = -1;
        }
        row += rowChange;
        column += columnChange;
        while (row != pairOfPairs.first.getFirst() || column != pairOfPairs.first.getSecond()) {
            if (row == 0 || row == 7 || column == 7 || column == 0) {
                ss += 2;
            } else {
                ss += 1;
            }
            row += rowChange;
            column += columnChange;
        }
        return ss;
    }

    List<PairOfPairs> checkPossibleSquares() {
        List<PairOfPairs> possibleSquares = new ArrayList<>();
        Pair help;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (table.table[i][j] != null && Objects.equals(table.table[i][j].color, currentPlayer)) {
                    if (i - 1 >= 0) {
                        if (j - 1 >= 0 && table.table[i - 1][j - 1] != null &&
                                !table.table[i - 1][j - 1].color.equals(currentPlayer)) {
                            help = findTopLeftSquare(i, j);
                            if (help.getFirst() != -1 && help.getSecond() != -1) {
                                possibleSquares.add(new PairOfPairs(help, new Pair(i, j)));
                            }
                        }
                        if (table.table[i - 1][j] != null && !table.table[i - 1][j].color.equals(currentPlayer)) {
                            help = findTopSquare(i, j);
                            if (help.getFirst() != -1 && help.getSecond() != -1) {
                                possibleSquares.add(new PairOfPairs(help, new Pair(i, j)));
                            }
                        }
                        if (j + 1 <= 7 && table.table[i - 1][j + 1] != null &&
                                !table.table[i - 1][j + 1].color.equals(currentPlayer)) {
                            help = findTopRightSquare(i, j);
                            if (help.getFirst() != -1 && help.getSecond() != -1) {
                                possibleSquares.add(new PairOfPairs(help, new Pair(i, j)));
                            }
                        }
                    }
                    if (j - 1 >= 0 && table.table[i][j - 1] != null &&
                            !table.table[i][j - 1].color.equals(currentPlayer)) {
                        help = findLeftSquare(i, j);
                        if (help.getFirst() != -1 && help.getSecond() != -1) {
                            possibleSquares.add(new PairOfPairs(help, new Pair(i, j)));
                        }
                    }
                    if (j + 1 <= 7 && table.table[i][j + 1] != null &&
                            !table.table[i][j + 1].color.equals(currentPlayer)) {
                        help = findRightSquare(i, j);
                        if (help.getFirst() != -1 && help.getSecond() != -1) {
                            possibleSquares.add(new PairOfPairs(help, new Pair(i, j)));
                        }
                    }
                    if (i + 1 <= 7) {
                        if (j - 1 >= 0 && table.table[i + 1][j - 1] != null &&
                                !table.table[i + 1][j - 1].color.equals(currentPlayer)) {
                            help = findBottomLeftSquare(i, j);
                            if (help.getFirst() != -1 && help.getSecond() != -1) {
                                possibleSquares.add(new PairOfPairs(help, new Pair(i, j)));
                            }
                        }
                        if (table.table[i + 1][j] != null && !table.table[i + 1][j].color.equals(currentPlayer)) {
                            help = findBottomSquare(i, j);
                            if (help.getFirst() != -1 && help.getSecond() != -1) {
                                possibleSquares.add(new PairOfPairs(help, new Pair(i, j)));
                            }
                        }
                        if (j + 1 <= 7 && table.table[i + 1][j + 1] != null &&
                                !table.table[i + 1][j + 1].color.equals(currentPlayer)) {
                            help = findBottomRightSquare(i, j);
                            if (help.getFirst() != -1 && help.getSecond() != -1) {
                                possibleSquares.add(new PairOfPairs(help, new Pair(i, j)));
                            }
                        }
                    }
                }
            }
        }
        return possibleSquares;
    }

    private Pair findBottomRightSquare(int i, int j) {
        int column = j + 2;
        int row = i + 2;
        while (column <= 7 && row <= 7) {
            if (table.table[row][column] == null) {
                return new Pair(row, column);
            }
            if (table.table[row][column].color.equals(table.table[i][j].color)) {
                return new Pair(-1, -1);
            }
            ++column;
            ++row;
        }
        return new Pair(-1, -1);
    }

    private Pair findBottomSquare(int i, int j) {
        int row = i + 2;
        while (row <= 7) {
            if (table.table[row][j] == null) {
                return new Pair(row, j);
            }
            if (table.table[row][j].color.equals(table.table[i][j].color)) {
                return new Pair(-1, -1);
            }
            ++row;
        }
        return new Pair(-1, -1);
    }

    private Pair findBottomLeftSquare(int i, int j) {
        int column = j - 2;
        int row = i + 2;
        while (column >= 0 && row <= 7) {
            if (table.table[row][column] == null) {
                return new Pair(row, column);
            }
            if (table.table[row][column].color.equals(table.table[i][j].color)) {
                return new Pair(-1, -1);
            }
            --column;
            ++row;
        }
        return new Pair(-1, -1);
    }

    private Pair findRightSquare(int i, int j) {
        int column = j + 2;
        while (column <= 7) {
            if (table.table[i][column] == null) {
                return new Pair(i, column);
            }
            if (table.table[i][column].color.equals(table.table[i][j].color)) {
                return new Pair(-1, -1);
            }
            ++column;
        }
        return new Pair(-1, -1);
    }

    private Pair findLeftSquare(int i, int j) {
        int column = j - 2;
        while (column >= 0) {
            if (table.table[i][column] == null) {
                return new Pair(i, column);
            }
            if (table.table[i][column].color.equals(table.table[i][j].color)) {
                return new Pair(-1, -1);
            }
            --column;
        }
        return new Pair(-1, -1);
    }

    private Pair findTopRightSquare(int i, int j) {
        int column = j + 2;
        int row = i - 2;
        while (column <= 7 && row >= 0) {
            if (table.table[row][column] == null) {
                return new Pair(row, column);
            }
            if (table.table[row][column].color.equals(table.table[i][j].color)) {
                return new Pair(-1, -1);
            }
            ++column;
            --row;
        }
        return new Pair(-1, -1);
    }

    private Pair findTopSquare(int i, int j) {
        int row = i - 2;
        while (row >= 0) {
            if (table.table[row][j] == null) {
                return new Pair(row, j);
            }
            if (table.table[row][j].color.equals(table.table[i][j].color)) {
                return new Pair(-1, -1);
            }
            --row;
        }
        return new Pair(-1, -1);
    }

    private Pair findTopLeftSquare(int i, int j) {
        int column = j - 2;
        int row = i - 2;
        while (column >= 0 && row >= 0) {
            if (table.table[row][column] == null) {
                return new Pair(row, column);
            }
            if (table.table[row][column].color.equals(table.table[i][j].color)) {
                return new Pair(-1, -1);
            }
            --column;
            --row;
        }
        return new Pair(-1, -1);
    }
}
