package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompressedRowMatrix {
    private final int numRows;
    private final int numCols;
    private int[] rowPointers;
    private final double[] values;
    private int[] columnIndices;

    public CompressedRowMatrix(DenseMatrix denseMatrix) {
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
            if (a.getRow() == b.getRow()) {
                return Integer.compare(a.getCol(), b.getCol());
            }
            return Integer.compare(a.getRow(), b.getRow());
        });

        this.values = new double[coordinates.size()];
        this.columnIndices = new int[coordinates.size()];
        this.rowPointers = new int[numRows + 1];

        int currentRow = -1;
        int pointer = 0;

        for (int i = 0; i < coordinates.size(); i++) {
            Coordinate coordinate = coordinates.get(i);
            int row = coordinate.getRow();
            int col = coordinate.getCol();
            double value = coordinate.getValue();

            values[i] = value;
            columnIndices[i] = col;

            if (row != currentRow) {
                while (currentRow < row) {
                    currentRow++;
                    rowPointers[currentRow] = pointer;
                }
            }

            pointer++;
        }

        while (currentRow < numRows) {
            currentRow++;
            rowPointers[currentRow] = pointer;
        }
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int[] getRowPointers() {
        return rowPointers;
    }

    public double[] getValues() {
        return values;
    }

    public int[] getColumnIndices() {
        return columnIndices;
    }
}
