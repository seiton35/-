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

    //public String getName() {
    //    return name;
    //}

    //public String getBacklog() {
    //    return backlog;
    //}

    //public Boolean getComplete() {
    //    return complete;
    //}

    //public void setNum(int num) {
    //    this.num = num;
    //}

    //public void setName(String name) {
    //    this.name = name;
    //}
    //public void setBacklog(String backlog) {
    //    this.backlog = backlog;
    //}

    public void setComplete() {
        this.complete = true;
    }




    @Override
    public String toString(){
        return "Task " + num +
                "[ " + name +
                ", steps( " + backlog +
                " ) complete: " + complete +
                " ]";
    }
}
