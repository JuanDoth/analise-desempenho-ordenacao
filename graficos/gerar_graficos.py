import os
import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("csv/resultados.csv")
os.makedirs("graficos", exist_ok=True)

resumo = (df.groupby(["algoritmo", "tamanho", "tipo_dado", "threads"])["tempo_ms"]
            .agg(media="mean", desvio="std").reset_index())

serial = (resumo[resumo["threads"] == 1]
          [["algoritmo", "tamanho", "tipo_dado", "media"]]
          .rename(columns={"media": "media_serial"}))
resumo = resumo.merge(serial, on=["algoritmo", "tamanho", "tipo_dado"])
resumo["speedup"] = resumo["media_serial"] / resumo["media"]
resumo.to_csv("csv/resumo.csv", index=False)

for (tipo, tam), g in resumo.groupby(["tipo_dado", "tamanho"]):
    tempo = g.pivot(index="algoritmo", columns="threads", values="media")
    ax = tempo.plot(kind="bar", logy=True, figsize=(9, 5))
    ax.set_ylabel("Tempo médio (ms, escala log)")
    ax.set_xlabel("")
    ax.set_title(f"Tempo médio - dados {tipo}, n = {tam}")
    ax.legend(title="Threads (1 = serial)")
    plt.xticks(rotation=0)
    plt.tight_layout()
    plt.savefig(f"graficos/tempo_{tipo}_{tam}.png")
    plt.close()

    sp = g[g["threads"] > 1].pivot(index="algoritmo", columns="threads", values="speedup")
    ax = sp.plot(kind="bar", figsize=(9, 5))
    ax.axhline(1, color="black", linestyle="--")
    ax.set_ylabel("Speedup (serial / paralelo)")
    ax.set_xlabel("")
    ax.set_title(f"Speedup - dados {tipo}, n = {tam}")
    ax.legend(title="Threads")
    plt.xticks(rotation=0)
    plt.tight_layout()
    plt.savefig(f"graficos/speedup_{tipo}_{tam}.png")
    plt.close()

print("Gráficos salvos na pasta graficos")