package software.ulpgc.bigdata.algebra.matrices.longint.reader;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CoordinateMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders.CoordinateBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatrixFileReader {

    public static CoordinateMatrix readMatrixFromFile(String fileName) throws IOException {
        CoordinateBuilder builder = new CoordinateBuilder();
        List<Coordinate> coordinates = new ArrayList<>();
        int numRows = 0;
        int numCols = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("%") || line.startsWith("%%")) {
                    continue;
                }

                String[] sizeParts = line.split("\\s+");
                if (sizeParts.length >= 3) {
                    numRows = Integer.parseInt(sizeParts[0]);
                    numCols = Integer.parseInt(sizeParts[1]);
                    int totalValues = Integer.parseInt(sizeParts[2]);
                    break;
                }
            }

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("%") || line.startsWith("%%")) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                if (parts.length >= 3) {
                    int row = Integer.parseInt(parts[0]);
                    int col = Integer.parseInt(parts[1]);
                    double value = Double.parseDouble(parts[2]);
                    coordinates.add(new Coordinate(row, col, value));
                }
            }
        }

        coordinates.sort((c1, c2) -> {
            int rowCompare = Integer.compare(c1.getRow(), c2.getRow());
            if (rowCompare != 0) {
                return rowCompare;
            }
            return Integer.compare(c1.getCol(), c2.getCol());
        });

        for (Coordinate coordinate : coordinates) {
            builder.addEntry(coordinate.getRow(), coordinate.getCol(), coordinate.getValue());
        }

        builder.setMatrixSize(numRows, numCols);
        return builder.build();
    }

    public static double[][] convertToDoubleArray(CoordinateMatrix matrix) {
        int maxRow = 0;
        int maxCol = 0;

        for (Coordinate coordinate : matrix.getEntries()) {
            maxRow = Math.max(maxRow, coordinate.getRow());
            maxCol = Math.max(maxCol, coordinate.getCol());
        }

        int numRows = maxRow + 1;
        int numCols = maxCol + 1;
        double[][] result = new double[numRows][numCols];

        for (Coordinate coordinate : matrix.getEntries()) {
            int row = coordinate.getRow();
            int col = coordinate.getCol();
            double value = coordinate.getValue();
            result[row][col] = value;
        }

        return result;
    }
}
