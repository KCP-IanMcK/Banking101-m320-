import java.util.*;

public class UserInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private static final KontoHandlerImpl kontoHandler = new KontoHandlerImpl();
    private static final CreditHandlerImpl creditHandler = new CreditHandlerImpl(kontoHandler);
    private static final PortfolioHandlerImpl portfolioHandler = new PortfolioHandlerImpl(kontoHandler);

    public UserInterface() {
    }

    public static void start() {
        boolean run = true;

        System.out.println("Willkommen zur Banking App!");
        while (run) {
            int answer;
            answer = getAnswer("Möchten Sie einzahlen[1], abheben[2], ihre Kredite verwalten[3], Aktien handeln[4] oder das Programm beenden[5]?", 1, 5);

            switch (answer) {
                case 1:
                    depositMoney();
                    break;
                case 2:
                    withDrawMoney();
                    break;
                case 3:
                    manageCredit();
                    break;
                case 4:
                    managePortfolio();
                    break;
                default:
                    run = false;
                    break;
            }
        }
    }

    private static void depositMoney() {
        boolean valid = false;
        int amount = 0;
        while (!valid) {
            System.out.println("Wie viel Geld wollen Sie einzahlen?");
            String amountString = scanner.nextLine();
            try {
                amount = Integer.parseInt(amountString);
                valid = true;
            } catch (Exception e) {
                System.out.println("Der Betrag muss eine Ganzzahl sein.");
            }
        }
        printKontoStand(kontoHandler.addToKonto(amount));
    }

    private static void withDrawMoney() {
        boolean valid = false;
        int amount = 0;
        while (!valid) {
            System.out.println("Wie viel Geld wollen Sie abheben?");
            String amountString = scanner.nextLine();
            try {
                amount = Integer.parseInt(amountString);
                if (amount > kontoHandler.getKontoStand()) {
                    throw new InsufficientFundsException("Zu wenig Geld auf dem Konto.");
                }
                valid = true;
            } catch (InsufficientFundsException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Der Betrag muss eine Ganzzahl sein.");
            }
        }
        printKontoStand(kontoHandler.subtractFromKonto(amount));
    }

    private static void manageCredit() {
        int answer;

        System.out.println("Ihre aktuellen Kredite:");
        for (Credit credit : creditHandler.getCredits()) {
            System.out.println(credit.getClassName() + ": " + credit.getDept() + "CHF, " + credit.getDuration() + " Monate Laufzeit");
        }
        System.out.println();
        answer = getAnswer("Möchten Sie einen Kredit aufnehmen[1] oder eine Kredit zurückzahlen[2]?", 1, 2);

        if (answer == 1) {
            takeUp();
        } else {
            payBack();
        }
    }

    private static void takeUp() {
        int answer = getAnswer("Möchten Sie ein Darlehen[1] oder eine Hypothek[2] aufnehmen?", 1, 2);

        if (answer == 1) {
            int amount = getInt("Geben Sie die Höhe des Darlehens an: ", false);
            int duration = getInt("Geben Sie die Laufzeit des Darlehens in Monaten an: ", false);
            creditHandler.takeUpLoan(amount, duration);
            System.out.println("Ihr Darlehen beträgt neu: " + creditHandler.getLoan().getFirst().getDept());
        } else {
            int amount = getInt("Geben Sie die Höhe der Hypothek an: ", false);
            int duration = getInt("Geben Sie die Laufzeit der Hypothek in Monaten an: ", false);
            boolean worked = creditHandler.takeUpMortage(amount, duration);
            if (worked) {
                System.out.println("Ihre Hypothek wurde eröffnet");
            } else {
                System.out.println("Sie haben bereits eine Hypothek, daher wurde Ihr Antrag abgelehnt");
            }
        }
    }

    private static void payBack() {
        int answer = getAnswer("Möchten Sie ein Darlehen[1] oder eine Hypothek[2] zurückzahlen?", 1, 2);
        if (answer == 1) {
            if (!creditHandler.getLoan().isEmpty()) {
                int amount = getInt("Geben Sie den Betrag an, welchen Sie zurückzahlen möchten: ", true);
                boolean worked = creditHandler.payBackLoan(amount);
                if (worked) {
                    System.out.println("Betrag wurde erfolgreich zurückgezahlt");
                } else {
                    System.out.println("Sie haben kein Darlehen");
                }
            } else {
                System.out.println("Sie haben kein Darlehen");
            }

        } else {
            if (!creditHandler.getMortage().isEmpty()) {
                int amount = getInt("Geben Sie den Betrag an, welchen Sie zurückzahlen möchten: ", true);
                boolean worked = creditHandler.payBackMortage(amount);
                if (worked) {
                    System.out.println("Betrag wurde erfolgreich zurückgezahlt");
                } else {
                    System.out.println("Sie haben keine Hypothek");
                }
            } else {
                System.out.println("Sie haben keine Hypothek");
            }
        }
    }

    private static int getInt(String question, boolean checkKontostand) {
        boolean valid = false;
        int number = 0;

        while (!valid) {
            System.out.println(question);
            String numberString = scanner.nextLine();
            try {
                number = Integer.parseInt(numberString);
                if (checkKontostand && number > kontoHandler.getKontoStand()) {
                    throw new InsufficientFundsException("Sie haben nicht genügend Geld um diesen Betrag zurückzuzahlen");
                }
                valid = true;
            } catch (InsufficientFundsException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Der Betrag muss eine Ganzzahl sein.");
            }
        }
        return number;
    }

    private static int getAnswer(String question, int min, int max) {
        boolean valid = false;
        int answer = 0;

        while (!valid) {
            System.out.println(question);
            try {
                String answerString = scanner.nextLine();
                answer = Integer.parseInt(answerString);
                if (answer < min || answer > max) {
                    throw new NotValidNumberException("Die Antwort muss zwischen " + min + " und " + max + " sein");
                } else {
                    valid = true;
                }
            } catch (NotValidNumberException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Die Antwort muss eine Zahl sein");
            }
        }
        return answer;
    }

    private static void printKontoStand(int amount) {
        System.out.println("Der Kontostand beträgt neu: " + amount);
    }

    private static void managePortfolio() {
        int answer;

        System.out.println("Ihr aktuelles Portfolio:");
        HashMap<Aktie, Integer> amountOfSpecificShare = new HashMap<>();
        for (Aktie share : portfolioHandler.getPortfolio().getShares()) {
            if (!amountOfSpecificShare.containsValue(share)) {
                amountOfSpecificShare.put(share, 0);
            }
        }
        for (Aktie share : portfolioHandler.getPortfolio().getShares()) {
            amountOfSpecificShare.replace(share, amountOfSpecificShare.get(share), amountOfSpecificShare.get(share) + 1);
        }
        for (Map.Entry<Aktie, Integer> aktieIntegerEntry : amountOfSpecificShare.entrySet()) {
            System.out.println(aktieIntegerEntry.getValue() + "x " + aktieIntegerEntry.getKey().getName());
        }

        System.out.println();
        answer = getAnswer("Möchten Sie Aktien kaufen[1], verkaufen[2] oder sich die verfügbaren Aktien anschauen[3]?", 1, 3);

        if (answer == 1) {
            buyShares();
        } else if (answer == 2) {
            sellShares();
        } else {
            showAvailableShares();
        }
    }

    private static void buyShares() {
        int answer1;
        int answer2;
        int counter = 1;
        for (Aktie availableShare : portfolioHandler.getAvailableShares()) {
            System.out.println(counter + " " + availableShare.getName() + " " + availableShare.getValue() + "CHF");
            counter++;
        }
        System.out.println();
        answer1 = getAnswer("Geben Sie die Nummer der Aktie an, welche Sie kaufen möchten: ", 1, portfolioHandler.getAvailableShares().size());
        answer2 = getAnswer("Geben Sie die Menge an, welche Sie kaufen möchten (max. 100): ", 1, 100);

        Portfolio portfolio = portfolioHandler.buyShare(portfolioHandler.getAvailableShares().get(answer1 - 1), answer2);
        if (portfolio != null) {
            System.out.println("Die Aktien wurden ihrem Portfolio hinzugefügt");
        } else {
            System.out.println("Sie verfügen über zu wenig Kapital, um sich diese Menge an Aktien zu kaufen");
        }
    }

    private static void sellShares() {
        int answer1;
        int answer2;
        int counter = 1;
        HashMap<Aktie, Integer> amountOfSpecificShare = new HashMap<>();
        for (Aktie share : portfolioHandler.getPortfolio().getShares()) {
            if (!amountOfSpecificShare.containsValue(share)) {
                amountOfSpecificShare.put(share, 0);
            }
        }
        for (Aktie share : portfolioHandler.getPortfolio().getShares()) {
            amountOfSpecificShare.replace(share, amountOfSpecificShare.get(share), amountOfSpecificShare.get(share) + 1);
        }
        for (Map.Entry<Aktie, Integer> aktieIntegerEntry : amountOfSpecificShare.entrySet()) {
            System.out.println(counter + ": " + aktieIntegerEntry.getValue() + "x " + aktieIntegerEntry.getKey().getName());
            counter++;
        }
        if (counter == 1) {
            System.out.println("Sie haben keine Aktien zum Verkaufen");
            return;
        }

        System.out.println();
        answer1 = getAnswer("Geben Sie die Nummer der Aktien an, welche Sie verkaufen wollen: ", 1, counter - 1);
        answer2 = getAnswer("Geben Sie die Anzahl der zu verkaufenden Aktien an: ", 1, amountOfSpecificShare.get(portfolioHandler.getPortfolio().shares.get(answer1 -1)));

        List<Map.Entry<Aktie, Integer>> hashList = new ArrayList<>();
        hashList.addAll(amountOfSpecificShare.entrySet());

        Portfolio portfolio = portfolioHandler.sellShare(hashList.get(answer1 -1).getKey(), answer2);

        if (portfolio != null) {
            System.out.println("Die angegebenen Aktien wurden erfolgreich verkauft");
        } else {
            System.out.println("Beim Verkauf der Aktien gab es ein Problem");
        }
    }

    private static void showAvailableShares() {
        System.out.println("---------");
        for (Aktie availableShare : portfolioHandler.getAvailableShares()) {
            System.out.println(availableShare.getName() + " " + availableShare.getValue() + " CHF");
        }
        System.out.println("---------");
    }
}
