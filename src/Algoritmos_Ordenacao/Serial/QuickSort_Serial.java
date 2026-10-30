package Algoritmos_Ordenacao.Serial;

public class QuickSort_Serial {

    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    public static void sort(int[] arr, int low, int high) {
        if (low >= high) return;
        int[] p = particiona(arr, low, high);
        sort(arr, low, p[1]);
        sort(arr, p[0], high);
    }

    public static int[] particiona(int[] arr, int low, int high) {
        int pivo = arr[low + (high - low) / 2];
        int i = low, j = high;
        while (i <= j) {
            while (arr[i] < pivo) i++;
            while (arr[j] > pivo) j--;
            if (i <= j) {
                int aux = arr[i];
                arr[i] = arr[j];
                arr[j] = aux;
                i++;
                j--;
            }
        }
        return new int[]{i, j};
    }
}