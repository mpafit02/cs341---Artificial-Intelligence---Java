The program requires two input arguments in order to run the first argument refers to the dataset given 
by the .csv files and the second one refers to the percentage split used for the splitting of the dataset 
into training and testing set.

Please use a 25% split for test set thus giving the following (0.25 as an argument)

How to execute the three different datasets:

python3 DecisionTreeVsMPLClassifier.py IRIS.csv 0.25
python3 DecisionTreeVsMPLClassifier.py SPAM.csv 0.25
python3 DecisionTreeVsMPLClassifier.py IONOSPHERE.csv 0.25

This scripts learns based on Decision Tree Classifier and MPL Classifier. Then it tests itself in the same 
set for both methods and prints the Classification Report for each method.

Author: Marios Pafitis