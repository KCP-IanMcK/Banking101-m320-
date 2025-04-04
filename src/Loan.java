public class Loan extends Credit{

    public Loan(int dept, int duration) {
        super(dept, duration);
    }

    @Override
    int takeUpCredit(int amount) {
        return 0;
    }

    @Override
    int payBackCredit(int amount) {
        return 0;
    }

    @Override
    int extendCreditDuration(int timeInMonths) {
        return 0;
    }

    @Override
    String getClassName() {
        return "Darlehen";
    }
}
