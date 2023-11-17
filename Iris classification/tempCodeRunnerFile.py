 weight = [-.47, -.47]
    bias = 4.10
    point = [2,6]
    
    x = Perceptron(data_vector, weight, bias)
    
    # 2b 
    print(x.sigmoid(weight[0] * point[0]) + (weight[1] * point[1]) + bias)