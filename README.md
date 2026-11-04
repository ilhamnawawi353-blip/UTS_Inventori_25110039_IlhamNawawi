# UTS_Inventori_25110039_IlhamNawawi
Ujian Tengah Semester

# Kesimpulan Ujian
1. Database db_inventori berhasil dirancang sebagai tempat penyimpanan data barang laboratorium. Tabel barang memiliki empat field, yaitu kode barang, nama barang, kategori, dan stok. Kode barang digunakan sebagai Primary Key agar setiap barang memiliki identitas unik.
2. Project Java dibuat menggunakan NetBeans dengan struktur package koneksi, model, dao, dan view. Pembagian package bertujuan agar program lebih terstruktur dan memudahkan pengelolaan kode.
3. MySQL Connector/J ditambahkan ke dalam Libraries NetBeans sebagai driver penghubung antara aplikasi Java dengan database MySQL. Dengan JDBC, aplikasi dapat menjalankan perintah SQL.
4. Class Koneksi dibuat untuk menghubungkan aplikasi Java dengan database db_inventori menggunakan JDBC. Class ini menggunakan DriverManager dan Connection untuk melakukan koneksi serta menangani kesalahan apabila koneksi gagal.
5. Class Barang dibuat sebagai model data yang memiliki atribut kode barang, nama barang, kategori, dan stok. Class ini menggunakan constructor, getter, dan setter untuk mengelola data barang.
6. Class BarangDAO dibuat untuk mengelola proses CRUD (Create, Read, Update, Delete) pada database. Dengan menggunakan PreparedStatement, proses penyimpanan, penampilan, perubahan, dan penghapusan data dapat dilakukan secara lebih terstruktur dan aman.
7. FormBarang dibuat menggunakan Java Swing sebagai antarmuka pengguna. Form ini menyediakan fitur input data, pilihan kategori, tombol Simpan, Ubah, Hapus, dan Bersihkan, serta JTable untuk menampilkan data barang. Selain itu, terdapat validasi input dan konfirmasi penghapusan untuk memastikan pengelolaan data berjalan dengan baik.
