import java.util.List;

public class PairOfPairAndList {
    Pair first;
    List<Pair> second;

    public PairOfPairAndList(Pair first, List<Pair> second) {
        this.first = first;
        this.second = second;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PairOfPairAndList pair) {
            return pair.first.equals(first);
        }
        return false;
    }
    @Override
    public String toString() {
        return "PairOfPairs{" +
                "possibleSquare=" + first +
                ", initialSquare=" + second +
                '}';
    }
}
