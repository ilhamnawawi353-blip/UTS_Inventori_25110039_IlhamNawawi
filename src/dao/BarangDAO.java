
package dao;

import koneksi.Koneksi;
import model.Barang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {

    // CREATE: Menambahkan barang
    public boolean simpan(Barang barang) throws SQLException {

        String sql = "INSERT INTO barang "
                   + "(kode_barang, nama_barang, kategori, stok) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection()) {

            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, barang.getKodeBarang());
                ps.setString(2, barang.getNamaBarang());
                ps.setString(3, barang.getKategori());
                ps.setInt(4, barang.getStok());

                return ps.executeUpdate() > 0;
            }
        }
    }

    // READ: Mengambil seluruh data barang
    public List<Barang> tampilkan() throws SQLException {

        List<Barang> daftar = new ArrayList<>();

        String sql = "SELECT * FROM barang ORDER BY kode_barang";

        try (Connection conn = Koneksi.getConnection()) {

            if (conn == null) return daftar;

            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {

                while (rs.next()) {

                    Barang barang = new Barang(
                        rs.getString("kode_barang"),
                        rs.getString("nama_barang"),
                        rs.getString("kategori"),
                        rs.getInt("stok")
                    );

                    daftar.add(barang);
                }
            }
        }

        return daftar;
    }

    // UPDATE: Mengubah data berdasarkan kode barang
    public boolean ubah(Barang barang) throws SQLException {

        String sql = "UPDATE barang SET "
                   + "nama_barang=?, kategori=?, stok=? "
                   + "WHERE kode_barang=?";

        try (Connection conn = Koneksi.getConnection()) {

            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, barang.getNamaBarang());
                ps.setString(2, barang.getKategori());
                ps.setInt(3, barang.getStok());
                ps.setString(4, barang.getKodeBarang());

                return ps.executeUpdate() > 0;
            }
        }
    }

    // DELETE: Menghapus data berdasarkan kode barang
    public boolean hapus(String kode) throws SQLException {

        String sql = "DELETE FROM barang WHERE kode_barang=?";

        try (Connection conn = Koneksi.getConnection()) {

            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, kode);

                return ps.executeUpdate() > 0;
            }
        }
    }
}
