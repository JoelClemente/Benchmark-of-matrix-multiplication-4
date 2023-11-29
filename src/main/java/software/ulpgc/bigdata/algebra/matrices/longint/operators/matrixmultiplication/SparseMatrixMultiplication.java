package software.ulpgc.bigdata.algebra.matrices.longint.operators.matrixmultiplication;

import software.ulpgc.bigdata.algebra.matrices.longint.Matrix;
import software.ulpgc.bigdata.algebra.matrices.longint.MatrixBuilder;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedColumnMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedRowMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders.SparseMatrixBuilder;

public class SparseMatrixMultiplication {
    public static Matrix multiply(CompressedRowMatrix crsMatrix, CompressedColumnMatrix ccsMatrix) {

        if (crsMatrix.getNumCols() != ccsMatrix.getNumRows()) {
            throw new IllegalArgumentException("The dimensions of the matrix are not compatible.");
        }

        MatrixBuilder resultBuilder = new SparseMatrixBuilder(crsMatrix.getNumRows(), ccsMatrix.getNumCols());

        int[] crsRowPointers = crsMatrix.getRowPointers();
        int[] crsColumnIndices = crsMatrix.getColumnIndices();
        double[] crsValues = crsMatrix.getValues();
        int[] ccsColumnPointers = ccsMatrix.getColumnPointers();
        int[] ccsRowIndices = ccsMatrix.getRowIndices();
        double[] ccsValues = ccsMatrix.getValues();

        for (int i = 0; i < crsMatrix.getNumRows(); i++) {
            for (int j = 0; j < ccsMatrix.getNumCols(); j++) {
                long result = 0L;
                int crsStart = crsRowPointers[i];
                int crsEnd = crsRowPointers[i + 1];
                int ccsStart = ccsColumnPointers[j];
                int ccsEnd = ccsColumnPointers[j + 1];
                int crsIndex = crsStart;
                int ccsIndex = ccsStart;

                while (crsIndex < crsEnd && ccsIndex < ccsEnd) {
                    int crsCol = crsColumnIndices[crsIndex];
                    int ccsRow = ccsRowIndices[ccsIndex];
                    if (crsCol == ccsRow) {
                        result += (long) (crsValues[crsIndex] * ccsValues[ccsIndex]);
                        crsIndex++;
                        ccsIndex++;
                    } else if (crsCol < ccsRow) {
                        crsIndex++;
                    } else {
                        ccsIndex++;
                    }
                }

                resultBuilder.set(i, j, result);
            }
        }

        return resultBuilder.get();
    }
}
