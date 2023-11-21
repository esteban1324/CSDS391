# Imports 
import numpy as np
import matplotlib.pyplot as plt
import random
import pandas as pd


iris = pd.read_csv("C:\\Users\\esteb\\OneDrive\\Documents\\GitHub\\CSDS391\\Iris classification\\irisdata.csv")

features = ['sepal_length','sepal_width','petal_length', 'petal_width']

data_vector = iris[features].values 

""" This is the main class for K means clustering for all the data points. This is for Question 1. (a-d) """
class K_Means:
    
    def __init__(self, k:int, data:np.ndarray, max_iter:int):
        self.k = k  
        self.data = data
        self.max_iter = max_iter
        self.centroids = []
        self.inertia = []
        self.centroids_history = self.k_means()
             
    # initialize the centroids within range of random data points k times
    def initialize_centroids(self)-> np.ndarray:   
        np.random.seed(23)
           
        indices = np.random.choice(len(self.data), self.k, replace=False)
        
        centroids = self.data[indices[:self.k]]
                
        return centroids
    

    def objective_function(self, cluster_assignment:np.ndarray, centroids:np.ndarray, data: np.ndarray) -> float:
        total_distance = 0

        for i in range(self.data.shape[0]):
            k = cluster_assignment[i]
            distance = np.linalg.norm((data[i, :] - centroids[k, :]) ** 2, axis = 0)
       
            total_distance += distance 

        return total_distance

    
    # assign the clusters to the data points, based on the closest centroid
    def assign_clusters(self, centroids, data):
        
        cluster_assignment = np.zeros((data.shape[0], self.k), float)
        
        # get all the dimensions of the data points
        for k in range(self.k): 
            distance = np.linalg.norm(data - centroids[k, :], axis = 1)
            cluster_assignment[:, k] = distance 
                 
        cluster = np.argmin(cluster_assignment, axis = 1)
        
        return cluster
    
    # updates the clusters by taking the clusters  
    def update_centroids(self, cluster_assignment:np.ndarray, data:np.ndarray) -> np.ndarray:
           
            updated_centroids = np.zeros((self.k, data.shape[1]), float)
           
            for k in range(self.k):
                indices = np.where(cluster_assignment == k)
                selected_data = data[indices]
                updated_centroids[k, :] = np.mean(selected_data, axis = 0)
                
                
            return updated_centroids
        
    # kmeans algorithm, which is the main function, Q1 (a)
    def k_means(self):   
    
        centroids_list = self.initialize_centroids()
        centroids_history = []

        for n in range(self.max_iter):    
            
            # Randomly assign each observation to an initial cluster, from 1 to K. 
            clusters = self.assign_clusters(centroids_list, self.data)
          
            centroids_list = self.update_centroids(clusters, self.data)
            centroids_history.append(centroids_list)
           
            self.inertia.append(self.objective_function(clusters, centroids_list, self.data))
            
            # if the centroids do not change, then break out of the loop
            if n > 0 and np.array_equal(centroids_list, centroids_history[n-1]):
                break
          
        # collect points for decision boundaries, adds to centroid list. 
        self.get_last_centroids(centroids_history)
   
        return centroids_history
    
    # we are getting the last centroids in order to plot the decision boundaries.   
    def get_last_centroids(self, centroids_history:list):
        
        last_centroids = centroids_history[-1:]
        for array in last_centroids:
            for sublist in array:
                petals = sublist[-2:]
                pairs = tuple(petals)
                self.centroids.append(pairs)
        
                
    #plot the objective function values Q1 (b)
    def plot_objective_function(self):
        
        plt.plot(self.inertia)
        
        plt.xlabel("iteration")
        plt.ylabel("Sum of error")
        plt.title("Objective Function for " + str(self.k) + " Clusters")
        
        plt.show()
       
    # plot the learning algorithm, kmeans algorithm when centoids move and classify clusters,   Q1 (c)
    def plot_learning_algorithm(self):
        
        #centroids_list, centroids_history = self.k_means()
             
        species = {'setosa': 'red', 'versicolor':'green', 'virginica': 'blue'}
        colors = [species[s] for s in iris['species']]       
        
        for i, centroids in enumerate(self.centroids_history):
            plt.figure()
            plt.scatter(self.data[:, 2], self.data[:, 3], color = colors)
            plt.scatter(centroids[:, 2], centroids[:, 3], color = 'black', marker = 'x')
            plt.title("K means Clustering, Iteration: " + str(i))
            plt.xlabel("Petal Length")
            plt.ylabel("Petal Width")
            plt.show()
        
        
    # To be continued: decision boundaries Q1 (d)
    def plot_decision_boundary(self):
        # for k = 2, take the cordinates and get the midpoint of them and plot them along a line with form of y = m(x+a) + b.  
             
        plt.title("Decision Boundary for " + str(self.k) +  " Clusters")
        plt.xlabel("Petal Length")
        plt.ylabel("Petal Width")
        
        x, y = zip(*self.centroids)
             
        species = {'setosa': 'red', 'versicolor':'green', 'virginica': 'blue'}
        colors = [species[s] for s in iris['species']]        
        
        plt.scatter(self.data[:, 2], self.data[:, 3], c = colors)
        plt.scatter(x, y, color = 'black', marker = '^')
        
        if(len(self.centroids) == 2):
           self.decision_boundary_2(self.centroids)

        # plot using two midpoints and two lines
        if(len(self.centroids) == 3):
            self.decision_boundary_3(self.centroids)             
        plt.show()
      
    def decision_boundary_2(self, centroids):
        x1, y1 = centroids[0]
        x2, y2 = centroids[1]
        midpoint = [(x1 + x2) / 2, (y1 + y2) / 2]
        slope = -1 * (y2 - y1) / (x2 - x1)
        x_values = np.arange(0, 8)
        y_intercept = midpoint[1] - (slope * midpoint[0])
        y_values = slope * x_values + y_intercept
        plt.plot(x_values, y_values, color="black")

    def decision_boundary_3(self, centroids):
        x1, y1 = centroids[0]
        x2, y2 = centroids[1]
        x3, y3 = centroids[2]
        midpoint1 = [(x1 + x2) / 2, (y1 + y2) / 2]
        midpoint2 = [(x2 + x3) / 2, (y2 + y3) / 2]
        slope1 = -1 * (y2 - y1) / (x2 - x1)
        slope2 = -1 * (y3 - y2) / (x3 - x2)
        y_intercept1 = midpoint1[1] - (slope1 * midpoint1[0])
        y_intercept2 = midpoint2[1] - (slope2 * midpoint2[0])
        x_values = np.arange(0, 8)
        y_values1 = slope1 * x_values + y_intercept1
        y_values2 = slope2 * x_values + y_intercept2
        plt.ylim(0, 4.0)
        plt.plot(x_values, y_values1, color="black")
        plt.plot(x_values, y_values2, color="black")


# add the species to the data vector                 
if __name__ == "__main__":
    
    x = K_Means(3, data_vector, 20)
    
    #x.plot_objective_function()
    
    #print(x.centroids)
     
    #x.plot_decision_boundary()

    x.plot_learning_algorithm()
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    