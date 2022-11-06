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
      inputList[i] = random.nextInt();
    }
    System.out.println(Arrays.toString(inputList));
    MergeSort mergeSortObject = new MergeSort();
    int[] outputList;
    try {
      outputList = mergeSortObject.recursiveSort(inputList, 0, inputList.length - 1);
    } catch (UnsortedException e) {
      throw new RuntimeException(e);
    }
    System.out.println(Arrays.toString(outputList));
    System.out.println(mergeSortObject.getCount());
    BenchmarkSorts benchmarkSortsObject = new BenchmarkSorts();
    int[][] data = benchmarkSortsObject.generateData(1000);
    long[] timeArray = new long[50];
    for (int i = 0; i < 50; i++) {
      try {
        mergeSortObject.resetCount();
        mergeSortObject.resetTime();
        mergeSortObject.setStart();
        mergeSortObject.recursiveSort(data[i], 0, data[i].length - 1);
        mergeSortObject.setStop();
        System.out.println(mergeSortObject.getCount());
        System.out.println(mergeSortObject.getTime());
        timeArray[i] = mergeSortObject.getTime();
      } catch (UnsortedException e) {
        throw new RuntimeException(e);
      }
    }
    long sum = 0;
    for (int i = 0; i < 50; i++) {
      sum += timeArray[i];
    }
    double average = 1.0d * sum / 50;
    System.out.println(average);
  }

  private int[][] generateData(int length) {
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