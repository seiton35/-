package com.company;


import support.Task;

import java.util.Scanner;

public class Main {

    static Task[] task = new Task[20];

    static String help = "new - new task\n" +
            "start([num of task]) - start timer for task [num of task]\n" +
            "stop - stop timer\n" +
            "complete([num of task]) - mark as completed\n" +
            "delete([num of task]) - delete task [num of task]";

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
        switch (com){
            case ("help"):
                System.out.println(help);
                break;
                case ("")
        }
    }


}
