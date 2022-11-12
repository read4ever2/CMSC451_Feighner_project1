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

public class SortsReport extends JFrame {
  public static void main(String[] args) {
    int[][] testData = testData();
    displayGUI(testData);
  }

  private static void displayGUI(int[][] data) {
    SortsReport jFrame = new SortsReport();
    JTable jTable;

    jFrame.setTitle("Benchmark Report");

    Object[][] objectData = new Object[10][5];

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        objectData[i][j]= data[i][j];
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

  private static int[][] testData() {
    int[][] sampleData = new int[10][5];
    for (int i = 0; i < sampleData.length; i++) {
      for (int j = 0; j < sampleData[i].length; j++) {
        sampleData[i][j] = i + j;
      }
    }
    System.out.println(Arrays.deepToString(sampleData));
    return sampleData;
  }
}
