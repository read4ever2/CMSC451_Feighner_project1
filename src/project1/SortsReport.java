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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class SortsReport {

  // Fields
  private int[][] reportData;
  private double[][] displayData;

  public SortsReport() {
    reportData = new int[0][];
    displayData = new double[10][];
  }

  public static void main(String[] args) {

    SortsReport sortsReport = new SortsReport();

    sortsReport.setReportData(sortsReport.readData());
    System.out.println(Arrays.deepToString(sortsReport.getReportData()));

    double[][] tempArray = sortsReport.processData(sortsReport.getReportData());

    sortsReport.setDisplayData(sortsReport.testData());
    sortsReport.displayGUI(sortsReport.getDisplayData());
  }

  public double[][] processData(int[][] inputData) {
    double[][] returnData = new double[10][5];
    for (int i = 0; i < inputData.length; i++) {

    }
    return returnData;
  }

  private int[][] readData() {
    JFileChooser jFileChooser = new JFileChooser();
    jFileChooser.showOpenDialog(null);
    File file = new File(jFileChooser.getSelectedFile().toURI());

    // System.out.println(file);

    String[][] draftFileData = new String[10][101];

    try {
      Scanner scanner = new Scanner(file);

      int counter = 0;
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        draftFileData[counter] = line.split(",");
        counter++;
      }
    } catch (FileNotFoundException fileNotFoundException) {
      System.out.println("An error occurred.");
      fileNotFoundException.printStackTrace();
    }

    int[][] returnFileData = new int[draftFileData.length][draftFileData[0].length];

    for (int i = 0; i < draftFileData.length; i++) {
      for (int j = 0; j < draftFileData[i].length; j++) {
        returnFileData[i][j] = Integer.parseInt(draftFileData[i][j]);
      }
    }
    return returnFileData;
  }

  /**
   * @return Sample Display Data
   */
  private double[][] testData() {
    double[][] sampleData = new double[10][5];
    for (int i = 0; i < sampleData.length; i++) {
      for (int j = 0; j < sampleData[i].length; j++) {
        sampleData[i][j] = (i + 1) * (j + 1);
      }
    }
    // System.out.println(Arrays.deepToString(sampleData));
    return sampleData;
  }

  /**
   * @param data Input data to display
   */
  private void displayGUI(double[][] data) {
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

  public double[][] getDisplayData() {
    return displayData;
  }

  public void setDisplayData(double[][] displayData) {
    this.displayData = displayData;
  }
}
