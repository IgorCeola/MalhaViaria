package controller;

import gerenciador.GerenciadorSimulacao;
import constantes.Geral;
import view.TelaMalha;

public class RecarregadorTela extends Thread{
	private final TelaMalha view;
    private final GerenciadorSimulacao db;
    
    public RecarregadorTela(TelaMalha view) {
        this.view = view;
        this.db = GerenciadorSimulacao.getInstance();
    }

    @Override
    public void run() {
        while (true) {            
            try {
                Thread.sleep(Geral.refreshIntervalMs);
                view.updateView();
                
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
