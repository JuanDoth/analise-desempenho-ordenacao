# Comparação de Algoritmos de Ordenacao

Trabalho da AV2 para computação paralela e concorrente. É um projeto em Java para comparar o desempenho de quatro algoritmos de ordenacao nas versoes serial e paralela. O programa testa diferentes tamanhos e distribuicoes de dados, verifica se cada algoritmo ordenou corretamente e salva os tempos em CSV. Um script em Python resume os resultados e gera graficos.

## Algoritmos

- Merge Sort
- Quick Sort
- Insertion Sort
- Selection Sort

Cada algoritmo possui implementacao serial e paralela. Merge Sort e Quick Sort paralelos usam `ForkJoinPool`; Insertion Sort e Selection Sort paralelos dividem o vetor em partes, ordenam as partes em threads e depois fazem a intercalação.

## Como funciona

O ponto de entrada e `src/Main.java`. Ao executar, o programa:

1. Gera vetores aleatorios, ordenados e em ordem inversa, com tamanhos de 10.000, 50.000 e 100.000 elementos.
2. Usa a mesma entrada para comparar as versoes serial e paralela de cada algoritmo. As versoes paralelas sao testadas com 2, 4 e 8 threads.
3. Executa cinco amostras registradas por configuracao e confere o vetor resultante com `Arrays.sort`.
4. Grava as medidas em `csv/resultados.csv`.

Os dados aleatorios sao gerados com uma semente fixa (`42`), permitindo repetir a mesma sequencia de entradas. O CSV inclui algoritmo, versao, tamanho, tipo de dado, quantidade de threads, numero da amostra e tempo em milissegundos.

## Requisitos

- JDK instalado (Java 17 ou mais recente).
- Python instalado para gerar os graficos.
- Bibliotecas Python `pandas` e `matplotlib`.

## Como executar

Execute os comandos na pasta raiz do projeto, no PowerShell.

Compile os arquivos Java:

```powershell
javac -d out (Get-ChildItem src -Recurse -Filter *.java | ForEach-Object FullName)
```

Rode o benchmark:

```powershell
java -cp out Main
```

Os resultados serao escritos em `csv/resultados.csv`, substituindo o arquivo anterior.

## Gerar os graficos

Instale as dependencias Python (uma vez):

```powershell
py -m pip install pandas matplotlib
```

Depois de executar o benchmark, gere os resumos e graficos:

```powershell
py graficos/gerar_graficos.py
```

O script cria ou atualiza `csv/resumo.csv` com a media, o desvio padrao e o speedup. Tambem salva em `graficos/` graficos de tempo medio e de speedup para cada combinacao de tipo de dado e tamanho.

## Estrutura do projeto

- `src/Main.java`: gera as entradas, executa os testes e grava os tempos.
- `src/Algoritmos_Ordenacao/Serial/`: implementacoes seriais.
- `src/Algoritmos_Ordenacao/Paralelo/`: implementacoes paralelas.
- `csv/resultados.csv`: tempos medidos por amostra.
- `csv/resumo.csv`: estatisticas e speedup calculados pelo script Python.
- `graficos/gerar_graficos.py`: processa o CSV e cria os graficos.
- `graficos/`: imagens de tempo medio e speedup geradas pelo script.
- `out/`: arquivos `.class` produzidos pela compilacao.

## Observacao sobre desempenho

Insertion Sort e Selection Sort possuem complexidade quadratica. Por isso, os testes com vetores de 50.000 e 100.000 elementos podem levar bastante tempo, especialmente na versao serial. Os resultados de tempo tambem variam conforme o computador e os processos em execucao.
