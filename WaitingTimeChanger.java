import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Andrei on 06/04/2017.
 */
public class WaitingTimeChanger implements Runnable {
    private AtomicInteger waitingTime;
    private Server server;

    public WaitingTimeChanger(AtomicInteger waitingTime, Server server)
    {
        this.waitingTime=waitingTime;
        this.server=server;
    }
    public void run()
    {
        while(true)
        try{
            Thread.sleep(1);
            waitingTime.decrementAndGet();
            server.setWaitingPeriod(waitingTime);
        }catch(InterruptedException e)
        {
            break;
        }
    }
}
