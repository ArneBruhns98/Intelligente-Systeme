import datetime
import sys

import matplotlib.pyplot as plt
import pandas as pd
import matplotlib.patches as patches
import matplotlib

matplotlib.use('TkAgg')

# call visualize.py datafile labelfile predictionfile recall precision fscore

data = pd.read_csv(sys.argv[1], header=None)

recall = sys.argv[4]
precision = sys.argv[5]
fscore = sys.argv[6]


size = data.size
shape = data.shape

print("Data stats:\nSize = {}\nShape ={}\n".
      format(size, shape))

labels = pd.read_csv(sys.argv[2], header=None, names=['X', 'Y'])

size = labels.size
shape = labels.shape

print("Label stats:\nSize = {}\nShape ={}\n".
      format(size, shape))

predicitons = pd.read_csv(sys.argv[3], header=None, names=['X', 'Y'])

size = predicitons.size
shape = predicitons.shape

print("Prediction stats:\nSize = {}\nShape ={}\n".
      format(size, shape))


plt.figure(figsize=(104, 32))
ax = plt.gca()
ax.set_aspect('equal')
figure = plt.gcf()
plt.imshow(data,cmap='hot',interpolation='nearest')
plt.xlabel('X-Coord')
plt.ylabel('Y-Coord')
results = "Recall: {} Precision {} F-Score {}\n".format(recall, precision, fscore)
plt.text(0, 0, results)
plt.colorbar()

# adding points from labels
for index, row in labels.iterrows():
    rect = patches.Rectangle((row['Y'],row['X']),10,10,linewidth=1,edgecolor='black',facecolor='lime')
    ax.add_patch(rect)

# adding points from predictions
for index, row in predicitons.iterrows():
    circ = patches.Circle((row['Y'],row['X']),radius=7.5,edgecolor='snow',facecolor='dodgerblue')
    ax.add_patch(circ)

plt.savefig('heatmap-' + str(datetime.datetime.now().date()) + '_'
            + str(datetime.datetime.now().time()).replace(':', '.') + '.png')
plt.show(block=True)
