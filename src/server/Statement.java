package server;

import server.StatementInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Statement implements StatementInterface {
    private int accountNumber;
    private Date startDate;
    private Date endDate;
    private String accountName;
    private List<Transaction> transactions;

    public Statement(Date start, Date end, Account acc){
        this.accountName = acc.getUsername();
        this.accountNumber = acc.getAccountNum();
        this.startDate = start;
        this.endDate = end;
        for (Transaction t: acc.getTransactions()){
            if (t.getTransactionDate().after(start) && t.getTransactionDate().before(end)){
                this.transactions.add(t);
            }
        }
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    @Override
    public String toString(){
        // Create from and to
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String from = df.format(this.startDate);
        String to = df.format(this.endDate);
        String statement = "Printing server.Statement for server.Account "+this.getAccountNumber()+"for the period "+from+" - "+to+"\n";
        for (Transaction t: this.getTransactions()) {
            statement += "Date: "+t.getTransactionDate()+" Type: "+t.getType()+" Balance: €"+t.getBalance()+"\n";
        }
        return statement;
    }
}
