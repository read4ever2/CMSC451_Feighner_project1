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
  }

  public static void main(String[] args) {

    BenchmarkSorts benchmarkSortsObject = new BenchmarkSorts();
    benchmarkSortsObject.setDataSource(benchmarkSortsObject.generateData(benchmarkSortsObject.baseNValue * 10));

    benchmarkSortsObject.setFinalRecursiveReportOutput(benchmarkSortsObject.recursiveDataSort(benchmarkSortsObject.dataSource));
    benchmarkSortsObject.setFinalIterativeReportOutput(benchmarkSortsObject.iterativeDataSort(benchmarkSortsObject.dataSource));

    System.out.println(Arrays.deepToString(benchmarkSortsObject.getFinalRecursiveReportOutput()));
    System.out.println(Arrays.deepToString(benchmarkSortsObject.getFinalIterativeReportOutput()));
  }

  private long[][] getFinalIterativeReportOutput() {
    return finalIterativeReportOutput;
  }

  private void setFinalIterativeReportOutput(long[][] finalIterativeReportOutput) {
    this.finalIterativeReportOutput = finalIterativeReportOutput;
  }

  public long[][] iterativeDataSort(int[][] data) {
    long[][] draftOutputReport = new long[10][101];
    int reportRowNumber = 10;
    MergeSort mergeSortObject = new MergeSort();
    mergeSortObject.resetTime();
    mergeSortObject.resetCount();
    for (int i = 1; i <= reportRowNumber; i++) {
      draftOutputReport[i - 1][0] = i * baseNValue;
      try {
        for (int j = 1; j <= data.length; j++) {
          mergeSortObject.resetCount();
          mergeSortObject.resetTime();
          mergeSortObject.setStart();
          mergeSortObject.iterativeSort(Arrays.copyOfRange(data[j - 1], 0, i * baseNValue));
          mergeSortObject.setStop();
          draftOutputReport[i - 1][(j * 2) - 1] = mergeSortObject.getCount();
          draftOutputReport[i - 1][(j * 2)] = mergeSortObject.getTime();
        }

      } catch (UnsortedException e) {
        throw new RuntimeException(e);
      }
    }
    return draftOutputReport;
  }

  public long[][] recursiveDataSort(int[][] data) {
    long[][] draftOutputReport = new long[10][101];
    int reportRowNumber = 10;
    MergeSort mergeSortObject = new MergeSort();
    mergeSortObject.resetTime();
    mergeSortObject.resetCount();
    for (int i = 1; i <= reportRowNumber; i++) {
      draftOutputReport[i - 1][0] = i * baseNValue;
      try {
        for (int j = 1; j <= data.length; j++) {
          mergeSortObject.resetCount();
          mergeSortObject.resetTime();
          mergeSortObject.setStart();
          mergeSortObject.recursiveSort(Arrays.copyOfRange(data[j - 1], 0, i * baseNValue));
          mergeSortObject.setStop();
          draftOutputReport[i - 1][(j * 2) - 1] = mergeSortObject.getCount();
          draftOutputReport[i - 1][(j * 2)] = mergeSortObject.getTime();
        }
      } catch (UnsortedException e) {
        throw new RuntimeException(e);
      }
    }
    return draftOutputReport;
  }

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