public class Pair {
    private int first;

    private int second;

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
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
