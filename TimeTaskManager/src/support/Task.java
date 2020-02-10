package support;

public class Task {

    private int num;
    private String name;
    private String backlog;
    private Boolean complete;

    public Task(int num, String name, String backlog, Boolean complete) {
        this.num = num;
        this.name = name;
        this.backlog = backlog;
        this.complete = complete;
    }

    public int getNum() {
        return num;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete() {
        this.complete = true;
    }

    @Override
    public String toString(){
        return "Task " + num +
                "[ name: " + name +
                ", steps: ( " + backlog +
                " ) complete: " + complete +
                " ]";
    }
}
