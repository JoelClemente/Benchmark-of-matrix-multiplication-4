package software.ulpgc.bigdata.algebra.matrices.longint.test;

import org.junit.jupiter.api.Test;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CoordinateMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.reader.MatrixFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinateMatrixTest {

    @Test
    public void testGet() throws IOException {
        String fileName = "mycielskian3.mtx";
        CoordinateMatrix coordinateMatrix = MatrixFileReader.readMatrixFromFile(fileName);
        int numRows = coordinateMatrix.getNumRows();
        int numCols = coordinateMatrix.getNumCols();
        List<Coordinate> entries = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                entries.addAll(coordinateMatrix.getEntries());
                System.out.println(coordinateMatrix.getEntries());
            }
        }
        CoordinateMatrix matrix = new CoordinateMatrix(2, 2, entries);

        assertEquals(1.0, matrix.getValue(2, 1));
        assertEquals(2.0, matrix.getValue(4, 1));
        assertEquals(3.0, matrix.getValue(3, 2));
        assertEquals(4.0, matrix.getValue(5, 3));
        assertEquals(5.0, matrix.getValue(5, 4));
    }
}
