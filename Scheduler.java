import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei on 25/03/2017.
 */
public class Scheduler {

    private int numberOfServers;

    private List<Server> servers;


   // private List<Thread> threads;

    public Scheduler(int numberOfServers)
    {
        this.numberOfServers=numberOfServers;
        //this.numberOfServers=maxNumbersClients;
        servers=new ArrayList<Server>();
        //waitingClients=new ArrayList<Client>();
        //threads=new ArrayList<Thread>();
        for(int i=0;i<numberOfServers;i++)
        {
            //Server server=new Server();
            servers.add(new Server(i));
        }



        //System.out.println("In magazin avem "+ threads.size()+" cozi "+servers.size());
    }

    public synchronized  void addTask(Client client,long timeInit)
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


                    /// /ok = 1;
                    //waitingClients=clients;
                    //return true;
                }
            }



    }

    public List<Server> getServers()
    {
        return this.servers;
    }


}
