package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class ByMyself {

    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void printOptions(String[] tab) {
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option: " + ConsoleColors.RESET);
        for (String option : tab) {
            System.out.println(option);
        }
    }

    public static void main(String[] args) {

        tasks = loadDataToTab(FILE_NAME);
        printOptions(OPTIONS);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            switch (input) {
                case "exit":
                    saveTabToFile(tasks, FILE_NAME);
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    System.exit(0);
                    break;
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks, getTheNumber());
                    System.out.println("Value was successfully deleted.");
                    break;
                case "list":
                    printTab(tasks);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }

            printOptions(OPTIONS);
        }
    }

    public static String[][] loadDataToTab(String fileName) {

        Path path = Paths.get(fileName);

        if (!Files.exists(path)) {
            System.out.println("File not exist.");
            System.exit(0);
        }

        String[][] tab = null;
        try {
            List<String> string = new ArrayList<>(Files.readAllLines(path));
            tab = new String[string.size()][string.get(0).split(",").length];
            for (int i = 0; i < string.size(); i++) {
                String[] split = string.get(i).split(",");
                for (int j = 0 ; j < split.length; j++) {
                    tab[i][j] = split[j];
                }
            }
        } catch  (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }

    public static void addTask() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task due date");
        String date = scanner.nextLine();
        System.out.println("Is your task important true/false");
        String importance = scanner.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length -1][0] = description;
        tasks[tasks.length -1][1] = date;
        tasks[tasks.length -1][2] = importance;

    }

    public static void printTab(String[][] tab) {
        for (int i = 0 ; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean isNumberGreaterEqualsZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
            return false;
        }


    public static int getTheNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        String n = scanner.nextLine();

        while (!isNumberGreaterEqualsZero(n)) {
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            scanner.nextLine();
        }
        return Integer.parseInt(n);
    }
    private static void removeTask(String[][] tab, int index) {

        try {
            if (index < tab.length) {
                tasks = ArrayUtils.remove(tab, index);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Element not exist in tab");
        }
    }

    public static void saveTabToFile(String[][] tab, String fileName) {
        Path path = Paths.get(fileName);
        String[] line = new String[tab.length];
        for (int i = 0; i < tab.length; i++) {
            line[i] = String.join(", ",tab[i]);
        }

        try {
            Files.write(path,Arrays.asList(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


