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

flower = iris.drop(iris[iris.species == 'setosa'].index)[species].values

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

def sigmoid_prime(x):
    return (np.exp(-x) / math.pow(1 + np.exp(-x), 2))
    
# function that plots the decision boundary for the perceptron (2c)
def plot_decision_boundary(weight, bias, point):        
        
    #plot the decision boundary
    x_range = np.arange(2.5, 6.7, 0.1)
    y_range = -(weight[0] * x_range) + bias
        
    plt.plot(x_range, y_range, color = 'black')
        
    #plot the versicolor and virginica classes
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
        self.learning_curve = []
        self.iterations = 0 
    
    
    # function that calculates mean squared error (MSE) (3a)
    def mse(self):
        pre_process_data = pre_process(self.data)
        
        error = 0
        
        for i in range(pre_process_data.shape[0]):    
            x1 = pre_process_data[i, 2]
            x2 = pre_process_data[i, 3]
  
            prediction = 0 if flower[i] == 'versicolor' else 1
            
            z = sigmoid(self.weight[0] * x1 +  self.weight[1] * x2  + self.bias)
            
            error += math.pow(z - prediction, 2)
        
             
        mean_error = error / pre_process_data.shape[0]
           
        return mean_error
    
    
    # function that computes the mse for two different decision boundaries, plots boundary (3b)
    def plot_mse(self, weight):
              
        x_range = np.arange(2.5, 6.7, 0.1)
        
        y_range = self.get_line(weight, self.bias)
        
        plt.plot(x_range, y_range, color = 'black')
              
        plot_2nd_3rd_classes()
        
        return self.mse()
    
    # the line for the decision boundary
    def get_line(self, weight, bias):
        slope = -(weight[0] / weight[1])
        y_intercept = -(bias / weight[1])
        x_range = np.arange(2.5, 6.7, 0.1)
        y_range = slope * x_range + y_intercept
        
        return y_range
         
    # function that computes summed gradient for an ensamble of paterns, use formula derived from 3c-d
    def summed_gradient(self):
        pre_process_data = pre_process(self.data)
       
        gradient_w0 = 0
        gradient_w1 = 0
        gradient_w2 = 0

        for i in range(pre_process_data.shape[0]):

            x1 = pre_process_data[i, 2]
            x2 = pre_process_data[i, 3]
            
            prediction = 0 if flower[i] == 'versicolor' else 1
            z = self.bias + self.weight[0] * x1 + self.weight[1] * x2
            actual = sigmoid(z)
            
            gradient_w0 -= 2 * (prediction - actual) * (-sigmoid_prime(z))
            gradient_w1 -= 2 * (prediction - actual) * (-sigmoid_prime(z)) * (x1)
            gradient_w2 -=  2 * (prediction - actual) * (-sigmoid_prime(z)) * (x2)
        
        # take all the gradients and get the mean of each. 
        gradient_w0 /= pre_process_data.shape[0]
        gradient_w1 /= pre_process_data.shape[0]
        gradient_w2 /= pre_process_data.shape[0]
    
        return (gradient_w0, gradient_w1, gradient_w2)

    # plot the gradient summation 
    def plot_gradient(self, step_size):
        
        # plot the line before taking the gradient
        x_range = np.arange(2.5, 6.7, 0.1)
        y_range = self.get_line(self.weight, self.bias)
        
        plt.plot(x_range, y_range, color = 'magenta')
        
        #get the tuple from summed_gradient and plot the line of this.
        gradient = self.summed_gradient()
          
        #update the weights and bias, and plot the line again
        self.bias += step_size * gradient[0]
        self.weight[0] += step_size * gradient[1]
        self.weight[1] += step_size * gradient[2]
          
        new_yrange = self.get_line(self.weight, self.bias)
        
        plt.plot(x_range, new_yrange, color = 'red')
           
        plot_2nd_3rd_classes()
        
    '''Learning through optmization''' 
    
    
    #gradient decent 4a 
    def gradient_decent(self, step_size, k):
              
        iterations = 0
        threshold = 0.01
               
       # this algorithm tries to minimize the mean squared error by updating the weights and bias with the gradient decreasing the error.
        while(iterations < k):
            gradient = self.summed_gradient()

            self.bias -= step_size * gradient[0]
            self.weight[0] -= step_size * gradient[1]
            self.weight[1] -= step_size * gradient[2]
            
            # plot the middle line
            if iterations == 100:
                x_range = np.arange(2.5, 6.7, 0.1)
                y_range = self.get_line(self.weight, self.bias)
                plt.plot(x_range, y_range, color = 'black')
            
             
            norm = math.sqrt(math.pow(gradient[0], 2) + math.pow(gradient[1], 2) + math.pow(gradient[2], 2))
            self.learning_curve.append(norm)
            
           
            if norm < threshold:
                break
           
           
            iterations += 1
        
        
       
        return [self.bias, self.weight[0], self.weight[1]]
    
    #plot gradient decent 4b, plot the initial and final location of the weight and bias.   
    def plot_learning_curve(self, step_size, k):
        #plot the learning curve of the gradient decent, plot the objective function as a function of the number of iterations
        weight = self.gradient_decent(step_size , k)
        
        plt.plot(self.learning_curve)
             
        plt.title('Learning Curve of Gradient Decent')
        plt.xlabel('Number of Iterations')
        plt.ylabel('Mean Squared Error')
        plt.show()
    
    #plot the initial and final location of the weight and bias.
    def plot_gradient_decent(self, step_size, k):
        # initial line "magenta line"
        x_range = np.arange(2.5, 6.7, 0.1)
        y_range = self.get_line(self.weight, self.bias)
        plt.plot(x_range, y_range, color = 'magenta')
        
        self.gradient_decent(step_size , k)
        
        # final line "red line"
        new_yrange = self.get_line(self.weight, self.bias)
        plt.plot(x_range, new_yrange, color = 'red') 
        plot_2nd_3rd_classes()
        
        
    
    
    #randomize the weights and show two output plots with initial, middle and final locations.  4c 
    def radomize_weights(self, bias, weight1, weight2):
        np.random.seed(232)
        bias = random.uniform(-1, 4)
        weight1 = random.uniform(-3, 1)
        weight2 = random.uniform(-2, 2)
        
        return bias, [weight1, weight2]
    
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
    weight3 = [7,12]
    
    #bias32 = -31
    #weight32 = [2, 13]
      
    #print(NueralNetwork(data_vector, weight3, bias3).mse())
    
    # 3b 
    #print(NueralNetwork(data_vector, weight3, bias3).plot_mse(weight3))
    
    # 3e
    #print(NueralNetwork(data_vector, weight3, bias3).plot_gradient(12))
    
    # 4a
    bias4 = -45
    weight4 = [7, 12]
    
    
    #print(NueralNetwork(data_vector, weight3, bias3).gradient_decent(.2, 1000))
    
    # 4b
    #print(NueralNetwork(data_vector, weight4, bias4).plot_learning_curve(-.2, 1000))
    #print(NueralNetwork(data_vector, weight4, bias4).plot_gradient_decent(-.2, 1000))
    
    # 4c random weights 
    random_weights = NueralNetwork(data_vector, weight4, bias4).radomize_weights(bias4, weight4[0], weight4[1])
    print(NueralNetwork(data_vector, random_weights[1], random_weights[0]).plot_gradient_decent(-.2, 1000))
    #print(NueralNetwork(data_vector, random_weights[1], random_weights[0]).plot_learning_curve(-.2, 1000))
    
    
     
    
    