package software.ulpgc.bigdata.algebra.matrices.longint.operators.matrixmultiplication;

import software.ulpgc.bigdata.algebra.matrices.longint.Matrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders.DenseMatrixBuilder;

public class DenseMatrixMultiplication {
    public static Matrix multiply(DenseMatrix a, DenseMatrix b) {
        int numRowsA = a.getNumRows();
        int numColsA = a.getNumCols();
        int numRowsB = b.getNumRows();
        int numColsB = b.getNumCols();

        if (numColsA != numRowsB) {
            throw new IllegalArgumentException("The dimensions of the matrix are not compatible.");
        }

        DenseMatrixBuilder resultBuilder = new DenseMatrixBuilder(numRowsA,numColsA);

        for (int i = 0; i < numRowsA; i++) {
            for (int j = 0; j < numColsB; j++) {
                long sum = 0;
                for (int k = 0; k < numColsA; k++) {
                    sum += a.get(i, k) * b.get(k, j);
                }
                resultBuilder.set(i, j, sum);
            }
        }

        return resultBuilder.get();
    }
}
