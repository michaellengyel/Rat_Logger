import csv
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import math

FILE_NAME = "./data.txt"


def split_to_dims(data):
    return data[1:][::2], data[0:][::2]


def split_to_dims_all(data):
    x = []
    y = []
    for track in data:
        for index in range(len(track)):
            if not math.isnan(track[index]):
                if index % 2 == 0:
                    x.append(track[index])
                else:
                    y.append(track[index])
    return x, y


def main():

    df = pd.read_csv(FILE_NAME, delimiter=';', header=None, names=list(range(100)), dtype=np.float32)
    # df = df.fillna(0.0)
    data = df.to_numpy()
    n_samples, n_features = data.shape

    print(data.shape, type(data[0][0]))

    # x, y = split_to_dims(data[2])
    x, y = split_to_dims_all(data)
    print(x)
    print(y)

    plt.scatter(x, y)
    plt.plot(x, y)
    plt.show()


if __name__ == "__main__":
    main()
