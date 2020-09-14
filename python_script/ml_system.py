from numpy import loadtxt
from keras.models import Sequential
from keras.layers import Dense
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from keras.optimizers import SGD


from io import StringIO
import numpy as np


import math
def convertToNumber(s):
    return int.from_bytes(str(s).encode(), 'little')

def convertFromNumber (n):
    return n.to_bytes(math.ceil(n.bit_length() / 8), 'little').decode()


# Import the dataset
dataset_file_location = '/Users/brenodcruz/eclipse-workspace/cool.parser/python_script/output-file.txt'
dataset = np.genfromtxt(dataset_file_location, delimiter=', ', converters={0: convertToNumber, 2: convertToNumber, 4: convertToNumber})

# (0) string    (1) entropy (2) heuristics (3) heuristic weight (4) regex patterns (5) regex weights
# (6) label 		

## dnn for overall label prediction

X = dataset[0:,[1,2,3,4,5]] # input data -- entropy -- heuristic -- regex
y = dataset[0:,6] # label - 1 true or 0 false -- 


#Split the data for testing and training
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 42)

# Standardize the data
scaler = StandardScaler().fit(X_train)

## Scale the train set
X_train = scaler.transform(X_train)

## Scale the test set
X_test = scaler.transform(X_test)


model = Sequential()
model.add(Dense(12, input_dim=5, activation='relu'))
model.add(Dense(24, activation='selu'))
model.add(Dense(36, activation='relu'))
model.add(Dense(96, activation='elu'))
model.add(Dense(96, activation='selu'))
model.add(Dense(32, activation='relu'))
model.add(Dense(24, activation='selu'))
model.add(Dense(1, activation='linear'))

model.compile(loss='mean_squared_error', optimizer='adam', metrics=['accuracy'])
model.fit(X_train, y_train, epochs=200, batch_size=128, verbose=0)

from sklearn.metrics import confusion_matrix, precision_score, recall_score, f1_score, cohen_kappa_score, r2_score

mse_value, mae_value = model.evaluate(X_test, y_test, verbose = 0)
print("mse: ", mse_value)
print("mae: ", mae_value)

#_, accuracy = model.evaluate(X_train, y_train, verbose=0)
#print('Accuracy: %.2f' % (accuracy*100))

y_pred = model.predict(X_test)

r2 = r2_score(y_test, y_pred)

#y_pred2 = model.predict(X_plot)

print("r2: ", r2)

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd


for i in range(10):
    print('%s => %s (expected %s)' % (X[i].tolist(), y_pred[i], y_test[i]))

df = pd.DataFrame({'x': range(0, len(y_test)), 'breadps values':y_test, 'breadps predicted':y_pred.flatten()})
plt.plot( 'x', 'breadps values', data=df, marker='', markerfacecolor='blue', markersize=10, color='skyblue', linewidth=2)
plt.plot( 'x', 'breadps predicted', data=df, marker='', color='olive', linewidth=1)
plt.legend()
plt.show()

