package software.ulpgc.bigdata.algebra.matrices.longint.test;

import org.junit.jupiter.api.Test;
import software.ulpgc.bigdata.algebra.matrices.longint.Matrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CoordinateMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.operators.matrixmultiplication.DenseMatrixMultiplication;
import software.ulpgc.bigdata.algebra.matrices.longint.operators.matrixparallelmultiplication.MatrixParallelMultiplier;
import software.ulpgc.bigdata.algebra.matrices.longint.reader.MatrixFileReader;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MatrixParallelMultiplicationTest {

    @Test
    public void testMatrixParallelMultiplier() throws InterruptedException, IOException {
        String fileNameA = "A.mtx";
        String fileNameB = "B.mtx";

        CoordinateMatrix A = MatrixFileReader.readMatrixFromFile(fileNameA);
        CoordinateMatrix B = MatrixFileReader.readMatrixFromFile(fileNameB);

        DenseMatrix denseA = new DenseMatrix(A);
        DenseMatrix denseB = new DenseMatrix(B);

        double[][] doubleArrayA = MatrixFileReader.convertToDoubleArray(A);
        double[][] doubleArrayB = MatrixFileReader.convertToDoubleArray(B);

        double[][] resultArray = new double[denseA.getNumRows()][denseB.getNumCols()];

        int tileSize = 2;

        System.out.println("Tiles of MatrixA:");
        printTiles(doubleArrayA, tileSize);
        System.out.println("Tiles of MatrixB:");
        printTiles(doubleArrayB, tileSize);

        Semaphore semaphore = new Semaphore(1);
        MatrixParallelMultiplier parallelMultiplier = new MatrixParallelMultiplier(doubleArrayA, doubleArrayB, resultArray, tileSize, semaphore);
        parallelMultiplier.multiplyInParallel();

        Matrix sequentialResult = DenseMatrixMultiplication.multiply(denseA, denseB);

        System.out.println("Result Matrix C:");
        printMatrix(resultArray);

        assertArrayEquals(sequentialResult.toArray(), resultArray);
    }

    private void printMatrix(double[][] matrix) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void printTiles(double[][] matrix, int tileSize) {
        for (int i = 0; i < matrix.length; i += tileSize) {
            for (int j = 0; j < matrix[0].length; j += tileSize) {
                System.out.println("Tile at (" + i + ", " + j + "):");
                for (int row = i; row < i + tileSize && row < matrix.length; row++) {
                    for (int col = j; col < j + tileSize && col < matrix[0].length; col++) {
                        System.out.print(matrix[row][col] + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
    }
}
