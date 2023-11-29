package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

import java.util.List;

public class CoordinateMatrix {
    private final int numRows;
    private final int numCols;
    private final List<Coordinate> entries;

    public CoordinateMatrix(int numRows, int numCols, List<Coordinate> entries) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.entries = entries != null ? entries : List.of();
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public List<Coordinate> getEntries() {
        return entries;
    }

    public double getValue(int row, int col) {
        return entries.stream()
                .filter(entry -> entry.getRow() == row && entry.getCol() == col)
                .mapToDouble(Coordinate::getValue)
                .findFirst()
                .orElse(0.0);
    }
}
