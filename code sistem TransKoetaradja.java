import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

//Enum untuk tipe penumpang : biasa atau prioritas
enum TipePenumpang {
  BIASA,
  PRIORITAS
}

//Interface untuk perilaku umum penumpang
interface PenumpangInterface {
  boolean bisaDudukPrioritas(); //untuk cek apakah boleh duduk di kursi prioritas atau tidak
  boolean bayarOngkos(int ongkos); //bayar ongkos bus, dan akan dikurangi saldonya
  int getUmur();
  boolean isHamil();
  TipePenumpang getTipe();
  String getId();
}

//Class abstrak untuk penumpang, yg bakal menyimpan data umum penumpang
abstract class Penumpang implements PenumpangInterface {
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

  //Kurangi saldo jika cukup untuk bayar ongkos
  public boolean bayarOngkos(int ongkos) {
    if (saldo >= ongkos) {
      saldo -= ongkos;
      return true;
    }
    return false; // saldo kurang
  }

   @Override
    public String toString() {
        return String.format("ID: %s, Umur: %d, Hamil: %b, Saldo: %d, Tipe: %s", id, umur, hamil, saldo, getTipe());
    }
}

// Penumpang biasa tidak boleh duduk kursi prioritas
class PenumpangBiasa extends Penumpang {
    public PenumpangBiasa(String id, int umur, boolean hamil, int saldo) {
        super(id, umur, hamil, saldo);
    }

    public boolean bisaDudukPrioritas() {
        return false; // Tidak boleh duduk kursi prioritas
    }

    public TipePenumpang getTipe() {
        return TipePenumpang.BIASA;
    }
}

// Penumpang prioritas boleh duduk kursi prioritas jika berlaku kriterianya
class PenumpangPrioritas extends Penumpang {
    public PenumpangPrioritas(String id, int umur, boolean hamil, int saldo) {
        super(id, umur, hamil, saldo);
    }

    // Lansia >60, anak <10, atau ibu hamil boleh duduk prioritas
    public boolean bisaDudukPrioritas() {
        return umur > 60 || umur < 10 || hamil;
    }

    public TipePenumpang getTipe() {
        return TipePenumpang.PRIORITAS;
    }
}

// Custom exception jika bus sudah penuh tidak bisa naik lagi
class BusFullException extends Exception {
    public BusFullException(String message) {
        super(message);
    }
}

// Class Bus mengatur penumpang di kursi biasa, prioritas, dan berdiri
class Bus {
    private final int KURSI_BIASA = 16;
    private final int KURSI_PRIORITAS = 4;
    private final int KAPASITAS_TOTAL = 40;
    private final int ONGKOS = 5000;

    private List<Penumpang> kursiBiasa = new ArrayList<>();
    private List<Penumpang> kursiPrioritas = new ArrayList<>();
    private List<Penumpang> penumpangBerdiri = new ArrayList<>();
    private int totalPendapatan = 0;

    // Getter untuk koleksi penumpang (fix private access error)
    public List<Penumpang> getKursiBiasa() { return kursiBiasa; }
    public List<Penumpang> getKursiPrioritas() { return kursiPrioritas; }
    public List<Penumpang> getPenumpangBerdiri() { return penumpangBerdiri; }

    // Menyimpan log aktivitas harian (naik/turun)
    private List<String> logAktivitas = new ArrayList<>();

    // Method untuk menaikkan penumpang sesuai aturan dan kapasitas bus
    public void naik(Penumpang p) throws BusFullException {
        if (!p.bayarOngkos(ONGKOS)) {
            System.out.println("Penumpang " + p.id + " saldo kurang, tidak bisa naik.");
            return;
        }

        int totalPenumpang = kursiBiasa.size() + kursiPrioritas.size() + penumpangBerdiri.size();
        if (totalPenumpang >= KAPASITAS_TOTAL) {
            throw new BusFullException("Bus sudah penuh, penumpang tidak bisa naik.");
        }
      
        if (p.getTipe() == TipePenumpang.PRIORITAS && p.bisaDudukPrioritas()) {
            if (kursiPrioritas.size() < KURSI_PRIORITAS) {
                kursiPrioritas.add(p);
                totalPendapatan += ONGKOS;
                logAktivitas.add("Naik: " + p.id + " duduk kursi prioritas");
                System.out.println("Penumpang prioritas naik duduk kursi prioritas.");
                return;
            } else if (kursiBiasa.size() < KURSI_BIASA) {
                kursiBiasa.add(p);
                totalPendapatan += ONGKOS;
                logAktivitas.add("Naik: " + p.id + " duduk kursi biasa");
                System.out.println("Penumpang prioritas naik duduk kursi biasa.");
                return;
            }
          
        } else if (p.getTipe() == TipePenumpang.BIASA) {
            if (kursiBiasa.size() < KURSI_BIASA) {
                kursiBiasa.add(p);
                totalPendapatan += ONGKOS;
                logAktivitas.add("Naik: " + p.id + " duduk kursi biasa");
                System.out.println("Penumpang biasa naik duduk kursi biasa.");
                return;
            }
        }
