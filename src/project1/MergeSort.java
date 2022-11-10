package project1;

import java.util.Arrays;

public class MergeSort implements SortInterface {

  private int counter;
  private long start;
  private long stop;

  public MergeSort() {
    this.start = 0;
    this.stop = 0;
    this.counter = 0;
  }

  public static int[] recursiveMerge(int[] array, int low, int mid, int high) {
    // Creating temporary subArrays
    int[] leftArray = new int[mid - low + 1];
    int[] rightArray = new int[high - mid];

    // Copying our subArrays into temporaries
    System.arraycopy(array, low, leftArray, 0, leftArray.length);
    for (int i = 0; i < rightArray.length; i++)
      rightArray[i] = array[mid + i + 1];

    // Iterators containing current index of temp subArrays
    int leftIndex = 0;
    int rightIndex = 0;

    // Copying from leftArray and rightArray back into array
    for (int i = low; i < high + 1; i++) {
      // If there are still un-copied elements in R and L, copy minimum of the two
      if (leftIndex < leftArray.length && rightIndex < rightArray.length) {
        if (leftArray[leftIndex] < rightArray[rightIndex]) {
          array[i] = leftArray[leftIndex];
          leftIndex++;
        } else {
          array[i] = rightArray[rightIndex];
          rightIndex++;
        }
      } else if (leftIndex < leftArray.length) {
        // If all elements have been copied from rightArray, copy rest of leftArray
        array[i] = leftArray[leftIndex];
        leftIndex++;
      } else if (rightIndex < rightArray.length) {
        // If all elements have been copied from leftArray, copy rest of rightArray
        array[i] = rightArray[rightIndex];
        rightIndex++;
      }
    }
    return array;
  }

  public static int[] iterativeMerge(int[] list, int[] temp, int from, int mid, int to) {
    int k = from, i = from, j = mid + 1;
    // loop till no elements are left in the left and right runs
    while (i <= mid && j <= to) {
      if (list[i] < list[j]) {
        temp[k++] = list[i++];
      } else {
        temp[k++] = list[j++];
      }
    }

    // copy remaining elements
    while (i < list.length && i <= mid) {
      temp[k++] = list[i++];
    }
         /* no need to copy the second half (since the remaining items
           are already in their correct position in the temporary array) */
    // copy back to the original array to reflect sorted order
    for (i = from; i <= to; i++) {
      list[i] = temp[i];
    }
    return list;
  }

  // Iteratively sort subarray `A[low…high]` using a temporary array
  public static int[] iterativeMergeSort(int[] list) {
    int low = 0;
    int high = list.length - 1;

    // sort array `A[]` using a temporary array `temp`
    int[] temp = Arrays.copyOf(list, list.length);

    // divide the array into blocks of size `m`
    // m = [1, 2, 4, 8, 16…]
    for (int m = 1; m <= high - low; m = 2 * m) {
      // for m = 1, i = 0, 2, 4, 6, 8 …
      // for m = 2, i = 0, 4, 8, 12 …
      // for m = 4, i = 0, 8, 16 …
      // …
      for (int i = low; i < high; i += 2 * m) {
        int mid = i + m - 1;
        int to = Integer.min(i + 2 * m - 1, high);

        iterativeMerge(list, temp, i, mid, to);
      }
    }
    return list;
  }

  public long getStart() {
    return start;
  }

  public void setStart() {
    start = System.nanoTime();
  }

  public long getStop() {
    return stop;
  }

  public void setStop() {
    stop = System.nanoTime();
  }

  /**
   * @param array array passed to be sorted
   * @param low   index of low end of array
   * @param high  index of high end of array
   */
  public int[] recursiveMergeSort(int[] array, int low, int high) {
    counter++;
    if (high <= low) {
      return array;
    }

    int mid = (low + high) / 2;
    recursiveMergeSort(array, low, mid);
    recursiveMergeSort(array, mid + 1, high);
    return recursiveMerge(array, low, mid, high);
  }

  @Override
  public int[] recursiveSort(int[] list) throws UnsortedException {
    int low = 0;
    int high = list.length - 1;
    return recursiveMergeSort(list, low, high);
  }

  @Override
  public int[] iterativeSort(int[] list) throws UnsortedException {
    iterativeMergeSort(list);
    return list;
  }

  @Override
  public int getCount() {
    return counter;
  }

  public void resetCount() {
    counter = 0;
  }

  @Override
  public long getTime() {
    return stop - start;
  }

  public void resetTime() {
    start = 0;
    stop = 0;
  }
}
