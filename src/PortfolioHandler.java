import java.util.List;

public interface PortfolioHandler {
    Portfolio buyShare(Aktie aktie, int amount);
    Portfolio sellShare(Aktie aktie, int amount);
    Portfolio getPortfolio();
    List<Aktie> getAvailableShares();
}
