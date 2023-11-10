import numpy as np
import matplotlib.pyplot as plt
import random
import math
import csv 
import pandas as pd

""" This is the main class for K means clustering for all the data points. """
class K_Means():
    
    def __init__(self, k:int, data:np.ndarray, max_iter:int):
        self.k = k  
        self.data = data
        self.max_iter = max_iter
        self.centroids = self.initialize_centroids()
        
    # initialize the centroids within range of random data points k times
    def initialize_centroids(self)-> np.ndarray:   
        random.seed(32)
           
        indices = np.random.choice(len(self.data), self.k, replace=False)
        
        centroids = self.data[indices]
                
        return centroids
    
    
    # calculate the distance between the data point and the centroid 
    def euclidean_distance(self) -> float:
        return  (np.linalg.norm(self.data[:, 2:4] - self.centroids[:,2:4], axis=1) ** 2)
      
    # Calculate the sum of squared distances between each data point and its nearest centroid.   
    def objective_function(self) -> float:
        total_distance = 0 
            
        # Summation of k centroids and data points to find the total distance
        for i in range(len(self.data)):     
            min_distance = math.inf
            for j in range(len(self.centroids)):      
                distance = np.linalg.norm(self.data[i, 2:4] - self.centroids[j, 2:4], axis = 2)
                
                # update the minimum distance
                if distance < min_distance:
                    min_distance = distance        
        
            total_distance += min_distance ** 2
            
        return total_distance
    
    
    
    # plot the data points
    def plot_data_points(self):     
        # plot the based on different colors, flower type  
        unique_flower = {'setosa': 'red', 'versicolor': '#006BA4', 'virginica': 'm'}
        
        # plot the data points
        for data_point in self.data:
            plt.scatter(data_point[2], data_point[3], color = unique_flower[data_point[4]])
        
        # plot the centroids
        for centroid in self.centroids:
            plt.scatter(centroid[2], centroid[3], color = 'black', marker = 'x')
        
        # label the graph
        plt.xlabel('Petal Length')
        plt.ylabel('Petal Width')
        plt.title('Iris Data')
            
        plt.show()
        
    
              
if __name__ == "__main__":
     
    iris = pd.read_csv("irisdata.csv")

    features = ['sepal_length','sepal_width','petal_length', 'petal_width', 'species']

    data_vector = iris[features].values
    
    x = K_Means(2, data_vector, 200)
    
    x.plot_data_points()
