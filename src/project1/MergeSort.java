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
import java.util.concurrent.atomic.AtomicInteger;

public class MergeSort implements SortInterface {

  private final AtomicInteger counter = new AtomicInteger();
  private long start;
  private long stop;

  public MergeSort() {
    this.start = 0;
    this.stop = 0;
    this.counter.set(0);
  }

  /**
   * @param list Input arrays of increasing sizes
   * @param temp Temporary array for copying
   * @param from Beginning index of comparisons in array
   * @param mid  Mid-point index of array length to be merged
   * @param to   ENd index of subsection of array to be compared
   * @return Sorted subarray for inclusion in larger subarray sorting
   */
  public int[] iterativeMerge(int[] list, int[] temp, int from, int mid, int to) {
    int k = from, i = from, j = mid + 1;
    // Loop to compare the lowest remaining value in left and right sorted sub-arrays
    // The lowest value is copied into the next spot in the temporary array
    while (i <= mid && j <= to) {
      if (list[i] < list[j]) {
        temp[k++] = list[i++];
      } else {
        temp[k++] = list[j++];
      }
      counter.getAndIncrement();
    }

    // Once one sub-array is exhausted, copy the remaining elements from the other array
    while (i < list.length && i <= mid) {
      temp[k++] = list[i++];
      counter.getAndIncrement();
    }

    // Copy sorted temporary array back to original locations of adjacent left and right source arrays
    for (i = from; i <= to; i++) {
      list[i] = temp[i];
      counter.getAndIncrement();
    }
    return list;
  }

  /**
   * @param list Array of values to sort
   * @return Sorted array
   */
  public int[] iterativeMergeSort(int[] list) {

    // Indexes of start and end of array
    int low = 0;
    int high = list.length - 1;

    // Copy array into a temporary working array
    int[] temp = Arrays.copyOf(list, list.length);

    // Break array into smaller sub-arrays to compare and merge
    // Ex, compare elements [0] and [1], the lower value goes to [0] and higher goes to [1]
    // Then same for [2] and [3], then compare [0], [1], [2] and [3] and so on
    // m = [1, 2, 4, 8, 16…]
    int[] outputList = new int[0];
    for (int m = 1; m <= high - low; m = 2 * m) {
      // for m = 1, i = 0, 2, 4, 6, 8 …
      // for m = 2, i = 0, 4, 8, 12 …
      // for m = 4, i = 0, 8, 16 …
      // …

      for (int i = low; i < high; i += 2 * m) {
        int mid = i + m - 1;
        int to = Integer.min(i + 2 * m - 1, high);
        counter.getAndIncrement();
        outputList = iterativeMerge(list, temp, i, mid, to);
      }
    }
    return outputList;
  }

  /**
   * @param array Sub-array to sort, merge
   * @param low   Index of the lowest value
   * @param mid   Index of the Mid-point
   * @param high  Index of the highest value
   * @return Sorted sub-array
   */
  public int[] recursiveMerge(int[] array, int low, int mid, int high) {
    // Creating temporary subArrays to merge
    int[] leftArray = new int[mid - low + 1];
    int[] rightArray = new int[high - mid];

    // Copying our subArrays into temporaries
    System.arraycopy(array, low, leftArray, 0, leftArray.length);
    Arrays.setAll(rightArray, i -> array[mid + i + 1]);

    // Iterators containing current index of temp subArrays
    int leftIndex = 0;
    int rightIndex = 0;

    // Copying from leftArray and rightArray back into array
    for (int i = low; i < high + 1; i++) {
      // If there are still un-copied elements in Right and Left, copy the lowest of the two into
      // return array
      if (leftIndex < leftArray.length && rightIndex < rightArray.length) {
        if (leftArray[leftIndex] < rightArray[rightIndex]) {
          array[i] = leftArray[leftIndex];
          leftIndex++;
        } else {
          array[i] = rightArray[rightIndex];
          rightIndex++;
        }
        counter.getAndIncrement();
      } else if (leftIndex < leftArray.length) {
        // If all elements have been copied from rightArray, copy rest of leftArray
        array[i] = leftArray[leftIndex];
        leftIndex++;
        counter.getAndIncrement();
      } else if (rightIndex < rightArray.length) {
        // If all elements have been copied from leftArray, copy rest of rightArray
        array[i] = rightArray[rightIndex];
        rightIndex++;
        counter.getAndIncrement();
      }
    }
    return array;
  }

  public void setStart() {
    start = System.nanoTime();
  }

  public void setStop() {
    stop = System.nanoTime();
  }

  /**
   * @param array Array to split in half, sort and merge
   * @param low   Index of the start of the array
   * @param high  Index of the end of the array
   * @return Merged, sorted sub-array
   */
  public int[] recursiveMergeSort(int[] array, int low, int high) {
    counter.getAndIncrement();
    // Recursion base case, return if array length is 1
    if (high <= low) {
      return array;
    }

    int mid = (low + high) / 2;
    // Recursive calls to split arrays in half
    recursiveMergeSort(array, low, mid);
    recursiveMergeSort(array, mid + 1, high);
    // Merge returned sub-arrays
    return recursiveMerge(array, low, mid, high);
  }

  /**
   * Recursive Caller
   *
   * @param list Array to be sorted
   * @return Sorted array
   * @throws UnsortedException if array is not sorted
   */
  @Override
  public int[] recursiveSort(int[] list) throws UnsortedException {
    int low = 0;
    int high = list.length - 1;
    counter.getAndIncrement();
    return recursiveMergeSort(list, low, high);
  }

  /**
   * Iterative caller
   *
   * @param list Array to sorted
   * @return Sorted array
   * @throws UnsortedException
   */
  @Override
  public int[] iterativeSort(int[] list) throws UnsortedException {
    return iterativeMergeSort(list);
  }

  @Override
  public int getCount() {
    return counter.get();
  }

  public void resetCount() {
    counter.set(0);
  }

  /**
   * @return Time duration in nanoseconds from stop-start
   */
  @Override
  public long getTime() {
    return stop - start;
  }

  public void resetTime() {
    start = 0;
    stop = 0;
  }
}
