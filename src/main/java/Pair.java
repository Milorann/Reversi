public record Pair(int first, int second) {

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
