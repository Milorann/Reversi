import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Table {
    private int numberOfBlack = 2;
    private int numberOfWhite = 2;

    public int getNumberOfBlack() {
        return numberOfBlack;
    }

    public void setNumberOfBlack(int numberOfBlack) {
        this.numberOfBlack = numberOfBlack;
    }

    public int getNumberOfWhite() {
        return numberOfWhite;
    }

    public void setNumberOfWhite(int numberOfWhite) {
        this.numberOfWhite = numberOfWhite;
    }

    public int getNumberOfFilledSquares() {
        return numberOfBlack + numberOfWhite;
    }

    Disk[][] table = new Disk[8][8];

    {
        table[3][3] = new Disk("white", 3, 3);
        table[3][4] = new Disk("black", 3, 4);
        table[4][3] = new Disk("black", 4, 3);
        table[4][4] = new Disk("white", 4, 4);
    }

//    public void Print() {
//        System.out.println("    Black: " + numberOfBlack + "        |       White: " + numberOfWhite);
//        System.out.println(" ___________________________________");
//        System.out.print("|___|");
//        for (int column = 0; column < 8; ++column) {
//            System.out.print("_" + column + "_|");
//        }
//        System.out.println();
//        for (int row = 0; row < 8; ++row) {
//            System.out.print("|_");
//            System.out.print(row + "_|");
//            for (int column = 0; column < 8; ++column) {
//                System.out.print("_" + (table[row][column] == null ? "_" :
//                        (Objects.equals(table[row][column].color, "black") ? "⭘" : "●")) + "_|");
//            }
//            System.out.println();
//        }
//    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder("   Black: " + numberOfBlack + "        |       White: " + numberOfWhite);
        print.append("\n ___________________________________\n|___|");
        for (int column = 0; column < 8; ++column) {
            print.append("_").append(column).append("_|");
        }
        print.append('\n');
        for (int row = 0; row < 8; ++row) {
            print.append("|_");
            print.append(row).append("_|");
            for (int column = 0; column < 8; ++column) {
                print.append("_").append(table[row][column] == null ? "_" :
                        (Objects.equals(table[row][column].color, "black") ? "⭘" : "●")).append("_|");
            }
            print.append('\n');
        }
        return print.toString();
    }
}
