package constantes;

import modos.Monitor;
import modos.Semaforo;
import modos.Modo;

public enum ModoSelecionado {

	SEMAFORO, 
	MONITOR;

    public Class<? extends Modo> toClass() {
        return switch (this) {
            case SEMAFORO -> Semaforo.class;
            case MONITOR -> Monitor.class;
        };
    }
}
