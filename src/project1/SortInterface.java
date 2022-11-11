/*
 * Filename: BenchmarkSorts.java
 * Author: Will Feighner
 * Date: 2022 11 06
 * Purpose: This program analyses the efficiency of iterative and recursive versions
 * the Merge Sort algorithm by generating random data to be sorted, timing
 * how long it takes, and counting the number operations the algorithm takes.
 */

package project1;

public interface SortInterface {
  int[] recursiveSort(int[] list) throws UnsortedException;

  int[] iterativeSort(int[] list) throws UnsortedException;

  int getCount();

  long getTime();
}
