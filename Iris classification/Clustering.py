# Imports 
import numpy as np
import matplotlib.pyplot as plt
import random
import math
import csv 
import pandas as pd

""" This is the main class for K means clustering for all the data points. This is for Question 1. (a-d) """
class K_Means():
    
    def __init__(self, k:int, data:np.ndarray, max_iter:int):
        self.k = k  
        self.data = data
        self.max_iter = max_iter
        self.centroids = self.initialize_centroids()
        self.inertia = []
             
    # initialize the centroids within range of random data points k times
    def initialize_centroids(self)-> np.ndarray:   
        random.seed(32)
           
        indices = np.random.choice(len(self.data), self.k, replace=False)
        
        centroids = self.data[indices]
                
        return centroids
         
    def objective_function(self, cluster_assignment:np.ndarray, centroids:np.ndarray, data: np.ndarray) -> float:
        total_distance = 0

   # For each data point, calculate the distance to its assigned centroid
        for i in range(data.shape[0]):
            k = cluster_assignment[i]
            distance = np.linalg.norm((data[i, 2:4] - centroids[k, 2:4]) ** 2, axis = 0)
       
            # Add the squared distance to the total distance
            total_distance += distance 

        return total_distance

    
    # assign the clusters to the data points, based on the closest centroid
    def assign_clusters(self, centroids:np.ndarray, data: np.ndarray) -> np.ndarray:
        
        cluster_assignment = np.zeros((data.shape[0], self.k), float)
        
        for k in range(self.k):
            distance = np.linalg.norm(data[:, 2:4] - centroids[k, 2:4], axis = 1)
            cluster_assignment[:, k] = distance 
                 
        cluster = np.argmin(cluster_assignment, axis = 1)
        
        return cluster
    
    # update the centroids
    def update_centroids(self, cluster_assignment:np.ndarray, data:np.ndarray) -> np.ndarray:
            # update the centroids by taking the mean of the data points in each cluster
            updated_centroids = np.zeros((self.k, data.shape[1]), float)
            # take the mean of the data points in each cluster, only where the cluster assignment is equal to the centroid index to calc mean.
            for k in range(self.k):
                indices = np.where(cluster_assignment == k)
                selected_data = data[indices]
                updated_centroids[k, :] = np.mean(selected_data, axis = 0)
                
                
            return updated_centroids
        
    # kmeans algorithm, which is the main function
    def k_means(self):   
        #step 1: initialize the centroids k times that are from the data points
        centroids_list = self.centroids
        
        for n in range(self.max_iter):    
            
            # assign the clusters to the data points, based on the closest centroid
            # Randomly assign each observation to an initial cluster, from 1 to K. 
            clusters = self.assign_clusters(centroids_list, self.data)
            # update the centroids
            centroids_list = self.update_centroids(clusters, self.data)
            # update the objective function
            self.inertia.append(self.objective_function(clusters, centroids_list, self.data))
                
        # step 5: return the centroids and cluster assignments
        return centroids_list
    
    def plot_objective_function(self):
        # plot the objective function values, make sure they minimize everytime
        plt.plot(self.inertia)
        
        plt.xlabel("iteration")
        plt.ylabel("Sum of error")
        
        plt.show()
        
        
        
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
        
              
if __name__ == "__main__":
    
    
    
    x = K_Means(2, data, 60)
    
    x.plot_objective_function()
    
    
      
    '''
    iris = pd.read_csv("irisdata.csv")

    features = ['sepal_length','sepal_width','petal_length', 'petal_width']

    data_vector = iris[features].values
    
    x = K_Means(2, data_vector, 70)
    
    
    x.k_means()
    
    
    x.plot_objective_function()
    '''
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    