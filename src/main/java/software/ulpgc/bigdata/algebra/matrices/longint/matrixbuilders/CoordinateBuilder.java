package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CoordinateMatrix;
import java.util.ArrayList;
import java.util.List;

public class CoordinateBuilder {
    private int numRows;
    private int numCols;
    private final List<Coordinate> entries;

    public CoordinateBuilder() {
        entries = new ArrayList<>();
    }

    public void setMatrixSize(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public void addEntry(int row, int col, double value) {
        entries.add(new Coordinate(row, col, value));
    }

    public CoordinateMatrix build() {
        return new CoordinateMatrix(numRows, numCols, entries);
    }
}
