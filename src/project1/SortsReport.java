/*
 * Filename: BenchmarkSorts.java
 * Author: Will Feighner
 * Date: 2022 11 12
 * Purpose: This program analyses the efficiency of iterative and recursive versions
 * the Merge Sort algorithm by generating random data to be sorted, timing
 * how long it takes, and counting the number operations the algorithm takes.
 */

package project1;

import javax.swing.*;
import java.util.Arrays;

public class SortsReport {

  // Fields
  private int[][] reportData;
  private int[][] displayData;

  public SortsReport() {
    reportData = new int[0][];
    displayData = new int[10][];
  }

  public static void main(String[] args) {

    SortsReport sortsReport = new SortsReport();

    sortsReport.setDisplayData(sortsReport.testData());
    sortsReport.displayGUI(sortsReport.getDisplayData());
  }

  /**
   * @return Sample Display Data
   */
  private int[][] testData() {
    int[][] sampleData = new int[10][5];
    for (int i = 0; i < sampleData.length; i++) {
      for (int j = 0; j < sampleData[i].length; j++) {
        sampleData[i][j] = i + j;
      }
    }
    System.out.println(Arrays.deepToString(sampleData));
    return sampleData;
  }

  /**
   * @param data Input data to display
   */
  private void displayGUI(int[][] data) {
    JFrame jFrame = new JFrame();
    JTable jTable;

    jFrame.setTitle("Benchmark Report");

    Object[][] objectData = new Object[data.length][data[0].length];

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        objectData[i][j] = data[i][j];
      }
    }
    String[] columnNames = {"Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time"};
    jTable = new JTable(objectData, columnNames);
    jTable.setBounds(30, 40, 200, 200);

    JScrollPane scrollPane = new JScrollPane(jTable);
    jFrame.add(scrollPane);
    jFrame.setSize(500, 250);
    jFrame.setLocationRelativeTo(null);
    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    jFrame.setVisible(true);

  }

  public int[][] getReportData() {
    return reportData;
  }

  public void setReportData(int[][] reportData) {
    this.reportData = reportData;
  }

  public int[][] getDisplayData() {
    return displayData;
  }

  public void setDisplayData(int[][] displayData) {
    this.displayData = displayData;
  }
}
