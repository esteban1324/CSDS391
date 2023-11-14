import numpy as np
import matplotlib.pyplot as plt
import random
import math
import csv 
import pandas as pd


# this module will be used to plot the decision boundary for Q2. (a) - (e)
 
#load the data
iris = pd.read_csv("irisdata.csv")

features = ['sepal_length','sepal_width','petal_length', 'petal_width']

species = ['species']   
types = iris[species].values

data_vector = iris[features].values

class Perceptron():
    
    def __init__(self, data:np.ndarray, weight: int, bias: int):
        self.data = data
        self.weight = weight
        self.bias = bias
        
    # function that plots the 2nd and 3rd classes of the data (2a)
    def plot_2nd_3rd_classes(self):
        self.plot_classes()
        plt.title('2nd and 3rd classes of the data')
        plt.show()
        
    def plot_classes(self):
        species = {'versicolor': 'green', 'virginica': 'blue'}   
        # iterate for loop and if the species is versicolor or virginica, plot the data
        for i in range(data_vector.shape[0]):
            if types[i] == 'versicolor' or types[i] == 'virginica':
                plt.scatter(data_vector[i, 2], data_vector[i, 3], color = iris['species'].map(species)[i])
                
        plt.xlim(2.5, 7.0)
        plt.ylim(.9, 2.6)
        plt.xlabel('petal_length')
        plt.ylabel('petal_width')

    # function that computes output of a simple perceptron using the sigmoid function (2b)
    def sigmoid(self, x):
        return 1 / (1 + np.exp(-x))
    
    # function that plots the decision boundary for the perceptron (2c)
    def plot_decision_boundary(self):
        
        # plot the decision boundary
        x_range = np.arange(2.5, 6.7, 0.1)
        y_range = - (self.weight[0] * x_range) + self.bias
       
        plt.plot(x_range, y_range, color = 'black')
        
        # plot the versicolor and virginica classes
        self.plot_classes()
        
        plt.title("Decision Boundary for Non Linearity")
        plt.show()
        
        
         
       
        
        
   
if __name__ == '__main__':
    
    # initialize the weight and bias
    weight = [.5, 1]
    bias = 4.25
    
    
    x = Perceptron(data_vector, weight, bias)
    
    x.plot_decision_boundary()
 