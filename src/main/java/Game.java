public class Game {
    Table table = new Table();
    String currentPlayer = "black";
    String mode;
    String difficulty;

    public Game(String mode, String difficulty) {
        this.mode = mode;
        this.difficulty = difficulty;
    }
}
