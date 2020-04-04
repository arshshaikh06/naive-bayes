package com.algorithms.naivebayes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.algorithms.naivebayes.datasets.Data;

public class test2 {
    public static void main(String[] args) {

        System.out.println(
                "Choose Data Set\n1. Weather Data Set\n2. Skin Tan Data Set\n3. Car Data Set\n4. Income Data Set");
        System.out.print("Enter Data Set Number: ");

        boolean check = true;
        String[][] data = { {} };

        Scanner sc = new Scanner(System.in);

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

        int rows = data.length - 1;
        int columns = data[0].length;

        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%15s ", data[i][j]);
            }
            System.out.println();
        }

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

        String val0 = values.get(0);
        String val1 = values.get(1);

        HashMap<String, Integer> calc = new HashMap<>();
        calc.put(val0, 0);
        calc.put(val1, 0);

        HashMap<String, Double> finalProb = new HashMap<>();
        finalProb.put(val0, 0.0);
        finalProb.put(val1, 0.0);

        for (int i = 1; i <= rows; i++) {
            if (data[i][columns - 1].equals(checkVal))
                calc.merge(values.get(0), 1, (a, b) -> a + b);
            else
                calc.merge(values.get(1), 1, (a, b) -> a + b);
        }

        finalProb.put(val0, calc.get(val0) * 1.0 / rows);
        finalProb.put(val1, calc.get(val1) * 1.0 / rows);

        int feature0Count = 0;
        int feature1Count = 0;
        for (int t = 0; t < testTuple.length; t++) {
            feature0Count = 0;
            feature1Count = 0;

            for (int i = 1; i <= rows; i++) {

                if (testTuple[t].equals(data[i][t])) {

                    if (data[i][columns - 1].equals(checkVal))
                        feature0Count++;
                    else
                        feature1Count++;
                }
            }

            finalProb.put(val0, (feature0Count * 1.0 / calc.get(val0)) * finalProb.get(val0));
            finalProb.put(val1, (feature1Count * 1.0 / calc.get(val1)) * finalProb.get(val1));

        }

        if (finalProb.get(val0) > finalProb.get(val1))
            System.out.println(val0);
        else
            System.out.println(val1);

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}