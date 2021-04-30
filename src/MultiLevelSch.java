import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MultiLevelSch {
    private List<Process> processes;
    int RoundRobin,endTime;
    private Queue<Process> queue1;
    private LinkedList<Process> queue2;

    MultiLevelSch(Process[] processes, int RoundRobin){
        this.processes = Arrays.asList(processes.clone());
        this.queue1 = new LinkedList<Process>() ;
        this.queue2 = new LinkedList<Process>() ;
        this.RoundRobin=RoundRobin;
    }
    public void simulate(){
        int startTime=0;
        endTime=0;
        for (int i = 0; i < processes.size(); i++) {
            if (processes.get(i).arrivalTime==startTime&&
            processes.get(i).queueNum==1)
                queue1.add(processes.get(i));
            else if (processes.get(i).arrivalTime==startTime&&
                    processes.get(i).queueNum==2)
                queue2.add(processes.get(i));
            endTime+=processes.get(i).burstTime+processes.get(i).arrivalTime;
        }
        int index;

        for (int i = 0; i < endTime; i++) {
            boolean temp=false;
            if (!queue1.isEmpty()){
                index=processes.indexOf(queue1.remove());
                int [] startEnd =new int[2];
                startEnd[0]=startTime;

                for (int k = 0; k < RoundRobin; k++) {
                    if (processes.get(index).burstTime==0)
                        break;
                    processes.get(index).burstTime--;
                    startTime++;
                    for (int j = 0; j < processes.size(); j++) {
                        if (processes.get(j).arrivalTime==startTime&&
                        processes.get(j).queueNum==1)
                            queue1.add(processes.get(j));
                        else if (processes.get(j).arrivalTime==startTime&&
                                processes.get(j).queueNum==2)
                            queue2.add(processes.get(i));
                    }
                    temp=true;
                }
                startEnd[1]=startTime;
                processes.get(index).history.add(startEnd);
                if (processes.get(index).burstTime>0)
                    queue1.add(processes.get(index));
            }
            else if(!queue2.isEmpty()){
                index=processes.indexOf(queue2.remove());
                int [] startEnd =new int[2];
                startEnd[0]=startTime;
                while(processes.get(index).burstTime!=0){

                    if (!queue1.isEmpty()) {
                        queue2.addFirst(processes.get(index));
                        break;
                    }
                    startTime++;
                    processes.get(index).burstTime--;

                    for (int j = 0; j < processes.size(); j++) {
                        if (processes.get(j).arrivalTime==startTime&&
                                processes.get(j).queueNum==1)
                            queue1.add(processes.get(j));
                        else if (processes.get(j).arrivalTime==startTime&&
                                processes.get(j).queueNum==2)
                            queue2.add(processes.get(i));
                    }
                    temp=true;
                }
                startEnd[1]=startTime;
                processes.get(index).history.add(startEnd);
            }
            if (temp==false) {
                startTime++;
                for (int j = 0; j < processes.size(); j++) {
                    if (processes.get(j).arrivalTime==startTime&&
                            processes.get(j).queueNum==1)
                        queue1.add(processes.get(j));
                    else if (processes.get(j).arrivalTime==startTime&&
                            processes.get(j).queueNum==2)
                        queue2.add(processes.get(i));
                }
            }
        }
    }
    public void print(){
        int startTime=0;
        System.out.println("Execution Order:");
        System.out.println("Name\tStarts\tEnds");
        for (int i = 0; i < endTime; i++) {
            boolean temp=false;
            for (int j = 0; j < processes.size(); j++) {
                for (int k = 0; k < processes.get(j).history.size(); k++) {
                    if (processes.get(j).history.get(k)[0]==i) {
                        startTime = processes.get(j).history.get(k)[1] ;
                        processes.get(j).print(k);
                        temp=true;
                    }
                }
            }
            if (temp)
                i=startTime-1;
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
