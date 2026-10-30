package Algoritmos_Ordenacao.Serial;

public class SelectionSort_Serial {

    public static void sort(int[] arr) {
        sort(arr, 0, arr.length);
    }

    public static void sort(int[] arr, int low, int high) {
        for (int i = low; i < high - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < high; j++) {
                if (arr[j] < arr[menor]) menor = j;
            }
            int aux = arr[i];
            arr[i] = arr[menor];
            arr[menor] = aux;
        }
    }
}