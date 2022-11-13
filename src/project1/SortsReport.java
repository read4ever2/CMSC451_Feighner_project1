/*
 * Filename: BenchmarkSorts.java
 * Author: Will Feighner
 * Date: 2022 11 12
 * Purpose: This program analyses the efficiency of iterative and recursive versions
 * the Merge Sort algorithm by generating random data to be sorted, timing
 * how long it takes, and counting the number operations the algorithm takes.
 */

package project1;

// Imports

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
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

    // Declare object
    SortsReport sortsReport = new SortsReport();

    // Get data from file
    sortsReport.setReportData(sortsReport.readData());

    // Process data and set display data
    sortsReport.setDisplayData(sortsReport.processData(sortsReport.getReportData()));

    // Display data
    sortsReport.displayGUI(sortsReport.getDisplayData());
  }

  /**
   * Takes an array of data containing benchmark from sorting algorithms and calculates
   * the average and Coefficient of Variance of the data
   *
   * @param inputData Data from CSV file to calculate Averages and Coefficient of variance
   * @return Averages and coefficient of variance of input data
   */
  public double[][] processData(int[][] inputData) {
    // Declare return variable
    double[][] returnData = new double[10][5];

    // Outer loop for each row of input data array
    for (int i = 0; i < inputData.length; i++) {

      // Set first element of each output row and first element of input row, number of elements sorted to generate benchmark data
      returnData[i][0] = inputData[i][0];

      // Get average of count values of file data
      double countSum = 0;

      // Loop through 1,3,5,7... elements of array and sum values
      for (int j = 1; j < inputData[0].length; j = j + 2) {
        countSum += inputData[i][j];
      }

      // Calculate average of count values
      returnData[i][1] = countSum / (double) ((inputData[0].length - 1) / 2);

      // Get average of time values of file data
      double timeSum = 0;

      // Loop through 2,4,6,8... elements of array and sum values

      for (int j = 2; j < inputData[0].length; j = j + 2) {
        timeSum += inputData[i][j];
      }

      // Calculate average of count values
      returnData[i][3] = timeSum / (double) ((inputData[0].length - 1) / 2);

      // Calculate coefficient of Variance of Time values
      double[] coefTime = new double[inputData[0].length / 2];

      // Copy Time values from 101 element array to 50 element array
      for (int j = 2; j < inputData[0].length; j = j + 2) {
        coefTime[(j / 2) - 1] = inputData[i][j];
      }
      returnData[i][4] = coefOfVariation(coefTime);

      // Calculate coefficient of Variance of Count values
      double[] coefCount = new double[inputData[0].length / 2];

      // Copy Count values from 101 element array to 50 element array
      for (int j = 1; j < inputData[0].length; j = j + 2) {
        coefCount[j / 2] = inputData[i][j];
      }
      returnData[i][2] = coefOfVariation(coefCount);
    }
    return returnData;
  }

  /**
   * Calculates the Coefficient of Variance of a supplied array
   *
   * @param coefData Array to calculate Coefficient of Variance of
   * @return Coefficient of Variance of data
   */
  private double coefOfVariation(double[] coefData) {

    // Calculate Mean
    double meanSum = 0;
    for (double v : coefData) {
      meanSum += v;
    }
    double mean = meanSum / coefData.length;

    // Calculate Standard Deviation
    double stdDevSum = 0;
    for (double v : coefData) {
      stdDevSum += (v - mean) * (v - mean);
    }
    double stdDev = Math.sqrt(stdDevSum / (coefData.length - 1));

    // Coefficient of Variance
    return stdDev / mean;
  }

  /**
   * Reads data from a csv file, expected 10 x 101 values
   * converts csv to 2D array
   *
   * @return 2D array of data from csv file
   */
  private int[][] readData() {

    // Select file to read
    JFileChooser jFileChooser = new JFileChooser();
    jFileChooser.showOpenDialog(null);
    File file = new File(jFileChooser.getSelectedFile().toURI());

    String[][] draftFileData = new String[10][101];

    try {

      // Read file
      Scanner scanner = new Scanner(file);

      int counter = 0;

      // Scan each line of the file
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        // Split line on commas, assign resulting array to corresponding array row
        draftFileData[counter] = line.split(",");
        counter++;
      }
    } catch (FileNotFoundException fileNotFoundException) {
      System.out.println("An error occurred.");
      fileNotFoundException.printStackTrace();
    }

    int[][] returnFileData = new int[draftFileData.length][draftFileData[0].length];

    // Convert string values to integers
    for (int i = 0; i < draftFileData.length; i++) {
      for (int j = 0; j < draftFileData[i].length; j++) {
        returnFileData[i][j] = Integer.parseInt(draftFileData[i][j]);
      }
    }
    return returnFileData;
  }

  /**
   * Takes calculated data, displays data in JTable within JFrame
   *
   * @param data Data to display
   */
  private void displayGUI(double[][] data) {

    // Create components
    JFrame jFrame = new JFrame();
    JTable jTable;

    jFrame.setTitle("Benchmark Report");

    Object[][] objectData = new Object[data.length][data[0].length];

    // Format data into strings for table
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        switch (j) {
          case 0 -> objectData[i][j] = String.format("%.0f", data[i][j]);
          case 1, 3 -> objectData[i][j] = String.format("%.2f", data[i][j]);
          case 2, 4 -> objectData[i][j] = String.format("%.2f", data[i][j] * 100) + " %";
          default -> {
          }
        }
      }
    }

    // Format JTable
    String[] columnNames = {"Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time"};
    jTable = new JTable(objectData, columnNames);
    jTable.setBounds(30, 40, 200, 200);
    JScrollPane scrollPane = new JScrollPane(jTable);

    // Format JFrame
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
