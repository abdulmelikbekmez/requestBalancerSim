import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class RequestAssigner extends Thread {
    ArrayList<Request> requests;
    ArrayList<Client> clients;
    private double prioConst;
    private double totalReqConst;
    private double durationConst;
    private double compJobConst;
    private double timeConst;
    public long stepSize;
    private FileWriter out;

    public RequestAssigner(ArrayList<Request> requests, long stepSize, ArrayList<Client> clients) throws IOException {
        super();
        this.requests = requests;
        this.stepSize = stepSize;
        this.clients = clients;
        out = new FileWriter("cikti.csv");
    }

    @Override
    public void run() {
        try {
            threadMethod();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException b) {
            b.printStackTrace();
        }
    }

    private boolean getLoopCondition() {
        if (requests.size() > 0) {
            return true;
        }
        for (Client client : clients) {
            if (client.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private void threadMethod() throws IOException, InterruptedException {
        out.write(
                "\"weight\",\"client id\",\"total waiting time\",\"duration\",\"total request\",\"priority\",\"completed jobs\",\"time\"\n");
        while (getLoopCondition()) {
            if (requests.size() < 1) {
                continue;
            }
            double max = -1;
            int index = 0;

            for (int i = 0; i < requests.size(); i++) {

                prioConst = 1 + Math.pow(requests.get(i).owner.prio, 2);
                totalReqConst = 1 + Math.pow(requests.get(i).owner.totalReq, 1.5);
                durationConst = 1 + Math.pow(requests.get(i).duration, 0.25);
                compJobConst = 1 + Math.pow(requests.get(i).owner.completedJobs, 1);
                timeConst = 1 + Math.pow(requests.get(i).waitingTime, 2);
                requests.get(i).updateWaitingTime();
                double weight = requests.get(i).getWeight(timeConst, prioConst, totalReqConst, durationConst,
                        compJobConst);
                if (weight > max) {
                    max = weight;
                    index = i;
                }

            }
            requests.get(index).owner.totalReq--;
            requests.get(index).owner.completedJobs++;

            Request maxWeightedReq = requests.get(index);
            requests.remove(maxWeightedReq);
            out.write(max + "," + maxWeightedReq.toString() + "," + new Date().getTime() + "\n");
            System.out.println(maxWeightedReq.toString());
            Thread.sleep(stepSize);
        }
        out.close();
    }

}
