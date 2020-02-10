package com.company;

import support.Task;
import support.Timer;

import java.util.Scanner;

public class Main {

    static Task[] task = new Task[20];

    private static Timer timer;
    private Scanner in;
    private boolean quit;

    public boolean getQuit() {
        return quit;
    }

    public void setQuit(){
        this.quit = true;
    }
    

    public Main() {
        this.timer = new Timer();
    }

    static String help = "list - list tasks\n" +
            "new - new task\n" +
            "start - start timer \n" +
            "stop - stop timer\n" +
            "comp - mark as completed\n" +
            "del - delete task\n" +
            "quit - quit task manager";

    public static void main(String[] args) {
        Main main = new Main();

        main.in = new Scanner(System.in);

        System.out.println(help);
        while (!main.getQuit())
            main.inputCommand();
    }

    private int getLastTaskNum() {
        int lastTaskNum = 0;

        for (Task tasy : task) {
            if (tasy != null) {
                lastTaskNum++;
            }
        }
        return lastTaskNum;
    }

    private boolean hasTask(){
        boolean hasTask = false;
        for (Task tasy : task) {
            if (tasy != null) {
                hasTask = true;
            }
        }
        return hasTask;
    }

    private void printTasks() {
        if (hasTask()){
            for (Task tasy : task) {
                if (tasy != null) {
                    System.out.println(tasy);
                }
            }
        }else {
            System.out.println("no tasks!");
        }
    }

    private int InputNumOfaTask() {
        System.out.print("Input a number of task: ");
        int numberOfTask = 0;
        if (in.hasNextInt()){
            numberOfTask = in.nextInt();
        }
        return numberOfTask;
    }

    private void completeTask() {
        if (hasTask()) {
            int numOfTask = InputNumOfaTask();
            if (task[numOfTask - 1] != null) {
                if (!task[numOfTask - 1].getComplete()){
                    task[numOfTask - 1].setComplete();
                    System.out.println("task has been completed!");
                }
                else {
                    System.out.println("task already completed!");
                }
            }
            else
                System.out.println("no task with this number!");
        }
        else {
            System.out.println("no tasks!");
        }
    }

    private void deleteTask() {
        if (hasTask()) {
            int numOfTask = InputNumOfaTask();
            if (task[numOfTask - 1] != null) {
                task[numOfTask - 1] = null;
                System.out.println("task has been deleted!");
            }
            else
                System.out.println("no task with this number!");
        }
        else {
            System.out.println("no tasks!");
        }
    }

    public void startTimer() {
        timer.start();
    }

    private void stopTimer() {
        timer.setStop();
    }

    private void newTask() {
        System.out.print("Input a name of task: ");
        String name = in.nextLine();
        String steps = "";
        int lastTask = getLastTaskNum();
        if (name.length() != 0) {
            System.out.print("Input a steps: ");
            steps = in.nextLine();
            if (steps.length() == 0) {
                System.out.println("must be at least one step!");
                return;
            }
        } else {
            System.out.println("name is too short!");
            return;
        }
        task[lastTask] = new Task(lastTask + 1, name, steps, false);
        System.out.println("task has been added!");

    }

    private void inputCommand() {
        //String com = "";
        //if (in.hasNextLine()) {
        //    com = in.nextLine();
        //}
        switch (in.nextLine()) {
            case ("help"):
                System.out.println(help);
                break;
            case ("start"):
                startTimer();
                break;
            case ("stop"):
                stopTimer();
                break;
            case ("comp"):
                completeTask();
                break;
            case ("del"):
                deleteTask();
                break;
            case ("new"):
                newTask();
                break;
            case ("list"):
                printTasks();
                break;
            case ("quit"):
                setQuit();
                break;
            default:
                System.out.println("unknown command");
                break;
        }
    }
}
