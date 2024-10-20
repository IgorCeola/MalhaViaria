package controller;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Random;

import gerenciador.GerenciadorSimulacao;
import model.Carro;
import model.Posicao;
import constantes.Geral;

public class SpawnerCarro extends Thread{

	private final Random rng = new Random();
    private final int CarroCount;
    private final long interval;

    private final static Color[] CarroColors = {
        Color.red,
        Color.black,
        Color.blue,
        Color.orange.darker().darker(),
        Color.green.darker(),
        Color.cyan.darker(),
    };
    private int currentColor = 0;

    private int currentId = 0;
    
    public SpawnerCarro(int CarroCount, int interval) {
        this.CarroCount = CarroCount;
        this.interval = interval * 1000L;
    }
    
    @Override
    public void run() {
        handleCarros();
    }

    private Color nextColor() {
        var i = currentColor;
        currentColor = (currentColor + 1) % CarroColors.length;
        return CarroColors[i];
    }

    private int nextId() {
        return currentId++;
    }

    private synchronized void handleCarros() {
        GerenciadorSimulacao gerenciador = GerenciadorSimulacao.getInstance();

        try {
            while (!isInterrupted()) {
                Thread.sleep(Geral.carSpawnIntervalMs);
                List<Posicao> entryPoints = gerenciador.getMalha().getEntryPoints();
                for (Posicao Posicao : entryPoints) {
                    while (gerenciador.getCarros().size() >= CarroCount) {    
                        Thread.sleep(Geral.carSpawnIntervalMs);
                    }

                    Map<Posicao, Carro> Carros = gerenciador.getCarros();
                    while (gerenciador.getMalha().getModo(Posicao).isLocked()){}
                    
                    int speed = rng.nextInt(Geral.minCarIntervalMs, 1 + Geral.maxCarIntervalMs);
                    Carro Carro = new Carro((Posicao) Posicao.clone(), nextId(), speed, nextColor());
                    Carros.put(Posicao, Carro);
                    
                    Carro.start();
                    while (!gerenciador.getMalha().getModo(Posicao).isLocked()){}
                    
                    if (interval > 0)
                        Thread.sleep(interval);
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Interrupted Carro spawner (ok)");
            currentId = 0;
        }
    }
}
