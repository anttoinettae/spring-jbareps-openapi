package com.anttoinettae.observers.Client;

import com.anttoinettae.exceptions.NullClientException;

public interface ClientObservable {
    void addSubscriber(ClientObserver subscriber) throws NullClientException;
    void notify(String message);
}
