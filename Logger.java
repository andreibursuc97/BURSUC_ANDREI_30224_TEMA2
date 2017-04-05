//import java.time.LocalTime;

/**
 * Created by Andrei on 02/04/2017.
 */
public class Logger implements Runnable {

    private IEL listener;
    private View view;
    private StringBuilder text;
    private int flag;
    private long timeInit;
    private long simulationTime;

    public Logger(IEL listener,View view)
    {
        this.listener=listener;
        this.view=view;
        text=new StringBuilder();
    }

    public synchronized void appendToString(String s)
    {
        text.append(s);
        text.append("\n");
    }

    public synchronized void setFlag(int n)
    {
        this.flag=n;
    }

    public void setTimeInit(long n)
    {
        this.timeInit=n;
    }

    public void setSimulationTime(long n)
    {
        this.simulationTime=n;
    }

    public void run() {
        long time=0;
        EventListener listen=(EventListener) listener;
        int size=0;
        while(true)
        {
            try {
                time = System.currentTimeMillis();
                if ((time - timeInit) <= simulationTime)
                    view.setContorField((int) (time - timeInit));
                if (text.toString().length() > size)
                    view.setLogger(text.toString());
                size = text.toString().length();
                view.setProgressBars(listen.getRealTimeClientCounter());
                Thread.sleep(1);
                if (flag == 1) {
                    listen.averageWaitingTimeDisplayer(this);
                    listen.emptyQueueTimeDisplayer(this);
                    listen.peakHourDisplayer(this);
                    flag = 2;
                } else if (flag == 2)
                    break;
            }catch(InterruptedException e)
           {
                break;
            }
        }
    }
}
