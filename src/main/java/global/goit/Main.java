package global.goit;

import global.goit.entity.Client;
import global.goit.service.ClientService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        ClientService clientService = new ClientService();

        long clientId = clientService.create("Vitaliy Volochay");

        System.out.println("Client with ID " + clientId + ": " + clientService.getById(clientId));

        List<Client> clients = clientService.listAll();
        System.out.println("All clients: " + "\n" + clients);

        clientService.setName(clientId, "Vitaliy Mazepa");
        System.out.println("Updated client with ID " + clientId + ": " + clientService.getById(clientId));

        clientService.deleteById(clientId);
        System.out.println("All clients after deletion: " + "\n" + clientService.listAll());


    }
}
