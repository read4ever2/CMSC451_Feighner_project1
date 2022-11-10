package project1;

public interface SortInterface {
  int[] recursiveSort(int[] list) throws UnsortedException;

  int[] iterativeSort(int[] list) throws UnsortedException;

  int getCount();

  long getTime();
}
