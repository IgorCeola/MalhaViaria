package gerenciador;

import java.util.HashMap;
import java.util.Map;

import gerenciador.GerenciadorSimulacao;
import model.Carro;
import model.Posicao;
import model.Malha;
import constantes.Status;
import modos.Modo;



public class GerenciadorSimulacao {
	
	private static GerenciadorSimulacao database;
    private GerenciadorSimulacao() {
        this.carros = new HashMap<>();
        this.status = Status.DESLIGADO;
    }
    
    private Malha world;
    private Map<Posicao, Carro> carros;
    private Status status;
    private Class<? extends Modo> modo;

    public static synchronized GerenciadorSimulacao getInstance() {
        if (database == null) {
            database = new GerenciadorSimulacao();
        }
        return database;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Malha getMalha() {
        return world;
    }

    public void setMalha(Malha world) {
        this.world = world;
    }
    
    public Carro getCarro(Posicao position) {
        return carros.get(position);
    }
    
    public Carro removeCarro(Posicao position) {
        return carros.remove(position);
    }
    
    public void setCarro(Posicao position, Carro car) {
        carros.put(position, car);
    }

    public Map<Posicao, Carro> getCarros() {
        return carros;
    };
    
    public void limparCarros() {
        this.carros.clear();
    };

    public Class<? extends Modo> getModo() {
        return this.modo;
    }

    public void setModo(Class<? extends Modo> modo) {
        this.modo = modo;
    }
	
}
