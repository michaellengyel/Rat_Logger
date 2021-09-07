import random

import torch
import torch.nn as nn
import torchvision
import torchvision.transforms as transforms
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

from torch.utils.data import Dataset
from torch.utils.data import DataLoader

from torch.utils.tensorboard import SummaryWriter
import time


class LSTMPredictor(nn.Module):
    def __init__(self, n_hidden):
        super(LSTMPredictor, self).__init__()
        self.n_hidden = n_hidden
        # lstm1, lstm2, linear
        self.lstm1 = nn.LSTMCell(1, self.n_hidden)  # input, hidden
        self.lstm2 = nn.LSTMCell(self.n_hidden, self.n_hidden)
        self.linear = nn.Linear(self.n_hidden, 1)

    def forward(self, x, future=0):
        outputs = []
        n_samples = x.size(0)

        h_t1 = torch.zeros(n_samples, self.n_hidden, dtype=torch.float32)  # initial hidden state
        c_t1 = torch.zeros(n_samples, self.n_hidden, dtype=torch.float32)  # initial hidden state

        h_t2 = torch.zeros(n_samples, self.n_hidden, dtype=torch.float32)  # initial hidden state
        c_t2 = torch.zeros(n_samples, self.n_hidden, dtype=torch.float32)  # initial hidden state

        for input_t in x.split(1, dim=1):
            # N, 1
            h_t1, c_t1 = self.lstm1(input_t, (h_t1, c_t1))
            h_t2, c_t2 = self.lstm2(h_t1, (h_t2, c_t2))
            output = self.linear(h_t2)
            outputs.append(output)

        for i in range(future):
            h_t1, c_t1 = self.lstm1(output, (h_t1, c_t1))
            h_t2, c_t2 = self.lstm2(h_t1, (h_t2, c_t2))
            output = self.linear(h_t2)
            outputs.append(output)

        outputs = torch.cat(outputs, dim=1)
        return outputs


def main():
    N = 100  # Number of training data elements
    L = 1000  # length of sequence
    T = 20  # Phase shift of training data (sin wave)

    # Generating the training data
    x = np.empty((N, L), np.float32)
    x[:] = np.array(range(L)) + np.random.randint(-4 * T, 4 * T, N).reshape(N, 1)
    y = np.sin(x / 1.0 / T).astype(np.float32)
    # print(y.shape)

    # Visualizing the training data
    for i in range(0, N):
        plt.title("Sine wave")
        plt.xlabel("x")
        plt.ylabel("y")
        plt.xlim([-400, 1400])
        plt.plot(x[i, :], y[0, :], 'r', linewidth=0.1)
    plt.show()

    # Setting up training
    # train_input = torch.from_numpy(y[3:, :-1])  # 97, 999
    # train_target = torch.from_numpy(y[3:, 1:])  # 97, 999
    # test_input = torch.from_numpy(y[:3, :-1])  # 3, 999
    # test_target = torch.from_numpy(y[:3, 1:])  # 3, 999

    train_input = torch.from_numpy(y[3:, :-5])  # 97, 999
    train_target = torch.from_numpy(y[3:, 5:])  # 97, 999
    test_input = torch.from_numpy(y[:3, :50])  # 3, 20
    test_target = torch.from_numpy(y[:3, :50])  # 3, 999

    model = LSTMPredictor(n_hidden=10)
    criterion = nn.MSELoss()

    optimizer = torch.optim.LBFGS(model.parameters(), lr=0.8)

    n_steps = 10

    for i in range(n_steps):
        print("Step", i)

        def closure():
            optimizer.zero_grad()
            future = 0
            out = model(train_input, future=future)
            loss = criterion(out, train_target)
            print("loss", loss.item())
            loss.backward()
            return loss

        optimizer.step(closure)

        with torch.no_grad():
            future = 950
            pred = model(test_input, future=future)
            loss = criterion(pred[:, :-future], test_target)
            print("test loss", loss.item())
            y = pred.detach().numpy()

        plt.title("Sine wave")
        plt.xlabel("x")
        plt.ylabel("y")

        # n = train_input.shape[1]
        n = test_input.shape[1]

        def draw(y_i, color):
            plt.plot(np.arange(n), y_i[:n], color, linewidth=0.5)
            plt.plot(np.arange(n, n + future), y_i[n:], color + ":", linewidth=0.5)

        draw(y[0], 'r')
        # draw(y[1], 'b')
        # draw(y[2], 'g')

        plt.savefig("predict%d.png" % i)
        plt.close()


if __name__ == '__main__':
    main()
