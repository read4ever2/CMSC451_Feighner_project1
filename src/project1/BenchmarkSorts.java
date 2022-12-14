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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

    // Create and fill object with random integers to sort
    BenchmarkSorts benchmarkSortsObject = new BenchmarkSorts();
    benchmarkSortsObject.setDataSource(benchmarkSortsObject.generateData(benchmarkSortsObject.baseNValue * 10));

    // Sort data recursively and iteratively
    benchmarkSortsObject.setFinalRecursiveReportOutput(benchmarkSortsObject.recursiveDataSort(benchmarkSortsObject.dataSource));
    benchmarkSortsObject.setFinalIterativeReportOutput(benchmarkSortsObject.iterativeDataSort(benchmarkSortsObject.dataSource));

    // Convert summary reports to strings for CSV
    String recursiveReportString = getStringBuilder(benchmarkSortsObject.finalRecursiveReportOutput);
    String iterativeReportString = getStringBuilder(benchmarkSortsObject.finalIterativeReportOutput);

    // Write CSV files
    try {
      benchmarkSortsObject.CSVWriter(recursiveReportString, "recursiveReport.csv");
      benchmarkSortsObject.CSVWriter(iterativeReportString, "iterativeReport.csv");
    } catch (IOException e) {
      throw new RuntimeException(e);
    } // End try/catch
  } // End main

  /**
   * Converts benchmark data of counts and time duration of each sort run into string form
   * to prep for writing to CSV
   *
   * @param data Report data to convert to string for writing to CSV
   * @return Data in string form for writing to CSV
   */
  private static String getStringBuilder(long[][] data) {
    StringBuilder stringBuilder = new StringBuilder();

    for (long[] datum : data) {
      for (int j = 0; j < datum.length; j++) {
        if (j == datum.length - 1) {
          stringBuilder.append(datum[j]);
        } else {
          stringBuilder.append(datum[j]).append(",");
        }
      }
      stringBuilder.append("\n");
    }
    return stringBuilder.toString();
  }

  /**
   * Writes strings of values seperated by comma to CSV file
   *
   * @param fileData string to write to file
   * @param fileName string of file name
   * @throws IOException file writing errors
   */
  public void CSVWriter(String fileData, String fileName)
          throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
    writer.write(fileData);

    writer.close();
  }

  private void setFinalIterativeReportOutput(long[][] finalIterativeReportOutput) {
    this.finalIterativeReportOutput = finalIterativeReportOutput;
  }

  /**
   * Performs a Merge sort of data via an iterative method
   * First sorts the first 1/10 of data provided, then 2/10 and so on until
   * all data is sorted on last run
   *
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
    for (int n = 0; n < 2; n++) { // run twice for warmup
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
    }
    return draftOutputReport;
  } // End iterativeDataSort

  /**
   * Verifies to see if data sorted via manual algorithms matches data sorted via Java methods
   *
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
   * Performs a Merge sort of data via a recursive method
   * First sorts the first 1/10 of data provided, then 2/10 and so on until
   * all data is sorted on last run
   *
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
    for (int n = 0; n < 2; n++) { // run twice for warmup
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
    }
    return draftOutputReport;
  } // End iterativeDataSort

  public void setFinalRecursiveReportOutput(long[][] finalRecursiveReportOutput) {
    this.finalRecursiveReportOutput = finalRecursiveReportOutput;
  }

  public void setDataSource(int[][] dataSource) {
    this.dataSource = dataSource;
  }

  /**
   * Generates 50 rows of 10000 random integers to use in sorting algorithms
   *
   * @param length number of random elements to generate for each run
   * @return 2d array of random integers
   */
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