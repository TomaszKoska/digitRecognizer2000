package helper;

import org.nd4j.linalg.api.ndarray.INDArray;

public class GeneralHelper {
	public static int findIndexOfMax(INDArray array) {
    	int outcome = 0;
    	for(int i =1; i<array.length();i++) {
    		if(array.ravel().getFloat(outcome) < array.ravel().getFloat(i)) {
    			outcome = i;
    		}
    	}
    	return outcome;
    }
    
    public static void printWithBreaks(INDArray array, int inRow) {
    	for(int i =1; i<array.length();i++) {
    		System.out.print(array.ravel().getFloat(i) + ",");
    		if(i % 28 ==0) {
    			System.out.print("\n");
    		}
    	}
    }
}
