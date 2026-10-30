package Algoritmos_Ordenacao.Serial;

public class MergeSort_Serial {

    public static void sort(int[] arr) {
        int[] tmp = new int[arr.length];
        sort(arr, tmp, 0, arr.length);
    }

    public static void sort(int[] arr, int[] tmp, int lo, int hi) {
        if (hi - lo < 2) return;
        int mid = (lo + hi) / 2;
        sort(arr, tmp, lo, mid);
        sort(arr, tmp, mid, hi);
        merge(arr, tmp, lo, mid, hi);
    }

    public static void merge(int[] arr, int[] tmp, int lo, int mid, int hi) {
        int i = lo, j = mid, k = lo;
        while (i < mid && j < hi) {
            tmp[k++] = (arr[i] <= arr[j]) ? arr[i++] : arr[j++];
        }
        while (i < mid) tmp[k++] = arr[i++];
        while (j < hi)  tmp[k++] = arr[j++];
        System.arraycopy(tmp, lo, arr, lo, hi - lo);
    }
}