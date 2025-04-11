public abstract class Credit {
    private int dept;
    private int duration;

    public Credit(int dept, int duration) {
        this.dept = dept;
        this.duration = duration;
    }

    abstract void takeUpCredit(int amount);
    abstract void payBackCredit(int amount);
    abstract void extendDuration(int duration);
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
