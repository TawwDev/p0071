package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Validation {

    private static Scanner sc = new Scanner(System.in);

    public int getInt(String msg, int min, int max) {
        while (true) {
            System.out.print(msg);
            try {
                int n = Integer.parseInt(sc.nextLine());
                if (n >= min && n <= max) {
                    return n;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter an integer number in range: " + min + " to " + max);
            }
        }
    }

    public String getString(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine();
            if (!s.isEmpty()) {
                return s;
            } else {
                System.err.println("Input must be not empty. Please re-input!");
            }
        }
    }

    public double getDouble(String msg, double min, double max) {
        while (true) {
            System.out.print(msg);
            try {
                double n = Double.parseDouble(sc.nextLine());
                if (n % 0.5 != 0) {
                    throw new NumberFormatException();
                }
                if (n >= min && n <= max) {
                    return n;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter an integer number in range: " + min + " to " + max);
            }
        }
    }

    public String getDate() {
        System.out.print("Date:");
        while (true) {
            try {
                String s = getString("");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = dateFormat.parse(s);
                if (s.equalsIgnoreCase(dateFormat.format(date))) {
                    return dateFormat.format(date);
                } else {
                    System.err.println("Date not valid. Please re-input: ");
                }
            } catch (ParseException pe) {
                System.err.print("Please input follow format dd-MM-yyyy. Enter again: ");
            }
        }
    }

}
