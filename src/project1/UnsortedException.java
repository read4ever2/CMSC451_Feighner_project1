package project1;

public class UnsortedException extends Exception {
  public UnsortedException(String error) {
    super(error);
    System.out.println(error);
  }
}
