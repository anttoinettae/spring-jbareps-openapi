package com.anttoinettae.console;

import com.anttoinettae.entities.Bank;
import com.anttoinettae.entities.Client;
import com.anttoinettae.models.Account;
import com.anttoinettae.services.CentralBank;

import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * The class for the communication with user. Specialises on different colours of the messages and showing data with special methods
 */
public abstract class Console {
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static void loading() throws InterruptedException {
        System.out.println(ANSI_WHITE + "   ▐▀▄      ▄▀▌   ▄▄▄▄▄▄▄");
        System.out.println("   ▌▒▒▀▄▄▄▄▄▀▒▒▐▄▀▀▒██▒██▒▀▀▄");
        System.out.println("  ▐▒▒▒▒▀▒▀▒▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀▄");
        System.out.println("  ▌▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▄▒▒▒▒▒▒▒▒▒▒▒▒▀▄");
        System.out.println("▀█▒▒▒" + ANSI_CYAN +"█▌"+ ANSI_WHITE +"▒▒█▒▒" + ANSI_CYAN + "▐█"+ ANSI_WHITE +"▒▒▒▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▌");
        System.out.println("▀▌▒▒▒▒▒▒▀▒▀▒▒▒▒▒▒▀▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐   ▄▄");
        System.out.println("▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▌▄█▒█");
        System.out.println("▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█▒█▀");
        System.out.println("▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█▀");
        System.out.println("▐▒▒▒▒▒▒▒▒▒▒▒▒▒" + ANSI_CYAN + "BANKS" + ANSI_WHITE + "▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▌");
        System.out.println(" ▌▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐");
        System.out.println(" ▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▌");
        System.out.println("  ▌▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐");
        System.out.println("  ▐▄▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▄▌");
        System.out.println("    ▀▄▄▀▀▀▀▀▄▄▀▀▀▀▀▀▀▄▄▀▀▀▀▀▄▄▀");
        System.out.print("\n");
        TimeUnit.SECONDS.sleep(1);
        for (int i = 0; i < 16; i++) {
            System.out.print(ANSI_CYAN + "▒▒");
            TimeUnit.NANOSECONDS.sleep(100000000);
        }
        System.out.print("\r" + " ");
    }

    public static void printMessage(String string){
        System.out.println("\n" + ANSI_CYAN + string);
    }
    public static void printQuestion(String string){
        System.out.print(ANSI_GREEN + string);
    }
    public static void printOptions(String string){
        System.out.println(ANSI_YELLOW + string);
    }
    public static void printDefaultOptions(){
        printOptions("""
                1) Create a bank!
                2) Create a client!
                3) Create an account!
                
                4) Fill someone's balance with cash or withdraw it!
                5) Transfer money!
                
                6) Control the time! (not the universe)
                7) Check everyone's balances!
                8 or else) Quit
                
                """);
        printQuestion("Please enter a number: ");
    }
    public static void printAccountOptions(){
        printOptions("""
                1) Credit
                2) Debit
                3) Deposit
                4 or else) I changed my mind
                
                """);
        printQuestion("Please enter a number: ");
    }
    public static void printError(String string){
        System.out.println("\n" + ANSI_RED + string);
    }
    public static void printUnexpectedError(){
        System.out.println("\n" + ANSI_RED + "wow sorry that SHOULD NOT have happened. no way. we gonna break. please come back after the next release.");
        System.exit(566);
    }
    public static void printSystemMessage(String string){
        System.out.println("\n" + ANSI_PURPLE + string + "\n");
    }
    public static void printAllBanks(CentralBank cb){
        for (Bank bank : cb.banks.getAllBanks()) {
            printOptions(String.format("%s -> id: %s", bank.name, bank.id.toString()));
        }
    }
    public static Bank letSelectBank(CentralBank cb, Scanner scanner){
        printSystemMessage("Select the bank.");
        printAllBanks(cb);
        while (true){
            Console.printQuestion("Enter the bank's id: ");
            String bankId = scanner.nextLine();
            try {
                Bank bank = cb.banks.getBankById(UUID.fromString(bankId));
                printSystemMessage("The bank has been selected.");
                return bank;
            } catch (Exception e){
                printError("We didn't find bank with that id. Please, try again.");
            }
        }

    }
    public static void printAllClients(Bank bank){
        for (Client client : bank.clients.getAllClients()){
            printOptions(String.format("%s -> id: %s", client.toString(), client.id.toString()));
        }
    }
    public static Client letSelectClient(Bank bank, Scanner scanner){
        printSystemMessage("Select the client");
        printAllClients(bank);
        while (true){
            Console.printQuestion("Enter the client's id: ");
            String clientId = scanner.nextLine();
            try {
                Client client = bank.clients.getClientById(UUID.fromString(clientId));
                printSystemMessage("The client has been selected.");
                return client;
            } catch (Exception e){
                printError("We didn't find client with that id. Please, try again.");
            }
        }
    }
    public static void printAllAccounts(Client client){
        for (Account account : client.accounts.getAllAccounts()) {
            printOptions(String.format("%s with balance %s -> id: %s", account.getAccountType().toString(), account.getBalance().toString(), account.getId().toString()));
        }
    }

    public static void printAllAccounts(Bank bank){
        for (Account account : bank.accounts.getAllAccounts()) {
            printOptions(String.format("%s's %s with balance %s -> id: %s", account.getClient().name, account.getAccountType().toString(), account.getBalance().toString(), account.getId().toString()));
        }
    }
    public static Account letSelectAccount(Client client, Scanner scanner){
        printSystemMessage("Select the account");
        printAllAccounts(client);
        while (true){
            Console.printQuestion("Enter the account's id: ");
            String accountsId = scanner.nextLine();
            try {
                Account account = client.accounts.getAccountById(UUID.fromString(accountsId));
                printSystemMessage("The account has been selected.");
                return account;
            } catch (Exception e){
                printError("We didn't find account with that id. Please, try again.");
            }
        }
    }
    public static void printBye(){
        System.out.println(ANSI_WHITE + "  ,-.       _,---._ __ " + ANSI_BLACK + " / \\");
        System.out.println(ANSI_WHITE + " /  )    .-'       `./ " + ANSI_BLACK + "/   \\");
        System.out.println(ANSI_WHITE + "(  (   ,'            `" + ANSI_BLACK + "/    /|");
        System.out.println(ANSI_WHITE + " \\  `-\"             \\'" + ANSI_BLACK + "\\   / |");
        System.out.println(ANSI_WHITE + "  `.              ,  \\ " + ANSI_BLACK + "\\ /  |");
        System.out.println(ANSI_WHITE + "   /`.          ,'" + ANSI_BLACK + "-" + ANSI_WHITE + "`" + ANSI_BLACK + "----Y   |");
        System.out.println(ANSI_WHITE + "  (            ;   " + ANSI_BLACK + "     |   '");
        System.out.println(ANSI_WHITE + "  |  ,-.    ,-'    " + ANSI_BLACK + "     |  /");
        System.out.println(ANSI_WHITE + "  |  | (   |" + ANSI_CYAN + " okay bye!! " + ANSI_BLACK + "| /");
        System.out.println(ANSI_WHITE + "  )  |  \\  `." + ANSI_BLACK + "___________|/");
        System.out.println(ANSI_WHITE + "  `--'   `--'");
        System.exit(0);
    }
}
