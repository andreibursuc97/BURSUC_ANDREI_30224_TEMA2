import javax.swing.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Andrei on 25/03/2017.
 */
public class SimulationManager implements Runnable {

    private List<Client> clients;
    private List<Thread> threads;
    private Scheduler sched;
    private int numberQueues;
    private int minTimeBetweenClients;
    private int maxTimeBewtweenClients;
    private int minServiceTime;
    private int maxServiceTime;
    private int ServiceTime;

    public SimulationManager(int minTimeBetweenClients,int maxTimeBewtweenClients,int minServiceTime,int maxServiceTime,int numberQueues)
    {

        //this.generateTask(numarClienti);
        this.maxTimeBewtweenClients=maxTimeBewtweenClients;
        this.minTimeBetweenClients=minTimeBetweenClients;
        this.minServiceTime=minServiceTime;
        this.maxServiceTime=maxServiceTime;
        this.numberQueues=numberQueues;
        Random rand = new Random();
        this.ServiceTime = rand.nextInt((this.maxServiceTime - this.minServiceTime) + 1) + this.minServiceTime;
        sched=new Scheduler(this.numberQueues);
        threads=new ArrayList<Thread>();
        List<Server> servers=sched.getServers();
        for(Server server:servers)
        {
            Thread thread=new Thread(server);
            threads.add(thread);
            thread.start();
        }

        this.generateTask(3000);
    }

    /*public void addClient(Client client)
    {
        clientiCoada.add(client);
    }*/

    public List<Client> getClients()
    {
        return this.clients;
    }

    public void run() {

        //LocalTime.ofNanoOfDay(0);
        int i=0;

        long timeInit=System.currentTimeMillis();
        System.out.println(timeInit);
        long time=timeInit;


        while(time<=timeInit+this.ServiceTime && !clients.isEmpty())
        {
            try {
                sched.addTask(clients.get(i),timeInit);
                // int time=(int)System.currentTimeMillis()%100000;
                clients.remove(i);
                //i++;
                Random rand = new Random();
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
        List<Server> servers=sched.getServers();
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
        }
        for(Thread thread:this.threads)
            thread.interrupt();
    }

    private void generateTask(int numarClienti)
    {
        clients= Collections.synchronizedList(new ArrayList<Client>());
        for(int i=0;i<numarClienti;i++)
        {
            Random rand=new Random();
            int numarProduse=rand.nextInt(999)+1;
            Client client=new Client(i,numarProduse);
            clients.add(client);
        }
    }

    public static void main(String[] args) {
        // write your code here

        // View view=new View();
        //Calculator calculator =new Calculator();
        //Controller controller=new Controller(view, calculator);
        //Client ionel=new Client(1,200);
        int minServiceTime=1000;
        int maxServiceTime=3000;
        SimulationManager simulationManager =new SimulationManager(1,200,minServiceTime,maxServiceTime,3);
        Thread t=new Thread(simulationManager);
        t.start();
        //t.kill();
        //t.stop();
        //simulationManager.addClient(ionel);
       // Thread rand1=new Thread(simulationManager);
        //rand1.start();


    }
}
