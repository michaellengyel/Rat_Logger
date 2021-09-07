# https://www.youtube.com/watch?v=y2BaTt1fxJU

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


class DecoderRNN(nn.Module):
    def __init__(self, embed_size, hidden_size, vocab_size, num_layers):
        super(DecoderRNN, self).__init__()
        self.embed = nn.Embedding(vocab_size, embed_size)
        self.lstm = nn.LSTM(embed_size, hidden_size, num_layers)
        self.linear = nn.Linear(hidden_size, vocab_size)
        self.dropout = nn.Dropout(0.5)

    def forward(self, features, captions):
        embeddings = self.dropout(self.embed(captions))
        embeddings = torch.cat((features.unsqueeze(0), embeddings), dim=0)
        hiddens, _ = self.lstm(embeddings)
        outputs = self.linear(hiddens)
        return outputs


def main():

    print("START")

    embed_size = 3
    hidden_size = 10
    vocab_size = 50
    num_layers = 3

    model = DecoderRNN(embed_size, hidden_size, vocab_size, num_layers)

    features = torch.tensor([0.32, 0.42, 0.1, 0.03])
    captions = torch.tensor([3.2, 4.2, 7.3, 2.2, 1.0, 2.2])

    outputs = model(features, captions)


if __name__ == '__main__':
    main()
