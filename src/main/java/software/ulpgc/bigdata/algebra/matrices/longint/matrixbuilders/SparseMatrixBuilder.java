package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.Matrix;
import software.ulpgc.bigdata.algebra.matrices.longint.MatrixBuilder;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CoordinateMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.SparseMatrix;

import java.util.ArrayList;
import java.util.List;

public class SparseMatrixBuilder implements MatrixBuilder {
    private final int numRows;
    private final int numCols;
    private final List<Coordinate> entries;

    public SparseMatrixBuilder(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.entries = new ArrayList<>();
    }

    @Override
    public void set(int i, int j, long value) {
        entries.add(new Coordinate(i, j, value));
    }

    @Override
    public Matrix get() {
        return new SparseMatrix(new CoordinateMatrix(numRows, numCols, entries));
    }
}
