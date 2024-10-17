package modos;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor extends ReentrantLock implements Modo{

	@Override
    public void release() {
        if (isLocked())
            super.unlock();
    }

    @Override
    public void acquire() {
        super.lock();   
    }

    @Override
    public boolean tryAcquire(long timeout) {
        try {
            return super.tryLock(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            return false;
        }
    }

    @Override
    public boolean isLocked() {
        return super.isLocked();
    }

}
