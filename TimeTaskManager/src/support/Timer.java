package support;

public class Timer extends Thread {

    public static boolean stop = false;

     public static void setStop(){
        stop = true;
    }

    public void run() {
            try {
                for(int i = 1; i <= 600; i++) {
                    if (!stop){
                        if (i % 100 == 0){

                            System.out.println(i);
                            System.out.println(i/20+" minutes left");

                            if(i == 500){
                                System.out.println("break 5 minutes!");
                            }
                            if(i == 600){
                                System.out.println("1 pomodoro is left!");
                            }
                        }
                        Thread.sleep(3000);
                    }
                    else {
                        System.out.println("timer is stopped!");
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("timer is interrupted");
        }
    }
}