package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedColumnMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;

public class CompressedColumnMatrixBuilder {
    private DenseMatrix denseMatrix;

    public CompressedColumnMatrixBuilder setDenseMatrix(DenseMatrix denseMatrix) {
        this.denseMatrix = denseMatrix;
        return this;
    }

    public CompressedColumnMatrix build() {
        return new CompressedColumnMatrix(denseMatrix);
    }

}
