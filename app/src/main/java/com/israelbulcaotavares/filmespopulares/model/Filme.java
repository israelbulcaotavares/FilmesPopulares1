package com.israelbulcaotavares.filmespopulares.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Filme implements Parcelable {
    private String id;
    private String titulo;
    private String votacaoMedia;
    private String cartaz;
    private String sinopse; //synopsis
    private String dataLancamento;

    public static final Creator CREATOR = new Creator() {
        public Filme createFromParcel(Parcel in) {
            return new Filme(in);
        }

        public Filme[] newArray(int size) {
            return new Filme[size];
        }
    };

    public Filme(String id, String titulo, String votacaoMedia,
                 String cartaz, String sinopse, String dataLancamento) {
        this.id = id;
        this.titulo = titulo;
        this.votacaoMedia = votacaoMedia;
        this.cartaz = cartaz;
        this.sinopse = sinopse;
        this.dataLancamento = dataLancamento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getVotacaoMedia() {
        return votacaoMedia;
    }

    public void setVotacaoMedia(String votacaoMedia) {
        this.votacaoMedia = votacaoMedia;
    }

    public String getCartaz() {
        return cartaz;
    }

    public void setCartaz(String cartaz) {
        this.cartaz = cartaz;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    //Parcelling part
    public Filme(Parcel in) {
        this.id = in.readString();
        this.titulo = in.readString();
        this.votacaoMedia = in.readString();
        this.cartaz = in.readString();
        this.sinopse = in.readString();
        this.dataLancamento = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.titulo);
        dest.writeString(this.votacaoMedia);
        dest.writeString(this.cartaz);
        dest.writeString(this.sinopse);
        dest.writeString(this.dataLancamento);
    }

    @Override
    public String toString() {
        return "Filme{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", votacaoMedia='" + votacaoMedia + '\'' +
                ", cartaz='" + cartaz + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", dataLancamento='" + dataLancamento + '\'' +
                '}';
    }
}
