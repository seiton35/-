package com.company;


import support.Task;
import support.Timer;

import java.util.Scanner;

public class Main {

    static Task[] task = new Task[20];

    static String help = "list - list tasks\n" +
            "new - new task\n" +
            "start - start timer \n" +
            "stop - stop timer\n" +
            "complete - mark as completed\n" +
            "delete - delete task\n" +
            "quit - quit task manager";

    public static void main(String[] args) {
        System.out.println(help);
        inputCommand();
    }

    private static int getLastTaskNum(){
        int lastTaskNum = 0;

        for (Task tasy : task){
            if (tasy!= null){
                lastTaskNum++;
            }
        }
        return lastTaskNum;
    }

    private static void printTasks(){
        for (Task tasy : task){
            if (tasy!= null){
                System.out.println(tasy);
            }
            else {
                System.out.println("no tsaks");
            }
        }
    }

    private static int InputNumOfaTask(){
        Scanner in = new Scanner(System.in);
        System.out.print("Input a number of task: ");
        int numberOfTask = in.nextInt();
        in.close();
        return numberOfTask;
    }

    private static void completeTask(int numOfTask){
        for (Task tasy : task){
            if (tasy.getNum() == numOfTask){
                tasy.setComplete();
            }
        }
    }

    private static void deleteTask(int numOfTask){
        for (Task tasy : task){
            if (tasy.getNum() == numOfTask){
                tasy = null;
            }
        }
    }

    private static void startTimer(int numOfTask){
        Timer newTimer = new Timer();
        newTimer.start();
    }

    private static void newTask(){
        Scanner in = new Scanner(System.in);
        System.out.print("Input a name of task: ");
        String name = in.nextLine();
        String steps = "";
        int lastTask = getLastTaskNum();
        if(name.length()!= 0) {
            System.out.print("Input a steps: ");
            steps = in.nextLine();
            if(steps.length()== 0) {
                System.out.println("must be at least one step!");
                return;
            }
        }else {
            System.out.println("name is too short!");
            return;
        }
        in.close();
        task[lastTask] = new Task(lastTask + 1, name, steps, false);

    }

    private static void inputCommand(){
        boolean quit = true;
        String com = "";
        while (quit) {
            Scanner in = new Scanner(System.in);
            com = in.nextLine();
            in.close();
            switch (com){
                case ("help"):
                    System.out.println(help);
                    break;
                case ("start"):
                    startTimer(InputNumOfaTask());
                    break;
                case ("stop"):
                    //stopTimer();                                          TODO
                    break;
                case ("complete"):
                    completeTask(InputNumOfaTask());
                    break;
                case ("delete"):
                    deleteTask(InputNumOfaTask());
                    break;
                case ("new"):
                    newTask();
                    break;
                case("list"):
                    quit = false;
                    printTasks();
                    break;
                case("quit"):
                    quit = false;
                    break;
                default:
                    System.out.println("uncnown command");
                    inputCommand();
                    break;
            }
        }
    }


}
