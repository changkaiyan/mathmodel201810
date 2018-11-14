#--------------------------------------------------------
# Copyright 2018 Kaiyan Chang, Kaiyuan Tian, Ruilin Chen
# For the data regression
# Python 3.6 for Linux. Only run in Linux system.
#--------------------------------------------------------

from matplotlib import pyplot as plt
from pandas import read_csv
from keras.utils.vis_utils import plot_model
from keras.callbacks import TensorBoard
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import LSTM
from sklearn.preprocessing import MinMaxScaler
import numpy as np

look_back=2  # A length of time window
epochs=1000   # training Epochs
batch_size=20   #In minibatch the batch size is 20

#Create a time windows and extends matrix to tensor
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

#Define batch normalization class
scaler_x = MinMaxScaler()
scaler_y=MinMaxScaler()
scaler=MinMaxScaler()

#Read data from .csv
data_set = read_csv('datanoraw.csv', header=0, index_col=0)
data_set=data_set.values.astype('float32')

#Batch normalization
Train_x = scaler_x.fit_transform(data_set[:,:data_set.shape[1]-1])
Train_y = scaler_y.fit_transform(data_set[:,data_set.shape[1]-1:data_set.shape[1]])
data_set=np.hstack((Train_x,Train_y))
train = data_set[0:, :]
X_train, y_train = create_dataset(train)

#Define model using keras
model = Sequential()
model.add(LSTM(units=4, input_shape=(look_back, data_set.shape[1]-1),return_sequences=True))
model.add(Dense(units=1))
model.compile(loss='mean_squared_error', optimizer='adam')

#Run the model
history=model.fit(X_train, y_train, epochs=epochs, batch_size=batch_size, verbose=2,validation_split=0.33,callbacks=[TensorBoard(log_dir='./tmp/log')])

#Model visulization
plot_model(model,to_file='model.png',show_shapes=True,show_layer_names=False)
model.summary()
plt.plot(history.history['loss'], label='train')
plt.legend('train')
plt.plot(history.history['val_loss'], label='test')
plt.legend('validation')
plt.title('Loss')
plt.show()
plt.savefig('loss')
plt.plot(history.history['acc'], label='train')
plt.legend('train')
plt.plot(history.history['val_acc'], label='validation')
plt.legend('validation')
plt.title('Acc')
plt.show()
plt.savefig('acc')




