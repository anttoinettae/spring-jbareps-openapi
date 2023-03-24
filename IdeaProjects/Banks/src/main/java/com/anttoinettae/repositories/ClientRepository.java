package com.anttoinettae.repositories;

import com.anttoinettae.entities.Client;
import com.anttoinettae.exceptions.AbsentClientException;
import com.anttoinettae.exceptions.NullClientException;
import com.anttoinettae.exceptions.NullIdException;
import com.anttoinettae.exceptions.SameClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientRepository {
    private List<Client> clients = new ArrayList<>();

    public void addClient(Client client) throws NullClientException, SameClientException {
        if (client == null)
            throw new NullClientException("u tried to add null client to a repository");
        if (clients.contains(client))
            throw new SameClientException("u tried to add existing client to a repository");
        clients.add(client);
    }

    public void removeClient(UUID clientId) throws NullIdException, AbsentClientException {
        if (clientId == null)
            throw new NullIdException("u tried to remove a client with null id from a repository");
        Client tempClient = getClientById(clientId);
        if (tempClient == null)
            throw new AbsentClientException("u tried to remove an absent client from a repository");
        this.clients.remove(tempClient);
    }

    public Client getClientById(UUID clientId) throws NullIdException, AbsentClientException {
        if (clientId == null)
            throw new NullIdException("u tried to find client with null id in a repository");
        for (Client client : clients) {
            if(client.id.equals(clientId)) return client;
        }
        throw new AbsentClientException("there is no client with that id");
    }

    public boolean contains(Client client) throws NullClientException {
        if (client == null)
            throw new NullClientException("u tried to check null client in a repository");
        return clients.contains(client);
    }
    public List<Client> getAllClients(){
        return new ArrayList<>(clients);
    }
}
