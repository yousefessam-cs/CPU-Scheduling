import java.util.ArrayList;
import java.util.List;

public class Process {
    public int burstTime, arrivalTime, ID, Remaining,queueNum,priority;
    private static int cid = 0;
    String name;
    public List<int[]> history ;

    Process(String name, int burstTime, int arrivalTime) {
        this.ID = cid++;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.name = name;
        history=new ArrayList<>();
        Remaining=burstTime;

    }
    Process(String name, int burstTime, int arrivalTime, int queueNum) {
        this.ID = cid++;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.name = name;
        this.queueNum=queueNum;
        history=new ArrayList<>();
        Remaining=burstTime;

    }
    Process(String name, int burstTime, int arrivalTime, int Priority,int dump) {
        this(name, burstTime, arrivalTime);
        this.priority = Priority;
    }
    public void print(int i){
        System.out.println(name+"\t\t"+history.get(i)[0]+"\t\t"+history.get(i)[1]);
    }
    public int waitTime(){
        int wait=arrivalTime,last=0;

        for (int i = 0; i < history.size(); i++) {
            if (i==0&&arrivalTime==history.get(i)[0]){
                wait=0;
                last=history.get(i)[1];
                continue;
            }
            if (last!=history.get(i)[0])
                wait+=(history.get(i)[0]-last);
            last=history.get(i)[1];
        }
        return wait;
    }
    public int TurnAroundTime(){
        return waitTime()+Remaining;
    }
}