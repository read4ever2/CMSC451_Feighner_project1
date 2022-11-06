package project1;

/*
 * Filename: BenchmarkSorts.java
 * Author: Will Feighner
 * Date: 2022 11 06
 * Purpose: This program analyses the efficiency of iterative and recursive versions
 * the Merge Sort algorithm by generating random sata to be sorted and timing
 * how long it takes.
 */

import java.util.Arrays;
import java.util.Random;

public class BenchmarkSorts {
  public static void main(String[] args) {
    System.out.println("Hello world!");
    int[] inputList = new int[50];
    Random random = new Random();
    for (int i = 0; i < 50; i++) {
      inputList[i]= random.nextInt();
    }
    System.out.println(Arrays.toString(inputList));
    MergeSort mergeSortObject = new MergeSort();
    int[] outputList;
    try {
      outputList = mergeSortObject.recursiveSort(inputList, 0, inputList.length-1);
    } catch (UnsortedException e) {
      throw new RuntimeException(e);
    }
    System.out.println(Arrays.toString(outputList));
    System.out.println(mergeSortObject.getCounter());
    BenchmarkSorts benchmarkSortsObject = new BenchmarkSorts();
    System.out.println(Arrays.deepToString(benchmarkSortsObject.generateData(100)));
  }
  private int[][] generateData(int length){
    Random random = new Random();
    int[][] dataArray = new int[50][length];
    for (int i = 0; i < 50; i++) {
      for (int j = 0; j < length; j++) {
        dataArray[i][j] = random.nextInt();
      }
    }
    return dataArray;
  }
}