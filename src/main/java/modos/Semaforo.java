package modos;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Semaforo extends Semaphore implements Modo{

	public Semaforo() {
        super(1);
    }

    @Override
    public boolean tryAcquire(long timeout) {
        try {
            return super.tryAcquire(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            return false;
        }
    }

    @Override
    public void acquire() {
        try {
            super.acquire();
        } catch (InterruptedException ex) {
            System.out.println("Something went wrong!");
        }
    }

    @Override
    public void release() {
        super.release();
    }

    @Override
    public boolean isLocked() {
        return super.availablePermits() == 0;
    }

}
