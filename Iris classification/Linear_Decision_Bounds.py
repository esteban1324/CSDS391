import numpy as np
import matplotlib.pyplot as plt
import random
import math 
import random 
import pandas as pd


# this module will be used to plot the decision boundary for Q2. (a) - (e)
 
#load the data
iris = pd.read_csv("C:\\Users\\esteb\\OneDrive\\Documents\\GitHub\\CSDS391\\Iris classification\\irisdata.csv")

features = ['sepal_length','sepal_width','petal_length', 'petal_width', 'species']

species = ['species']   
types = iris[species].values

data_vector = iris[features].values

class Perceptron():
    
    def __init__(self, data:np.ndarray, weight, bias):
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
    def plot_decision_boundary(self, weight, bias, point):
        
        # plot the decision boundary
        x_range = np.arange(2.5, 6.7, 0.1)
        y_range = - (weight * x_range) + bias
       
        plt.plot(x_range, y_range, color = 'black')
        
        # plot the versicolor and virginica classes
        self.plot_classes()
        
        plt.title("Decision Boundary for Non Linearity")
        plt.show()
    
        # sigmoid funit
        return 1.0 - self.sigmoid(weight * point[0] - point[1] + bias)
    
    # function that plots a surface plot of the output of the perceptron (2d)
    def plot_output(self, data:np.ndarray, weight, bias):
        fig = plt.figure()
        ax = fig.add_subplot(111, projection = '3d')
        
        
        process_data = self.pre_process(data)
        
        x_values = np.linspace(process_data[:, 2].min(), process_data[:, 2].max(), 100)
        y_values = np.linspace(process_data[:, 3].min(), process_data[:, 3].max(), 100)
            
        x, y = np.meshgrid(x_values, y_values)

        # Compute the perceptron output for each point in the meshgrid
        z = self.sigmoid(weight * x - y + bias)
        
        x_flat = x.flatten()
        y_flat = y.flatten()
        z_flat = z.flatten()
          
        # Plot the surface
        ax.scatter(x_flat, y_flat, z_flat, color='green', marker='o')
        
        #ax.plot(x_flat, y_flat, z_flat, color='black')
        
        
        
                                          
        # plot only the versicolor and virginica classes                                 
        ax.set_xlabel('x')
        ax.set_ylabel('y')
        #ax.set_zlabel('z')
        ax.set_title('Surface Plot of Output of Perceptron')
           
        plt.show()
 
        
    # only get the versicolor and virginica classes and return a new data vector
    def pre_process(self, data):
        
        process_data = []
        
        for i in range(data.shape[0]):
            if types[i] == 'versicolor' or types[i] == 'virginica':
                process_data.append(data[i])
        
        return np.array(process_data)
    
    
    def print_output(self,data, weight, bias):
        
        process_data = self.pre_process(data)
        
        for i in range(process_data.shape[0]):    
            print(process_data[i, :])
            print(self.sigmoid(weight * process_data[i, 2] - process_data[i, 3] + bias))
      
           
if __name__ == '__main__':
    
    # initialize the weight and bias and point on graph.
    weight = .47
    bias = 4.10
    point = [5.4, 1.3]
    
    x = Perceptron(data_vector, weight, bias)
    
    # 2b 
    #print(x.sigmoid(weight * point[0] - point[1] + bias))
    
    # 2c
    #print(x.plot_decision_boundary(weight, bias, point))
    
    # 2d     
   # x.plot_output(data_vector, -.47, bias)
    
    # 2e 
    x.print_output(data_vector, -.6, 4.8)
    
 