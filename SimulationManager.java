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
    private int maxTimeBewtweenClients;
    private int simulationTime;
    private int minServiceTime;
    private int maxServiceTime;

    public List<Server> getServers() {
        return servers;
    }

    private List<Server> servers;

    public SimulationManager(int minTimeBetweenClients, int maxTimeBewtweenClients, int simultationTime, int numberQueues, int minServiceTime, int maxServiceTime,IEL listener)
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
        threads=new ArrayList<Thread>();
        servers=new ArrayList<Server>();
        for(int i=0;i<numberQueues;i++)
        {
            //Server server=new Server();
            servers.add(new Server(i,listener));
        }
        for(Server server:servers)
        {
            Thread thread=new Thread(server);
            threads.add(thread);
            thread.start();
        }

        ClientGenerator gen=new ClientGenerator(minTimeBetweenClients,maxTimeBewtweenClients,simultationTime,numberQueues,minServiceTime,maxServiceTime,this.servers,this.threads,listener);
        Thread t=new Thread(gen);
        t.start();

        //this.generateTask(3000);
    }


    public static void main(String[] args) {
        // write your code here

        // View view=new View();
        //Calculator calculator =new Calculator();
        //Controller controller=new Controller(view, calculator);
        //Client ionel=new Client(1,200);
        //int minServiceTime=1000;
        //int maxServiceTime=3000;
        IEL listener=new EventListener();
        SimulationManager simulationManager =new SimulationManager(100,200,1000,3,200,500,listener);

        //Thread t=new Thread(clientGenerator);
        //t.start();
        //t.kill();
        //t.stop();
        //clientGenerator.addClient(ionel);
        // Thread rand1=new Thread(clientGenerator);
        //rand1.start();


    }
}
