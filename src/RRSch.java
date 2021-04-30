import java.util.*;

public class RRSch {
    private List<Process> processes;
    int contextSwitching, RoundRobin,endTime;
    private Queue<Process> queue;


    public RRSch(Process[] processes, int contextSwitching, int RoundRobin) {
        this.processes = Arrays.asList(processes.clone());
        this.queue = new LinkedList<Process>() ;
        this.contextSwitching=contextSwitching;
        this.RoundRobin=RoundRobin;
    }
    public void simulate(){
        int startTime=0;
        endTime=0;
        for (int i = 0; i < processes.size(); i++) {
            if (processes.get(i).arrivalTime==startTime)
                queue.add(processes.get(i));
            endTime+=processes.get(i).burstTime+processes.get(i).arrivalTime;
        }
        endTime+=contextSwitching*processes.size();

        int index;

        for (int x = 0; x < endTime; x++) {
            boolean temp=false;
            if (!queue.isEmpty()){
                index=processes.indexOf(queue.remove());
                int [] startEnd =new int[2];
                startEnd[0]=startTime;

                for (int i = 0; i < RoundRobin; i++) {
                    if (processes.get(index).burstTime==0)
                        break;
                    processes.get(index).burstTime--;
                    startTime++;
                    for (int j = 0; j < processes.size(); j++) {
                        if (processes.get(j).arrivalTime==startTime)
                            queue.add(processes.get(j));
                    }
                    temp=true;
                }
                startEnd[1]=startTime;
                processes.get(index).history.add(startEnd);
                if (processes.get(index).burstTime>0)
                    queue.add(processes.get(index));
                for (int i = 0; i <contextSwitching; i++) {
                    for (int j = 0; j < processes.size(); j++) {
                        if (processes.get(j).arrivalTime==startTime)
                            queue.add(processes.get(j));
                    }
                }
            }
            if (temp==false) {
                startTime++;
                for (int i = 0; i < processes.size(); i++) {
                    if (processes.get(i).arrivalTime == startTime)
                        queue.add(processes.get(i));
                }
            }
        }
    }
    public void print(){
        int startTime=0;
        System.out.println("Execution Order:");
        System.out.println("Name\tStarts\tEnds");
        for (int i = 0; i < endTime; i++) {
            for (int j = 0; j < processes.size(); j++) {
                for (int k = 0; k < processes.get(j).history.size(); k++) {
                    if (processes.get(j).history.get(k)[0]==i) {
                        startTime = processes.get(j).history.get(k)[1] + contextSwitching;
                        processes.get(j).print(k);
                        i=startTime-1;
                    }
                }
            }
        }
    }
    public void WaitingTime(){
        System.out.println("Waiting Time:");
        System.out.println("Name\tTime");
        for (Process p: processes) {
            System.out.println(p.name+"\t\t"+p.waitTime());
        }
    }
    public double AverageWaitingTime(){
        double sum=0;
        for (Process p: processes) {
            sum+=p.waitTime();
        }
        return sum/(double)processes.size();
    }
    public void TurnAroundTime(){
        System.out.println("Turn Around Time:");
        System.out.println("Name\tTime");
        for (Process p: processes) {
            System.out.println(p.name+"\t\t"+p.TurnAroundTime());
        }
    }
    public double AverageTurnAroundTime(){
        double sum=0;
        for (Process p: processes) {
            sum+=p.TurnAroundTime();
        }
        return sum/(double)processes.size();
    }
}
