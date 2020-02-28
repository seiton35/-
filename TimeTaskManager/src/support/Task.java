package support;

public class Task {

    private int num;
    private String name;
    private String status;

    //конструктор без параметров для парсера
    public Task() {
    }

    //конструктор
    public Task(int num, String name, String status) {
        this.num = num;
        this.name = name;
        this.status = status;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String backlog) {
        this.status = backlog;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }


    @Override
    public String toString(){
        return  num + ")- " +
                name +
                " [ " + status + " ]";
    }
}
