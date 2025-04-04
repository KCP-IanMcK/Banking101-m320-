public abstract class Credit {
    private int dept;
    private int duration;

    public Credit(int dept, int duration) {
        this.dept = dept;
        this.duration = duration;
    }

    abstract int takeUpCredit(int amount);
    abstract int payBackCredit(int amount);
    abstract int extendCreditDuration(int timeInMonths);
    abstract String getClassName();

    public int getDept() {
        return dept;
    }

    public void setDept(int dept) {
        this.dept = dept;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
