public class Pair {
    private final int first;

    private final int second;

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Pair pair) {
            return pair.first == first && pair.second == second;
        }
        return false;
    }

    @Override
    public String toString() {
        return "{row=" + first +
                ", column=" + second + '}';
    }
}
