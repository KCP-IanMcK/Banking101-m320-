public class Mortage extends Credit {

    public Mortage(int dept, int duration) {
        super(dept, duration);
    }

    @Override
    void takeUpCredit(int amount) {
        //Wird nicht verwendet
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
        return "Hypothek";
    }
}
