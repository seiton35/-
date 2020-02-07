package support;

public class Timer extends Thread {
    public void run() {
        try {
            for(int i = 1; i < 7; i++) {
                System.out.println(i*5+" minutes left");
                Thread.sleep(300000);
                if(i == 5){
                    System.out.println("break 5 minutes!");
                }
                if(i == 6){
                    System.out.println("1 pomodoro is left!");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("timer is interrupted");
        }
    }
}