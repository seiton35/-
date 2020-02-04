package com.company;


import support.Task;

import java.util.Scanner;

public class Main {

    static Task[] task = new Task[20];

    static String help = "new - new task\n" +
            "start - start timer \n" +
            "stop - stop timer\n" +
            "complete - mark as completed\n" +
            "delete - delete task ";

    public static void main(String[] args) {
        inputTask();

    }

    public static int getLastTaskNum(){
        int lastTaskNum = 0;

        for (Task tasy : task){
            if (tasy!= null){
                lastTaskNum++;
            }
        }
        return lastTaskNum;
    }

    public static void printTasks(){
        for (Task tasy : task){
            if (tasy!= null){
                System.out.println(tasy);
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


    private static void inputTask(){
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

        printTasks();
    }

    private static void command(){
        Scanner in = new Scanner(System.in);
        String com = in.nextLine();
        in.close();
        switch (com){
            case ("help"):
                System.out.println(help);
                break;
            case ("start"):
                //startTimer(InputNumOfaTask());                         TODO
                break;
            case ("stop"):
                //stopTimer();
                break;
            case ("complete"):
                completeTask(InputNumOfaTask());
                break;
            case ("delete"):
                //deleteTask(InputNumOfaTask());                         TODO
                break;
        }
    }


}
