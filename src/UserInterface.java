import java.util.Scanner;

public class UserInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private static final KontoHandlerImpl kontoHandler = new KontoHandlerImpl();

    public UserInterface() {
    }

    public static void start() {
        System.out.println("Willkommen zur Banking App!");
        while (true) {
            boolean valid = false;
            int answer = 0;
            while (!valid) {
                System.out.println("Möchten Sie einzahlen[1] oder abheben[2]?");
                try {
                    String answerString = scanner.nextLine();
                    answer = Integer.parseInt(answerString);
                    if (answer != 1 && answer != 2) {
                        throw new NotValidNumberException("Die Antwort muss entweder 1 oder 2 sein");
                    } else {
                        valid = true;
                    }
                } catch (NotValidNumberException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("Die Antwort muss eine Zahl sein");
                }
            }
            if (answer == 1) {
                depositMoney();
            } else {
                withDrawMoney();
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

    private static void printKontoStand(int amount) {
        System.out.println("Der Kontostand beträgt neu: " + amount);
    }
}
