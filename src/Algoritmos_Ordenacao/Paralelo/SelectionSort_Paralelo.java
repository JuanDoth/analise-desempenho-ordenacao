package Algoritmos_Ordenacao.Paralelo;
import Algoritmos_Ordenacao.Serial.MergeSort_Serial;
import Algoritmos_Ordenacao.Serial.SelectionSort_Serial;

public class SelectionSort_Paralelo {

    public static void sort(int[] array, int qtdThreads) throws InterruptedException {
        int n = array.length;
        int[] limite = new int[qtdThreads + 1];
        for (int i = 0; i <= qtdThreads; i++) limite[i] = n * i / qtdThreads;

        Thread[] threads = new Thread[qtdThreads];
        for (int t = 0; t < qtdThreads; t++) {
            int lo = limite[t], hi = limite[t + 1];
            threads[t] = new Thread(() -> SelectionSort_Serial.sort(array, lo, hi));
            threads[t].start();
        }
        for (Thread t : threads) t.join();

        int[] tempo = new int[n];
        for (int t = 1; t < qtdThreads; t++) {
            MergeSort_Serial.merge(array, tempo, 0, limite[t], limite[t + 1]);
        }
    }
}