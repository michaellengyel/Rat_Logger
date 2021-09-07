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

input_dim = 2  # The size of the input at each time step e.g. 3 will be [4, 2, 5]
hidden_dim = 4  # Size of hidden state and cell state at each time step
n_layers = 1  # Number of LSTM layers stacked on top of each other

lstm_layer = nn.LSTM(input_size=input_dim, hidden_size=hidden_dim, num_layers=n_layers, batch_first=True)

batch_size = 1
seq_len = 10

input_data = torch.randn(batch_size, seq_len, input_dim)  # Dummy data (batch size, sequence length, input dimension)

hidden_state = torch.randn(n_layers, batch_size, hidden_dim)  # Short Term Memory (Hidden State) (This is the output of the cell?)
cell_state = torch.randn(n_layers, batch_size, hidden_dim)  # Long Term Memory (Cell State) (This is the memory of the cell?)
hidden_and_cell_state = (hidden_state, cell_state)  # The hidden state is stored as a tuple`

out, hidden_and_cell_state = lstm_layer(input_data, hidden_and_cell_state)
print(out.shape)
print(out)
#print(out[0, :, :])
#print(hidden_and_cell_state)

