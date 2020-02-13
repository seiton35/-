package support;

public class Timer extends Thread {

    private volatile boolean stop;

    public boolean getStop() {
        return stop;
    }

    public void setStop() {
        this.stop = true;
    }

    public void setContin() {
        this.stop = false;
    }



    public void run() {
            try {
                for(int i = 1; i <= 600; i++) {
                    if (!getStop()){
                        if (i % 100 == 0){

                            System.out.println(i/20+" minutes left");

                            if(i == 500){
                                System.out.println("break 5 minutes!");
                            }
                            if(i == 600){
                                System.out.println("1 pomodoro is left!");
                            }
                        }
                        Thread.sleep(20);
                    }
                    else {
                        setContin();
                        System.out.println("timer is stopped!");
                        break;
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("timer is interrupted");
        }
    }


}