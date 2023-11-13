import numpy as np
import matplotlib.pyplot as plt
import random
import math
import csv 
import pandas as pd


# this module will be used to plot the decision boundary for Q2. (a) - (e)
 
#load the data
iris = pd.read_csv("C:\\Users\\esteb\\OneDrive\\Documents\\GitHub\\CSDS391\\Iris classification\\irisdata.csv")

features = ['sepal_length','sepal_width','petal_length', 'petal_width']

species = ['species']   
types = iris[species].values

data_vector = iris[features].values

class perceptron():
    
    def __init__(self, data:np.ndarray, weight: int, bias:int):
        self.data = data
        self.weight = weight
        self.bias = bias
        
    
    # function that plots the 2nd and 3rd classes of the data (2a)
    def plot_2nd_3rd_classes(data:np.ndarray):
    # plot the versicolor and virginica classes
        species = {'versicolor': 'green', 'virginica': 'blue'}   
    # iterate for loop and if the species is versicolor or virginica, plot the data
        for i in range(data.shape[0]):
            if types[i] == 'versicolor' or types[i] == 'virginica':
                plt.scatter(data[i, 2], data[i, 3], color = iris['species'].map(species)[i])

    plt.xlim(2.5, 7.0)
    plt.ylim(.9, 2.6)
    plt.xlabel('petal_length')
    plt.ylabel('petal_width')
    plt.title('2nd and 3rd classes of the data')
    plt.show()

# function that computes output of a simple perceptron using the sigmoid function (2b)
    def sigmoid(x):
        return 1 / (1 + np.exp(-x))
   
   
if __name__ == '__main__':
    print("This is the main function")
 