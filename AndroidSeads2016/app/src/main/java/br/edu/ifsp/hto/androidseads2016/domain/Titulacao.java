package br.edu.ifsp.hto.androidseads2016.domain;

/**
 * Created by gustavohome on 17/05/2016.
 */
public class Titulacao {
    private long id;
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
