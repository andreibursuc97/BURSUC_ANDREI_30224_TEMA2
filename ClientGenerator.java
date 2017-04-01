import javax.swing.*;
import java.util.List;
import java.util.Random;

//import java.time.LocalTime;

/**
 * Created by Andrei on 25/03/2017.
 * Gestionare cozi.
 */
public class ClientGenerator implements Runnable {

   // private List<Client> clients;
    //private List<Thread> threads;
    //private Scheduler sched;
    //private int numberQueues;
    private int minTimeBetweenClients;
    private int maxTimeBewtweenClients;
    private int simulationTime;
    private int minServiceTime;
    private int maxServiceTime;
    private List<Server> servers;
    private List<Thread> threads;
    IEL listener;

    public ClientGenerator(int minTimeBetweenClients, int maxTimeBewtweenClients, int simultationTime, int numberQueues, int minServiceTime, int maxServiceTime,List<Server> servers,List<Thread> threads,IEL listener)
    {

        //this.generateTask(numarClienti);
        this.maxTimeBewtweenClients=maxTimeBewtweenClients;
        this.minTimeBetweenClients=minTimeBetweenClients;
        this.minServiceTime=minServiceTime;
        this.maxServiceTime=maxServiceTime;
        //this.numberQueues=numberQueues;
        //Random rand = new Random();
        this.simulationTime =simultationTime; //rand.nextInt((this.maxServiceTime - this.minServiceTime) + 1) + this.minServiceTime;
        //sched=new Scheduler(this.numberQueues);
        this.servers=servers;
        this.threads=threads;
        this.listener=listener;
        //this.generateTask(3000);
    }

    /*public void addClient(Client client)
    {
        clientiCoada.add(client);
    }*/

    /*public List<Client> getClients()
    {
        return this.clients;
    }*/

    private synchronized  void addTask(Client client,long timeInit)
    {

        int min = Integer.MAX_VALUE;
        //int ok=0;
        for (Server server : servers) {
            if (server.getWaitingPeriod() < min ) min = server.getWaitingPeriod();
        }

        for (Server server : servers) {
            if (server.getWaitingPeriod() == min ) {
                {
                    server.addClient(client,timeInit);
                    break;
                }

            }
        }
    }


    public void run() {

        //LocalTime.ofNanoOfDay(0);
        int i=0;

        long timeInit=System.currentTimeMillis();
        //System.out.println(timeInit);
        long time=timeInit;


        while(time<timeInit+this.simulationTime)
        {
            try {
                Random rand=new Random();
                int numarProduse=rand.nextInt((this.maxServiceTime-this.minServiceTime+1)+this.minServiceTime);
                Client client=new Client(i,numarProduse);
                i++;
                this.addTask(client,timeInit);
                // int time=(int)System.currentTimeMillis()%100000;
                //clients.remove(i);
                //i++;
               // Random rand = new Random();
                int interval = rand.nextInt((this.maxTimeBewtweenClients - this.minTimeBetweenClients) + 1) + this.minTimeBetweenClients;
                Thread.sleep(interval);
                time = System.currentTimeMillis();
                System.out.println(time);
            }
            catch(InterruptedException e)
            {
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
        }
        /*List<Server> servers=sched.getServers();
        synchronized (servers) {
            for (Server server : servers) {
                while (!server.getClients().isEmpty()) {
                }
                //else break;
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        for(Thread thread:this.threads)
            thread.interrupt();
        listener.averageWaitingTimeDisplayer();
    }

   /* private void generateTask(int numarClienti)
    {
        clients= Collections.synchronizedList(new ArrayList<Client>());
        for(int i=0;i<numarClienti;i++)
        {
            Random rand=new Random();
            int numarProduse=rand.nextInt(999)+1;
            Client client=new Client(i,numarProduse);
            clients.add(client);
        }
    }*/


}
