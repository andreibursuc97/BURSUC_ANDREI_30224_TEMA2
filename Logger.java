//import java.time.LocalTime;

/**
 * Created by Andrei on 02/04/2017.
 */
public class Logger implements Runnable {

    private IEL listener;
    private View view;

    public Logger(IEL listener,View view)
    {
        this.listener=listener;
        this.view=view;
    }

    public void run() {
        long time=0;
        long hourTime=0;
        EventListener listen=(EventListener) listener;
        //this.startPeakHour(servers);
        while(true)
        {
            try{

                //time=System.currentTimeMillis();
                Thread.sleep(0);

                view.setProgressBars(listen.getNumberOfClients());
            }
            catch (InterruptedException e)
            {
                break;
            }
        }
    }
}
