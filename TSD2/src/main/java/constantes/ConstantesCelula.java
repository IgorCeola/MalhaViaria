package constantes;

import java.awt.Color;

import constantes.ConstantesCelula;
import constantes.Direcao;

public enum ConstantesCelula {

	//Para a definição do valor de cada tipo de célula.
	VAZIA(0),
    CIMA(1),
    DIREITA(2),
    BAIXO(3),
    ESQUERDA(4),
    CRUZAMENTO_CIMA(5),
    CRUZAMENTO_DIREITA(6),
    CRUZAMENTO_BAIXO(7),
    CRUZAMENTO_ESQUERDA(8),
    CRUZAMENTO_CIMA_DIREITA(9),
    CRUZAMENTO_CIMA_ESQUERDA(10),
    CRUZAMENTO_BAIXO_DIREITA(11),
    CRUZAMENTO_BAIXO_ESQUERDA(12);

    private final int id;

    public boolean verificaRua() {
        return this == CIMA || this == BAIXO || this == ESQUERDA || this == DIREITA;
    }

    public boolean verificaVazio() {
        return this == VAZIA;
    }

    public boolean verificaCruzamento() {
        return !verificaVazio() && !verificaRua();
    }

    //Retorna a direção pretendida.
    public Direcao direcaoPretendida() {
        switch(this) {
            case CIMA -> { return Direcao.CIMA; }
            case BAIXO -> { return Direcao.BAIXO; }
            case DIREITA -> { return Direcao.DIREITA; }
            case ESQUERDA -> { return Direcao.ESQUERDA; }
            default -> { throw new RuntimeException("roadDirection() só pode ser aplicado a estradas"); }
        }
    }

    ConstantesCelula(int id) {
        this.id = id;
    }
    
    
    //A definir utilização.
    public static ConstantesCelula from(int id) {
        for (var c : ConstantesCelula.values()) {
            if (c.id == id) {
                return c;
            }
        }
        return null;
    }

    
    //Usado para exibir os caracteres de cada célula de acordo com seu papel(Rua e suas direções ou cruzamentos).
    public String toString() {
        return switch (this) {
            case CIMA -> "^";
            case DIREITA -> ">";
            case BAIXO -> "v";
            case ESQUERDA -> "<";
            default -> verificaCruzamento() ? "X" : " ";
        };
    }

    public Color toColor() {
        return switch (this) {
            case CIMA -> Color.decode("#FCD5B4");
            case DIREITA -> Color.decode("#B8CCE4");
            case BAIXO -> Color.decode("#E6B8B7");
            case ESQUERDA -> Color.decode("#D8E4BC");
            default -> verificaCruzamento() ? Color.decode("#A7A6A8") : Color.white;
        };
    }
}
