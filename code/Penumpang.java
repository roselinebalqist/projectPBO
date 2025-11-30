public abstract class Penumpang implements PenumpangInterface {
    protected String id;
    protected int umur;
    protected boolean hamil;
    protected int saldo;

    public Penumpang(String id, int umur, boolean hamil, int saldo) {
        this.id = id;
        this.umur = umur;
        this.hamil = hamil;
        this.saldo = saldo;
    }

    public int getUmur() { return umur; }
    public boolean isHamil() { return hamil; }
    public String getId() { return id; }

    public boolean bayarOngkos(int ongkos) {
        if (saldo >= ongkos) {
            saldo -= ongkos;
            return true;
        }
        return false; 
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Umur: %d, Hamil: %b, Saldo: %d, Tipe: %s", id, umur, hamil, saldo, getTipe());
    }
}
    
