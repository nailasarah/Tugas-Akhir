import java.util.ArrayList;
import java.util.Scanner;

public class MyAtm {
    private static ArrayList<String> arraySementara = new ArrayList<>();
    private static ArrayList<String> arrayPermanen = new ArrayList<>();
    private static String[] logTransaksi = new String[10];
    private static int transaksiIndex = 0;
    private static double saldo = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pilihan;

        do {
            tampilkanMenuUtama();
            pilihan = getPilihan(scanner);

            switch (pilihan) {
                case 1:
                    tabung(scanner);
                    break;
                case 2:
                    tarik(scanner);
                    break;
                case 3:
                    transfer(scanner);
                    break;
                case 4:
                    simpan();
                    break;
                case 5:
                    informasiAkun();
                    break;
                case 6:
                    tampilkanMenuRiwayat(scanner);
                    break;
                case 7:
                    System.out.println("Terima kasih telah menggunakan aplikasi tabungan.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (pilihan != 7);

        scanner.close();
    }

    private static void tampilkanMenuUtama() {
        System.out.println("\nSelamat datang di MyATM");
        System.out.println("1. TABUNG");
        System.out.println("2. TARIK");
        System.out.println("3. TRANSFER");
        System.out.println("4. SIMPAN");
        System.out.println("5. INFORMASI AKUN");
        System.out.println("6. RIWAYAT TRANSAKSI");
        System.out.println("7. SELESAI");
    }

    private static void tampilkanMenuRiwayat(Scanner scanner) {
        int pilihan;
        do {
            System.out.println("\nRiwayat Transaksi");
            System.out.println("1. RIWAYAT PERMANEN");
            System.out.println("2. RIWAYAT TERAKHIR (LOG)");
            System.out.println("3. KEMBALI KE MENU UTAMA");
            System.out.print("");
            pilihan = getPilihan(scanner);

            switch (pilihan) {
                case 1:
                    tampilkanRiwayatPermanen();
                    break;
                case 2:
                    tampilkanRiwayatTerakhir();
                    break;
                case 3:
                    System.out.println("Kembali ke menu utama.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (pilihan != 3);
    }

    private static int getPilihan(Scanner scanner) {
        try {
            System.out.print("Masukkan pilihan: ");
            return scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Input tidak valid! Masukkan angka.");
            scanner.nextLine(); // Kosongkan buffer input
            return -1;
        }
    }

    private static void tabung(Scanner scanner) {
        System.out.print("Masukkan jumlah yang ingin ditabung: ");
        double jumlah = scanner.nextDouble();
        saldo += jumlah;
        String transaksi = "TABUNG: Rp" + jumlah;
        arraySementara.add(transaksi);
        catatLog(transaksi);
        System.out.println("Berhasil menabung Rp" + jumlah);
    }

    private static void tarik(Scanner scanner) {
        System.out.print("Masukkan jumlah yang ingin ditarik: ");
        double jumlah = scanner.nextDouble();
        if (jumlah <= saldo) {
            saldo -= jumlah;
            String transaksi = "TARIK: Rp" + jumlah;
            arraySementara.add(transaksi);
            catatLog(transaksi);
            System.out.println("Berhasil menarik Rp" + jumlah);
        } else {
            System.out.println("Saldo tidak mencukupi.");
        }
    }

    private static void transfer(Scanner scanner) {
        System.out.print("Masukkan jumlah yang ingin ditransfer: ");
        double jumlah = scanner.nextDouble();
        scanner.nextLine(); // Kosongkan buffer
        System.out.print("Masukkan tujuan transfer (opsional, tekan Enter jika tidak ada): ");
        String tujuan = scanner.nextLine();
        if (!tujuan.isEmpty()) {
            transfer(jumlah, tujuan);
        } else {
            transfer(jumlah);
        }
    }

    private static void transfer(double jumlah) {
        if (jumlah <= saldo) {
            saldo -= jumlah;
            String transaksi = "TRANSFER: Rp" + jumlah;
            arraySementara.add(transaksi);
            catatLog(transaksi);
            System.out.println("Berhasil transfer Rp" + jumlah);
        } else {
            System.out.println("Saldo tidak mencukupi.");
        }
    }

    private static void transfer(double jumlah, String tujuan) {
        if (jumlah <= saldo) {
            saldo -= jumlah;
            String transaksi = "TRANSFER ke " + tujuan + ": Rp" + jumlah;
            arraySementara.add(transaksi);
            catatLog(transaksi);
            System.out.println("Berhasil transfer Rp" + jumlah + " ke " + tujuan);
        } else {
            System.out.println("Saldo tidak mencukupi.");
        }
    }

    private static void simpan() {
        arrayPermanen.addAll(arraySementara);
        arraySementara.clear();
        System.out.println("Data transaksi sementara telah disimpan permanen.");
    }

    private static void informasiAkun() {
        System.out.println("\nInformasi Akun:");
        System.out.println("Saldo saat ini: Rp" + saldo);

        // Jumlah transaksi permanen
        int jumlahTransaksiPermanen = arrayPermanen.size();
        System.out.println("Jumlah transaksi permanen: " + jumlahTransaksiPermanen);

        // Menggunakan rekursi untuk menghitung total saldo
        double totalSaldo = hitungTotalRekursif(arrayPermanen.size() - 1);
        System.out.println("Total saldo berdasarkan riwayat permanen: Rp" + totalSaldo);

        // Transaksi terakhir
        String transaksiTerakhir = "Tidak ada transaksi terbaru.";
        for (int i = logTransaksi.length - 1; i >= 0; i--) {
            if (logTransaksi[i] != null) {
                transaksiTerakhir = logTransaksi[i];
                break;
            }
        }
        System.out.println("Transaksi terakhir: " + transaksiTerakhir);
    }

    private static double hitungTotalRekursif(int index) {
        if (index < 0) return 0;
        String transaksi = arrayPermanen.get(index);
        double jumlah = Double.parseDouble(transaksi.split(": Rp")[1]);
        return jumlah + hitungTotalRekursif(index - 1);
    }

    private static void tampilkanRiwayatPermanen() {
        System.out.println("\nRiwayat Transaksi Permanen:");
        if (arrayPermanen.isEmpty()) {
            System.out.println("Tidak ada transaksi permanen.");
        } else {
            for (String transaksi : arrayPermanen) {
                System.out.println(transaksi);
            }
        }
    }

    private static void tampilkanRiwayatTerakhir() {
        System.out.println("\nRiwayat Transaksi Terakhir (Log):");
        boolean adaLog = false;
        for (String log : logTransaksi) {
            if (log != null) {
                System.out.println(log);
                adaLog = true;
            }
        }
        if (!adaLog) {
            System.out.println("Tidak ada transaksi terbaru.");
        }
    }

    private static void catatLog(String transaksi) {
        if (transaksiIndex < logTransaksi.length) {
            logTransaksi[transaksiIndex] = transaksi;
            transaksiIndex++;
        } else {
            for (int i = 0; i < logTransaksi.length - 1; i++) {
                logTransaksi[i] = logTransaksi[i + 1];
            }
            logTransaksi[logTransaksi.length - 1] = transaksi;
        }
    }
}
