package model;

import model.Posicao;

public class Posicao {
	
	private int linha;
    private int coluna;

    public Posicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
    
    public void update(Posicao position) {
        this.linha = position.linha;
        this.coluna = position.coluna;
    }

    @Override
    public boolean equals(Object obj) {
        Posicao posicaoVerificada = (Posicao) obj;
        return this.linha == posicaoVerificada.linha && this.coluna == posicaoVerificada.coluna;
    }

    @Override
    public int hashCode() {
        String aux = String.valueOf(linha) + String.valueOf(coluna);
        return Integer.parseInt(aux);
    }

    @Override
    public String toString() {
        return String.valueOf(linha) + "-" + String.valueOf(coluna);
    }

    @Override
    public Object clone() {
        return new Posicao(linha, coluna);
    }
}
