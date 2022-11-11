/*
 * Filename: BenchmarkSorts.java
 * Author: Will Feighner
 * Date: 2022 11 06
 * Purpose: This program analyses the efficiency of iterative and recursive versions
 * the Merge Sort algorithm by generating random data to be sorted, timing
 * how long it takes, and counting the number operations the algorithm takes.
 */

package project1;

// Imports

import java.util.Arrays;
import java.util.Random;

public class BenchmarkSorts {

  // Fields
  private final int baseNValue = 10000;
  private final int sortRuns = 50;
  private long[][] finalIterativeReportOutput;
  private long[][] finalRecursiveReportOutput;
  private int[][] dataSource;

  public BenchmarkSorts() {
    finalRecursiveReportOutput = new long[10][2 * sortRuns + 1];
    finalIterativeReportOutput = new long[10][2 * sortRuns + 1];
    dataSource = new int[sortRuns][baseNValue * 10];
  } // End constructor

  public static void main(String[] args) {

    BenchmarkSorts benchmarkSortsObject = new BenchmarkSorts();
    benchmarkSortsObject.setDataSource(benchmarkSortsObject.generateData(benchmarkSortsObject.baseNValue * 10));

    benchmarkSortsObject.setFinalRecursiveReportOutput(benchmarkSortsObject.recursiveDataSort(benchmarkSortsObject.dataSource));
    benchmarkSortsObject.setFinalIterativeReportOutput(benchmarkSortsObject.iterativeDataSort(benchmarkSortsObject.dataSource));

    System.out.println(Arrays.deepToString(benchmarkSortsObject.getFinalRecursiveReportOutput()));
    System.out.println(Arrays.deepToString(benchmarkSortsObject.getFinalIterativeReportOutput()));
  } // End main

  private long[][] getFinalIterativeReportOutput() {
    return finalIterativeReportOutput;
  }

  private void setFinalIterativeReportOutput(long[][] finalIterativeReportOutput) {
    this.finalIterativeReportOutput = finalIterativeReportOutput;
  }

  /**
   * @param data 2D array containing 50 rows of 10000 random integers
   * @return 2D array containing number of values sorted (n) and 50 pairs of count and time to sort each run of n
   */
  public long[][] iterativeDataSort(int[][] data) {
    // Declare array for report data
    int reportRowNumber = 10;
    long[][] draftOutputReport = new long[reportRowNumber][2 * sortRuns + 1];

    // Prep sorting object
    MergeSort mergeSortObject = new MergeSort();
    mergeSortObject.resetTime();
    mergeSortObject.resetCount();

    // For each row of the output report, sort 50 runs of random data of a defined length
    for (int i = 1; i <= reportRowNumber; i++) {
      // Populate report array with number of elements sorted for that row
      draftOutputReport[i - 1][0] = i * baseNValue;
      try {
        // For each row of random source data
        for (int j = 1; j <= data.length; j++) {
          mergeSortObject.resetCount();
          mergeSortObject.resetTime();
          // Declare separate array to sort via Java to compare to manual sort
          int[] sortCheck = Arrays.copyOfRange(data[j - 1], 0, i * baseNValue);
          Arrays.sort(sortCheck);
          // Start timer
          mergeSortObject.setStart();
          // Sort portion of 10000 source data elements, depending on amount to sort for that report row
          int[] sortedArray = mergeSortObject.iterativeSort(Arrays.copyOfRange(data[j - 1], 0, i * baseNValue));
          // Stop timer and compare Java and manual sorted array
          sortCheck(draftOutputReport, mergeSortObject, i, j, sortCheck, sortedArray);
        } // End inner for loop
      } catch (UnsortedException e) {
        throw new RuntimeException(e);
      }
    } // End outer for loop
    return draftOutputReport;
  } // End iterativeDataSort

  /**
   * @param draftOutputReport Data for output report
   * @param mergeSortObject   Sorting object
   * @param i                 output report row number
   * @param j                 row of data to be sorted
   * @param sortCheck         Java sorted array to compare
   * @param sortedArray       Manual sorted array to compare
   * @throws UnsortedException Exception if sorted arrays do not match
   */
  private void sortCheck(long[][] draftOutputReport, MergeSort mergeSortObject, int i, int j, int[] sortCheck, int[] sortedArray) throws UnsortedException {
    mergeSortObject.setStop();
    draftOutputReport[i - 1][(j * 2) - 1] = mergeSortObject.getCount();
    draftOutputReport[i - 1][(j * 2)] = mergeSortObject.getTime();
    if (!Arrays.equals(sortCheck, sortedArray)) {
      throw new UnsortedException("Array is unsorted");
    }
  }

  /**
   * @param data 2D array containing 50 rows of 10000 random integers
   * @return 2D array containing number of values sorted (n) and 50 pairs of count and time to sort each run of n
   */
  public long[][] recursiveDataSort(int[][] data) {
    // Declare array for report data
    int reportRowNumber = 10;
    long[][] draftOutputReport = new long[reportRowNumber][2 * sortRuns + 1];

    // Prep sorting object
    MergeSort mergeSortObject = new MergeSort();
    mergeSortObject.resetTime();
    mergeSortObject.resetCount();
    // For each row of the output report, sort 50 runs of random data of a defined length
    for (int i = 1; i <= reportRowNumber; i++) {
      // Populate report array with number of elements sorted for that row
      draftOutputReport[i - 1][0] = i * baseNValue;
      try {
        // For each row of random source data
        for (int j = 1; j <= data.length; j++) {
          mergeSortObject.resetCount();
          mergeSortObject.resetTime();
          // Declare separate array to sort via Java to compare to manual sort
          int[] sortCheck = Arrays.copyOfRange(data[j - 1], 0, i * baseNValue);
          Arrays.sort(sortCheck);
          // Start timer
          mergeSortObject.setStart();
          // Sort portion of 10000 source data elements, depending on amount to sort for that report row

          int[] sortedArray = mergeSortObject.recursiveSort(Arrays.copyOfRange(data[j - 1], 0, i * baseNValue));
          // Stop timer and compare Java and manual sorted array
          sortCheck(draftOutputReport, mergeSortObject, i, j, sortCheck, sortedArray);
        } // End inner for loop
      } catch (UnsortedException e) {
        throw new RuntimeException(e);
      }
    } // End outer for loop
    return draftOutputReport;
  } // End iterativeDataSort

  public long[][] getFinalRecursiveReportOutput() {
    return finalRecursiveReportOutput;
  }

  public void setFinalRecursiveReportOutput(long[][] finalRecursiveReportOutput) {
    this.finalRecursiveReportOutput = finalRecursiveReportOutput;
  }

  public void setDataSource(int[][] dataSource) {
    this.dataSource = dataSource;
  }

  private int[][] generateData(int length) {

    Random random = new Random();
    int[][] dataArray = new int[sortRuns][length];
    for (int i = 0; i < sortRuns; i++) {
      for (int j = 0; j < length; j++) {
        dataArray[i][j] = random.nextInt();
      }
    }
    return dataArray;
  }
}