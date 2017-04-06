//import com.sun.jmx.snmp.tasks.Task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Andrei on 26/03/2017.
 * Aceasta clasa simuleaza modelul cozii de asteptare care preia clientii si ii serveste
 * avand un timp de asteptare si o ista de clietni.
 */
public class Server implements Runnable {


    private BlockingQueue<Client> clients;

    public synchronized void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    private AtomicInteger waitingPeriod;
    private int ID;
    private long timeInit;
    private Logger log;
    private IEL listener;
    //private AtomicInteger previousClientTime;

    public Server(int ID)
    {
        this.ID=ID;
        waitingPeriod=new AtomicInteger(0);
        clients=new ArrayBlockingQueue<Client>(20000);
        //previousClientTime=new AtomicInteger(0);
    }

    public synchronized void addClient(Client client,long timeInit)
    {
        try {
            this.timeInit = timeInit;
            if (client.getArrivalTime() == 0)
            {
                clients.put(client);
                listener.incrementRealTimeClientCounter(this);
                client.setArrivalTime(System.currentTimeMillis() - timeInit);
                log.appendToString("Clientul " + client.getID() + " s-a asezat la coada la casa " + this.ID + " la " + client.getArrivalTime() + "!");
                log.appendToString("Clientul "+client.getID()+" are timp de procesare "+client.getProcessingTime()+"!");
                log.appendToString("");
                waitingPeriod.addAndGet(client.getProcessingTime());
            }
        }
        catch(InterruptedException e)
        {}
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public int getID()
    {
        return this.ID;
    }

    public void setListener(IEL listener) {
        this.listener = listener;
    }

    public void setLog(Logger log) {
        this.log = log;
    }


    public void run() {

            while(true)
            {
                long timeInitEmpty=0;
                long timeFinalEmpty=0;
            try {
                timeInitEmpty = System.currentTimeMillis();
                Client client = clients.take();
                timeFinalEmpty = System.currentTimeMillis();
                if (timeFinalEmpty - timeInitEmpty > 0)
                    listener.notifyEmptyQueue(this, (int) (timeFinalEmpty - timeInitEmpty));
                this.listener.newClient(this, client);
                WaitingTimeChanger tm=new WaitingTimeChanger(waitingPeriod,this);
                Thread ts=new Thread(tm);
                ts.start();
                 Thread.sleep(client.getProcessingTime());
                 ts.interrupt();
                client.setFinishTime(System.currentTimeMillis()-timeInit);
                client.setWaitingTime(client.getFinishTime() - client.getArrivalTime());
                log.appendToString("Clientul " + client.getID() + " a iesit de la casa " + this.ID + " la " + client.getFinishTime()+" ms!" );
                log.appendToString("Clientul "+client.getID()+ " a asteptat " + client.getWaitingTime()+" ms la casa "+this.ID+"!");
                log.appendToString("");
                listener.notifyWaitingTime(this, (int) client.getWaitingTime());
                listener.decrementRealTimeClientCounter(this);
            } catch (InterruptedException e) {
                log.appendToString("Casa "+this.ID+" s-a inchis!");
                if(timeInitEmpty>0 && timeFinalEmpty==0)
                {
                    timeFinalEmpty=System.currentTimeMillis();
                    listener.notifyEmptyQueue(this,(int)(timeFinalEmpty-timeInitEmpty));
                }
                break;
            }
        }
    }
}
