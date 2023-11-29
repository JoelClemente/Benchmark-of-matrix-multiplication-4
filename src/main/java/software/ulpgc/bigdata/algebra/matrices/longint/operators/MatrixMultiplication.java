package software.ulpgc.bigdata.algebra.matrices.longint.operators;

import software.ulpgc.bigdata.algebra.matrices.longint.Matrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedColumnMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedRowMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;

public interface MatrixMultiplication {
    static Matrix multiply(DenseMatrix a, DenseMatrix b) {return null; }

    static Matrix multiply(CompressedRowMatrix a, CompressedColumnMatrix b) {
        return null;
    }
}
