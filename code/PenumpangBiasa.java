public class PenumpangBiasa extends Penumpang {
    public PenumpangBiasa(String id, int umur, boolean hamil, int saldo) {
        super(id, umur, hamil, saldo);
    }

    public boolean bisaDudukPrioritas() {
        return false; 
    }

    public TipePenumpang getTipe() {
        return TipePenumpang.BIASA;
    }
}
