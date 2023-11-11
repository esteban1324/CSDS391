# Imports 
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
        self.cluster_assignment = np.zeros(len(self.data))
        
    # initialize the centroids within range of random data points k times
    def initialize_centroids(self)-> np.ndarray:   
        random.seed(32)
           
        indices = np.random.choice(len(self.data), self.k, replace=False)
        
        centroids = self.data[indices]
                
        return centroids
    
    
    
    
        
    # Calculate the sum of squared distances between each data point and its nearest centroid.   
    def objective_function(self) -> float:
        total_distance = 0 
            
        # Summation of k centroids and data points to find the total distance
        for i in range(len(self.data)):     
            min_distance = math.inf     
            distance = np.linalg.norm(self.data[i, 2:4] - self.centroids[:, 2:4])
            # update the minimum distance
            min_distance = np.min(distance)  
             
        
            total_distance += min_distance ** 2
            
        return total_distance
    
    
    # kmeans algorithm, for 11/11 put implement helper functions to make it easier to read and get correct results for elbow method
    def kmeans(self):
        
        #step 1: initialize the centroids k times that are from the data points
        self.centroids = self.initialize_centroids()
        
        for n in range(self.max_iter):    
        
        # step 2: for each data point in the data set, assign a cluster that is closest to the data point
        
        # helper function to assign clusters to data points
            for data_point in self.data:
                
                clusters = np.argmin(np.linalg.norm(data_point[2:4] - self.centroids[:, 2:4]))
                self.cluster_assignment = clusters
                
        # helper function to update the centroids
                  
            # step 3: update the centroids by calculating the mean of the data points in the cluster 
            for i in range(self.k):
                if len(self.data[self.cluster_assignment == i]) != 0:
                    self.centroids[i] = np.mean(self.data[self.cluster_assignment == i])
                    
        # helper function that calculates the objective function to make sure it is decreasing with new centroids.  
  
        # step 4: repeat step 2 and 3 until the centroids do not change or max iterations reached
        
        # step 5: return the centroids and cluster assignments
        return self.centroids, self.cluster_assignment 
        
    # method to plot the data points and centroids as they change
    
    
    #plot the data points and centroids
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
        
    # make sure objective function is decreasing and is following the elbow method
    def plot_objective_function(self) -> None:    
        objective_vals = []
        
        print(self.kmeans())
        
        for i in range(1,11):
           
            objective_vals.append(self.objective_function())
        
        
        plt.plot(objective_vals)
        plt.xlabel('Iterations')
        plt.ylabel('squared error sum')
        
        plt.show()
              
if __name__ == "__main__":
     
    iris = pd.read_csv("irisdata.csv")

    features = ['sepal_length','sepal_width','petal_length', 'petal_width', 'species']

    data_vector = iris[features].values
    
    x = K_Means(2, data_vector, 200)
    
    #x.plot_data_points()
    
    
    y = K_Means(2, data_vector, 70)
    
    y.plot_objective_function()
    
    
    
    