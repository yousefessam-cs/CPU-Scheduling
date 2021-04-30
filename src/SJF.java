import java.util.Arrays;

public class SJF {


    public int[] calcWaitTime(Process[] process, int contextTime) {
        int t = 0;
        int doneExec = 0;
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        int pMinIndex = -1;
        boolean pArrived = false;
        boolean firstArrive = true;
        int[] pRemainingTime = new int[process.length];
        int[] pWaitTime = new int[process.length];
        for (int i = 0; i < process.length; i++) {
            pRemainingTime[i] = process[i].burstTime;
        }
        while (doneExec != process.length) {
            for (int i = 0; i < process.length; i++) {
                if ((process[i].arrivalTime <= t) &&
                        (pRemainingTime[i] < min) && pRemainingTime[i] > 0) {
                    min = pRemainingTime[i];
                    minIndex = i;
                    pArrived = true;
                    if (firstArrive) {
                        pMinIndex = minIndex;
                    }
                    firstArrive = false;
                }
            }
            if (!pArrived) {
                t++;
                continue;
            }

            if (pMinIndex != minIndex) {
                t += contextTime;

            }

            pRemainingTime[minIndex]--;
            min--;
            if (pRemainingTime[minIndex] == 0) {
                min = Integer.MAX_VALUE;
                doneExec++;
                pWaitTime[minIndex] = (t+1) - (process[minIndex].arrivalTime + process[minIndex].burstTime);

            }
            pMinIndex = minIndex;
            t++;
        }
        return pWaitTime;
    }

    public static int[] calcTurnaroundTime(Process[] process, int[] pWaitTime) {
        int[] pTurnaroundTime = new int[process.length];
        for (int i = 0; i < process.length; i++) {
            pTurnaroundTime[i] = pWaitTime[i] + process[i].burstTime;
        }
        return pTurnaroundTime;
    }

    public static String[] getExecOrder(Process[] process, int[] pTurnaroundTime) {
        int minIndex = 0;
        int[] ptat = Arrays.copyOf(pTurnaroundTime, pTurnaroundTime.length);
        String[] execOrder = new String[process.length];
        for (int i = 0; i < process.length; i++) {
            int minfTime = Integer.MAX_VALUE;
            for (int j = 0; j < process.length; j++) {
                int fTime = process[j].arrivalTime + ptat[j];
                if (fTime < minfTime && ptat[j] != -1) {
                    minIndex = j;
                    minfTime = process[minIndex].arrivalTime + ptat[minIndex];
                }
            }
            execOrder[i] = process[minIndex].name;
            ptat[minIndex] = -1;
        }
        return execOrder;
    }
    double avgWT (int [] pWait,Process[] process){
        int avgW = 0;
        for (int i = 0; i < process.length; i++) {
            avgW += pWait[i];
        }
        return (double)avgW / (double)process.length;
    }
    double avgTAT (int [] pTat,Process[] process){
        int avgTat = 0;
        for (int i = 0; i < process.length; i++) {
            avgTat += pTat[i];
        }
        return (double)avgTat / (double)process.length;
    }

}