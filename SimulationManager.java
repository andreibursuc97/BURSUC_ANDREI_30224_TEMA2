//import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei on 01/04/2017.
 */
public class SimulationManager {

    private List<Thread> threads;
    //private Scheduler sched;
    //private int numberQueues;
    private int minTimeBetweenClients;
    private int maxTimeBetweenClients;
    private int simulationTime;
    private int minServiceTime;
    private int maxServiceTime;
    private IEL listener;

    public List<Server> getServers() {
        return servers;
    }

    private List<Server> servers;

    public SimulationManager(int minTimeBetweenClients, int maxTimeBetweenClients, int simultationTime, int numberQueues, int minServiceTime, int maxServiceTime,View view)
    {

        //this.generateTask(numarClienti);
        this.maxTimeBetweenClients=maxTimeBetweenClients;
        this.minTimeBetweenClients=minTimeBetweenClients;
        this.minServiceTime=minServiceTime;
        this.maxServiceTime=maxServiceTime;
        //this.numberQueues=numberQueues;
        //Random rand = new Random();
        this.simulationTime =simultationTime; //rand.nextInt((this.maxServiceTime - this.minServiceTime) + 1) + this.minServiceTime;
        //sched=new Scheduler(this.numberQueues);
        threads=new ArrayList<Thread>();
        servers=new ArrayList<Server>();

        for(int i=0;i<numberQueues;i++)
        {
            //Server server=new Server();
            servers.add(new Server(i));
        }

        listener=new EventListener(servers,view);
        Logger log=new Logger(listener,view);

        for(int i=0;i<numberQueues;i++)
        {
            //Server server=new Server();
            servers.get(i).setListener(listener);
            servers.get(i).setLog(log);
        }

        for(Server server:servers)
        {
            Thread thread=new Thread(server);
            threads.add(thread);
            thread.start();
        }

        ClientGenerator gen=new ClientGenerator(minTimeBetweenClients,maxTimeBetweenClients,simultationTime,numberQueues,minServiceTime,maxServiceTime,this.servers,this.threads,listener,log);
        Thread t=new Thread(gen);
        t.start();

        //this.generateTask(3000);
    }



}

