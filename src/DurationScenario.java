import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class DurationScenario {
    static ArrayList<Request> requests;

    static Random random = new Random();
    static ArrayList<Client> clients;

    public static void main(String[] args) throws IOException {
        clients = new ArrayList<>();
        requests = new ArrayList<>();
        Client c1 = new Client(500, 1, 0, 10, requests, 10);
        Client c2 = new Client(5, 1, 0, 1000, requests, 10);
        clients.add(c1);
        clients.add(c2);
        RequestAssigner assigner = new RequestAssigner(requests, 20, clients);
        for (Client client : clients) {
            client.start();
        }
        assigner.start();
    }

}
