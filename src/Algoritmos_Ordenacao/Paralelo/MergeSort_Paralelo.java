package Algoritmos_Ordenacao.Paralelo;

import Algoritmos_Ordenacao.Serial.MergeSort_Serial;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MergeSort_Paralelo extends RecursiveAction {
    private final int[] array, tempo;
    private final int lo, hi;

    MergeSort_Paralelo(int[] array, int[] tempo, int lo, int hi) {
        this.array = array;
        this.tempo = tempo;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    protected void compute() {
        if (hi - lo < 5000) {
            MergeSort_Serial.sort(array, tempo, lo, hi);
            return;
        }
        int mid = (lo + hi) / 2;
        invokeAll(new MergeSort_Paralelo(array, tempo, lo, mid),
                new MergeSort_Paralelo(array, tempo, mid, hi));
        MergeSort_Serial.merge(array, tempo, lo, mid, hi);
    }

    public static void sort(int[] array, int qtdThreads) {
        ForkJoinPool pool = new ForkJoinPool(qtdThreads);
        pool.invoke(new MergeSort_Paralelo(array, new int[array.length], 0, array.length));
        pool.shutdown();
    }
}