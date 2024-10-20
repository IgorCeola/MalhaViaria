package mecanismos;

public interface Modo {
    boolean tryAcquire(long timeout);
    boolean isLocked();
    void acquire();
    void release();
}
