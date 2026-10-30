package Algoritmos_Ordenacao.Serial;

public class InsertionSort_Serial {

    public static void sort(int[] arr) {
        sort(arr, 0, arr.length);
    }

    public static void sort(int[] arr, int lo, int hi) {
        for (int i = lo + 1; i < hi; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= lo && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}