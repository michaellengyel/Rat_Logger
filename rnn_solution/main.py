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


class RNNp(nn.Module):
    def __init__(self, input_size, hidden_size, num_layers, num_classes, device):
        super(RNNp, self).__init__()

        self.device = device
        self.num_layers = num_layers
        self.hidden_size = hidden_size

        self.lstm = nn.LSTM(input_size, hidden_size, num_layers, batch_first=True)
        # Input needs to be of shape batch_size, seq, input_size
        self.fc = nn.Linear(hidden_size, num_classes)

    def forward(self, x):
        h0 = torch.zeros(self.num_layers, x.size(0), self.hidden_size).to(self.device)
        c0 = torch.zeros(self.num_layers, x.size(0), self.hidden_size).to(self.device)

        out, hidden_and_cell_state = self.lstm(x, (h0, c0))
        # out: batch_size, seq_length, hidden_size
        # out: (N, 28, 28)
        out = out[:, -1, :]
        # out (N, 128)
        out = self.fc(out)
        return out, hidden_and_cell_state


def main():
    # Device configuration
    device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
    print(device)

    # Hyperparameters
    # input_size = 784  # 28x28
    hidden_size = 128
    num_classes = 10
    num_epochs = 1
    batch_size = 100
    learning_rate = 0.001

    input_size = 28
    sequence_length = 28
    num_layers = 3

    # MNIST dataset
    train_dataset = torchvision.datasets.MNIST(root='./data',
                                               train=True,
                                               transform=transforms.ToTensor(),
                                               download=True)

    test_dataset = torchvision.datasets.MNIST(root='./data',
                                              train=False,
                                              transform=transforms.ToTensor())

    # Data loader
    train_loader = torch.utils.data.DataLoader(dataset=train_dataset,
                                               batch_size=batch_size,
                                               shuffle=True)

    test_loader = torch.utils.data.DataLoader(dataset=test_dataset,
                                              batch_size=batch_size,
                                              shuffle=False)

    # Model
    model = RNNp(input_size, hidden_size, num_layers, num_classes, device).to(device)

    # Loss and the Optimizer
    criterion = nn.CrossEntropyLoss()
    optimizer = torch.optim.Adam(model.parameters(), lr=learning_rate)

    # Train the model
    n_total_steps = len(train_loader)
    for epoch in range(num_epochs):
        for i, (images, labels) in enumerate(train_loader):
            # Original shape: [100, 1, 28, 28]
            # Resized shape: [100, 28, 28]
            images = images.reshape(-1, sequence_length, input_size).to(device)
            labels = labels.to(device)

            # Forward pass
            outputs, hidden_and_cell_state = model(images)
            loss = criterion(outputs, labels)

            # Backward and optimize
            optimizer.zero_grad()
            loss.backward()
            optimizer.step()

            # Print the the loss
            if (i + 1) % 100 == 0:
                print(f"epoch {epoch + 1} / {num_epochs}, step {i + 1}/{n_total_steps}, loss = {loss.item():.4}")

    with torch.no_grad():
        n_correct = 0
        n_samples = 0
        for i, (images, labels) in enumerate(test_loader):
            images = images.reshape(-1, sequence_length, input_size).to(device)
            labels = labels.to(device)
            output, hidden_and_cell_state = model(images)
            # Max returns (value, index)
            _, predicted = torch.max(output.data, 1)
            n_samples += labels.size(0)
            n_correct += (predicted == labels).sum().item()

        acc = 100.0 * n_correct / n_samples
        print(f'Accuracy of the network on the 10000 test images: {acc} %')



if __name__ == '__main__':
    main()
