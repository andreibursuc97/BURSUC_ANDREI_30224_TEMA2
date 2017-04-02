//import com.sun.jmx.snmp.tasks.Task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Andrei on 26/03/2017.
 */
public class Server implements Runnable {

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    private BlockingQueue<Client> clients;
    //AtomicInteger time;
    //private AtomicInteger ok;
    private AtomicInteger waitingPeriod;
    private int ID;
    private long timeInit;
    private Logger log;


    private IEL listener;
    //private boolean full;
    //private List<Client> waitingClients;
    //private ArrayList<Client> clientiCoada=new ArrayList<Client>();

    public Server(int ID)
    {
        //waitingClients=new ArrayList<Client>();
        //waitingClients.add(new Client(1,1));
        this.ID=ID;
        //this.listener=listener;
        //ok=new AtomicInteger(2);
        waitingPeriod=new AtomicInteger(0);
        //this.log=log;
       // time=new AtomicInteger(0);
        clients=new ArrayBlockingQueue<Client>(20000);
    }

    public synchronized void addClient(Client client,long timeInit)
    {
        try {
            this.timeInit = timeInit;
            if (client.getArrivalTime() == 0)
            //this.waitingClients=waitingClients;
            {
                //time.incrementAndGet();
                clients.put(client);
                listener.incrementRealTimeClientCounter(this);
                client.setArrivalTime(System.currentTimeMillis() - timeInit);
                //log.appendToString("");
                log.appendToString("Clientul " + client.getID() + " s-a asezat la coada la casa " + this.ID + " la " + client.getArrivalTime() + "!");
                int waiting=client.getProcessingTime()+this.getWaitingPeriod();
                log.appendToString("Clientul "+client.getID()+" are de asteptat "+waiting+"!");
                //log.appendToString("");
                //System.out.println("Clientul " + client.getID() + " s-a asezat la coada la casa " + this.ID + " la " + client.getArrivalTime() + "!");

                //Thread.sleep(1);
                waitingPeriod.addAndGet(client.getProcessingTime());
                //waitingPeriod=waitingPeriod+;
                //ok.set(1);}
                //waitingPeriod.addAndGet(client.getNumarProduse());
            }
        }
        catch(InterruptedException e)
        {}
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public int getSize()
    {
        return clients.size();
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
    /*public void setFull(boolean val)
    {
        this.full=val;
    }

    public boolean getFull()
    {
        return this.full;
    }*/


    public void run() {
        //while (ok.get() != 0) {
        //EventListener listen=(EventListener) listener;
            while(true)
            {
                long timeInitEmpty=0;
                long timeFinalEmpty=0;
            try {//System.out.println("Thread "+this.ID+ " merge la "+System.currentTimeMillis());
                //if (ok.get() == 1) {
                    //System.out.println("La casa " + this.ID + " sunt " + clients.size() + " clienti!");
                    //listen.notifyPeakHour((int)System.currentTimeMillis());
                    timeInitEmpty=System.currentTimeMillis();
                    Client client = clients.take();
                    timeFinalEmpty=System.currentTimeMillis();
                    if(timeFinalEmpty-timeInitEmpty>0)
                        listener.notifyEmptyQueue(this,(int)(timeFinalEmpty-timeInitEmpty));
                    this.listener.newClient(this,client);

                    //int time= (int) (System.currentTimeMillis()%10000);
                    //System.out.println("Clientul "+client.getID()+" a ajuns la casa la "+ time);
                    Thread.sleep(client.getProcessingTime());
                    waitingPeriod.addAndGet(-client.getProcessingTime());
                    //waitingPeriod = waitingPeriod - client.getProcessingTime();
                    //if (waitingPeriod.get() <= 0) ok.set(0);
                    //int time= (int) (System.currentTimeMillis()%100000);
                    //time.addAndGet(client.getProcessingTime());
                    client.setFinishTime(System.currentTimeMillis()-timeInit);
                    //if(client.getFinishTime()> client.getArrivalTime())
                    client.setWaitingTime(client.getFinishTime() - client.getArrivalTime());
                    log.appendToString("Clientul " + client.getID() + " a iesit de la casa " + this.ID + " la " + client.getFinishTime()+" ms!" );
                    log.appendToString("Clientul "+client.getID()+ " a asteptat " + client.getWaitingTime()+" ms la casa "+this.ID+"!");
                    //System.out.println("Clientul " + client.getID() + " a iesit de la casa " + this.ID + " la " + client.getFinishTime() + "!" + " si a asteptat " + client.getWaitingTime());
                    listener.notifyWaitingTime(this, (int) client.getWaitingTime());
                    listener.decrementRealTimeClientCounter(this);
                    //else
                        //System.out.println("Clientul " + client.getID() + " a iesit de la casa " + this.ID + " la " + client.getFinishTime() + "!" + " si a asteptat " + -(client.getFinishTime() - client.getArrivalTime()));
                //}

            } catch (InterruptedException e) {
                log.appendToString("Casa "+this.ID+" s-a inchis!");
                //System.out.println("Casa "+this.ID+" s-a inchis!");
                if(timeInitEmpty>0 && timeFinalEmpty==0)
                {
                    timeFinalEmpty=System.currentTimeMillis();
                    listener.notifyEmptyQueue(this,(int)(timeFinalEmpty-timeInitEmpty));
                }

                break;
            }
           // if(ok.get()==1 || ok.get()==0) System.out.println("Perioada de asteptare la casa "+this.ID+" este "+waitingPeriod.get());

        }
    }
}
