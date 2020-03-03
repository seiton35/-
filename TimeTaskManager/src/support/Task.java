package support;

/**
 * This is the <strong>Task</strong> class that describes tasks
 */
public class Task {

    /**
     * The field number of task
     */
    private int num;

    /**
     * The field name of task
     */
    private String name;

    /**
     * The field status of task
     */
    private String status;

    /**
     * Constructor for creating new object
     * @see #Task(int, String, String)
     */
    public Task() {
    }

    /**
     * Constructor for creating new object
     * @param num - number of task
     * @param name - name of task
     * @param status - status of task
     * @see #Task()
     */
    public Task(int num, String name, String status) {
        this.num = num;
        this.name = name;
        this.status = status;
    }

    /**
     * This field sets the value in the class attribute <strong>num</strong>
     * @param num - number of task
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * This field sets the value in the class attribute <strong>name</strong>
     * @param name - name of task
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This field sets the value in the class attribute <strong>status</strong>
     * @param status - status of task
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This field gets the value from the <strong>num</strong> attribute and returns it
     * @return num
     */
    public int getNum() {
        return num;
    }

    /**
     * This field gets the value from the <strong>name</strong> attribute and returns it
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * This field gets the value from the <strong>status</strong> attribute and returns it
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * This field displays in the console all class parameters with a description
     * @return all parametrs
     */
    @Override
    public String toString(){
        return  num + ")- " +
                name +
                " [ " + status + " ]";
    }
}
