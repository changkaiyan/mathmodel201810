from matplotlib import pyplot as plt
from pandas import read_csv
import math
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import LSTM
from sklearn.preprocessing import MinMaxScaler
from sklearn.metrics import mean_squared_error
import numpy as np
look_back=2
epochs=10
batch_size=20
scaler_x = MinMaxScaler()
scaler_y=MinMaxScaler()

def create_dataset(dataset):
    dataX, dataY = [],[]
    global look_back
    for i in range(len(dataset) - look_back - 1):
        x= dataset[i:i+look_back, :dataset.shape[1]-1]
        dataX.append(x)
        y = dataset[i:i+look_back, dataset.shape[1]-1:dataset.shape[1]]
        dataY.append(y)
    dataX=np.reshape(np.array(dataX),((len(dataset) - look_back - 1),look_back,dataset.shape[1]-1))
    dataY = np.reshape(np.array(dataY), ((len(dataset) - look_back - 1),look_back,1))
    return dataX, dataY

#读入数据并归一化并划分数据
data_set = read_csv('datanoraw.csv', header=0, index_col=0)
data_set=data_set.values.astype('float32')
data=data_set
scaler=MinMaxScaler()
Train_x = scaler_x.fit_transform(data_set[:,:data_set.shape[1]-1])
Train_y = scaler_y.fit_transform(data_set[:,data_set.shape[1]-1:data_set.shape[1]])
data_set=np.hstack((Train_x,Train_y))
train = data_set[0:, :]
X_train, y_train = create_dataset(train)
#X_train = np.reshape(X_train, (X_train.shape[0], look_back, X_train.shape[1]))
model = Sequential()
model.add(LSTM(units=4, input_shape=(look_back, data_set.shape[1]-1),return_sequences=True))
model.add(Dense(units=1))
model.compile(loss='mean_squared_error', optimizer='adam')

history=model.fit(X_train, y_train, epochs=epochs, batch_size=batch_size, verbose=2,validation_split=0.33)
model.summary()
plt.plot(history.history['loss'], label='train')
plt.legend('train')
plt.plot(history.history['val_loss'], label='test')
plt.legend('validation')
plt.show()
plt.savefig('损失1-10')




