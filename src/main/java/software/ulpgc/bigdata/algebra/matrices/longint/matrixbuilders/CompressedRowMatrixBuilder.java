package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedRowMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;

public class CompressedRowMatrixBuilder {
    private DenseMatrix denseMatrix;

    public CompressedRowMatrixBuilder setDenseMatrix(DenseMatrix denseMatrix) {
        this.denseMatrix = denseMatrix;
        return this;
    }

    public CompressedRowMatrix build() {
        return new CompressedRowMatrix(denseMatrix);
    }
}
