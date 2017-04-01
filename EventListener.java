import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Andrei on 01/04/2017.
 */
public class EventListener implements IEL {
    Map<Server,Integer> numberOfClients;
    Map<Server,Integer> totalWaitingTime;
    Map<Client,Server> clientsInServer;

    public EventListener()
    {
        numberOfClients=new ConcurrentHashMap<Server, Integer>();
        totalWaitingTime=new ConcurrentHashMap<Server, Integer>();
        clientsInServer=new ConcurrentHashMap<Client,Server>();
    }

    public void newClient(Server server,Client client)
    {

            int count;
            if(numberOfClients.containsKey(server))
            {
                count=numberOfClients.get(server);
            }
            else
            {
                numberOfClients.put(server,0);
                count=0;
            }
            numberOfClients.put(server, count + 1);
            clientsInServer.put(client, server);

    }

    public void notifyWaitingTime(Server server,int waitingTime)
    {
        int time;
        if (totalWaitingTime.containsKey(server)) {
            time=totalWaitingTime.get(server);
        }
        else
        {
            totalWaitingTime.put(server,0);
            time=0;
        }
        totalWaitingTime.put(server,time+waitingTime);

    }

    private void checkMap()
    {
        for(Map.Entry<Client,Server> entry:clientsInServer.entrySet())
        {
            if(entry.getKey().getWaitingTime()==0)
            {
                if (numberOfClients.containsKey(entry.getValue())) {
                    int count=numberOfClients.get(entry.getValue());
                    numberOfClients.put(entry.getValue(),count-1);
                }
                else
                    System.out.println("nu merge");
            }
        }
    }

    public void averageWaitingTimeDisplayer()
    {
        if(numberOfClients.isEmpty()) {
            System.out.println("Lista goala");
        }
        checkMap();
        for(Map.Entry<Server,Integer> entry:numberOfClients.entrySet())
        {
            float time=totalWaitingTime.get(entry.getKey());
            System.out.println(time+" "+entry.getValue());
            float average=time/entry.getValue();
            System.out.println("Timpul mediu de asteptare la casa "+entry.getKey().getID()+" a fost "+average);
        }

    }
}
