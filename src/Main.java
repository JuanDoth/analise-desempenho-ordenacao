import Algoritmos_Ordenacao.Serial.*;
import Algoritmos_Ordenacao.Paralelo.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int[] tamanhos = {10000, 50000, 100000};
        String[] tipos = {"aleatorio", "ordenado", "inverso"};
        int[] qtdThreads = {2, 4, 8};
        Random rnd = new Random(42);

        new File("csv").mkdirs();

        try (PrintWriter CSV = new PrintWriter("csv/resultados.csv")) {
            CSV.println("algoritmo,versao,tamanho,tipo_dado,threads,amostra,tempo_ms");

            for (int n : tamanhos) {
                for (String tipo : tipos) {
                    System.out.println("Testando array de tamanho " + n + " (tipo: " + tipo + ")");

                    int[] base = gerarArray(n, tipo, rnd);
                    int[] esperado = base.clone();
                    Arrays.sort(esperado);

                    executarTestes(CSV, "MergeSort", base, esperado, tipo, qtdThreads);
                    executarTestes(CSV, "QuickSort", base, esperado, tipo, qtdThreads);
                    executarTestes(CSV, "InsertionSort", base, esperado, tipo, qtdThreads);
                    executarTestes(CSV, "SelectionSort", base, esperado, tipo, qtdThreads);
                }
            }

            System.out.println("Testes finalizados! Resultados salvos em csv/resultados.csv");

        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static int[] gerarArray(int n, String tipo, Random rnd) {
        int[] arr = new int[n];

        if (tipo.equals("aleatorio")) {
            for (int i = 0; i < n; i++) {
                arr[i] = rnd.nextInt(1000000);
            }
        } else if (tipo.equals("ordenado")) {
            for (int i = 0; i < n; i++) {
                arr[i] = i;
            }
        } else if (tipo.equals("inverso")) {
            for (int i = 0; i < n; i++) {
                arr[i] = n - i;
            }
        }
        return arr;
    }

    static void executarTestes(PrintWriter csv, String nomeAlgo, int[] base, int[] esperado, String tipo, int[] threads) throws Exception {
        for (int amostra = 0; amostra <= 5; amostra++) {
            int[] copia = base.clone();

            long inicio = System.nanoTime();

            if (nomeAlgo.equals("MergeSort")) MergeSort_Serial.sort(copia);
            else if (nomeAlgo.equals("QuickSort")) QuickSort_Serial.sort(copia);
            else if (nomeAlgo.equals("InsertionSort")) InsertionSort_Serial.sort(copia);
            else if (nomeAlgo.equals("SelectionSort")) SelectionSort_Serial.sort(copia);

            double ms = (System.nanoTime() - inicio) / 1000000.0;

            if (!Arrays.equals(copia, esperado)) {
                System.out.println("ERRO: " + nomeAlgo + " Serial não ordenou corretamente!");
            }

            if (amostra > 0) {
                csv.println(nomeAlgo + ",serial," + base.length + "," + tipo + ",1," + amostra + "," + ms);
            }
        }

        for (int t : threads) {
            for (int amostra = 0; amostra <= 5; amostra++) {
                int[] copia = base.clone();

                long inicio = System.nanoTime();

                if (nomeAlgo.equals("MergeSort")) MergeSort_Paralelo.sort(copia, t);
                else if (nomeAlgo.equals("QuickSort")) QuickSort_Paralelo.sort(copia, t);
                else if (nomeAlgo.equals("InsertionSort")) InsertionSort_Paralelo.sort(copia, t);
                else if (nomeAlgo.equals("SelectionSort")) SelectionSort_Paralelo.sort(copia, t);

                double ms = (System.nanoTime() - inicio) / 1000000.0;

                if (!Arrays.equals(copia, esperado)) {
                    System.out.println("ERRO: " + nomeAlgo + " Paralelo (" + t + " threads) não ordenou corretamente!");
                }

                if (amostra > 0) {
                    csv.println(nomeAlgo + ",paralelo," + base.length + "," + tipo + "," + t + "," + amostra + "," + ms);
                }
            }
        }
    }
}