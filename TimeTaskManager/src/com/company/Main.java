package com.company;

import com.fasterxml.jackson.core.*;
import support.Task;
import support.Timer;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

    static Task[] task = new Task[20];

    JsonFactory jsonFactory = new JsonFactory();


    private static Timer timer;
    private Scanner in;
    private boolean quit;
    private boolean delCompWorked;


    public boolean getQuit() {
        return quit;
    }

    //выход из приложения
    public void setQuit(){
        this.quit = true;
    }

    //список всех команд
    static String help = "list - list tasks\n" +
            "new - new task\n" +
            "start - start timer \n" +
            "stop - stop timer\n" +
            "comp - mark as completed\n" +
            "del - delete task\n" +
            "quit - quit task manager";

    public static void main(String[] args) throws JsonProcessingException {
        Main main = new Main();

        main.parseTasksFromJson();

        main.in = new Scanner(System.in);

        System.out.println(help);
        while (!main.getQuit()){
            main.inputCommand();
        }
        main.in.close();
    }

    //запись задач в Tasks.json
    private void saveTasksToJson(){
        try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(
                new File("src/jsonFiles/Tasks.json"), JsonEncoding.UTF8)){
            jsonGenerator.writeStartArray();
            for (Task tasy : task){
                if (tasy != null){
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField("num", tasy.getNum());
                    jsonGenerator.writeStringField("name", tasy.getName());
                    jsonGenerator.writeStringField("backlog", tasy.getBacklog());
                    jsonGenerator.writeBooleanField("complete", tasy.getComplete());
                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    //парсинг задач из Tasks.json
    private void parseTasksFromJson(){
        try(JsonParser jsonParser = jsonFactory.createJsonParser(
                new File("src/jsonFiles/Tasks.json"))) {

            int i = 0;
            while(jsonParser.nextToken() != JsonToken.END_ARRAY){
                task[i] = new Task();
                while (jsonParser.nextToken() != JsonToken.END_OBJECT){
                    String fieldName = jsonParser.getCurrentName();
                    if (fieldName == null){
                        continue;
                    }
                    switch (fieldName){
                        case ("num"):
                            jsonParser.nextToken();
                            task[i].setNum(jsonParser.getIntValue());
                            break;
                        case ("name"):
                            jsonParser.nextToken();
                            task[i].setName(jsonParser.getText());
                            break;
                        case ("backlog"):
                            jsonParser.nextToken();
                            task[i].setBacklog(jsonParser.getText());
                            System.out.println();
                            break;
                        case ("complete"):
                            jsonParser.nextToken();
                            task[i].setComplete(jsonParser.getBooleanValue());
                            break;
                        default:
                            break;
                    }
                }
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    //получение первого свободного номера для новой задачи
    private int getLastTaskNum() {
        int lastTaskNum = 0;
        for (Task tasy : task) {
            if (tasy != null) {
                lastTaskNum++;
            }else{
                break;
            }
        }
        return lastTaskNum;
    }

    //проверка существования задач
    private boolean hasTask(){
        boolean hasTask = false;
        for (Task tasy : task) {
            if (tasy != null) {
                hasTask = true;
                break;
            }
        }
        return hasTask;
    }

    //вывод списка всех существующих задач
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

    //получение номера для создания или удаленя задачи
    private int InputNumOfaTask() {
        System.out.print("Input a number of task: ");
        int numberOfTask = 0;
        if (in.hasNextInt()){
            numberOfTask = in.nextInt();
        }
        else {
            return -1;
        }
        return numberOfTask;
    }

    //установка "выполнено" в задаче
    private void completeTask() {
        if (hasTask()) {
            int numOfTask = InputNumOfaTask();
            if (task[numOfTask - 1] != null) {
                if (!task[numOfTask - 1].getComplete()){
                    task[numOfTask - 1].setComplete(true);
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

    //удаление задачи
    private void deleteTask() {
        if (hasTask()) {
            int numOfTask = InputNumOfaTask();
            if (numOfTask != -1){
                if (task[numOfTask - 1] != null) {
                    task[numOfTask - 1] = null;
                    System.out.println("task has been deleted!");
                }
                else
                    System.out.println("no task with this number!");
            }
            else {
                System.out.println("it is not num!");
            }
        }
        else {
            System.out.println("no tasks!");
        }
    }

    //запуск таймера
    private void startTimer() {
        this.timer = new Timer();
        timer.start();
    }

    //преждевременная остановка таймера
    private void stopTimer() {
        timer.setStop();
    }

    //создание новой задачи
    private void newTask() {
        System.out.print("Input a name of task: ");
        String name = "";
        if (in.hasNextLine()){
            name = in.nextLine();
        }
        String steps = "";
        int lastTask = getLastTaskNum();
        if (name.length() != 0) {
            System.out.print("Input a steps: ");
            if(in.hasNextLine()){
                steps = in.nextLine();
            }
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

    //ввод команд
    private void inputCommand() {
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
                saveTasksToJson();
                delCompWorked = true;
                break;
            case ("del"):
                deleteTask();
                saveTasksToJson();
                delCompWorked = true;
                break;
            case ("new"):
                newTask();
                saveTasksToJson();
                break;
            case ("list"):
                printTasks();
                break;
            case ("quit"):
                setQuit();
                break;
            default:
                if (delCompWorked){
                    break;
                }
                System.out.println("unknown command. enter \"help\" to list commands");
                break;
        }
    }
}
