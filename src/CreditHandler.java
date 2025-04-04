import java.util.List;

public interface CreditHandler {
    void takeUpLoan(int amount, int duration);
    boolean payBackLoan(int amount);
    List<Loan> getLoan();
    boolean takeUpMortage(int amount, int duration);
    boolean payBackMortage(int amount);
    List<Mortage> getMortage();
}
