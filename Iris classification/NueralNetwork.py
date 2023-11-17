import numpy as np
import matplotlib.pyplot as plt
import random
import math 
import random 
import pandas as pd


iris = pd.read_csv("C:\\Users\\esteb\\OneDrive\\Documents\\GitHub\\CSDS391\\Iris classification\\irisdata.csv")

features = ['sepal_length','sepal_width','petal_length', 'petal_width', 'species']

species = ['species']   
types = iris[species].values

data_vector = iris[features].values


# only get the versicolor and virginica classes and return a new data vector
def pre_process(data):
    process_data = []
        
    for i in range(data.shape[0]):
        if types[i] == 'versicolor' or types[i] == 'virginica':
            process_data.append(data[i])
        
    return np.array(process_data)

         
'''Linear Decision Boundary methods '''
#function that plots the 2nd and 3rd classes of the data (2a)
def plot_2nd_3rd_classes():
    plot_classes()
    plt.title('2nd and 3rd classes of the data')
    plt.show()
        
def plot_classes():
    species = {'versicolor': 'green', 'virginica': 'blue'}   
    # iterate for loop and if the species is versicolor or virginica, plot the data
    for i in range(data_vector.shape[0]):
        if types[i] == 'versicolor' or types[i] == 'virginica':
            plt.scatter(data_vector[i, 2], data_vector[i, 3], color = iris['species'].map(species)[i])
                
    plt.xlim(2.5, 7.0)
    plt.ylim(.9, 2.6)
    plt.xlabel('petal_length')
    plt.ylabel('petal_width')

    #function that computes output of a simple perceptron using the sigmoid function (2b)  
def sigmoid(x):
    return 1 / (1 + np.exp(-x))
    
# function that plots the decision boundary for the perceptron (2c)
def plot_decision_boundary(weight, bias, point):        
        
        # plot the decision boundary
    x_range = np.arange(2.5, 6.7, 0.1)
    y_range = -(weight[0] * x_range) + bias
        
    plt.plot(x_range, y_range, color = 'black')
        
        # plot the versicolor and virginica classes
    plot_classes()
        
    plt.title("Decision Boundary for Non Linearity")
    plt.show()
    
    return 1 - sigmoid(-weight[0] * point[0] - point[1] + bias)
    
    # function that plots a surface plot of the output of the perceptron (2d)
def plot_output(data:np.ndarray, weight, bias):
    fig = plt.figure()
    ax = fig.add_subplot(111, projection = '3d')
        
    process_data = pre_process(data)
        
    x_values = np.linspace(process_data[:, 2].min(), process_data[:, 2].max(), 100)
    y_values = np.linspace(process_data[:, 3].min(), process_data[:, 3].max(), 100)
            
    x, y = np.meshgrid(x_values, y_values)
        
    # Compute the perceptron output for each point in the meshgrid, possibly vectorizing this
    # vector x*weight + y*weight + bias   
             
    z = sigmoid(-weight[0] * x - y + bias)
        
    ax.plot_surface(x, y, z)
                                                          
    ax.set_xlabel('x')
    ax.set_ylabel('y')
    #ax.set_zlabel('z')
    ax.set_title('Surface Plot of Output of Perceptron')
           
    plt.show()
 
# function that prints the output of the perceptron (2e)
def print_output(data, weight, bias):   
    process_data = pre_process(data)

    for i in range(process_data.shape[0]):    
        print(process_data[i, :])
        print(sigmoid(-weight[0] * process_data[i, 2] - process_data[i, 3] + bias))
      

class NueralNetwork:
    
    def __init__(self, data:np.ndarray, weight, bias):
        self.data = data
        self.weight = weight
        self.bias = bias
    
    
    # function that calculates mean squared error (MSE) (3a)
    def mse(self):
        pre_process_data = pre_process(self.data)
         
        error = 0
        
        for i in range(pre_process_data.shape[0]):    
            x1 = pre_process_data[i, 2]
            x2 = pre_process_data[i, 3]
  
            prediction = self.classifier(i)
            z = sigmoid(weight[0] * x1 + x2 * weight[1] + self.bias)
           
            error += (z - prediction) ** 2
            
        
        mean_error = error / pre_process_data.shape[0] 
        
        
        return mean_error
    
    # helper function that predicts the class 
    def classifier(self, i):
        if (types[i] == 'versicolor'):
            return 1
        else:
            return 0
    
    # function that computes the mse for two different decision boundaries, plots boundary (3b)
    def plot_mse(self, weight):
              
        x_range = np.arange(2.5, 6.7, 0.1)
        
        slope = -(weight[0] / weight[1])
        y_intercept = -(self.bias / weight[1])
        y_range = slope * x_range + y_intercept
        
        plt.plot(x_range, y_range, color = 'black')
       
       
        plot_2nd_3rd_classes()
        
        plt.title("Decision Boundary for Non Linearity")
        
     
        return self.mse()
    
    # function that computes summed gradient for an ensamble of paterns, plot this (3e)
    







if __name__ == '__main__':
    
    # initialize the weight and bias and point on graph.
    weight = [.47, -1]
    bias = 4.1
    point = [4.1, 1.5] 
    
    # 2a
    #plot_2nd_3rd_classes()
    # 2b z = y=mx+b -> z = mx - y + b
    #print(sigmoid(-.47 * 4.1 - 2.3 + 4.1))
    
    # 2c
    #print(plot_decision_boundary(weight, 4.1, point))
    
    # 2d     
    #plot_output(data_vector, weight, bias) 
    weight2 = [.47,3]
    bias2 = 4.1
    # 2e 
    #print_output(data_vector, weight, bias2)
    
    
    # 3a
    # define nonlinear functions weights -> [w0, w1, w2] x1 is petal length x2 is petal width.
    # initial weight (w0) is the bias
    bias3 = -45 
    weight3 = [6, 10]
    
    #print(NueralNetwork(data_vector, weight3, bias3).mse())
    
    # 3b 
    print(NueralNetwork(data_vector, weight3, bias3).plot_mse(weight3))
     
    
    