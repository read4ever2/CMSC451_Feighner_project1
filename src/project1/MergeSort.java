package project1;

import java.util.Arrays;

public class MergeSort implements SortInterface {

  /**
   * @param array array passed to be sorted
   * @param low   index of low end of array
   * @param high  index of high end of array
   */
  public static void recursiveMergeSort(int[] array, int low, int high) {
    if (high <= low) {
      return;
    }

    int mid = (low + high) / 2;
    recursiveMergeSort(array, low, mid);
    recursiveMergeSort(array, mid + 1, high);
    merge(array, low, mid, high);
  }

  public static void merge(int[] array, int low, int mid, int high) {
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

  }

  @Override
  public int[] recursiveSort(int[] list, int low, int high) throws UnsortedException {
    recursiveMergeSort(list, low, high);
    int[] javaList = list;
    Arrays.sort(javaList);
    int[] list2 = {5, 4, 3, 2, 1};
    if (!Arrays.equals(list2, javaList)) {
      throw new UnsortedException("Array is unsorted!");
    }
    return list2;
  }

  @Override
  public int[] iterativeSort(int[] list) throws UnsortedException {

    return new int[0];
  }

  @Override
  public int getCount() {
    return 0;
  }

  @Override
  public long getTime() {
    return 0;
  }

}
