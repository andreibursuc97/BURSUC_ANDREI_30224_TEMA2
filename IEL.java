/**
 * Created by Andrei on 01/04/2017.
 */
public interface IEL {

    public void newClient(Server server,Client client);
    public void notifyWaitingTime(Server server,int waitingTime);
    public void averageWaitingTimeDisplayer(Logger log);
    public void notifyEmptyQueue(Server server,int emptyTime);
    public void decrementRealTimeClientCounter(Server server);
    public void incrementRealTimeClientCounter(Server server);

}
