package com.anttoinettae.timetools;

import com.anttoinettae.exceptions.NullBankException;
import com.anttoinettae.observers.Time.TimeObservable;
import com.anttoinettae.observers.Time.TimeObserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeManager implements TimeObservable {
    public List<TimeObserver> timeSubscribers = new ArrayList<>();
    public LocalDate currentDateTime = LocalDate.now();

    public void addDays(Integer howMuch)
    {
        currentDateTime = currentDateTime.plusDays(howMuch);
        notify(howMuch);
    }

    public void addMonths(Integer howMuch)
    {
        currentDateTime = currentDateTime.plusMonths(howMuch);
        notify(30 * howMuch);
    }

    public void addYears(Integer howMuch)
    {
        currentDateTime = currentDateTime.plusYears(howMuch);
        notify(365 * howMuch);
    }

    public void addSubscriber(TimeObserver subscriber) throws NullBankException {
        if (subscriber == null)
            throw new NullBankException("u tried to subscribe null bank");
        timeSubscribers.add(subscriber);
    }

    public void notify(Integer howMuchDaysToSkip)
    {
        for (TimeObserver timeSubscriber : timeSubscribers) {
            timeSubscriber.notify(howMuchDaysToSkip);
        }
    }
}
