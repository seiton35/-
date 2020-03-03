package support;

/**
 * Class thread-timer
 */
public class Timer extends Thread {

    /**
     * This is a logical field to stop the timer
     */
    private volatile boolean stop;

    /**
     * This field gets the value from the <strong>stop</strong> attribute and returns it
     * @return stop
     */
    public boolean getStop() {
        return stop;
    }

    /**
     * This field sets the value in the class attribute <strong>stop</strong>
     */
    public void setStop() {
        this.stop = true;
    }

    /**
     *This field starts the timer for 30 minutes
     * @see #getStop()
     */
    public void run() {
        try {
            //600 раз по 3 сек = 1800 сек или 30 мин
            for(int i = 1; i <= 600; i++) {
                //каждые 3 сек проверка на остановку таймера
                if (!getStop()){
                    //каждые 5 мин оповещение
                    if (i % 100 == 0){
                        System.out.println(i/20+" minutes left");
                        if(i == 500){
                            //после 24 мин оповещение о перерыве
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
                    break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("timer is interrupted");
        }
    }
}