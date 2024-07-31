package com.chocs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {
    private ArrayList<Task> taskArrayList;
    private final Scanner scanner;
    private final Gson gson;
    private final String FILE_PATH = "C:\\Users\\ADM\\IdeaProjects\\task-manager\\tasks.json";

    public TaskManager() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        scanner = new Scanner(System.in);
        taskArrayList = new ArrayList<>();

        loadJsonFile();

        waitInput(true);
    }

    private void waitInput(boolean fromConstructor) {
        if(!fromConstructor) {
            scanner.nextLine();

        }

        saveJsonFile();
        printTaskList();

        System.out.println("Enter 1 to add task,");
        System.out.println("Enter 2 to delete task,");
        System.out.println("Enter 3 to edit task,");
        System.out.println("Enter 4 to sort tasks,");
        System.out.println("Enter 5 to print task list,");
        System.out.println("Enter 0 other to close.");

        String option = scanner.nextLine();
        if(!Character.isDigit(option.charAt(0)) || option.charAt(0) == '\n') {
            System.out.println("Invalid option. Try again.");
            waitInput(false);
        }

        switch (Integer.parseInt(option)) {
            case 0: System.exit(0);
            case 1: addTask(); break;
            case 2: deleteTask(); break;
            case 3: editTask(); break;
            case 4: sortTasks(); break;
            case 5: printTaskList(); break;
            default:
                System.out.println("Invalid option. Try again.");
                waitInput(false);
        }
    }

    private void addTask() {
        Task newTask = new Task();

        setType(newTask);
        setSubject(newTask);
        setDescription(newTask);
        setDate(newTask);

        taskArrayList.add(newTask);

        waitInput(false);
    }

    private void deleteTask() {
        printTaskList();

        taskArrayList.remove(getTaskArrayIndex());

        waitInput(false);
    }

    private void editTask() {
        printTaskList();

        Task task = taskArrayList.get(getTaskArrayIndex());

        System.out.print("Enter field to be edited (Type, Subject, Description or Date): ");
        String field = scanner.next();
        field = field.toLowerCase();

        switch(field) {
            case "type": setType(task); break;
            case "subject": setSubject(task); break;
            case "description": setDescription(task); break;
            case "date": setDate(task); break;
            default: System.out.println("Invalid input. Try again."); editTask();
        }

        waitInput(false);
    }

    private void setType(Task task) {
        System.out.print("\nEnter 1 to set as Prova and 2 to set as Trabalho: ");

        String option = scanner.next();
        if(option.length() != 1 || !Character.isDigit(option.charAt(0))) {
            System.out.println("Invalid input. Try again.");
            setType(task);
        }

        switch(Integer.parseInt(option)) {
            case 1: task.setType("Prova"); break;
            case 2: task.setType("Trabalho"); break;
            default: System.out.println("Invalid input. Try again."); setType(task); break;
        }
    }

    private void setSubject(Task task) {
        System.out.print("Enter subject: ");
        task.setSubject(scanner.next());
    }

    private void setDescription(Task task) {
        System.out.print("Enter description: ");
        task.setDescription(scanner.next());
    }

    private void setDate(Task task) {
        // date
        System.out.print("Enter task year: ");
        int year = scanner.nextInt();

        System.out.print("Enter task month: ");
        int month = scanner.nextInt();

        System.out.print("Enter task day: ");
        int day = scanner.nextInt();
        try {
            task.setDate(LocalDate.of(year, month, day));
        } catch(DateTimeException e) {
            System.out.println("Invalid date. Try again.");
            setDate(task);
        }

        task.calcDaysLeft();
    }

    private void sortTasks() {
        System.out.print("Enter field to be sort (Type, Subject, Description or Date/Days Left): ");
        String field = scanner.next();
        field = field.toLowerCase();

        switch(field) {
            case "type": TaskSortUtils.sortByType(taskArrayList); break;
            case "subject": TaskSortUtils.sortBySubject(taskArrayList); break;
            case "description": TaskSortUtils.sortByDescription(taskArrayList); break;
            case "daysleft":
            case "days left":
            case "date": TaskSortUtils.sortByDate(taskArrayList); break;
            default: System.out.println("Invalid input. Try again."); sortTasks();
        }

        waitInput(false);
    }

    private int getTaskArrayIndex() {
        System.out.print("\nInsert task index: ");
        int index = scanner.nextInt();

        if(index >= taskArrayList.size() || index < 0) {
            System.out.println("Task index out of bounds. Try again.");
            getTaskArrayIndex();
        }

        return index;
    }

    private void printTaskList() {
        System.out.println("\n");
        for(int i = 0; i < taskArrayList.size(); i++) {
            Task task = taskArrayList.get(i);
            System.out.println(i + ". " + task);
        }
        System.out.println("\n");
    }

    private void recalculateDaysLeft() {
        for(Task task : taskArrayList) {
            task.calcDaysLeft();
        }
    }

    private void loadJsonFile() {
        File jsonFile = new File(FILE_PATH);

        if (jsonFile.exists()) {
            try (FileReader reader = new FileReader(jsonFile)) {
                Type taskArrayListType = new TypeToken<ArrayList<Task>>() {}.getType();
                taskArrayList = gson.fromJson(reader, taskArrayListType);
            } catch (IOException e) {
                System.out.println("Failed to load tasks from file.");
            }
        }

        recalculateDaysLeft();
    }

    private void saveJsonFile() {
        String jsonString = gson.toJson(taskArrayList);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(jsonString);
        } catch(IOException e) {
            System.out.println("Failed to save tasks to file.");
        }
    }

    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
            jsonWriter.value(localDate.toString());
        }

        @Override
        public LocalDate read(JsonReader jsonReader) throws IOException {
            return LocalDate.parse(jsonReader.nextString());
        }
    }
}
