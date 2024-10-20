/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import static constantes.Geral.COLUNAS;
import static constantes.Geral.LINHAS;

import java.util.ArrayList;
import java.util.List;

import gerenciador.GerenciadorSimulacao;
import model.Posicao;
import model.Malha;
import constantes.ConstantesCelula;
import constantes.Direcao;
import modos.Modo;

/**
 *
 * @author Mailon
 */
public class Malha {

	private final ConstantesCelula[][] celulas;
    private final int linhas;
    private final int colunas;
    private final List<Posicao> entradas;
    private final Direcao[][] saidas;

    // Um semáforo por posição do cruzamento.
    private final Modo[][] Modos;


    private Malha(int linhas, int colunas) {
        this.celulas = new ConstantesCelula[linhas][colunas];
        this.linhas = linhas;
        this.colunas = colunas;

        entradas = new ArrayList<>();
        Modos = new Modo[linhas][colunas];
        saidas = new Direcao[linhas][colunas];

        LINHAS = linhas;
        COLUNAS = colunas;
    }

    public int linhas() {
        return linhas;
    }

    public int colunas() {
        return colunas;
    }

    public ConstantesCelula get(int i, int j) {
        return celulas[i][j];
    }

    public ConstantesCelula get(Posicao pos) {
        return celulas[pos.getLinha()][pos.getColuna()];
    }

    public List<Posicao> getEntryPoints() {
        return entradas;
    }

    public static Malha from(String str) {
        var lines = str.split("\n");
        for (var l = 0; l < lines.length; l++) {
            lines[l] = lines[l].trim();
        }

        var linhas = Integer.parseInt(lines[0]);
        var colunas = Integer.parseInt(lines[1]);

        var Malha = new Malha(linhas, colunas);

        for (var l = 2; l < lines.length; l++) {
            var celulas = lines[l].split("\t");
            var i = l - 2;
            for (var j = 0; j < colunas; j++) {
                var id = Integer.parseInt(celulas[j]);
                var celula = ConstantesCelula.from(id);

                if (celula == null) {
                    throw new RuntimeException("Invalid ConstantesCelula id" + id);
                }
                Malha.celulas[i][j] = celula;
            }
        }
        
        Malha.generateEntryPoints();

        // Achando a saída para os cruzamentos em formato de "T".
        for (var i = 0; i < LINHAS; i++) {
            for (var j = 0; j < COLUNAS; j++) {
                var cruzamentoCimaEsquerdaConstantesCelula
                    =  Malha.celulas[i][j].verificaCruzamento()
                    && Malha.celulas[i][j+1].verificaCruzamento()
                    && Malha.celulas[i+1][j].verificaCruzamento()
                    && Malha.celulas[i+1][j+1].verificaCruzamento();

                if (cruzamentoCimaEsquerdaConstantesCelula) {
                    Direcao missing = null;

                    if (Malha.celulas[i-1][j].verificaVazio()) missing = Direcao.CIMA;
                    if (Malha.celulas[i+2][j].verificaVazio()) missing = Direcao.BAIXO;
                    if (Malha.celulas[i][j-1].verificaVazio()) missing = Direcao.ESQUERDA;
                    if (Malha.celulas[i][j+2].verificaVazio()) missing = Direcao.DIREITA;

                    Malha.saidas[i][j]     = missing;
                    Malha.saidas[i][j+1]   = missing;
                    Malha.saidas[i+1][j]   = missing;
                    Malha.saidas[i+1][j+1] = missing;
                }
            }
        }

        return Malha;
    }
    
    public void loadLocks() {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                if (!celulas[i][j].verificaVazio()) {
                    try {
                        Modos[i][j] = GerenciadorSimulacao.getInstance().getModo().getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Error while calling new instances of Modo.");
                    }
                }
            }
        }
    }

    public Modo getModo(int row, int col) {
        return Modos[row][col];
    }

    public Modo getModo(Posicao pos) {
        return getModo(pos.getLinha(), pos.getColuna());
    }


    public Direcao saidas(int row, int col) {
        return saidas[row][col];
    }

    public Direcao saidas(Posicao p) {
        return saidas(p.getLinha(), p.getColuna());
    }

    private void generateEntryPoints() {
        entradas.clear();

        // Cima
        for (int i = 0; i < this.colunas; i++) {
            ConstantesCelula ConstantesCelula = this.celulas[0][i];
            if (ConstantesCelula == ConstantesCelula.BAIXO) {
                entradas.add(new Posicao(0, i));
            }
        }
        
        // Direita
        for (int i = 0; i < this.linhas; i++) {
            ConstantesCelula ConstantesCelula = this.celulas[i][0];
            if (ConstantesCelula == ConstantesCelula.DIREITA) {
                entradas.add(new Posicao(i, 0));
            }
        }
        
        // Baixo
        for (int i = 0; i < this.colunas; i++) {
            ConstantesCelula ConstantesCelula = this.celulas[this.linhas-1][i];
            if (ConstantesCelula == ConstantesCelula.CIMA) {
                entradas.add(new Posicao(linhas-1, i));
            }
        }
        
        // Esquerda
        for (int i = 0; i < this.linhas; i++) {
            ConstantesCelula ConstantesCelula = this.celulas[i][this.colunas-1];
            if (ConstantesCelula == ConstantesCelula.ESQUERDA) {
                entradas.add(new Posicao(i, colunas-1));
            }
        }
    }

    public String toString() {
        var builder = new StringBuilder();
        for (var i = 0; i < linhas; i++) {
            for (var j = 0; j < colunas; j++) {
                builder.append(celulas[i][j].toString());
                if (j < colunas - 1) {
                    builder.append('\t');
                }
            }
            if (i < linhas - 1) {
                builder.append('\n');
            }
        }
        return builder.toString();
    }

}
