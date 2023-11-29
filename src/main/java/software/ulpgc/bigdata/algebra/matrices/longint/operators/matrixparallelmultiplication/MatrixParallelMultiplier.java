package software.ulpgc.bigdata.algebra.matrices.longint.operators.matrixparallelmultiplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MatrixParallelMultiplier implements Runnable {
    private double[][] A, B, C;
    private int row;
    private Semaphore semaphore;

    public MatrixParallelMultiplier(double[][] A, double[][] B, double[][] C, int row, Semaphore semaphore) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.row = row;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            for (int i = 0; i < B[0].length; i++) {
                double sum = 0;
                for (int j = 0; j < A[0].length; j++) {
                    sum += A[row][j] * B[j][i];
                }
                C[row][i] = sum;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public static void multiplyInParallel(double[][] A, double[][] B, double[][] C) {
        Semaphore semaphore = new Semaphore(1);
        ExecutorService executorService = Executors.newFixedThreadPool(A.length);

        for (int i = 0; i < A.length; i++) {
            executorService.submit(new MatrixParallelMultiplier(A, B, C, i, semaphore));
        }

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
