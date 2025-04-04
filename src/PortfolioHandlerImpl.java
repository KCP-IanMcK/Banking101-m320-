import java.util.ArrayList;
import java.util.List;

public class PortfolioHandlerImpl implements PortfolioHandler {
    private final KontoHandlerImpl kontoHandler;
    private final List<Aktie> shares = new ArrayList<>();
    private final Portfolio portfolio = new Portfolio(shares);
    private final List<Aktie> availableShares = new ArrayList<>();
    private final Aktie microsoft = new Aktie("Microsoft", 120);
    private final Aktie apple = new Aktie("Apple", 134);
    private final Aktie nvidia = new Aktie("Nvidia", 111);
    private final Aktie facebook = new Aktie("Facebook", 108);

    public PortfolioHandlerImpl(KontoHandlerImpl kontoHandler) {
        this.kontoHandler = kontoHandler;
        this.availableShares.add(microsoft);
        this.availableShares.add(apple);
        this.availableShares.add(nvidia);
        this.availableShares.add(facebook);
    }

    @Override
    public Portfolio buyShare(Aktie aktie, int amount) {
        List<Aktie> sharesToAdd = portfolio.getShares();
        int amountToSubtract = 0;
        for (int i = 0; i < amount; i++) {
            if (kontoHandler.getKontoStand() - aktie.getValue() > 0) {
                amountToSubtract += aktie.getValue();
                sharesToAdd.add(aktie);
            } else {
                return null;
            }
        }
        kontoHandler.subtractFromKonto(amountToSubtract);
        portfolio.setShares(sharesToAdd);
        return portfolio;
    }

    @Override
    public Portfolio sellShare(Aktie aktie, int amount) {
        List<Aktie> leftShares = portfolio.getShares();
        int amountToAdd = 0;
        for (int i = 0; i < amount; i++) {
            if (leftShares.contains(aktie)) {
            leftShares.remove(aktie);
            amountToAdd += aktie.getValue();
            } else {
                return null;
            }
        }
        kontoHandler.addToKonto(amountToAdd);
        portfolio.setShares(leftShares);
        return portfolio;
    }

    @Override
    public Portfolio getPortfolio() {
        return portfolio;
    }

    @Override
    public List<Aktie> getAvailableShares() {
        return availableShares;
    }
}
