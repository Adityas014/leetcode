public class Rotate_Image {

	public class Solution {
	    /*  eg:

	        1   2   3   4   5
	        6   7   8   9   10
	        11  12  13  14  15
	        16  17  18  19  20
	        21  22  23  24  25
	    */

	    public void rotate(int[][] m) { // m for matrix

	        if (m == null || m.length == 0) {
	            return;
	        }

	        // for each circle, start position is (i,i), length is rectangle size
	        // eg. above: (0,0), length=5; (1,1), length=3
	        int i = 0;
	        int length = m.length;

	        while (i < m.length / 2) {

	            int count = 0;
	            while (count < length - 1) { // @note: extra attention here "-1".
	            // while (count < length) {

	                int tmp = m[i][i + count];

	                // i+length-1: last index of this rectangle
	                // i+length-1 - count: index from backward
	                m[i][i + count] = m[i + length - 1 - count][i];
	                m[i + length - 1 - count][i] = m[i + length - 1][i + length - 1 - count];
	                m[i + length - 1][i + length - 1 - count] = m[i + count][i + length - 1];
	                m[i + count][i + length - 1] = tmp;

	                count++;
	            }

	            length -= 2; // @note: shrink each edge length by 2
	            i++; // start point moving along diagonal
	        }
	    }
	}

	// An Inplace function to rotate a N x N matrix by 90 degrees in anti-clockwise direction
	static void rotateMatrix_anticlock(int N, int mat[][])
	{
		// Consider all squares one by one
		for (int x = 0; x < N / 2; x++) {
			// Consider elements in group of 4 in
			// current square
			for (int y = x; y < N-x-1; y++) {
				// store current cell in temp variable
				int temp = mat[x][y];

				// move values from right to top
				mat[x][y] = mat[y][N-1-x];

				// move values from bottom to right
				mat[y][N-1-x] = mat[N-1-x][N-1-y];

				// move values from left to bottom
				mat[N-1-x][N-1-y] = mat[N-1-y][x];

				// assign temp to left
				mat[N-1-y][x] = temp;
			}
		}
	}

	static void rotateMatrix_clockwise(int N, int mat[][])
	{
		// Consider all squares one by one
		for (int x = 0; x < N / 2; x++) {
			// Consider elements in group of 4 in
			// current square
			for (int y = x; y < N-x-1; y++) {
				// store current cell in temp variable
				int temp = mat[x][y];

				// move values from right to top
				mat[x][y] = mat[y][N-1-x];

				// move values from bottom to right
				mat[y][N-1-x] = mat[N-1-x][N-1-y];

				// move values from left to bottom
				mat[N-1-x][N-1-y] = mat[N-1-y][x];

				// assign temp to left
				mat[N-1-y][x] = temp;
			}
		}
	}
}

//////

class Solution_diagonal {
	public void rotate(int[][] matrix) {
		transpose(matrix);
		reflect(matrix);
	}

	public void transpose(int[][] matrix) {
		int n = matrix.length;
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				int tmp = matrix[j][i];
				matrix[j][i] = matrix[i][j];
				matrix[i][j] = tmp;
			}
		}
	}

	public void reflect(int[][] matrix) {
		int n = matrix.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n / 2; j++) {
				int tmp = matrix[i][j];
				matrix[i][j] = matrix[i][n - j - 1];
				matrix[i][n - j - 1] = tmp;
			}
		}
	}
}

//////

class Solution {
    public void rotate(int[][] matrix) {
        int s = 0, n = matrix.length;
        while (s < (n >> 1)) {
            int e = n - s - 1;
            for (int i = s; i < e; ++i) {
                int t = matrix[i][e];
                matrix[i][e] = matrix[s][i];
                matrix[s][i] = matrix[n - i - 1][s];
                matrix[n - i - 1][s] = matrix[e][n - i - 1];
                matrix[e][n - i - 1] = t;
            }
            ++s;
        }
    }
}