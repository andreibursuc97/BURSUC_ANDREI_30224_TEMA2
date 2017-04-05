import javax.swing.*;
import java.util.List;
import java.util.Random;

//import java.time.LocalTime;

/**
 * Created by Andrei on 25/03/2017.
 * Gestionare cozi.
 */
public class ClientGenerator implements Runnable {

    private int minTimeBetweenClients;
    private int maxTimeBewtweenClients;
    private int simulationTime;
    private int minServiceTime;
    private int maxServiceTime;
    private List<Server> servers;
    private List<Thread> threads;
    private IEL listener;
    private Logger log;

    public ClientGenerator(int minTimeBetweenClients, int maxTimeBewtweenClients, int simultationTime, int minServiceTime, int maxServiceTime,List<Server> servers,List<Thread> threads,IEL listener,Logger log)
    {

        this.maxTimeBewtweenClients=maxTimeBewtweenClients;
        this.minTimeBetweenClients=minTimeBetweenClients;
        this.minServiceTime=minServiceTime;
        this.maxServiceTime=maxServiceTime;
        this.simulationTime =simultationTime;
        this.servers=servers;
        this.threads=threads;
        this.listener=listener;
        this.log=log;

    }


    private synchronized  void addTask(Client client,long timeInit)
    {

        int min = Integer.MAX_VALUE;
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

        int i=0;
        long timeInit=System.currentTimeMillis();
        long time=timeInit;
        log.setTimeInit(timeInit);
        log.setSimulationTime(simulationTime);
        EventListener listen=(EventListener)listener;
        listen.setTimeInit(time);
        Thread t=new Thread(listen);
        t.start();
        Thread logThread=new Thread(log);
        logThread.start();

        generateClients(i, timeInit, time);
        t.interrupt();
        for(Thread thread:this.threads)
            thread.interrupt();
        log.setFlag(1);

    }

    private void generateClients(int i, long timeInit, long time) {
        while(time<timeInit+this.simulationTime)
        {
            try {
                Random rand=new Random();
                int numarProduse=rand.nextInt((this.maxServiceTime-this.minServiceTime+1))+this.minServiceTime;
                Client client=new Client(i,numarProduse);
                i++;
                this.addTask(client,timeInit);
                int interval = rand.nextInt((this.maxTimeBewtweenClients - this.minTimeBetweenClients + 1)) + this.minTimeBetweenClients;
                Thread.sleep(interval);
                time = System.currentTimeMillis();
            }
            catch(InterruptedException e)
            {
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
        }
    }

}
