package constantes;

public enum NovaDirecao {

	PROSSEGUE,
    VIRA_DIREITA,
    VIRA_ESQUERDA;
	
	public static NovaDirecao[] except(NovaDirecao dir){
        return switch (dir) {
            case PROSSEGUE -> new NovaDirecao[]{VIRA_DIREITA, VIRA_ESQUERDA};
            case VIRA_DIREITA -> new NovaDirecao[]{PROSSEGUE, VIRA_ESQUERDA};
            case VIRA_ESQUERDA -> new NovaDirecao[]{PROSSEGUE, VIRA_DIREITA};
        };
    }

    public static NovaDirecao mudandoDirecao(Direcao inicio, Direcao fim) {
        return switch (inicio) {
            case CIMA -> switch(fim) {
                case CIMA -> PROSSEGUE;
                case BAIXO -> throw new RuntimeException("Chegou no fim!");
                case ESQUERDA -> VIRA_ESQUERDA;
                case DIREITA -> VIRA_DIREITA;
            };
            case BAIXO -> switch(fim) {
                case CIMA -> throw new RuntimeException("Chegou no fim!");
                case BAIXO -> PROSSEGUE;
                case ESQUERDA -> VIRA_DIREITA;
                case DIREITA -> VIRA_ESQUERDA;
            };
            case ESQUERDA -> switch(fim) {
                case CIMA -> VIRA_DIREITA;
                case BAIXO -> VIRA_ESQUERDA;
                case ESQUERDA -> PROSSEGUE;
                case DIREITA -> throw new RuntimeException("Chegou no fim!");
            };
            case DIREITA -> switch(fim) {
                case CIMA -> VIRA_ESQUERDA;
                case BAIXO -> VIRA_DIREITA;
                case ESQUERDA -> throw new RuntimeException("Chegou no fim!");
                case DIREITA -> PROSSEGUE;
            };
        };
    }

    public Direcao altera(Direcao dir) {
        return switch (this) {
            case PROSSEGUE -> dir.prosseguir();
            case VIRA_DIREITA -> dir.prosseguirDireita();
            case VIRA_ESQUERDA -> dir.prosseguirEsquerda();
        };
    }
}
