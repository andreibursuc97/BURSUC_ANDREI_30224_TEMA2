//import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei on 01/04/2017.
 */
public class SimulationManager {

    private List<Thread> threads;
    private IEL listener;
    private List<Server> servers;

    public SimulationManager(int minTimeBetweenClients, int maxTimeBetweenClients, int simultationTime, int numberQueues, int minServiceTime, int maxServiceTime,View view)
    {


        threads=new ArrayList<Thread>();
        servers=new ArrayList<Server>();

        for(int i=0;i<numberQueues;i++)
        {

            servers.add(new Server(i));
        }

        listener=new EventListener(servers);
        Logger log=new Logger(listener,view);

        for(int i=0;i<numberQueues;i++)
        {

            servers.get(i).setListener(listener);
            servers.get(i).setLog(log);
        }

        for(Server server:servers)
        {
            Thread thread=new Thread(server);
            threads.add(thread);
            thread.start();
        }

        ClientGenerator gen=new ClientGenerator(minTimeBetweenClients,maxTimeBetweenClients,simultationTime,minServiceTime,maxServiceTime,this.servers,this.threads,listener,log);
        Thread t=new Thread(gen);
        t.start();


    }



}

