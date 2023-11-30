package software.ulpgc.bigdata.algebra.matrices.longint.operators.matrixparallelmultiplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MatrixParallelMultiplier {

    private double[][] A, B, C;
    private int tileSize;
    private int numRowsA, numColsA, numColsB;
    private Semaphore semaphore;

    public MatrixParallelMultiplier(double[][] A, double[][] B, double[][] C, int tileSize, Semaphore semaphore) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.tileSize = tileSize;
        this.numRowsA = A.length;
        this.numColsA = A[0].length;
        this.numColsB = B[0].length;
        this.semaphore = semaphore;
    }

    public void multiplyInParallel() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(numRowsA*numColsB);

        for (int i = 0; i < numRowsA; i += tileSize) {
            for (int j = 0; j < numColsB; j += tileSize) {
                executor.submit(new TileMultiplier(i, j));
            }
        }

        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.SECONDS);
    }

    private class TileMultiplier implements Runnable {
        private int startRow, startCol;

        public TileMultiplier(int startRow, int startCol) {
            this.startRow = startRow;
            this.startCol = startCol;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();

                for (int i = startRow; i < Math.min(startRow + tileSize, numRowsA); i++) {
                    for (int j = startCol; j < Math.min(startCol + tileSize, numColsB); j++) {
                        double sum = 0;
                        for (int k = 0; k < numColsA; k++) {
                            sum += A[i][k] * B[k][j];
                        }
                        C[i][j] = sum;
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }
}
