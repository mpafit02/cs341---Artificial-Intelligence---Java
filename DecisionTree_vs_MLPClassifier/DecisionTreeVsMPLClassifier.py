#      ===============================================================	#
#		IMPORT OF THE NECESSARY LIBRARIES REQUIRED FOR THIS ASSIGNMENT	#
#      ===============================================================	#
# 		System's required libraries (os, sys,csv)
import os
import sys
import csv
#      ===============================================================	#
# 		pandas: is a software library for data manipulation and analysis#
#      ===============================================================	#
# 		Scikit-learn is a free machine learning library for Python.		#
#      ===============================================================	#
import pandas as pd
import sklearn as sk
#      ===============================================================	#
# 		Classifier import, splitting and clsasifying report matrix      #
#      ===============================================================	#
from sklearn.neural_network import MLPClassifier
from sklearn import tree
from sklearn.model_selection import train_test_split
from sklearn.metrics import classification_report, confusion_matrix
#  =============================================================================================   	#
#  Generation of the appropriate dataframe (in pandas) for a given dataset (csv) 					#
#  =============================================================================================	#


def read_Dataset(filename):
    dt = pd.read_csv(str(filename), sep=',').values
    dt = pd.DataFrame(dt)
    dt = dt.values
    return dt


def decisionTreeClassifier(X_train, X_test, y_train, y_test):
    print("\nDecision Tree Classifier")
    print("=============================================================\n")
    clf = tree.DecisionTreeClassifier()
    clf = clf.fit(X_train, y_train)

    # Create the prediction set
    y_pred = list(clf.predict(X_test))

    print("Classification Report:")
    print(classification_report(y_test, y_pred))
    print("Confusion Matrix:")
    print(confusion_matrix(y_test, y_pred))


def mlpClassifier(X_train, X_test, y_train, y_test):
    print("\n\nMultilayer Perceptron Classifier")
    print("=============================================================\n")
    clf = MLPClassifier(max_iter=1000)
    clf.fit(X_train, y_train)

    # Create the prediction set
    y_pred = list(clf.predict(X_test))

    print("Classification Report:")
    print(classification_report(y_test, y_pred))
    print("Confusion Matrix:")
    print(confusion_matrix(y_test, y_pred))


def main():
    #   Call function that reads the appropriate csv (for dataset) into a panda dataframe dataset variable
    dataset = read_Dataset(sys.argv[1])
    #   Split Dataset into attribute vectors (X) and class attribute (y)
    # X Features are stored in the first elements of the dataset
    X = dataset[:, : -1]
    # Y Label instance is the last value of the dataset
    Y = dataset[:, len(dataset[0])-1]
    # Splitting dataset into training and testing sets by using a percentage of sys.argv[2]
    X_train, X_test, y_train, y_test = train_test_split(
        X, Y, test_size=float(sys.argv[2]))

    # X_train: Set for input at learning
    # X_test: Set for input at testng print("Input: ", X_test[i])
    # y_train: Correct answers for learning input set (X_train)
    # y_test: Correct answers for testing input set (X_test)
    decisionTreeClassifier(X_train, X_test, y_train, y_test)
    mlpClassifier(X_train, X_test, y_train, y_test)


main()
