package com.chocs;

import java.util.ArrayList;

public class TaskSortUtils {
    public static void sortByType(ArrayList<Task> taskArrayList) {
        for(int i = 0; i < taskArrayList.size() - 1; i++) {
            for(int j = i+1; j < taskArrayList.size(); j++) {
                if(taskArrayList.get(i).getType().compareTo(taskArrayList.get(j).getType()) > 0) {
                    Task temp = taskArrayList.get(i);
                    taskArrayList.set(i, taskArrayList.get(j));
                    taskArrayList.set(j, temp);
                }
            }
        }
    }

    public static void sortBySubject(ArrayList<Task> taskArrayList) {
        for(int i = 0; i < taskArrayList.size() - 1; i++) {
            for(int j = i+1; j < taskArrayList.size(); j++) {
                if(taskArrayList.get(i).getSubject().compareTo(taskArrayList.get(j).getSubject()) > 0) {
                    Task temp = taskArrayList.get(i);
                    taskArrayList.set(i, taskArrayList.get(j));
                    taskArrayList.set(j, temp);
                }
            }
        }
    }

    public static void sortByDescription(ArrayList<Task> taskArrayList) {
        for(int i = 0; i < taskArrayList.size() - 1; i++) {
            for(int j = i+1; j < taskArrayList.size(); j++) {
                if(taskArrayList.get(i).getDescription().compareTo(taskArrayList.get(j).getDescription()) > 0) {
                    Task temp = taskArrayList.get(i);
                    taskArrayList.set(i, taskArrayList.get(j));
                    taskArrayList.set(j, temp);
                }
            }
        }
    }

    public static void sortByDate(ArrayList<Task> taskArrayList) {
        for(int i = 0; i < taskArrayList.size() - 1; i++) {
            for(int j = i+1; j < taskArrayList.size(); j++) {
                if(taskArrayList.get(i).getDaysLeft() < taskArrayList.get(j).getDaysLeft()) {
                    Task temp = taskArrayList.get(i);
                    taskArrayList.set(i, taskArrayList.get(j));
                    taskArrayList.set(j, temp);
                }
            }
        }
    }
}
