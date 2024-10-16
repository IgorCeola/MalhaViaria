package modos;

public interface Modo {
    boolean tryAcquire(long timeout);
    boolean isLocked();
    void acquire();
    void release();
}
