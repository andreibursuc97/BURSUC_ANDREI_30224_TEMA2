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
    private AtomicInteger ok;
    private AtomicInteger waitingPeriod;
    private int ID;
    private long timeInit;
    //private boolean full;
    //private List<Client> waitingClients;
    //private ArrayList<Client> clientiCoada=new ArrayList<Client>();

    public Server(int ID)
    {
        //waitingClients=new ArrayList<Client>();
        //waitingClients.add(new Client(1,1));
        this.ID=ID;
        ok=new AtomicInteger(2);
        waitingPeriod=new AtomicInteger(0);
       // time=new AtomicInteger(0);
        clients=new ArrayBlockingQueue<Client>(20000);
    }

    public synchronized void addClient(Client client,long timeInit)
    {
        try {
            this.timeInit=timeInit;
            if(client.getArrivalTime()==0)
            //this.waitingClients=waitingClients;
            {
                //time.incrementAndGet();
            clients.put(client);
            client.setArrivalTime(System.currentTimeMillis()-timeInit);
            System.out.println("Clientul "+client.getID()+" s-a asezat la coada la casa "+this.ID+" la "+ client.getArrivalTime()+"!");
                //Thread.sleep(1);
                waitingPeriod.addAndGet(client.getProcessingTime());
            //waitingPeriod=waitingPeriod+;
            ok.set(1);}
            //waitingPeriod.addAndGet(client.getNumarProduse());
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
            while(true)
            {
            try {//System.out.println("Thread "+this.ID+ " merge la "+System.currentTimeMillis());
                //if (ok.get() == 1) {
                    System.out.println("La casa " + this.ID + " sunt " + clients.size() + " clienti!");
                    Client client = clients.take();

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
                    System.out.println("Clientul " + client.getID() + " a iesit de la casa " + this.ID + " la " + client.getFinishTime() + "!" + " si a asteptat " + (client.getFinishTime() - client.getArrivalTime()));
                    //else
                        //System.out.println("Clientul " + client.getID() + " a iesit de la casa " + this.ID + " la " + client.getFinishTime() + "!" + " si a asteptat " + -(client.getFinishTime() - client.getArrivalTime()));
                //}

            } catch (InterruptedException e) {
                System.out.println("EROARE");
                break;
            }
            if(ok.get()==1 || ok.get()==0) System.out.println("Perioada de asteptare la casa "+this.ID+" este "+waitingPeriod.get());

        }
    }
}
