public class KontoHandlerImpl implements KontoHandler{

    private final Konto konto = new Konto();

    @Override
    public int addToKonto(int amount) {
        int kontoStand = konto.getKontoStand();
        kontoStand += amount;
        konto.setKontoStand(kontoStand);
        return konto.getKontoStand();
    }

    @Override
    public int subtractFromKonto(int amount) {
        int kontoStand = konto.getKontoStand();
        kontoStand -= amount;
        konto.setKontoStand(kontoStand);
        return konto.getKontoStand();
    }

    @Override
    public int getKontoStand() {
        return konto.getKontoStand();
    }
}
