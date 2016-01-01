package com.czwief.crypto.utils;

import org.apache.commons.lang3.Validate;

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
    
    public static byte[][] chopIntoBlocks(final byte[] input, final int blockSize) {
        Validate.isTrue(input.length % blockSize == 0, "Input of size " + input.length 
                + " not divisible by " + blockSize);
        
        final byte[][] retval = new byte[input.length / blockSize][blockSize];
        for (int i = 0; i < input.length; i++) {
            retval[i / blockSize][i % blockSize] = input[i];
        }
        
        return retval;
    }
    
}
