import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Andrei on 01/04/2017.
 * Urmareste evenimentele din threadurile de tip server si colecteaza informatiile pentru ca mai apoi sa le trimita la
 * la logger care le afiseaza.
 */
public class EventListener implements IEL,Runnable {
    private Map<Server,Integer> numberOfClients;
    private Map<Server,Integer> totalWaitingTime;
    private Map<Server,Integer> emptyQueueTime;
    private Map<Client,Server> clientsInServer;
    private Map<Server,Integer> realTimeClientCounter;
    private Map<Server,Map<Integer,Integer>> peakHour;
    private List<Server> servers;
    private long timeInit;




    public EventListener(List<Server> servers)
    {
        numberOfClients=new ConcurrentHashMap<Server, Integer>();
        totalWaitingTime=new ConcurrentHashMap<Server, Integer>();
        clientsInServer=new ConcurrentHashMap<Client,Server>();
        emptyQueueTime=new ConcurrentHashMap<Server, Integer>();
        realTimeClientCounter=new ConcurrentHashMap<Server, Integer>();
        peakHour=new ConcurrentHashMap<Server, Map<Integer,Integer> >();
        this.servers=servers;
    }


    public Map<Server, Integer> getRealTimeClientCounter() {
        return realTimeClientCounter;
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

    public void incrementRealTimeClientCounter(Server server)
    {
        int count;
        if (realTimeClientCounter.containsKey(server)) {
            count=realTimeClientCounter.get(server);
            //System.out.println(count+" ");
        }
        else
        {
            realTimeClientCounter.put(server,0);
            count=0;
        }
        count++;
        realTimeClientCounter.put(server,count);
    }

    public void decrementRealTimeClientCounter(Server server)
    {
        int count;
        if (realTimeClientCounter.containsKey(server)) {
            count=realTimeClientCounter.get(server);
        }
        else
        {
            realTimeClientCounter.put(server,0);
            count=0;
        }
        count--;
        realTimeClientCounter.put(server,count);
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

    public void notifyEmptyQueue(Server server,int emptyTime)
    {
        int time;
        if (emptyQueueTime.containsKey(server)) {
            time=emptyQueueTime.get(server);
        }
        else
        {
            emptyQueueTime.put(server,0);
            time=0;
        }
        emptyQueueTime.put(server,time+emptyTime);
    }

    public void notifyPeakHour(int time)
    {
        for(Map.Entry<Server,Map<Integer,Integer>> entry:peakHour.entrySet()) {

            if(realTimeClientCounter.get(entry.getKey())!=null)
                entry.getValue().put(realTimeClientCounter.get(entry.getKey()),time);
        }

    }

    private void startPeakHour(List<Server> servers)
    {
        for(Server server:servers)
        {
            peakHour.put(server, new ConcurrentHashMap<Integer, Integer>());
        }
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

    public void averageWaitingTimeDisplayer(Logger log)
    {
        if(!numberOfClients.isEmpty()) {

            checkMap();
            log.appendToString("");
            for (Map.Entry<Server, Integer> entry : numberOfClients.entrySet()) {
                float time = totalWaitingTime.get(entry.getKey());
                float average = time / entry.getValue();
                log.appendToString("Timpul mediu de asteptare la casa " + entry.getKey().getID() + " a fost " + average);
            }
        }

    }

    public void peakHourDisplayer(Logger log)
    {
        if(!peakHour.isEmpty()) {
            log.appendToString("");
            for (Map.Entry<Server, Map<Integer, Integer>> entry : peakHour.entrySet()) {

                Map<Integer, Integer> values = entry.getValue();
                int max = 0, max2 = 0;
                if (!values.isEmpty()) {
                    for (Map.Entry<Integer, Integer> entry2 : values.entrySet()) {
                        max = entry2.getValue();
                        max2 = entry2.getKey();

                    }
                }
                max = max - max % 100;
                log.appendToString(" La ora de varf " + max + " ms pentru casa " + entry.getKey().getID() + " au fost " + max2 + " clienti!");
            }
        }
    }

    public void emptyQueueTimeDisplayer(Logger log)
    {
        if(!emptyQueueTime.isEmpty()) {
            log.appendToString("");
            for (Map.Entry<Server, Integer> entry : emptyQueueTime.entrySet()) {
                log.appendToString("Casa " + entry.getKey().getID() + " a fost goala timp de " + entry.getValue());
            }
        }
    }

    public void setTimeInit(long timeInit) {
        this.timeInit = timeInit;
    }

    public void run() {

        long time=0;
        this.startPeakHour(servers);
        while(true)
        {
            try{

                time=System.currentTimeMillis();
                if((time-timeInit)%100==0)
                    this.notifyPeakHour((int)(time-timeInit));
                Thread.sleep(1);
            }
            catch (InterruptedException e)
            {
                break;
            }
        }
    }
}
