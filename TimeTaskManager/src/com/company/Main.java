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
    private boolean statusChanged;


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
            "stop timer - stop timer\n" +
            "begin - set task status in \"in the process\"\n" +
            "stop task - set task status in \"beclog\"\n" +
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
        if (hasTask()){
            try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(
                    new File("src/jsonFiles/Tasks.json"), JsonEncoding.UTF8)){
                jsonGenerator.writeStartArray();
                for (Task tasy : task){
                    if (tasy != null){
                        jsonGenerator.writeStartObject();
                        jsonGenerator.writeStringField("name", tasy.getName());
                        jsonGenerator.writeStringField("status", tasy.getStatus());
                        jsonGenerator.writeEndObject();
                    }
                }
                jsonGenerator.writeEndArray();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        else {
            try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(
                    new File("src/jsonFiles/Tasks.json"), JsonEncoding.UTF8)){
                jsonGenerator.writeStartArray();
                jsonGenerator.writeStartObject();
                jsonGenerator.writeEndObject();
                jsonGenerator.writeEndArray();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
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
                    task[i].setNum(i+1);
                    switch (fieldName){
                        case ("name"):
                            jsonParser.nextToken();
                            task[i].setName(jsonParser.getText());
                            break;
                        case ("status"):
                            jsonParser.nextToken();
                            task[i].setStatus(jsonParser.getText());
                            System.out.println();
                            break;
                        default:
                            break;
                    }
                }
                i++;
            }
            if (task[0].getName() == null){
                task[0] = null;
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    //получение первого свободного номера для новой задачи
    private int getFirstNullTaskNum(){
        int getFirstNullTaskNum = 0;
        for (Task tasy : task) {
            if (tasy == null){
                break;
            }
            else {
                getFirstNullTaskNum++;
            }
        }
        return getFirstNullTaskNum;
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
        System.out.print("Input a number of task: \n");
        int numberOfTask = 0;
        if (in.hasNextInt()){
            numberOfTask = in.nextInt();
        }
        else {
            return -1;
        }
        return numberOfTask;
    }

    private void setInTheProcessStatus(){
        if (hasTask()){
            int numOfTask = InputNumOfaTask() - 1;
            if (task[numOfTask] != null){
                if (task[numOfTask].getStatus() != "in the process"){
                    task[numOfTask].setStatus("in the process");
                    System.out.println("the task was started!");
                }
                else {
                    System.out.println("the task is already in the proсess!");
                }
            }
            else {
                System.out.println("no tasks with this number");
            }
        }
    }

    private void setCompleteStatus(){
        if (hasTask()){
            int numOfTask = InputNumOfaTask() -1;
            if (task[numOfTask] != null){
                if (task[numOfTask].getStatus() != "complete"){
                    task[numOfTask].setStatus("complete");
                    System.out.println("task has been completed!");
                }
                else {
                    System.out.println("task already completed!");
                }
            }
            else {
                System.out.println("no tasks with this number");
            }
        }
    }

    private void setBacklogStatus(){
        if (hasTask()){
            int numOfTask = InputNumOfaTask() - 1;
            if (task[numOfTask] != null){
                if (task[numOfTask].getStatus() != "backlog"){
                    task[numOfTask].setStatus("backlog");
                    System.out.println("task has been stooped!");
                }
                else {
                    System.out.println("task is already stopped!");
                }
            }
            else {
                System.out.println("no tasks with this number");
            }
        }
    }

    //удаление задачи
    private void deleteTask() {
        boolean hasDelete = false;
        if (hasTask()) {
            int numOfTask = InputNumOfaTask();
            if (numOfTask < 21 && numOfTask > 0){
                for (int i = 0; i <20; i++){
                    if (task[i] != null) {
                        if (task[i].getNum() == numOfTask){
                            task[i] = null;
                            System.out.println("task has been deleted!");
                            hasDelete = true;
                            break;
                        }
                    }
                }
                if (!hasDelete){
                    System.out.println("no task with this number!");
                }
            }
            else {
                System.out.println("uncorrect number!");
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
        System.out.print("Input a name of task: \n");
        String name = "";
        if (in.hasNextLine()){
            name = in.nextLine();
        }
        int fitstNullTaskNum = getFirstNullTaskNum();
        if (name.length() == 0) {
            System.out.println("name is too short!");
        }
        task[fitstNullTaskNum] = new Task(fitstNullTaskNum + 1, name, "backlog");
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
            case ("stop timer"):
                stopTimer();
                saveTasksToJson();
                statusChanged = true;
                break;
            case ("comp"):
                setCompleteStatus();
                saveTasksToJson();
                statusChanged = true;
                break;
            case ("begin"):
                setInTheProcessStatus();
                saveTasksToJson();
                statusChanged = true;
                break;
            case ("stop task"):
                setBacklogStatus();
                saveTasksToJson();
                statusChanged = true;
                break;
            case ("del"):
                deleteTask();
                saveTasksToJson();
                statusChanged = true;
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
                if (statusChanged){
                    break;
                }
                System.out.println("unknown command. enter \"help\" to list commands");
                break;
        }
    }
}
