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

public class BenchmarkSorts {
  public static void main(String[] args) {
    System.out.println("Hello world!");
    int[] list={5,4,3,2,1};
    System.out.println(Arrays.toString(list));
    MergeSort.recursiveMergeSort(list, 0, 4);
    System.out.println(Arrays.toString(list));
  }
}