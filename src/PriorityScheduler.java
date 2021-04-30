import java.util.ArrayList;

class PriorityScheduler {
    ArrayList<String> order = new ArrayList<String>();

    int[] calcWaitTime(Process[] process) {
        int t = 0;
        int doneExec = 0;
        int min = Integer.MAX_VALUE;
        int minPrioIndex = -1;
        int pMinPrioIndex = -1;
        boolean pArrived = false;
        boolean firstArrive = true;
        int[] processPriority = new int[process.length];
        int[] pWaitTime = new int[process.length];
        for (int i = 0; i < process.length; i++) {
            processPriority[i] = process[i].priority;
        }
        while (doneExec != process.length) {
            for (int i = 0; i < process.length; i++) {
                if ((process[i].arrivalTime <= t) &&
                        (processPriority[i] < min) && process[i].Remaining > 0) {
                    min = processPriority[i];
                    minPrioIndex = i;
                    pArrived = true;
//                    if (firstArrive) {
//                        pMinPrioIndex = minPrioIndex;
//                    }
//                    firstArrive = false;
                }
            }
            if (!pArrived) {
                t++;
                continue;
            }
            if (pMinPrioIndex != minPrioIndex) {
                if (pMinPrioIndex != -1) {
                    order.set(order.size() - 1, order.get(order.size() - 1) + "\t\t" + t);
                }
                order.add(process[minPrioIndex].name + "\t\t" + t);
                pMinPrioIndex = minPrioIndex;
            }
            if (t % 5 == 0) {

                for (int i = 0; i < process.length; i++) {
                    if ((process[i].arrivalTime <= t) &&
                            minPrioIndex != i &&
                        process[i].priority>0) {
                        process[i].priority--;
                    }
                }
            }
            process[minPrioIndex].Remaining--;
            if (process[minPrioIndex].Remaining == 0) {
                min = Integer.MAX_VALUE;
                doneExec++;
                pWaitTime[minPrioIndex] = (t + 1) - (process[minPrioIndex].arrivalTime + process[minPrioIndex].burstTime);

            }

            t++;
        }
        order.set(order.size() - 1, order.get(order.size() - 1) + "\t\t" + t);
        return pWaitTime;
    }

    public int[] calcTurnaroundTime(Process[] process, int[] pWaitTime) {
        int[] pTurnaroundTime = new int[process.length];
        for (int i = 0; i < process.length; i++) {
            pTurnaroundTime[i] = pWaitTime[i] + process[i].burstTime;
        }
        return pTurnaroundTime;
    }

    public void execOrder() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name\tFrom\tTo\n");
        for (String s :
                order) {
            sb.append(s);
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    double avgWT (int [] pWait,Process[] process){
        int avgW = 0;
        for (int i = 0; i < process.length; i++) {
            avgW += pWait[i];
        }
        return (double) avgW / (double) process.length;
    }

    double avgTAT(int[] pTat, Process[] process) {
        int avgTat = 0;
        for (int i = 0; i < process.length; i++) {
            avgTat += pTat[i];
        }
        return (double) avgTat / (double) process.length;
    }

}

