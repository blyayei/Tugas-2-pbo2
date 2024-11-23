import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KonversiSuhu extends JFrame {
    private JTextField inputSuhu;
    private JComboBox<String> pilihSatuan;
    private JComboBox<String> pilihKonversi;
    private JLabel hasilKonversi;
    private JButton tombolKonversi;

    public KonversiSuhu() {
        setTitle("Aplikasi Konversi Suhu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        // Panel Input Suhu
        JPanel panelInput = new JPanel(new FlowLayout());
        panelInput.add(new JLabel("Masukkan Suhu: "));
        inputSuhu = new JTextField(10);
        panelInput.add(inputSuhu);

        // Panel Pilihan Satuan
        JPanel panelSatuan = new JPanel(new FlowLayout());
        panelSatuan.add(new JLabel("Dari Satuan: "));
        pilihSatuan = new JComboBox<>(new String[]{"Celsius", "Fahrenheit", "Reamur", "Kelvin"});
        panelSatuan.add(pilihSatuan);

        // Panel Pilihan Konversi
        JPanel panelKonversi = new JPanel(new FlowLayout());
        panelKonversi.add(new JLabel("Ke Satuan: "));
        pilihKonversi = new JComboBox<>(new String[]{"Celsius", "Fahrenheit", "Reamur", "Kelvin"});
        panelKonversi.add(pilihKonversi);

        // Panel Tombol Konversi
        JPanel panelTombol = new JPanel(new FlowLayout());
        tombolKonversi = new JButton("Konversi");
        panelTombol.add(tombolKonversi);

        // Panel Hasil
        JPanel panelHasil = new JPanel(new FlowLayout());
        panelHasil.add(new JLabel("Hasil: "));
        hasilKonversi = new JLabel("0");
        panelHasil.add(hasilKonversi);

        // Tambahkan Panel ke JFrame
        add(panelInput);
        add(panelSatuan);
        add(panelKonversi);
        add(panelTombol);
        add(panelHasil);

        // Event Listener
        tombolKonversi.addActionListener(e -> konversiSuhu());
        inputSuhu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != '-') {
                    e.consume(); // Mencegah input selain angka, titik, atau minus
                }
            }
        });

        setVisible(true);
    }

    private void konversiSuhu() {
        try {
            double suhu = Double.parseDouble(inputSuhu.getText());
            String dariSatuan = (String) pilihSatuan.getSelectedItem();
            String keSatuan = (String) pilihKonversi.getSelectedItem();

            if (dariSatuan.equals(keSatuan)) {
                hasilKonversi.setText(String.valueOf(suhu));
                return;
            }

            // Konversi ke Celsius terlebih dahulu
            double suhuCelsius = switch (dariSatuan) {
                case "Fahrenheit" -> (suhu - 32) * 5 / 9;
                case "Reamur" -> suhu * 5 / 4;
                case "Kelvin" -> suhu - 273.15;
                default -> suhu; // Jika sudah Celsius
            };

            // Konversi dari Celsius ke satuan yang dipilih
            double hasil = switch (keSatuan) {
                case "Fahrenheit" -> suhuCelsius * 9 / 5 + 32;
                case "Reamur" -> suhuCelsius * 4 / 5;
                case "Kelvin" -> suhuCelsius + 273.15;
                default -> suhuCelsius; // Jika ke Celsius
            };

            hasilKonversi.setText(String.format("%.2f", hasil));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KonversiSuhu::new);
    }
}
