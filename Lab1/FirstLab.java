public class FirstLab {
    public static class Complex {
        private double real;
        private double imag;
        public double getReal() {
            return real;
        }
        public double getImag() {
            return imag;
        }
        public Complex(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }
        public Complex add(Complex other) {
            return new Complex(real + other.real, imag + other.imag);
        }
        public Complex subtract(Complex other) {
            return new Complex(real - other.real, imag - other.imag);
        }
        public Complex multiply(Complex other) {
            return new Complex(real * other.real - imag * other.imag, real * other.imag + imag * other.real);
        }
        public Complex inverse() {
            double denom = real * real + imag * imag;
            if (denom == 0) {
                System.out.print("Error: The denominator cannot be zero");
            }
            return new Complex(real / denom, -imag / denom);
        }
        public String toString() {
            if (imag < 0) {
                return real + "-" + Math.abs(imag) + "i";
            }
            else {
                return real + "+" + imag + "i";
            }
        }
    }

    public static class Matrix {
        private int rows;
        private int columns;
        private Complex[][] data;
        public int getRows() {
            return rows;
        }
        public int getColumns() {
            return columns;
        }
        public Complex getValue(int row, int column) {
            return data[row][column];
        }
        public Matrix(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
            data = new Complex[rows][columns];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    data[i][j] = new Complex(0, 0);
                }
            }
        }
        public Matrix(Complex[][] data) {
            this.rows = data.length;
            this.columns = data[0].length;
            this.data = new Complex[rows][columns];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    this.data[i][j] = new Complex(data[i][j].getReal(), data[i][j].getImag());
                }
            }
        }
        public void insert(int row, int column, Complex value) {
            data[row][column] = value;
        }
        public Matrix add(Matrix other) {
            if ((rows != other.rows) || (columns != other.columns)) {
                System.out.print("Error: Different number of rows/columns in the matrices");
            }
            Matrix res = new Matrix(rows, columns);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    res.insert(i, j, data[i][j].add(other.getValue(i, j)));
                }
            }
            return res;
        }
        public Matrix subtract(Matrix other) {
            if ((rows != other.rows) || (columns != other.columns)) {
                System.out.print("Error: Different number of rows/columns in the matrices");
            }
            Matrix res = new Matrix(rows, columns);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    res.insert(i, j, data[i][j].subtract(other.getValue(i, j)));
                }
            }
            return res;
        }
        public Matrix multiply(Matrix other) {
            if (columns != other.rows) {
                System.out.print("Error: The number of columns in the first matrix must be equal to the number of rows in the second matrix");
            }
            Matrix res = new Matrix(rows, other.columns);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < other.columns; j++) {
                    Complex num = new Complex(0, 0);
                    for (int k = 0; k < columns; k++) {
                        num = num.add(data[i][k].multiply(other.getValue(k, j)));
                    }
                    res.insert(i, j, num);
                }
            }
            return res;
        }
        public Matrix transpose() {
            Matrix res = new Matrix(columns, rows);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    res.insert(j, i, data[i][j]);
                }
            }
            return res;
        }
        public Complex det() {
            if (rows != columns) {
                System.out.print("Error: The matrix must be square");
            }
            if (rows == 1) {
                return data[0][0];
            }
            else {
                if (rows == 2) {
                    return data[0][0].multiply(data[1][1]).subtract(data[0][1].multiply(data[1][0]));
                }
                else {
                    Complex det = new Complex(0, 0);
                    for (int j = 0; j < columns; j++) {
                        Matrix submatrix = new Matrix(rows - 1, columns - 1);
                        for (int i = 1; i < rows; i++) {
                            for (int k = 0; k < j; k++) {
                                submatrix.insert(i - 1, k, data[i][k]);
                            }
                            for (int k = j + 1; k < columns; k++) {
                                submatrix.insert(i - 1, k - 1, data[i][k]);
                            }
                        }
                        det = det.add(data[0][j].multiply(new Complex(Math.pow(-1, j), 0)).multiply(submatrix.det()));
                    }
                    return det;
                }
            }

        }
        public Matrix inverse() {
            if (rows != columns) {
                System.out.print("Error: The matrix must be square");
            }
            Complex det = det();
            if (det.getReal() == 0 && det.getImag() == 0) {
                System.out.print("Error: The determinant of the matrix must not be equal to zero");
            }
            Matrix adj = adjoint();
            Complex invDet = det.inverse();
            Matrix inverseMatrix = new Matrix(rows, columns);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    inverseMatrix.insert(i, j, invDet.multiply(adj.getValue(i, j)));
                }
            }
            return inverseMatrix;
        }
        public Matrix adjoint() {
            if (rows != columns) {
                System.out.print("Error: The matrix must be square");
            }
            Matrix result = new Matrix(rows, columns);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    Matrix minor = new Matrix(rows - 1, columns - 1);
                    for (int k = 0; k < i; k++) {
                        for (int l = 0; l < j; l++) {
                            minor.insert(k, l, data[k][l]);
                        }
                        for (int l = j + 1; l < columns; l++) {
                            minor.insert(k, l - 1, data[k][l]);
                        }
                    }
                    for (int k = i + 1; k < rows; k++) {
                        for (int l = 0; l < j; l++) {
                            minor.insert(k - 1, l, data[k][l]);
                        }
                        for (int l = j + 1; l < columns; l++) {
                            minor.insert(k - 1, l - 1, data[k][l]);
                        }
                    }
                    result.insert(i, j, minor.det().multiply(new Complex(Math.pow(-1, i + j), 0)));
                }
            }
            return result.transpose();
        }
        public Matrix divide(Matrix other) {
            if (columns != other.rows) {
                System.out.print("Error: The number of columns in the first matrix must be equal to the number of rows in the second matrix");
            }
            return multiply(other.inverse());
        }
        public String toString() {
            StringBuilder strbuild = new StringBuilder();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    strbuild.append(data[i][j].toString()).append(" ");
                }
                strbuild.append("\n");
            }
            return strbuild.toString();
        }
    }
    public static void main(String[] arguments) {
        Complex[][] data1 = {
            {new Complex(9, 10), new Complex(11, 12)},
            {new Complex(13, 14), new Complex(15, 16)}
        };
        Matrix matrix1 = new Matrix(data1);
        Complex[][] data2 = {
            {new Complex(17, 18), new Complex(19, 20)},
            {new Complex(21, 22), new Complex(23, 24)}
        };
        Matrix matrix2 = new Matrix(data2);
        System.out.println(matrix1);
        System.out.println(matrix2);
        System.out.println(matrix1.add(matrix2));
        System.out.println(matrix1.subtract(matrix2));
        System.out.println(matrix1.multiply(matrix2));
        System.out.println(matrix1.transpose());
        System.out.println(matrix2.transpose());
        System.out.println(matrix1.det() + "\n");
        System.out.println(matrix2.det() + "\n");
        System.out.println(matrix1.divide(matrix2));
    }
}