
package view;

import dao.BarangDAO;
import model.Barang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class FormBarang extends JFrame {

    private JTextField txtKode;
    private JTextField txtNama;
    private JTextField txtStok;

    private JComboBox<String> cmbKategori;

    private JTable tabel;
    private DefaultTableModel model;

    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnBersihkan;

    private BarangDAO dao = new BarangDAO();

    public FormBarang() {

        setTitle("Aplikasi Inventori Barang Laboratorium");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buatKomponen();
        tampilData();
    }

    private void buatKomponen() {

        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // Judul
        JLabel judul = new JLabel(
            "INVENTORI BARANG LABORATORIUM",
            SwingConstants.CENTER
        );

        judul.setFont(new Font("Arial", Font.BOLD, 20));

        panelUtama.add(judul, BorderLayout.NORTH);

        // Form input
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));

        txtKode = new JTextField();
        txtNama = new JTextField();
        txtStok = new JTextField();

        cmbKategori = new JComboBox<>(
            new String[]{
                "Komputer",
                "Jaringan",
                "Alat Ukur"
            }
        );

        panelForm.add(new JLabel("Kode Barang"));
        panelForm.add(txtKode);

        panelForm.add(new JLabel("Nama Barang"));
        panelForm.add(txtNama);

        panelForm.add(new JLabel("Kategori"));
        panelForm.add(cmbKategori);

        panelForm.add(new JLabel("Stok"));
        panelForm.add(txtStok);

        // Tombol
        btnSimpan = new JButton("Simpan");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnBersihkan = new JButton("Bersihkan");

        JPanel panelTombol = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 10, 10)
        );

        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnBersihkan);

        // Bagian form dan tombol
        JPanel panelAtas = new JPanel(new BorderLayout(5, 5));

        panelAtas.add(panelForm, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);

        // Tabel
        model = new DefaultTableModel(
            new String[]{
                "Kode Barang",
                "Nama Barang",
                "Kategori",
                "Stok"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabel = new JTable(model);
        tabel.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabel);

        JPanel panelTengah = new JPanel(new BorderLayout(5, 5));

        panelTengah.add(panelAtas, BorderLayout.NORTH);
        panelTengah.add(scroll, BorderLayout.CENTER);

        panelUtama.add(panelTengah, BorderLayout.CENTER);

        add(panelUtama);

        // Event tombol
        btnSimpan.addActionListener(e -> simpanData());

        btnUbah.addActionListener(e -> ubahData());

        btnHapus.addActionListener(e -> hapusData());

        btnBersihkan.addActionListener(e -> bersihkanForm());

        // Event klik tabel
        tabel.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int baris = tabel.getSelectedRow();

                if (baris != -1) {

                    txtKode.setText(
                        model.getValueAt(baris, 0).toString()
                    );

                    txtNama.setText(
                        model.getValueAt(baris, 1).toString()
                    );

                    cmbKategori.setSelectedItem(
                        model.getValueAt(baris, 2).toString()
                    );

                    txtStok.setText(
                        model.getValueAt(baris, 3).toString()
                    );
                }
            }
        });
    }

    // READ: Menampilkan data ke JTable
    private void tampilData() {

        model.setRowCount(0);

        try {

            List<Barang> daftar = dao.tampilkan();

            for (Barang b : daftar) {

                model.addRow(new Object[]{
                    b.getKodeBarang(),
                    b.getNamaBarang(),
                    b.getKategori(),
                    b.getStok()
                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                this,
                "Gagal menampilkan data: " + e.getMessage()
            );
        }
    }

    // Validasi input
    private Barang ambilInput() {

        String kode = txtKode.getText().trim();
        String nama = txtNama.getText().trim();
        String kategori = cmbKategori.getSelectedItem().toString();
        String stokText = txtStok.getText().trim();

        if (kode.isEmpty() || nama.isEmpty() || stokText.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Kode, nama, dan stok wajib diisi!"
            );

            return null;
        }

        if (kode.length() > 10 || nama.length() > 50) {

            JOptionPane.showMessageDialog(
                this,
                "Kode maksimal 10 karakter dan nama maksimal 50 karakter!"
            );

            return null;
        }

        try {

            int stok = Integer.parseInt(stokText);

            if (stok < 0) {

                JOptionPane.showMessageDialog(
                    this,
                    "Stok tidak boleh negatif!"
                );

                return null;
            }

            return new Barang(kode, nama, kategori, stok);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "Stok harus berupa angka!"
            );

            return null;
        }
    }

    // CREATE: Simpan data
    private void simpanData() {

        Barang barang = ambilInput();

        if (barang == null) return;

        try {

            boolean berhasil = dao.simpan(barang);

            if (berhasil) {

                JOptionPane.showMessageDialog(
                    this,
                    "Data barang berhasil disimpan!"
                );

                tampilData();
                bersihkanForm();

            }

        } catch (SQLException e) {

            if ("23000".equals(e.getSQLState())) {

                JOptionPane.showMessageDialog(
                    this,
                    "Kode barang sudah digunakan!"
                );

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Gagal menyimpan data: " + e.getMessage()
                );
            }
        }
    }

    // UPDATE: Ubah data
    private void ubahData() {

        if (tabel.getSelectedRow() == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Pilih data pada tabel terlebih dahulu!"
            );

            return;
        }

        Barang barang = ambilInput();

        if (barang == null) return;

        try {

            boolean berhasil = dao.ubah(barang);

            if (berhasil) {

                JOptionPane.showMessageDialog(
                    this,
                    "Data berhasil diubah!"
                );

                tampilData();
                bersihkanForm();

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Data tidak ditemukan!"
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                this,
                "Gagal mengubah data: " + e.getMessage()
            );
        }
    }

    // DELETE: Hapus data
    private void hapusData() {

        int baris = tabel.getSelectedRow();

        if (baris == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Pilih data yang ingin dihapus!"
            );

            return;
        }

        String kode = txtKode.getText().trim();

        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin menghapus barang "
                + kode + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {

            try {

                boolean berhasil = dao.hapus(kode);

                if (berhasil) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Data berhasil dihapus!"
                    );

                    tampilData();
                    bersihkanForm();

                } else {

                    JOptionPane.showMessageDialog(
                        this,
                        "Data gagal dihapus atau tidak ditemukan!"
                    );
                }

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(
                    this,
                    "Gagal menghapus data: " + e.getMessage()
                );
            }
        }
    }

    // Bersihkan form
    private void bersihkanForm() {

        txtKode.setText("");
        txtNama.setText("");
        txtStok.setText("");

        cmbKategori.setSelectedIndex(0);

        tabel.clearSelection();
        txtKode.requestFocus();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new FormBarang().setVisible(true);

        });
    }
}
