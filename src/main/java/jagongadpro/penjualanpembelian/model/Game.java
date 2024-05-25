package jagongadpro.penjualanpembelian.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nama;

    private String deskripsi;


    private Integer harga;

    private String kategori;

    @Setter
    private Integer stok;

    public Game(GameBuilder builder){
        this.nama = builder.nama;
        this.deskripsi = builder.deskripsi;
        this.harga = builder.harga;
        this.kategori = builder.kategori;
        this.stok = builder.stok;
    }
    public static class GameBuilder{
        private String nama;

        private String deskripsi;


        private Integer harga;

        private String kategori;


        private Integer stok;

        public GameBuilder nama(String nama){
            this.nama = nama;
            return this;
        }
        public GameBuilder deskripsi(String deskripsi){
            this.deskripsi = deskripsi;
            return this;
        }
        public GameBuilder kategori(String kategori){
            this.kategori = kategori;
            return this;
        }

        public GameBuilder stok(Integer stok){
            this.stok = stok;
            return this;
        }
        public GameBuilder harga(Integer harga){
            this.harga = harga;
            return this;
        }
        public Game build() {
            return  new Game(this);
        }


    }
}

