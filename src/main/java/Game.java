import java.util.*;

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
        System.out.println(table);
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
                List<PairOfPairAndList> possibleSquares = checkPossibleSquares();
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

        List<PairOfPairAndList> possibleSquares;
        double rPoints;
        double maxRPoints;
        possibleSquares = checkPossibleSquares();
        if (possibleSquares.isEmpty()) {
            throw new RuntimeException();
        } else {
            PairOfPairAndList maxRPair = null;
            if (currentPlayer.equals("white")) {
                maxRPoints = 0;
                for (PairOfPairAndList pairOfPairAndList :
                        possibleSquares) {
                    rPoints = R(pairOfPairAndList);
                    if (rPoints > maxRPoints) {
                        maxRPoints = rPoints;
                        maxRPair = pairOfPairAndList;
                    }
                }
            } else {
                printPossibleSquares(possibleSquares);
                Pair pair = getCoordinates(possibleSquares);
                for (PairOfPairAndList p :
                        possibleSquares) {
                    if (p.first.equals(pair)) {
                        maxRPair = p;
                    }
                }
            }
            assert maxRPair != null;
            changeColor(maxRPair);
        }
        System.out.println("\n\n\n\n\nИгрок " + currentPlayer + " сделал свой ход:\n\n" + table);
        if (currentPlayer.equals("black")) {
            currentPlayer = "white";
        } else {
            currentPlayer = "black";
        }
    }

    private void printPossibleSquares(List<PairOfPairAndList> possibleSquares) {
        System.out.println("Координаты клеток, на которые можно совершить ход:");
        for (PairOfPairAndList p :
                possibleSquares) {
            System.out.println(p.first);
        }
        System.out.println();
    }

    private Pair getCoordinates(List<PairOfPairAndList> possibleSquares) {
        System.out.println("Введите координаты выбранной клетки через пробел:");
        int row = 0;
        int column = 0;
        boolean isCorrect;
        do {
            isCorrect = true;
            Scanner in = new Scanner(System.in);
            try {
                row = in.nextInt();
                column = in.nextInt();
            } catch (RuntimeException ex) {
                isCorrect = false;
                System.out.println("Координаты некорректны. Введите координаты заново:");
                continue;
            }
            if (!possibleSquares.contains(new PairOfPairAndList(new Pair(row, column), null))) {
                isCorrect = false;
                System.out.println("Ход на эту клетку невозможен. Введите координаты заново:");
            }
        } while (!isCorrect);
        return new Pair(row, column);
    }

    private void changeColor(PairOfPairAndList pairOfPairAndList) {
        int numberOfColored = 0;
        for (Pair p :
                pairOfPairAndList.second) {
            int row = p.getFirst();
            int column = p.getSecond();
            int rowChange = 0;
            int columnChange = 0;

            if (row < pairOfPairAndList.first.getFirst()) {
                rowChange = 1;
            } else if (row > pairOfPairAndList.first.getFirst()) {
                rowChange = -1;
            }
            if (column < pairOfPairAndList.first.getSecond()) {
                columnChange = 1;
            } else if (column > pairOfPairAndList.first.getSecond()) {
                columnChange = -1;
            }
            row += rowChange;
            column += columnChange;
            while (row != pairOfPairAndList.first.getFirst() || column != pairOfPairAndList.first.getSecond()) {
                if (!table.table[row][column].color.equals(currentPlayer)) {
                    numberOfColored += 1;
                }
                table.table[row][column].color = currentPlayer;
                row += rowChange;
                column += columnChange;
            }
        }
        if (currentPlayer.equals("black")) {
            table.setNumberOfBlack(table.getNumberOfBlack() + numberOfColored + 1);
            table.setNumberOfWhite(table.getNumberOfWhite() - numberOfColored);
        } else {
            table.setNumberOfBlack(table.getNumberOfBlack() - numberOfColored);
            table.setNumberOfWhite(table.getNumberOfWhite() + numberOfColored + 1);
        }
        table.table[pairOfPairAndList.first.getFirst()][pairOfPairAndList.first.getSecond()] =
                new Disk(currentPlayer, pairOfPairAndList.first.getFirst(), pairOfPairAndList.first.getSecond());
    }

    private double R(PairOfPairAndList pairOfPairAndList) {
        double ss = 0;
        if (pairOfPairAndList.first.getFirst() == 0 && pairOfPairAndList.first.getSecond() == 0 ||
                pairOfPairAndList.first.getFirst() == 0 && pairOfPairAndList.first.getSecond() == 7 ||
                pairOfPairAndList.first.getFirst() == 7 && pairOfPairAndList.first.getSecond() == 0 ||
                pairOfPairAndList.first.getFirst() == 7 && pairOfPairAndList.first.getSecond() == 7) {
            ss = 0.8;
        } else if (pairOfPairAndList.first.getFirst() == 0 || pairOfPairAndList.first.getSecond() == 7 ||
                pairOfPairAndList.first.getFirst() == 7 || pairOfPairAndList.first.getSecond() == 0) {
            ss = 0.4;
        }
        for (Pair p :
                pairOfPairAndList.second) {
            int row = p.getFirst();
            int column = p.getSecond();
            int rowChange = 0;
            int columnChange = 0;

            if (row < pairOfPairAndList.first.getFirst()) {
                rowChange = 1;
            } else if (row > pairOfPairAndList.first.getFirst()) {
                rowChange = -1;
            }
            if (column < pairOfPairAndList.first.getSecond()) {
                columnChange = 1;
            } else if (column > pairOfPairAndList.first.getSecond()) {
                columnChange = -1;
            }
            row += rowChange;
            column += columnChange;
            while (row != pairOfPairAndList.first.getFirst() || column != pairOfPairAndList.first.getSecond()) {
                if (row == 0 || row == 7 || column == 7 || column == 0) {
                    ss += 2;
                } else {
                    ss += 1;
                }
                row += rowChange;
                column += columnChange;
            }
        }
        return ss;
    }

    List<PairOfPairAndList> checkPossibleSquares() {
        List<PairOfPairAndList> possibleSquares = new ArrayList<>();
        Pair help;
        int ind;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (table.table[i][j] != null && Objects.equals(table.table[i][j].color, currentPlayer)) {
                    if (i - 1 >= 0) {
                        if (j - 1 >= 0 && table.table[i - 1][j - 1] != null &&
                                !table.table[i - 1][j - 1].color.equals(currentPlayer)) {
                            help = findTopLeftSquare(i, j);
                            if (help.getFirst() != -1 && help.getSecond() != -1) {
                                List<Pair> arrList = new ArrayList<>();
                                ind = possibleSquares.indexOf(new PairOfPairAndList(help, arrList));
                                if (ind == -1) {
                                    arrList.add(new Pair(i, j));
                                    possibleSquares.add(new PairOfPairAndList(help, arrList));
                                } else {
                                    possibleSquares.get(ind).second.add(new Pair(i, j));
                                }
                            }
                        }
                        if (table.table[i - 1][j] != null && !table.table[i - 1][j].color.equals(currentPlayer)) {
                            help = findTopSquare(i, j);
                            if (help.getFirst() != -1 && help.getSecond() != -1) {
                                List<Pair> arrList = new ArrayList<>();
                                ind = possibleSquares.indexOf(new PairOfPairAndList(help, arrList));
                                if (ind == -1) {
                                    arrList.add(new Pair(i, j));
                                    possibleSquares.add(new PairOfPairAndList(help, arrList));
                                } else {
                                    possibleSquares.get(ind).second.add(new Pair(i, j));
                                }
                            }
                        }
                        if (j + 1 <= 7 && table.table[i - 1][j + 1] != null &&
                                !table.table[i - 1][j + 1].color.equals(currentPlayer)) {
                            help = findTopRightSquare(i, j);
                            if (help.getFirst() != -1 && help.getSecond() != -1) {
                                List<Pair> arrList = new ArrayList<>();
                                ind = possibleSquares.indexOf(new PairOfPairAndList(help, arrList));
                                if (ind == -1) {
                                    arrList.add(new Pair(i, j));
                                    possibleSquares.add(new PairOfPairAndList(help, arrList));
                                } else {
                                    possibleSquares.get(ind).second.add(new Pair(i, j));
                                }
                            }
                        }
                    }
                    if (j - 1 >= 0 && table.table[i][j - 1] != null &&
                            !table.table[i][j - 1].color.equals(currentPlayer)) {
                        help = findLeftSquare(i, j);
                        if (help.getFirst() != -1 && help.getSecond() != -1) {
                            List<Pair> arrList = new ArrayList<>();
                            ind = possibleSquares.indexOf(new PairOfPairAndList(help, arrList));
                            if (ind == -1) {
                                arrList.add(new Pair(i, j));
                                possibleSquares.add(new PairOfPairAndList(help, arrList));
                            } else {
                                possibleSquares.get(ind).second.add(new Pair(i, j));
                            }
                        }
                    }
                    if (j + 1 <= 7 && table.table[i][j + 1] != null &&
                            !table.table[i][j + 1].color.equals(currentPlayer)) {
                        help = findRightSquare(i, j);
                        if (help.getFirst() != -1 && help.getSecond() != -1) {
                            List<Pair> arrList = new ArrayList<>();
                            ind = possibleSquares.indexOf(new PairOfPairAndList(help, arrList));
                            if (ind == -1) {
                                arrList.add(new Pair(i, j));
                                possibleSquares.add(new PairOfPairAndList(help, arrList));
                            } else {
                                possibleSquares.get(ind).second.add(new Pair(i, j));
                            }
                        }
                    }
                    if (i + 1 <= 7) {
                        if (j - 1 >= 0 && table.table[i + 1][j - 1] != null &&
                                !table.table[i + 1][j - 1].color.equals(currentPlayer)) {
                            help = findBottomLeftSquare(i, j);
                            if (help.getFirst() != -1 && help.getSecond() != -1) {
                                List<Pair> arrList = new ArrayList<>();
                                ind = possibleSquares.indexOf(new PairOfPairAndList(help, arrList));
                                if (ind == -1) {
                                    arrList.add(new Pair(i, j));
                                    possibleSquares.add(new PairOfPairAndList(help, arrList));
                                } else {
                                    possibleSquares.get(ind).second.add(new Pair(i, j));
                                }
                            }
                        }
                        if (table.table[i + 1][j] != null && !table.table[i + 1][j].color.equals(currentPlayer)) {
                            help = findBottomSquare(i, j);
                            if (help.getFirst() != -1 && help.getSecond() != -1) {
                                List<Pair> arrList = new ArrayList<>();
                                ind = possibleSquares.indexOf(new PairOfPairAndList(help, arrList));
                                if (ind == -1) {
                                    arrList.add(new Pair(i, j));
                                    possibleSquares.add(new PairOfPairAndList(help, arrList));
                                } else {
                                    possibleSquares.get(ind).second.add(new Pair(i, j));
                                }
                            }
                        }
                        if (j + 1 <= 7 && table.table[i + 1][j + 1] != null &&
                                !table.table[i + 1][j + 1].color.equals(currentPlayer)) {
                            help = findBottomRightSquare(i, j);
                            if (help.getFirst() != -1 && help.getSecond() != -1) {
                                List<Pair> arrList = new ArrayList<>();
                                ind = possibleSquares.indexOf(new PairOfPairAndList(help, arrList));
                                if (ind == -1) {
                                    arrList.add(new Pair(i, j));
                                    possibleSquares.add(new PairOfPairAndList(help, arrList));
                                } else {
                                    possibleSquares.get(ind).second.add(new Pair(i, j));
                                }
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
