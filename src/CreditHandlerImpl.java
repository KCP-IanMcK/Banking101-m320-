import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreditHandlerImpl implements CreditHandler {
    private List<Credit> credits = new ArrayList<>();
    private final KontoHandlerImpl kontoHandler;

    public CreditHandlerImpl(KontoHandlerImpl kontoHandler) {
        this.kontoHandler = kontoHandler;
    }

    @Override
    public void takeUpLoan(int amount, int duration) {
        if (credits.contains(Loan.class)) {
            for (Credit credit : credits) {
                if (credit.getClass() == Loan.class) {
                    credit.takeUpCredit(amount);
                    kontoHandler.addToKonto(amount);
                }
            }
        } else {
            Loan loan = new Loan(amount, duration);
            credits.add(loan);
            kontoHandler.addToKonto(amount);
        }
    }

    @Override
    public boolean payBackLoan(int amount, Credit credit) {
        boolean containsLoan = credits.stream()
                .anyMatch(creditToCheck -> creditToCheck instanceof Loan);
        if (containsLoan) {
            for (Credit creditToCheck : credits) {
                if (creditToCheck.equals(credit)) {
                    if (creditToCheck.getDept() < amount) {
                        return false;
                    }
                    creditToCheck.payBackCredit(amount);
                    if (creditToCheck.getDept() == 0) {
                        credits.remove(creditToCheck);
                    }
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    public boolean extendDurationLoan(int duration, Credit credit) {
        boolean containsLoan = credits.stream()
                .anyMatch(creditToCheck -> creditToCheck instanceof Loan);
        if (containsLoan) {
            for (Credit creditToCheck : credits) {
                if (creditToCheck.equals(credit)) {
                    creditToCheck.extendDuration(duration);
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    public List<Loan> getLoan() {
        return credits.stream()
                .filter(credit -> credit.getClass() == Loan.class)
                .map(credit -> (Loan) credit)
                .collect(Collectors.toList());
    }

    @Override
    public boolean takeUpMortage(int amount, int duration) {
        if (credits.contains(Mortage.class)) {
            return false;
        } else {
            Mortage mortage = new Mortage(amount, duration);
            credits.add(mortage);
            kontoHandler.addToKonto(amount);
            return true;
        }
    }

    @Override
    public boolean payBackMortage(int amount) {
        boolean containsMortage = credits.stream()
                .anyMatch(credit -> credit instanceof Mortage);
        if (containsMortage) {
            for (Credit credit : credits) {
                if (credit.getClass() == Mortage.class) {
                    if (credit.getDept() < amount) {
                        return false;
                    }
                    credit.payBackCredit(amount);
                    if (credit.getDept() == 0) {
                        credits.remove(credit);
                    }
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    public boolean extendDurationMortage(int duration) {
        boolean containsMortage = credits.stream()
                .anyMatch(credit -> credit instanceof Mortage);
        if (containsMortage) {
            for (Credit credit : credits) {
                if (credit.getClass() == Mortage.class) {
                    credit.extendDuration(duration);
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    public List<Mortage> getMortage() {
        return credits.stream()
                .filter(credit -> credit.getClass() == Mortage.class)
                .map(credit -> (Mortage) credit)
                .collect(Collectors.toList());
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }
}
