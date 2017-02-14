import java.util.Date;

/**
 * Created by conor on 27/01/2017.
 */
public class Transaction {
    private int number;
    private Date transactionDate;
    private String type;
    private int balance;

    private static int incrementor = 0;

    public Transaction(String t, int b){
        this.number = incrementor;
        incrementor += 1;
        this.transactionDate = new Date();
        this.type = t;
        this.balance = b;
    }

    public Date getTransactionDate(){
        return this.transactionDate;
    }

    public String getType(){
        return this.type;
    }

    public int getBalance(){
        return this.balance;
    }
}
