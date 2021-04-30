import java.util.Scanner;

public class Main {
    public static Process[] getArr(int x){
        Process[] process;
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter Number of processes: ");
        int num= scanner.nextInt();
        scanner.skip("\n");
        process=new Process[num];
        for (int i = 0; i < num; i++) {
            System.out.println("Enter Name of process: ");
            String name=scanner.nextLine();
            System.out.println("Enter Burst Time: ");
            int BurstTime=scanner.nextInt();
            scanner.skip("\n");
            System.out.println("Enter Arrival Time: ");
            int ArrivalTime=scanner.nextInt();
            scanner.skip("\n");
            if (x==3){
                int Priority;
                System.out.println("Enter Priority: ");
                Priority=scanner.nextInt();
                scanner.skip("\n");
                process[i]=new Process(name,BurstTime,ArrivalTime,Priority,0);
                continue;
            }
            if (x==4){
                int QueueNumber;
                while (true) {
                    System.out.println("Enter Queue Number: ");
                    QueueNumber=scanner.nextInt();
                    scanner.skip("\n");
                    if (QueueNumber==1||QueueNumber==2) {
                        process[i]=new Process(name,BurstTime,ArrivalTime,QueueNumber);
                        break;
                    }
                    else{
                        System.out.println("Wrong Queue Number");
                        continue;
                    }
                }
                continue;
            }
            process[i]=new Process(name,BurstTime,ArrivalTime);
        }
        return process;
    }
    public static void Continue(){
        System.out.println("Do You Want To Continue: (y/n)");
        Scanner scanner= new Scanner(System.in);
        String Continue= scanner.nextLine();
        if (Continue.equalsIgnoreCase("y"))
            return;
        else
            System.exit(0);
    }
    public static void main(String[] args) {
//        Process[] process = {new Process("P1", 4, 0,1),
//                new Process("P2", 3, 0,1),
//                new Process("P3", 8, 0,2),
//                new Process("P4", 5, 10,1)};
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1- SFJ Scheduler.");
            System.out.println("2- RR Scheduler.");
            System.out.println("3- Priority Scheduler.");
            System.out.println("4- Multi Level Scheduler.");
            System.out.println("5- Exit.");
            int choice= scanner.nextInt();
            scanner.skip("\n");
            Process[] process;
            switch (choice){
                case 1:{
                    SJF sjf=new SJF();
                    process=getArr(0);
//                    process =new Process[ ]{new Process("1", 6, 1), new Process("2", 8, 1),
//                            new Process("3", 7, 2), new Process("4", 3, 3)};
                    System.out.println("Enter Context Switching: ");
                    int contextSwitching=scanner.nextInt();
                    scanner.skip("\n");
                    int[] pWait = sjf.calcWaitTime(process, contextSwitching);
                    int[] pTat = sjf.calcTurnaroundTime(process, pWait);
                    String[] order = sjf.getExecOrder(process, pTat);

                    System.out.println();
                    System.out.println("Order of Execution");
                    for (String pName :
                            order) {
                        System.out.print(pName + "\t");
                    }
                    System.out.println();
                    System.out.println();
                    System.out.println("Process\tWT\tTAT\t");
                    for (int i = 0; i < process.length; i++) {
                        System.out.println(process[i].name + "\t\t" + pWait[i] + "\t" + pTat[i]);
                    }
                    System.out.println();
                    System.out.println("Average Waiting Time: " + sjf.avgWT(pWait,process) );
                    System.out.println("Average Turnaround Time: " + sjf.avgTAT(pTat,process) );
                    System.out.println();
                    Continue();
                    break;
                }
                case 2:{
                    process=getArr(0);
                    System.out.println("Enter Context Switching: ");
                    int contextSwitching=scanner.nextInt();
                    scanner.skip("\n");

                    System.out.println("Enter Round Robin: ");
                    int RoundRobin=scanner.nextInt();
                    scanner.skip("\n");

                    RRSch rrSch = new RRSch(process, contextSwitching, RoundRobin);
                    rrSch.simulate();
                    rrSch.print();
                    System.out.println();
                    rrSch.WaitingTime();
                    System.out.println(rrSch.AverageWaitingTime());
                    System.out.println();
                    rrSch.TurnAroundTime();
                    System.out.println("Average Turnaround Time: " + rrSch.AverageTurnAroundTime());
                    System.out.println();
                    Continue();
                    break;
                }
                case 3:{
                    process=getArr(3);
//                    process =new Process[ ] {new Process("1", 3, 0, 3,0),
//                            new Process("2", 6, 1, 4,0), new Process("3", 1, 3, 9,0),
//                            new Process("4", 2, 2, 7,0), new Process("5", 4, 4, 8,0)};;
                    PriorityScheduler p=new PriorityScheduler();
                    int[] pWait = p.calcWaitTime(process);
                    int[] pTat = p.calcTurnaroundTime(process, pWait);
                    p.execOrder();
                    System.out.println();
                    System.out.println("Process\tWT\tTAT\t");
                    for (int i = 0; i < process.length; i++) {
                        System.out.println(process[i].name + "\t\t" + pWait[i] + "\t" + pTat[i]);
                    }
                    System.out.println();
                    System.out.println("Average Waiting Time: " + p.avgWT(pWait,process) );
                    System.out.println("Average Turnaround Time: " + p.avgTAT(pTat,process) );
                    System.out.println();

                    Continue();
                    break;
                }
                case 4:{
                    process=getArr(4);
                    System.out.println("Enter Round Robin: ");
                    int RoundRobin=scanner.nextInt();
                    scanner.skip("\n");

                    MultiLevelSch multiLevelSch = new MultiLevelSch(process, RoundRobin);
                    multiLevelSch.simulate();
                    multiLevelSch.print();
                    System.out.println();
                    multiLevelSch.WaitingTime();
                    System.out.println("Average Waiting Time: " + multiLevelSch.AverageWaitingTime());
                    System.out.println();
                    multiLevelSch.TurnAroundTime();
                    System.out.println("Average Turnaround Time: " + multiLevelSch.AverageTurnAroundTime());
                    System.out.println();
                    Continue();
                    break;
                }
                case 5: {
                    System.exit(0);
                    break;
                }
                default:{
                    System.out.println("Wrong Input.");
                }
            }
        }
    }
}
