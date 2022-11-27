import java.util.Scanner;

public class GameMenu {
    int highestScore = 0;

    void Menu() {
        boolean isCorrect;
        int choice;
        int score;
        do {
            choice = 0;
            System.out.println("Лучший счет в одиночной: " + highestScore +
                    "\n\nВыберите тип игры:\n\t1 - одиночная\n\t2 - два игрока\n\t3 - выход");
            do {
                isCorrect = true;
                Scanner in = new Scanner(System.in);
                try {
                    choice = in.nextInt();
                } catch (RuntimeException ex) {
                    isCorrect = false;
                    System.out.println("Некорректный ввод. Введите 1 или 2.");
                    continue;
                }
                if (choice != 1 && choice != 2 && choice != 3) {
                    isCorrect = false;
                    System.out.println("Некорректный ввод. Введите 1 или 2.");
                }
            } while (!isCorrect);
            Game g;
            if (choice == 1) {
                g = new Game("single");
                score = g.Play();
                if (score > highestScore) {
                    highestScore = score;
                }
            } else if (choice == 2) {
                g = new Game("multi");
                g.Play();
            }
        } while (choice != 3);
    }
}
