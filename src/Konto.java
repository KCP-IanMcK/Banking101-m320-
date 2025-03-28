public class Konto {
    private int kontoStand;

    public Konto() {
        kontoStand = 0;
    }

    public Konto(int kontoStand) {
        this.kontoStand = kontoStand;
    }

    public int getKontoStand() {
        return kontoStand;
    }

    public void setKontoStand(int kontoStand) {
        this.kontoStand = kontoStand;
    }
}
