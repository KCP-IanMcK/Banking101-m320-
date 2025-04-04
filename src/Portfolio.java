import java.util.List;

public class Portfolio {
    List<Aktie> shares;

    public Portfolio(List<Aktie> shares) {
        this.shares = shares;
    }

    public List<Aktie> getShares() {
        return shares;
    }

    public void setShares(List<Aktie> shares) {
        this.shares = shares;
    }
}
