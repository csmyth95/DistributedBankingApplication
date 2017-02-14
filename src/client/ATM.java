package client;

import interfaces.BankInterface;
import interfaces.StatementInterface;
import server.Statement;
import server.Transaction;

import java.rmi.Naming;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// CLIENT
public class ATM {

    public static void main (String args[]) throws Exception {
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "file:/Users/conor/IdeaProjects/DistributedBankingApplication/src/security.policy");
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String hostname = args[0];
            String portNumber = args[1];
            BankInterface bankServer = (BankInterface) Naming.lookup("//"+hostname+"/Accounts");
            // Get command line args
            String type = args[2];
            int accountNumber;
            int amount;

            switch (type) {
                case "login":
                    String username = args[3];
                    String password = args[4];
                    accountNumber = bankServer.login(username, password);
                    System.out.println("Successful login for " + username + " session is valid for 5 minutes\n" +
                            "Account number is : " + accountNumber);
                    break;
                case "inquiry":
                    accountNumber = Integer.parseInt(args[3]);
                    float balance = bankServer.inquiry(accountNumber);
                    System.out.println("Current balance of account " + accountNumber + " is " + balance);
                    break;
                case "deposit":
                    accountNumber = Integer.parseInt(args[3]);
                    amount = Integer.parseInt(args[4]);
                    if (bankServer.deposit(accountNumber, amount)){
                        System.out.println("Successfully deposited "+ amount + " to account " + accountNumber + "!");
                    }
                    break;
                case "withdraw":
                    accountNumber = Integer.parseInt(args[3]);
                    amount = Integer.parseInt(args[4]);
                    if (bankServer.withdraw(accountNumber, amount)){
                        System.out.println("Successfully withdrew " + amount + " from account " + accountNumber + "!");
                    }
                    break;
                case "statement":
                    accountNumber = Integer.parseInt(args[3]);
                    String to = args[4];
                    String from = args[5];
                    StatementInterface statement = bankServer.getStatement(accountNumber, to, from);

                    Date startDate = statement.getStartDate();
                    Date endDate = statement.getEndDate();
                    accountNumber = statement.getAccountNumber();
                    List<Transaction> transactions = statement.getTransactions();

                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    from = df.format(startDate);
                    to = df.format(endDate);
                    String statementString = "Printing server.Statement for server.Account "+accountNumber+"for the period "+from+" - "+to+"\n";
                    for (Transaction t: transactions) {
                        statementString += "Date: "+t.getTransactionDate()+" Type: "+t.getType()+" Balance: €"+t.getBalance()+"\n";
                    }
                    System.out.println(statementString);

                    break;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}