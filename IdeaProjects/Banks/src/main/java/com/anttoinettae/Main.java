package com.anttoinettae;

import com.anttoinettae.console.Console;
import com.anttoinettae.entities.Bank;
import com.anttoinettae.entities.Client;
import com.anttoinettae.exceptions.*;
import com.anttoinettae.models.Account;
import com.anttoinettae.services.CentralBank;
import com.anttoinettae.tools.ClientBuilder;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        CentralBank cb = new CentralBank();
        BigDecimal ccConst = new BigDecimal(50);
        int diConst = 2;
        int sdiConst = 3;
        int mdiConst = 4;
        int ldiConst = 5;
        Scanner scanner = new Scanner(System.in);
        Console.loading();

        Console.printMessage("hiiii so now you are a bank system!!! what do you wanna do?\n");

        while (true){

            Console.printDefaultOptions();
            int number = scanner.nextInt();
            scanner.nextLine();

            switch (number){

                case (1):
                    Console.printMessage("Cool!! now you need to give your bank a name!");
                    Console.printQuestion("Please name your bank: ");
                    String name = scanner.nextLine();
                    Console.printMessage("What a beautiful name! alright, do you wanna leave default settings or fill them in manually?\nIt might take a while...");

                    while (true){
                        Console.printQuestion("Give an answer (YES/NO): ");
                        String answer = scanner.nextLine();

                        switch (answer) {
                            case ("YES"):
                                Console.printMessage("okayyy let's go.");

                                Console.printQuestion("\nEnter the credit commission: ");
                                BigDecimal creditCommission = scanner.nextBigDecimal();
                                scanner.nextLine();

                                Console.printQuestion("\nEnter the debit interest: ");
                                int debitInterest = scanner.nextInt();
                                scanner.nextLine();

                                Console.printQuestion("\nEnter the small deposit interest: ");
                                int smallDepositInterest = scanner.nextInt();
                                scanner.nextLine();

                                Console.printQuestion("\nEnter the middle deposit interest: ");
                                int middleDepositInterest = scanner.nextInt();
                                scanner.nextLine();

                                Console.printQuestion("\nEnter the large deposit interest: ");
                                int largeDepositInterest = scanner.nextInt();
                                scanner.nextLine();

                                try {
                                    cb.addBank(name, creditCommission, debitInterest, smallDepositInterest, middleDepositInterest, largeDepositInterest);
                                } catch (InvalidQuantityException | NullNameException e){
                                    Console.printError("To create a bank you need to enter not null name for it and carefully enter its fields." +
                                                       "\nYou couldn't do that. Please, use default settings.");
                                    continue;
                                }

                                Console.printSystemMessage(String.format("Bank %s has been created", name));
                                break;

                            case ("NO"):
                                Console.printMessage("smart decision ;)");

                                try {
                                    cb.addBank(name, ccConst, diConst, sdiConst, mdiConst, ldiConst);
                                } catch (NullNameException e){
                                    Console.printError("Please, enter not null name.");
                                    continue;
                                } catch (InvalidQuantityException e){
                                    Console.printUnexpectedError();
                                }

                                Console.printSystemMessage(String.format("Bank %s has been created", name));
                                break;

                            default:
                                Console.printError("GIVE YES OR NO");
                                continue;
                        }
                        break;
                    }
                    break;

                case (2):

                    Console.printMessage("Client it is! First of all, let us check something");
                    if (!cb.thereIsBank()){
                        Console.printError("You need at least one bank to create a client");
                        break;
                    }

                    Console.printMessage("""
                        All clean, you can create a client!
                        Now this can be tricky: you will need to choose bank by it's id
                        We recommend copy ids that will appear here
                        """);

                    Bank bankForClient = Console.letSelectBank(cb, scanner);
                    ClientBuilder clientBuilder = new ClientBuilder();

                    String clientsName;
                    while (true){
                        Console.printQuestion("Enter the client's name: ");
                        clientsName = scanner.nextLine();
                        try {
                            clientBuilder.withName(clientsName);
                            break;
                        } catch (NullNameException e){
                            Console.printError("Name of the client can't be empty");
                        }
                    }

                    String clientsSurname;
                    while (true){
                        Console.printQuestion("Enter the client's surname: ");
                        clientsSurname = scanner.nextLine();
                        try {
                            clientBuilder.withSurname(clientsSurname);
                            break;
                        } catch (NullSurnameException e){
                            Console.printError("Surname of the client can't be empty");
                        }
                    }

                    Console.printMessage("Now, do you wanna specify passport and address? With it you wouldn't have limit on transactions");

                    while (true){
                        Console.printQuestion("Give an answer (YES/NO): ");
                        String answer = scanner.nextLine();

                        switch (answer){

                            case ("YES"):

                                String clientsPassport;
                                while (true){
                                    Console.printQuestion("Enter the client's passport in '**** ******' format: ");
                                    clientsPassport = scanner.nextLine();
                                    try {
                                        clientBuilder.withPassport(clientsPassport);
                                        break;
                                    } catch (InvalidPassportException e){
                                        Console.printError("Invalid passport");
                                    }
                                }

                                String clientsAddress;
                                while (true){
                                    Console.printQuestion("Enter the client's address: ");
                                    clientsAddress = scanner.nextLine();
                                    try {
                                        clientBuilder.withAddress(clientsAddress);
                                        break;
                                    } catch (NullAddressException e){
                                        Console.printError("Address of the client can't be empty");
                                    }
                                }
                                break;

                            case ("NO"):
                                Console.printMessage("Okay, it's your choice");
                                break;

                            default:
                                Console.printError("GIVE YES OR NO");
                                continue;
                        }
                        break;
                    }
                    Client newClient = clientBuilder.build();

                    try {
                        cb.addClient(bankForClient, newClient);
                        Console.printSystemMessage(String.format("Client %s %s has been added to bank %s", newClient.name, newClient.surname, bankForClient.name));
                    } catch (Exception e){
                        Console.printUnexpectedError();
                    }

                    break;

                case (3):

                    Console.printMessage("Account it is! First of all, let us check something");
                    if (!cb.thereIsBank() || !cb.thereIsClient()){
                        Console.printError("You need at least one bank and at least one client in it to create an account.");
                        break;
                    }

                    Console.printMessage("""
                        All clean, you can create an account!
                        Now this can be tricky: you will need to choose bank and client by their ids
                        We recommend copy ids that will appear here
                        """);

                    Bank bankForAccount = Console.letSelectBank(cb, scanner);
                    if (bankForAccount.clients.getAllClients().size() == 0){
                        Console.printError("That bank doesn't have clients yet. Please, choose a different bank or consider adding more clients");
                        break;
                    }
                    Client client = Console.letSelectClient(bankForAccount, scanner);

                    Console.printAccountOptions();
                    int accountType = scanner.nextInt();
                    scanner.nextLine();

                    switch (accountType){

                        case(1):
                            try {
                                cb.addCreditAccountToClient(bankForAccount, client);
                                Console.printSystemMessage(String.format("Credit account has been added to bank %s by %s", bankForAccount.name, client.name));
                                break;
                            } catch (Exception e){
                                Console.printUnexpectedError();
                            }

                        case(2):
                            try {
                                cb.addDebitAccountToClient(bankForAccount, client);
                                Console.printSystemMessage(String.format("Debit account has been added to bank %s by %s", bankForAccount.name, client.name));
                                break;
                            } catch (Exception e){
                                Console.printUnexpectedError();
                            }

                        case(3):
                            try {
                                cb.addDepositAccountToClient(bankForAccount, client);
                                Console.printSystemMessage(String.format("Deposit account has been added to bank %s by %s", bankForAccount.name, client.name));
                                break;
                            } catch (Exception e){
                                Console.printUnexpectedError();
                            }

                        default:
                            Console.printMessage("okie-dokie");
                            break;
                    }
                    break;

                case (4):
                    Console.printMessage("Cash it is! First of all, let us check something");
                    if (!cb.thereIsBank() || !cb.thereIsClient() || !cb.thereIsAccount()){
                        Console.printError("You need at least one account to operate its balance");
                        break;
                    }

                    Console.printMessage("""
                        All clean, you can operate with cash!
                        Now this can be tricky: you will need to choose bank, client and account by their ids
                        We recommend copy ids that will appear here
                        """);

                    Bank bankForAccountToOperate = Console.letSelectBank(cb, scanner);
                    if (bankForAccountToOperate.clients.getAllClients().size() == 0){
                        Console.printError("That bank doesn't have clients yet. Please, choose a different bank or consider adding more clients");
                        break;
                    }

                    Client clientForAccountToOperate = Console.letSelectClient(bankForAccountToOperate, scanner);
                    if (clientForAccountToOperate.accounts.getAllAccounts().size() == 0){
                        Console.printError("That client doesn't have accounts yet. Please, choose a different client or consider adding more accounts");
                        break;
                    }

                    Account accountToOperate = Console.letSelectAccount(clientForAccountToOperate, scanner);

                    while (true){
                        Console.printQuestion("Enter the amount of money you want to put into the account. To withdraw money, enter a negative amount: ");

                        BigDecimal amountOfCash = scanner.nextBigDecimal();
                        scanner.nextLine();

                        try {
                            cb.changeBalance(accountToOperate, amountOfCash);
                            Console.printSystemMessage(String.format("The balance of the %s's account has been changed", clientForAccountToOperate.name));
                            break;
                        } catch (InvalidQuantityException e) {
                            Console.printError("This account can't provide this amount of money");
                        } catch (NotReliableException e) {
                            Console.printError("This client hasn't been approved. You can't change his balance over the limit of 1000 money");
                        } catch (Exception e){
                            Console.printUnexpectedError();
                        }
                    }
                    break;

                case (5):

                    Console.printMessage("Transfer it is! First of all, let us check something");
                    if (!cb.thereIsBank() || !cb.thereIsClient() || !cb.thereAreAccounts()){
                        Console.printError("You need at least two accounts to transfer money");
                        break;
                    }

                    Console.printMessage("""
                        All clean, you can transfer money!
                        Now this can be tricky: you will need to choose bank, client and two accounts by their ids
                        We recommend copy ids that will appear here
                        """);

                    Bank bankForSender = Console.letSelectBank(cb, scanner);
                    if (bankForSender.clients.getAllClients().size() == 0){
                        Console.printError("That bank doesn't have clients yet. Please, choose a different bank or consider adding more clients");
                        break;
                    }

                    Client sender = Console.letSelectClient(bankForSender, scanner);
                    if (sender.accounts.getAllAccounts().size() == 0){
                        Console.printError("That client doesn't have accounts yet. Please, choose a different client or consider adding more accounts");
                        break;
                    }

                    Account senderAccount = Console.letSelectAccount(sender, scanner);

                    Bank bankForReceiver = Console.letSelectBank(cb, scanner);
                    if (bankForReceiver.clients.getAllClients().size() == 0){
                        Console.printError("That bank doesn't have clients yet. Please, choose a different bank or consider adding more clients");
                        break;
                    }

                    Client receiver = Console.letSelectClient(bankForReceiver, scanner);
                    if (receiver.accounts.getAllAccounts().size() == 0){
                        Console.printError("That client doesn't have accounts yet. Please, choose a different client or consider adding more accounts");
                        break;
                    }

                    Account receiverAccount = Console.letSelectAccount(receiver, scanner);

                    while (true){
                        Console.printQuestion("Enter the amount of money you want to transfer: ");

                        BigDecimal amountOfMoney = scanner.nextBigDecimal();
                        scanner.nextLine();

                        try {
                            cb.transferMoney(senderAccount, receiverAccount, amountOfMoney);
                            Console.printSystemMessage(String.format("Client %s transferred %s money to client %s", sender.name, amountOfMoney.toString(), receiver.name));
                            break;
                        } catch (InvalidQuantityException e) {
                            Console.printError("This account can't provide this amount of money");
                        } catch (NotReliableException e) {
                            Console.printError("This client hasn't been approved. You can't change his balance over the limit of 1000 money");
                        } catch (Exception e){
                            Console.printUnexpectedError();
                        }
                    }
                    break;

                case (6):

                    Console.printMessage("""
                                         Alright, let's try that!
                                         You can add days, month or years and then check how much balances had changed :)
                                         """);

                    while (true){
                        Console.printQuestion("What objects of the time do you wanna add (DAY/MONTH/YEAR): ");
                        String answer = scanner.nextLine();

                        switch (answer){

                            case ("DAY"):
                                Console.printQuestion("Input the quantity of days to add: ");
                                int qd = scanner.nextInt();
                                scanner.nextLine();

                                cb.timeManager.addDays(qd);
                                break;

                            case ("MONTH"):
                                Console.printQuestion("Input the quantity of months to add: ");
                                int qm = scanner.nextInt();
                                scanner.nextLine();

                                cb.timeManager.addDays(qm);
                                break;

                            case ("YEAR"):
                                Console.printQuestion("Input the quantity of years to add: ");
                                int qy = scanner.nextInt();
                                scanner.nextLine();

                                cb.timeManager.addDays(qy);
                                break;

                            default:
                                Console.printError("DAY MONTH OR YEAR WHAT IS WRONG WITH YOU");
                                continue;

                        }
                        break;
                    }
                    break;

                case (7):
                    Console.printMessage("Yes, let's check some money!! First? let us check something.");
                    if (!cb.thereIsBank()){
                        Console.printError("You need at least one bank to check accounts balances in it");
                        break;
                    }

                    Console.printMessage("""
                        All clean, you can check balances!
                        Now this can be tricky: you will need to choose bank by its id.
                        We recommend copy ids that will appear here
                        """);

                    Bank bankForChecking = Console.letSelectBank(cb, scanner);

                    Console.printAllAccounts(bankForChecking);

                    Console.printMessage("That's all, thanks for being interested");

                    break;

                default:
                    Console.printBye();
            }
        }
    }
}