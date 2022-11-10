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

  private final int baseNValue = 100;
  private final int sortRuns = 50;
  private long[][] finalReportOutput;

  private int[][] dataAmount10;

  public BenchmarkSorts() {
    finalReportOutput = new long[10][101];
    dataAmount10 = new int[sortRuns][baseNValue * 10];
  }

  public static void main(String[] args) {


    BenchmarkSorts benchmarkSortsObject = new BenchmarkSorts();
    benchmarkSortsObject.setDataAmount10(benchmarkSortsObject.generateData(benchmarkSortsObject.baseNValue * 10));

/*
    int[] inputList = new int[50];
    Random random = new Random();
    for (int i = 0; i < 50; i++) {
      inputList[i] = random.nextInt();
    }
    System.out.println(Arrays.toString(inputList));
    MergeSort mergeSortObject = new MergeSort();
    int[] outputList;

    try {
      outputList = mergeSortObject.recursiveSort(inputList);
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


    try {
      outputList = mergeSortObject.iterativeSort(inputList);
    } catch (UnsortedException unsortedException) {
      throw new RuntimeException(unsortedException);
    }
    System.out.println(Arrays.toString(outputList));
*/

    benchmarkSortsObject.setFinalReportOutput(benchmarkSortsObject.sortData(benchmarkSortsObject.dataAmount10));

    System.out.println(Arrays.deepToString(benchmarkSortsObject.getFinalReportOutput()));
  }

  public long[][] sortData(int[][] data) {
    long[][] draftOutputReport = new long[10][101];
    int reportRowNumber = 10;
    MergeSort mergeSortObject = new MergeSort();
    mergeSortObject.resetTime();
    mergeSortObject.resetCount();
    int countSum = 0;
    long timeSum = 0;
    for (int i = 0; i < reportRowNumber; i++) {
      draftOutputReport[i][0] = i * 100;
      try {
        for (int j = 1; j <= data.length; j++) {
          mergeSortObject.resetCount();
          mergeSortObject.resetTime();
          mergeSortObject.setStart();
          mergeSortObject.recursiveSort(Arrays.copyOfRange(data[j],0,i*100));
          mergeSortObject.setStop();
          draftOutputReport[i][j * 2] = mergeSortObject.getCount();
          draftOutputReport[i][(j * 2) + 1] = mergeSortObject.getTime();
        }

      } catch (UnsortedException e) {
        throw new RuntimeException(e);
      }
    }
    return draftOutputReport;
  }

  public long[][] getFinalReportOutput() {
    return finalReportOutput;
  }

  public void setFinalReportOutput(long[][] finalReportOutput) {
    this.finalReportOutput = finalReportOutput;
  }


  public int[][] getDataAmount10(int rows) {
    int[][] returnArray = new int[rows][sortRuns];
    for (int i = 0; i < rows; i++) {
      System.arraycopy(dataAmount10[i], 0, returnArray[i], 0, sortRuns);
    }
    return returnArray;
  }

  public void setDataAmount10(int[][] dataAmount10) {
    this.dataAmount10 = dataAmount10;
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