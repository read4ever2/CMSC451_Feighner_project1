package project1;

public interface SortInterface {
  public int[] recursiveSort(int[] list, int low, int high) throws UnsortedException;

  public int[] iterativeSort(int[] list) throws UnsortedException;

  public int getCount();

  public long getTime();
}
