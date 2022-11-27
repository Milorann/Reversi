import java.util.ArrayList;
import java.util.List;

public class Table {
    int numberOfBlack = 2;
    int numberOfWhite = 2;

    Disk[][] table = new Disk[8][8];

    {
        table[3][3] = new Disk("white", 3, 3);
        table[3][4] = new Disk("black", 3, 4);
        table[4][3] = new Disk("black", 4, 3);
        table[4][4] = new Disk("white", 4, 4);
    }

    public void Print() {
        System.out.print("| ");
        for (int column = 0; column < 8; ++column) {
            System.out.print(column + " |");
        }
        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 8; ++column) {

            }
        }
    }
}
