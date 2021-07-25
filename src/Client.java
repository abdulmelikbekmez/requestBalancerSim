import java.util.ArrayList;

public class Client extends Thread {
    public int id;
    public int totalReq;
    public int prio;
    public int completedJobs;
    public long stepSize;
    public double jobDuration;
    public ArrayList<Request> requests;

    private int loopSize;

    private static int globalId = 0;

    public Client(int totalReq, int prio, int completedJobs, long stepSize, ArrayList<Request> requests,
            double jobDuration) {
        globalId++;
        this.id = globalId;
        this.jobDuration = jobDuration;
        this.totalReq = totalReq;
        this.loopSize = totalReq;
        this.stepSize = stepSize;
        this.prio = prio;
        this.completedJobs = completedJobs;
        this.requests = requests;
    }

    @Override
    public void run() {
        try {
            threadMethod();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void threadMethod() throws InterruptedException {

        for (int i = 0; i < loopSize; i++) {

            requests.add(new Request(jobDuration, this));
            Thread.sleep(stepSize);
        }
    }

    @Override
    public String toString() {
        return totalReq + "," + prio + "," + completedJobs;
    }

}
