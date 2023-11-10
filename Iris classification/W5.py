import numpy as np
import math
import matplotlib.pyplot as plt

# this module will plot the binomial distribution and likihood.

# this function will calculate the combination of n and k (n choose k)
def combination(n, k) -> float:
    return math.factorial(n) / (math.factorial(k) * math.factorial(n - k))

# this function will calculate the binomial distribution of n and y
def binomial_dis(n, y, theta) -> float:
    return combination(n,y) * (theta ** y) * ((1 - theta) ** (n - y))

# this function will calculate the posterior from given equations.
def posterior_fun(n, y, theta):
    return binomial_dis(n, y, theta) * (n + 1)

# this function will plot the likelihood. 
# n is the number of trials, theta is the probability of success. y is the number of success.
def plot_likelihood(n = 4, theta = .75):
    
    # y_range is the range of y, from 0 to n
    y_range = np.arange(0, n + 1, 1)
    
    # list is the list of likelihood
    likelihoods = []
    
    # collect all the likelihoods
    for y in y_range:
        likelihoods.append(binomial_dis(n, y, theta))
         
    # plot the likelihood
    plt.plot(y_range, likelihoods)
    plt.title("Likelihood")
    plt.xlabel("successes")
    plt.ylabel("probability")
    plt.show()
    
    # bar plot of the likelihood
    plt.bar(y_range, likelihoods)
    plt.xlabel("y")
    plt.ylabel("probability")
    plt.show()

# this function will plot the posterior.
# n is the number of trials, y is the number of success, theta is the probability of success.
def plot_posterior(n = 4, y = 3, size = 100):
    
    thetas = np.linspace(0, 1, size)
    
    # list is the list of posterior
    posterior = posterior_fun(n = n, y = y, theta = thetas)
    
    plt.title("Posterior: " + " n = " + str(n) + " y = " + str(y)) 
    
    # plot the posterior
    plt.scatter(thetas, posterior)
    plt.xlabel("theta")
    plt.ylabel("probability")
    plt.show()
    
   
    
    
if __name__ == "__main__":
    
    #plot_likelihood()
    plot_posterior()
