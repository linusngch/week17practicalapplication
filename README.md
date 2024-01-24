Link to notebook: https://github.com/linusngch/week17practicalapplication/blob/main/prompt_III.ipynb

This project utilizes data and information from an abstract regarding data mining for bank direct marketing with the CRSIP-DM method. The goal of this abstract was to 
use machine learning models and classifiers to figure out how to increase the rate at which subjects would subscribe to a long term bank deposit. 

To approach this dataset, only the first 7 banking categories of the data was utilized. This data was mainly categorical values, meaning encoding was necessray to perform
machine learning methods. Additionally, some duplicates were removed to ensure increased accuracy of the data. 

Firstly, a baseline classifier was introduced. This model was benchmark for future models to beat. Next, simple models for logistic regression, SVM, decision trees and 
KNN were implemented to see how they compared to the model. Logistic regression and SVM had the highest testing accuracy. Since these models were simple, optimization and 
fine tuning could be applied to acheive even higher accuracy. GridSearchCV was applied to each of these models with varying hyperparameters in hopes of increasing successful
prediction rates. Various changes included trying differnt model types with different evaluation metrics.

After fine tuning the 4 different models and utilizing 2 different performance metrics, it seems that logistic regression ias still the most reliable and accurate model
for this database. Optimizing and tuning hyperparameters yielded an increase in accuracy and a higher ROC/AUC score for all models. Most significant would be the SVC model.
Surprisingly, the baseline model for logistic regression and its fine-tuned counterpart did not show much difference in accuracy scores. In the future, tuning other 
logistic regression hyperparameters, utilizing gradient boosting, or focusing on more important features might yield higher scores.

These results show that the most important features when selecting candidates for a successful campaign are whether or not the subject is a student, retired, and their age.
Logistic regression and decision tree models show that these features have the greatest influence on whether the targeting a marketing campaign is successful (subject agrees 
to a long term deposit subscription). This makes sense, as subjects who are retired, no longer go to school, and of an older age would be more likely to deposit money. These
people most likely have the necessary funds to complete the transaction. Although this logic seems valid, these models can continually improve and results feature importance 
rankings can possibly change.
