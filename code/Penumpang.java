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
