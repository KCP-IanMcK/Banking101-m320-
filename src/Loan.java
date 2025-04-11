public class Loan extends Credit{

    public Loan(int dept, int duration) {
        super(dept, duration);
    }

    @Override
    void takeUpCredit(int amount) {
        setDept(getDept() + amount);
    }

    @Override
    void payBackCredit(int amount) {
        setDept(getDept() - amount);
    }

    @Override
    void extendDuration(int duration) {
        setDuration(getDuration() + duration);
    }

    @Override
    String getClassName() {
        return "Darlehen";
    }
}
