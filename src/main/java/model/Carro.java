/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.EnumMap;
import java.util.Queue;
import java.util.Random;

import gerenciador.GerenciadorSimulacao;
import model.Posicao;
import modos.Modo;
import constantes.NovaDirecao;
import constantes.Status;
import constantes.Direcao;
import constantes.Geral;


/**
 *
 * @author Mailon
 */
public class Carro extends Thread { // primeira estrutura do carro

	private static final EnumMap<NovaDirecao, NovaDirecao[]> caminhosCruzamento;

    static {
        caminhosCruzamento = new EnumMap<>(NovaDirecao.class);

        caminhosCruzamento.put(NovaDirecao.VIRA_DIREITA, new NovaDirecao[]{
            NovaDirecao.VIRA_DIREITA
        });

        caminhosCruzamento.put(NovaDirecao.PROSSEGUE, new NovaDirecao[]{
            NovaDirecao.PROSSEGUE,
            NovaDirecao.PROSSEGUE
        });

        caminhosCruzamento.put(NovaDirecao.VIRA_ESQUERDA, new NovaDirecao[]{
            NovaDirecao.PROSSEGUE,
            NovaDirecao.VIRA_ESQUERDA,
            NovaDirecao.PROSSEGUE
        });
    }

    private final int id;
    private final int sleep;
    private final Color color;
    private final Posicao posicao;
    private final GerenciadorSimulacao gerenciador;
    private final Random rng;
    private boolean vezConcedida;

    // Fila das posições que o carro vai tomar pelo cruzamento.
    private final Queue<Posicao> posicoesRestantesCruzamento;

    // Fila paralela à de cima, com os semáforos correspondentes a cada posição.
    private final Queue<Modo> filaModosCruzamento;

    public Carro(Posicao posicao, int id, int sleep, Color color) {
        this.id = id;
        this.sleep = sleep;
        this.color = color;
        this.posicao = posicao;
        this.gerenciador = GerenciadorSimulacao.getInstance();
        this.rng = new Random();
        this.vezConcedida = false;
        posicoesRestantesCruzamento = new ArrayDeque<>();
        filaModosCruzamento = new ArrayDeque<>();
    }

    public Color getColor() {
        return this.color;
    }

    public int id() { return this.id; }

    @Override
    public void run() {
        if (!vezConcedida){
            vezConcedida = true;
            acquire(posicao);
        }
            
        try {
            while (!isInterrupted()) {
                Thread.sleep(sleep);
                parar();
                movimentar();
            }
        } catch (InterruptedException ex) {
            handleRemove(false);
        }
    }

    private void releaseData() {
        filaModosCruzamento.forEach(Modo::release);
        filaModosCruzamento.clear();
        resetar(posicao);
    }

    public void movimentar() throws InterruptedException {
        Random random = new Random();
        Posicao proximaPosicao;

        var cell = gerenciador.getMalha().get(posicao);

        if (cell.verificaRua()) {
            proximaPosicao = cell.direcaoPretendida().moved(posicao);

            if (!movimentoValido(proximaPosicao)) {
                handleRemove(true);
                return;
            }

            var nextCell = gerenciador.getMalha().get(proximaPosicao);

            if (nextCell.verificaRua()) {
                // Ficará esperando a próxima posição até a mesma estar disponível para uso.
                acquire(proximaPosicao);
            } else {
                assert nextCell.verificaCruzamento();


                Direcao currDir = cell.direcaoPretendida();
                Posicao currPos = proximaPosicao;

                NovaDirecao[] path = getValidCrossingPath(currPos, currDir);

                Direcao originalCurrDir = Direcao.valueOf(currDir.name());
                Posicao originalCurrPos = (Posicao) currPos.clone();

                boolean acquiredNeededCrossing = false;
                do {      
                    boolean tryAcquire = true;
                    for (var dirChange : path) {
                        var lockable = gerenciador.getMalha().getModo(currPos);

                        tryAcquire = lockable.tryAcquire(50);
                        if (!tryAcquire) {
                           break; 
                        }

                        filaModosCruzamento.add(lockable);
                        currDir = dirChange.altera(currDir);
                        currPos = currDir.moved(currPos);
                        posicoesRestantesCruzamento.add(currPos);
                    }
                    
                    if (tryAcquire) {
                        acquiredNeededCrossing = true;
                        continue;
                    } else { // release and reset state if some was not acquired
                        filaModosCruzamento.forEach(Modo::release);
                        filaModosCruzamento.clear();
                        posicoesRestantesCruzamento.clear();
                        currDir = originalCurrDir;
                        currPos = originalCurrPos;
                    }
                    sleep(random.nextInt(500));
                } while (!acquiredNeededCrossing);
            }
        } else {
            assert cell.verificaCruzamento();
            assert !posicoesRestantesCruzamento.isEmpty();

            proximaPosicao = posicoesRestantesCruzamento.remove();
            filaModosCruzamento.remove();
            
            // acquire posicao from first road after crossing
            if (posicoesRestantesCruzamento.isEmpty()) {
                acquire(proximaPosicao);
            }
        }

        // invariant: at this point proximaPosicao is available and the car can just move onto it
        // either because the next cell is a road, and we waited for it to become available
        // or because it's a crossing, and we acquired its lockables
        var lastPosicao = (Posicao) posicao.clone();
        handleMove(proximaPosicao);
        resetar(lastPosicao);
    }

    private NovaDirecao[] getValidCrossingPath(Posicao posicaoAtual, Direcao direcaoAtual) {
        Direcao missingExit = gerenciador.getMalha().saidas(posicaoAtual);
        NovaDirecao[] availableChanges;
        if (missingExit != null) {
            var changeForMissingExit = NovaDirecao.mudandoDirecao(direcaoAtual, missingExit);
            availableChanges = NovaDirecao.except(changeForMissingExit);
        } else {
            availableChanges = NovaDirecao.values();
        }

        NovaDirecao chosenCrossingExit = availableChanges[rng.nextInt(0, availableChanges.length)];
        NovaDirecao[] path = caminhosCruzamento.get(chosenCrossingExit);

        return path;
    }

    private void resetar(Posicao pos) {
        gerenciador.getMalha().getModo(pos).release();
    }
    
    private void acquire(Posicao pos) {
        gerenciador.getMalha().getModo(pos).acquire();
    }


    private void handleMove(Posicao nextMove) {
        if (isInterrupted()) return;
        gerenciador.setCarro(nextMove, this);
        gerenciador.removeCarro(posicao);
        posicao.update(nextMove);
    }
    
    private boolean movimentoValido(Posicao movePosicao) {
        if (!(movePosicao.getLinha() >= 0 && movePosicao.getLinha() <= Geral.LINHAS-1)) {
            return false;
        }
        
        if (!(movePosicao.getColuna() >= 0 && movePosicao.getColuna() <= Geral.COLUNAS-1)) {
            return false;
        }
            
        return true;
    }
    
    private void handleRemove(boolean parar) {
        filaModosCruzamento.forEach(Modo::release);
        filaModosCruzamento.clear();
        
        gerenciador.removeCarro(posicao);
        resetar(posicao);
        if (parar)
            stop();
    }
    
    private void parar() {
        Status status = gerenciador.getStatus();
        if (status != Status.LIGADO) {
            handleRemove(true);
        }
    }

}
