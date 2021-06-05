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


def normalize(data, min, max):
    result = []
    for point in data:
        result.append((point - min)/(max - min))
    return result


def main():

    df = pd.read_csv(FILE_NAME, delimiter=';', header=None, names=list(range(100)), dtype=np.float32)
    # df = df.fillna(0.0)
    data = df.to_numpy()
    n_samples, n_features = data.shape

    print(data.shape, type(data[0][0]))

    for i in range (0, 100):
        x, y = split_to_dims(data[i])
        # x, y = split_to_dims_all(data)

        x = normalize(x, 0, 500)
        y = normalize(y, 0, 500)

        print(x)
        print(y)

        #plt.scatter(x, y, marker='.')
        plt.plot(x, y, '-ok')
        plt.xlim([0, 1])
        plt.ylim([0, 1])

        plt.show()


if __name__ == "__main__":
    main()
