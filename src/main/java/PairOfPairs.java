public class PairOfPairs {
    Pair first;
    Pair second;

    public PairOfPairs(Pair first, Pair second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "PairOfPairs{" +
                "possibleSquare=" + first +
                ", initialSquare=" + second +
                '}';
    }
}
