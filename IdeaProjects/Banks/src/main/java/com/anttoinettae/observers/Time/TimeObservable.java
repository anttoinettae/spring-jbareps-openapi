package com.anttoinettae.observers.Time;

import com.anttoinettae.exceptions.NullBankException;

public interface TimeObservable {
    void addSubscriber(TimeObserver subscriber) throws NullBankException;
    void notify(Integer howMuchDaysToSkip);
}
