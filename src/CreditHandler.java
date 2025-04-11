import java.util.List;

public interface CreditHandler {
    void takeUpLoan(int amount, int duration);
    boolean payBackLoan(int amount, Credit credit);
    boolean extendDurationLoan(int duration, Credit credit);
    List<Loan> getLoan();
    boolean takeUpMortage(int amount, int duration);
    boolean payBackMortage(int amount);
    boolean extendDurationMortage(int duration);
    List<Mortage> getMortage();
}
