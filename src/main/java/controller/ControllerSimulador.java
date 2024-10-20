package controller;

import java.util.Map;

import gerenciador.GerenciadorSimulacao;
import gerenciador.GerenciadorSimulacao;
import controller.SpawnerCarro;
import controller.RecarregadorTela;
import model.Carro;
import model.Posicao;
import view.TelaMalha;
import constantes.Status;
//import presentation.view.UpdatableSimulatorView;

public class ControllerSimulador {

	private final GerenciadorSimulacao gerenciador;
    private SpawnerCarro spawner;
    private final RecarregadorTela recarregador;

    public ControllerSimulador(TelaMalha view) {
        this.gerenciador = GerenciadorSimulacao.getInstance();
        
        recarregador = new RecarregadorTela(view);
        recarregador.start();
    }
    
    public void handleStart(int CarroCount, int interval) {
        gerenciador.setStatus(Status.LIGADO);
        handleStopSpawner();
        stopCarros();
        
        
        gerenciador.getMalha().loadLocks();
        spawner = new SpawnerCarro(CarroCount, interval);
        spawner.start();
    };
    
    public boolean handleStop() {
        gerenciador.setStatus(Status.DESLIGADO);
        handleStopSpawner();
        stopCarros();
        return true;
    }
    
    public boolean handleStopAndWait() {
        handleStopSpawner();
        return true;
    }
    
    private void handleStopSpawner() {
        if (spawner != null) {
            spawner.stop();
        }
    };
    
    private void stopCarros() {
        for (Map.Entry<Posicao, Carro> entry : gerenciador.getCarros().entrySet()) {
            Carro Carro = entry.getValue();
            Carro.stop();
        }
        gerenciador.limparCarros();
    }
}
