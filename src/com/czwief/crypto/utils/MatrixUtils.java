package com.czwief.crypto.utils;

/**
 *
 * @author cody
 */
public class MatrixUtils {

    /**
     * Transpose a 2D matrix.
     *
     * @param originalMatrix
     * @return
     */
    public static byte[][] transpose(final byte[][] originalMatrix) {
        byte[][] retVal = new byte[originalMatrix[0].length][originalMatrix.length];
        for (int i = 0; i < originalMatrix.length; i++) {
            for (int j = 0; j < originalMatrix[0].length; j++) {
                retVal[j][i] = originalMatrix[i][j];
            }
        }
        return retVal;
    }
    
}
