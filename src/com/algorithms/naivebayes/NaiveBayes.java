package com.algorithms.naivebayes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.algorithms.naivebayes.datasets.Data;

public class NaiveBayes {
    public static void main(String[] args) {

        System.out.println(
                "Choose Data Set\n1. Weather Data Set\n2. Skin Tan Data Set\n3. Car Data Set\n4. Income Data Set");
        System.out.print("Enter Data Set Number: ");

        boolean check = true;
        String[][] data = { {} };

        Scanner sc = new Scanner(System.in);

        // Choose a data set

        do {
            int choice = sc.nextInt();
            check = false;

            if (choice == 1)
                data = Data.WEATHER;
            else if (choice == 2)
                data = Data.TAN;
            else if (choice == 3)
                data = Data.CAR;
            else if (choice == 4)
                data = Data.INCOME;
            else {
                check = true;
                System.out.println("Please Enter a Valid Input");
                System.out.print("Enter Data Set Number: ");
            }

        } while (check);

        // Calculating number of rows and columns

        int rows = data.length - 1;
        int columns = data[0].length;

        // Logging the data set

        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%15s ", data[i][j]);
            }
            System.out.println();
        }

        // Find the result attributes values

        List<String> values = new ArrayList<>();

        values.add(data[1][columns - 1]);

        for (int i = 2; i <= rows; i++) {
            if (values.get(0) != data[i][columns - 1]) {
                values.add(data[i][columns - 1]);
                break;
            }
        }

        System.out.print("\nEnter Test Case in the following format:\t");
        for (int i = 0; i < columns - 1; i++)
            System.out.printf("%s, ", data[0][i]);

        System.out.printf("%s\n", data[0][columns - 1]);

        sc.nextLine();

        String testTupleStr = sc.nextLine();

        sc.close();

        String[] testTuple = testTupleStr.split(", ");

        long start = System.currentTimeMillis();

        String checkVal = data[1][columns - 1];
        int[] calc = new int[2];
        double[] finalProb = new double[2];

        // Actual calculation starts here

        for (int i = 1; i <= rows; i++) {
            if (data[i][columns - 1].equals(checkVal))
                calc[0]++;
            else
                calc[1]++;
        }

        finalProb[0] = calc[0] * 1.0 / rows;
        finalProb[1] = calc[1] * 1.0 / rows;

        int feature0Count = 0;
        int feature1Count = 0;

        // * When using TAN Data Set
        // int SKIP = 1;

        for (int t = 0; t < testTuple.length; t++) {
            feature0Count = 0;
            feature1Count = 0;

            for (int i = 1; i <= rows; i++) {

                // * When using TAN Data Set
                // if (testTuple[t].equals(data[i][t + SKIP]))

                if (testTuple[t].equals(data[i][t])) {

                    if (data[i][columns - 1].equals(checkVal))
                        feature0Count++;
                    else
                        feature1Count++;
                }
            }

            finalProb[0] = (feature0Count * 1.0 / calc[0]) * finalProb[0];
            finalProb[1] = (feature1Count * 1.0 / calc[1]) * finalProb[1];

        }

        if (finalProb[0] > finalProb[1])
            System.out.println(values.get(0));
        else
            System.out.println(values.get(1));

        long end = System.currentTimeMillis();

        System.out.println("Runtime: " + (end - start) + "ms");
    }
}