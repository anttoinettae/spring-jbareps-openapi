package com.anttoinettae.repositories;

import com.anttoinettae.entities.Bank;
import com.anttoinettae.exceptions.AbsentBankException;
import com.anttoinettae.exceptions.NullBankException;
import com.anttoinettae.exceptions.NullIdException;
import com.anttoinettae.exceptions.SameBankException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankRepository {
    private List<Bank> banks = new ArrayList<>();

    public void addBank(Bank bank) throws NullBankException, SameBankException {
        if (bank == null)
            throw new NullBankException("u tried to add null bank to a repository");
        if (banks.contains(bank))
            throw new SameBankException("repository already has that bank");
        banks.add(bank);
    }

    public void removeBank(UUID bankId) throws AbsentBankException, NullIdException {
        if (bankId == null)
            throw new NullIdException("u tried to remove bank with null id from a repository");
        Bank tempBank = getBankById(bankId);
        if (tempBank == null)
            throw new AbsentBankException("u tried to remove an absent bank from a repository");
        this.banks.remove(tempBank);
    }

    public Bank getBankById(UUID bankId) throws NullIdException, AbsentBankException {
        if (bankId == null)
            throw new NullIdException("u tried to find bank with null id in a repository");
        for (Bank bank : banks) {
            if(bank.id.equals(bankId)) return bank;
        }
        throw new AbsentBankException("there is no bank with that id");
    }

    public boolean contains(Bank bank) throws NullBankException {
        if (bank == null)
            throw new NullBankException("u tried to check null bank in a repository");
        return banks.contains(bank);
    }

    public List<Bank> getAllBanks(){
        return banks;
    }
}
