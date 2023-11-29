package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompressedColumnMatrix {
    private final int numRows;
    private final int numCols;
    private int[] columnPointers;
    private final double[] values;
    private int[] rowIndices;

    public CompressedColumnMatrix(DenseMatrix denseMatrix) {
        this.numRows = denseMatrix.getNumRows();
        this.numCols = denseMatrix.getNumCols();

        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                long value = denseMatrix.get(i, j);
                if (value != 0) {
                    coordinates.add(new Coordinate(i, j, (double) value));
                }
            }
        }

        Collections.sort(coordinates, (a, b) -> {
            if (a.getCol() == b.getCol()) {
                return Integer.compare(a.getRow(), b.getRow());
            }
            return Integer.compare(a.getCol(), b.getCol());
        });

        this.values = new double[coordinates.size()];
        this.rowIndices = new int[coordinates.size()];
        this.columnPointers = new int[numCols + 1];

        int currentCol = -1;
        int pointer = 0;

        for (int i = 0; i < coordinates.size(); i++) {
            Coordinate coordinate = coordinates.get(i);
            int col = coordinate.getCol();
            int row = coordinate.getRow();
            double value = coordinate.getValue();

            values[i] = value;
            rowIndices[i] = row;

            if (col != currentCol) {
                while (currentCol < col) {
                    currentCol++;
                    columnPointers[currentCol] = pointer;
                }
            }

            pointer++;
        }

        while (currentCol < numCols) {
            currentCol++;
            columnPointers[currentCol] = pointer;
        }
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int[] getColumnPointers() {
        return columnPointers;
    }

    public double[] getValues() {
        return values;
    }

    public int[] getRowIndices() {
        return rowIndices;
    }
}
