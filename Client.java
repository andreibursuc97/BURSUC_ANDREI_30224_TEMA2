/**
 * Created by Andrei on 25/03/2017.
 */
public class Client {
    private int ID;

    //private int numarProduse;
    private long arrivalTime;

    private long finishTime;
    private int processingTime;

    private long waitingTime;

    public Client(int ID,int numarProduse)
    {
        //this.arrivalTime=arrivalTime;
        this.ID=ID;
        //this.numarProduse=numarProduse;
        this.processingTime=numarProduse;
        this.waitingTime=0;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }


    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }


    public long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(long arrivalTime)
    {
        this.arrivalTime=arrivalTime;
    }
    public int getProcessingTime()
    {
        return this.processingTime;
    }

    public int getID()
    {
        return this.ID;
    }

}
