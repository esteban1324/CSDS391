# Imports 
import numpy as np
import matplotlib.pyplot as plt
import random
import math
import csv 
import pandas as pd


iris = pd.read_csv("irisdata.csv")


features = ['sepal_length','sepal_width','petal_length', 'petal_width']

data_vector = iris[features].values 
