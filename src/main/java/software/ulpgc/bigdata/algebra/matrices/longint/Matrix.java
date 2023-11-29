package software.ulpgc.bigdata.algebra.matrices.longint;

public interface Matrix {
    int size();
    long get(int i, int j);
    int getNumRows();
    int getNumCols();
    void set(int i, int j, long value);
    default double[][] toArray() {
        int numRows = getNumRows();
        int numCols = getNumCols();
        double[][] array = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                array[i][j] = get(i, j);
            }
        }

        return array;
    }
}


