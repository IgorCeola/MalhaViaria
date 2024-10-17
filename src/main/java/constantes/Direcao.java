package constantes;

import model.Posicao;
import constantes.Direcao;

public enum Direcao {
	CIMA,
    BAIXO,
    DIREITA,
    ESQUERDA;
    

    public void move(Posicao pos) {
        switch (this) {
            case CIMA -> pos.setLinha(pos.getLinha() - 1);
            case BAIXO -> pos.setLinha(pos.getLinha() + 1);
            case ESQUERDA -> pos.setColuna(pos.getColuna() - 1);
            case DIREITA -> pos.setColuna(pos.getColuna() + 1);
        }
    }

    public Posicao moved(Posicao pos) {
        var moved = (Posicao) pos.clone();
        move(moved);
        return moved;
    }

    public Direcao prosseguir() {
        return this;
    }

    public Direcao voltar() {
        return switch (this) {
            case CIMA -> BAIXO;
            case BAIXO -> CIMA;
            case DIREITA -> ESQUERDA;
            case ESQUERDA -> DIREITA;
        };
    }

    public Direcao prosseguirDireita() {
        return switch (this) {
            case CIMA -> DIREITA;
            case DIREITA -> BAIXO;
            case BAIXO -> ESQUERDA;
            case ESQUERDA -> CIMA;
        };
    }

    public Direcao prosseguirEsquerda() {
        return switch (this) {
            case CIMA -> ESQUERDA;
            case ESQUERDA -> BAIXO;
            case BAIXO -> DIREITA;
            case DIREITA -> CIMA;
        };
    }
    
    
}
