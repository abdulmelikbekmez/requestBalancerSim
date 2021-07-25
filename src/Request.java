import java.util.Date;

public class Request {
    public int id;
    public long waitingTime;
    public long arrivalTime;
    public double duration;
    public Client owner;
    private static int globalId;

    public Request(double duration, Client owner) {
        this.id = globalId;
        this.waitingTime = 0;
        this.duration = duration;
        this.owner = owner;
        this.arrivalTime = new Date().getTime();
        globalId++;
    }

    public void updateWaitingTime() {
        long currentTime = new Date().getTime();
        waitingTime = currentTime - arrivalTime;
    }

    public double getWeight(double timeConst, double prioConst, double totalReqConst, double durationConst,
            double compJobConst) {
        return waitingTime * timeConst * owner.prio * prioConst * (1
                / (owner.totalReq * totalReqConst * duration * durationConst * compJobConst * owner.completedJobs));
    }

    @Override
    public String toString() {
        return owner.id + "," + this.waitingTime + "," + this.duration + "," + owner.toString();
    }
}