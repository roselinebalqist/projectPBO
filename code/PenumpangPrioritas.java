public class PenumpangPrioritas extends Penumpang {
    public PenumpangPrioritas(String id, int umur, boolean hamil, int saldo) {
        super(id, umur, hamil, saldo);
    }

    public boolean bisaDudukPrioritas() {
        return umur > 60 || umur < 10 || hamil;
    }

    public TipePenumpang getTipe() {
        return TipePenumpang.PRIORITAS;
    }
}
