# Rat Logger
The objective of the operation is to create artificial mouse trajectories that mimic the natural movement of a human operator.

### Quick Start Guide
#### Data Collection:
Run the following command in the root folder (executes app.jar):  
java -jar app.jar

#### Data Collection Script:
Open root location in Eclipse (Java)

#### Anaconda Environment Setup:
From the cloned repository, run the following commands in the terminal:

$ conda env create -f environment.yml  
$ conda activate ml_env

To utilize GPU (Optional):

$ pip install torch==1.7.1+cu101 torchvision==0.8.2+cu101 torchaudio==0.7.2 -f https://download.pytorch.org/whl/torch_stable.html

If using pycharm, set the interpreter to the python version in the created conda env e.g:

.../anaconda3/envs/sheep_env/bin/python

When adding or removing a dependency from the environment.yml list, run:  
$ conda env update --file environment.yml

To run Tensorboard enter:  
$ tensorboard --logdir=/path/to/output/logs/folder/  
or  
$ tensorboard --logdir runs

#### Machine Learning
preprocessing.py (transforms the variable length data into fixed size)  
inference.py (runs the saved nn and displays nn output compared to human output)  
training.py (executes the training loop and saves the model)  
visualizer.py (visualized raw data)


### Used Sources/Dependencies
TBD

### System Dependencies:
TBD

### TODO:
TBD
