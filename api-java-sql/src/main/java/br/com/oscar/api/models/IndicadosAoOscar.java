package br.com.oscar.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // Diz ao SPRING que é uma entidade do banco de dados
public class IndicadosAoOscar {

    @Id // Diz ao SPRING que é uma chave primária
    @Column(name = "id_registro") // Dá nome a coluna
    public int idRegistro;

    @Column(name = "ano_filmagem")
    public int anoFilmagem;

    @Column(name = "ano_cerimonia")
    public int anoCerimonia;

    @Column(name = "edicao_cerimonia")
    public int edicaoCerimonia;

    @Column(name = "categoria")
    public String categoria;

    @Column(name = "nome_filme")
    public String nomeFilme;

    @Column(name = "nome_indicado")
    public String nomeIndicado;

    public char vencedor;

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getAnoFilmagem() {
        return anoFilmagem;
    }

    public void setAnoFilmagem(int anoFilmagem) {
        this.anoFilmagem = anoFilmagem;
    }

    public int getAnoCerimonia() {
        return anoCerimonia;
    }

    public void setAnoCerimonia(int anoCerimonia) {
        this.anoCerimonia = anoCerimonia;
    }

    public int getEdicaoCerimonia() {
        return edicaoCerimonia;
    }

    public void setEdicaoCerimonia(int edicaoCerimonia) {
        this.edicaoCerimonia = edicaoCerimonia;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNomeFilme() {
        return nomeFilme;
    }

    public void setNomeFilme(String nomeFilme) {
        this.nomeFilme = nomeFilme;
    }

    public String getNomeIndicado() {
        return nomeIndicado;
    }

    public void setNomeIndicado(String nomeIndicado) {
        this.nomeIndicado = nomeIndicado;
    }

    public char getVencedor() {
        return vencedor;
    }

    public void setVencedor(char vencedor) {
        this.vencedor = vencedor;
    }
}
