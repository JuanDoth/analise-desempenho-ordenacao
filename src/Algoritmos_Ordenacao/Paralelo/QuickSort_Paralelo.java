package Algoritmos_Ordenacao.Paralelo;
import Algoritmos_Ordenacao.Serial.QuickSort_Serial;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class QuickSort_Paralelo extends RecursiveAction {
    private final int[] array;
    private final int low, high;

    QuickSort_Paralelo(int[] array, int low, int high) {
        this.array = array; this.low = low; this.high = high;
    }

    @Override
    protected void compute() {
        if (low >= high) return;
        if (high - low < 5000) {
            QuickSort_Serial.sort(array, low, high);
            return;
        }
        int[] p = QuickSort_Serial.particiona(array, low, high);
        invokeAll(new QuickSort_Paralelo(array, low, p[1]),
                new QuickSort_Paralelo(array, p[0], high));
    }

    public static void sort(int[] array, int qtdThreads) {
        ForkJoinPool pool = new ForkJoinPool(qtdThreads);
        pool.invoke(new QuickSort_Paralelo(array, 0, array.length - 1));
        pool.shutdown();
    }
}