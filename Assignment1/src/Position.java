public class Position implements Comparable<Position> {
    private int line;
    private int col;

    public Position (int line, int col) {
        this.line = line;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getLine() {
        return line;
    }

    @Override
    public int compareTo(Position o) {
        if (line == o.getLine()) return Integer.compare(col, o.col);
        else return Integer.compare(line, o.getLine());
    }
}
