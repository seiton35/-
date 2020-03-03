package com.company;

import com.fasterxml.jackson.core.*;
import support.Task;
import support.Timer;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author seyton35
 * @version 1.0
 * This is main class, responsible for managing the program
 */
public class Main {

    /**
     * This field is an array for storing all objects of the <strong>Task</strong>
     */
    static Task[] task = new Task[20];

    JsonFactory jsonFactory = new JsonFactory();

    /**
     * This field creates an object of class <strong>Timer</strong>
     */
    private static Timer timer;

    /**
     * This field creates an object of class <strong>Scaner</strong>
     */
    private Scanner in;

    /**
     * The boolean private field required to exit the program
     */
    private boolean quit;
    private boolean statusChanged;

    /**
     * This field returns the value to exit the program
     * @return quit
     */
    public boolean getQuit() {
        return quit;
    }

    /**
     * This field set true on quit
     */
    public void setQuit(){
        this.quit = true;
    }

    /**
     * The string field where I store all the command names
     */
    static String help =
            "list - list tasks\n" +
            "new - new task\n" +
            "start - start timer \n" + 
            "stop timer - stop timer\n" +
            "begin - set task status in \"in the process\"\n" +
            "stop task - set task status in \"beclog\"\n" +
            "comp - mark as completed\n" +
            "del - delete task\n" +
            "quit - quit task manager";

    /**
     * Here start point of the program
     * @param args
     * @throws JsonProcessingException when it happens
     * @see #getQuit()
     * @see #inputCommand()
     */
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

    /**
     * This field saves all tasks from the array to json file
     * if they are not, then keep empty json file
     * @see #hasTask()
     * @see Task#getName()
     * @see Task#getStatus()
     *
     */
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

    /**
     * This field extracts all tasks from json file to array
     * @see Task#setNum(int)
     * @see Task#setName(String)
     * @see Task#setStatus(String)
     * @see Task#getName()
     */
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

    /**
     * This field finds and return the first empty space in the array
     * @return int getFirstNullTaskNum is first empty space in array
     */
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

    /**
     * This field returns false if there are no tasks
     * @return boolean hasTask
     */
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

    /**
     * This field displays all tasks in the console
     * if they are not, present a message about their absence is displayed
     * @see #hasTask()
     */
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

    /**
     * This field returns the number entered by the user
     * @return int numberOfTask
     */
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

    /**
     * This field sets the value "in process" in the properties of the task
     * @see #InputNumOfaTask()
     * @see Task#getStatus()
     * @see Task#setStatus(String)
     */
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
        else {
            System.out.println("no tasks!");
        }
    }

    /**
     * This field sets the value "complete" in the properties of the task
     * @see #InputNumOfaTask()
     * @see Task#getStatus()
     * @see Task#setStatus(String)
     */
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
        else {
            System.out.println("no tasks!");
        }
    }

    /**
     * This field sets the value "backlog" in the properties of the task
     * @see #InputNumOfaTask()
     * @see Task#getStatus()
     * @see Task#setStatus(String)
     */
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
        else {
            System.out.println("no tasks!");
        }
    }

    /**
     * This field removes the task from the array, if it exists
     * @see #hasTask()
     * @see #InputNumOfaTask()
     * @see Task#getNum()
     */
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

    /**
     * This field starts <strong>start</strong> method from class <strong>Timer</strong>
     * @see Timer#start()
     */
    private void startTimer() {
        this.timer = new Timer();
        timer.start();
    }

    /**
     This field starts <strong>stop</strong> method from class <strong>Timer</strong>
     * @see Timer#setStop()
     */
    private void stopTimer() {
        timer.setStop();
    }

    /**
     * This field creates an object of class <strong> Task </strong> and writes it to an array
     * @see #getFirstNullTaskNum()
     * @see Task#Task(int, String, String)
     */
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

    /**
     * This field takes the value from the keyboard and selects the appropriate method
     * @see #startTimer()
     * @see #stopTimer()
     * @see #saveTasksToJson()
     * @see #setCompleteStatus()
     * @see #setInTheProcessStatus()
     * @see #setBacklogStatus()
     * @see #deleteTask()
     * @see #newTask()
     * @see #printTasks()
     * @see #setQuit()
     */
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
                    statusChanged = false;
                    break;
                }
                System.out.println("unknown command. enter \"help\" to list commands");
                break;
        }
    }
}
