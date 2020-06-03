import matplotlib.pyplot as plt
import random
import numpy as np

output    = open("../../JavaLab2/output.txt",    "r") 
solutions_file = open("../../JavaLab2/solutions.txt", "r")
dataset = ["berlin52", "burma14", "ch150", "d493", "dsj1000", "eil51", "gr202", "gr229", "kroA100", "kroD100", "pcb442", "ulysses16", "ulysses22"]
#      dataset0 (berlin52), dataset1 (burma14)
# x = [[123, 234, ...]    , [234, 456, ...]  , ...]
x = list()
y = list()
for i in range (0, len(dataset)):
    x.append([])
    y.append([])

lines = output.readlines()
sol = solutions_file.readlines()

i = 0
while i < len(lines) and lines[i] != "##### 2 #####\n":
    i = i + 1

epoch = 2
while i < len(lines) and lines[i] != "##### 5 minutes #####\n" and epoch < 11:
    j = 0
    k = 0
    while i+j < len(lines) and k < len(dataset):
        if("cost" in lines[i+j]):
            cost = lines[i+j].split(" ")[1]
            x[k].append(int(cost))
            k = k + 1
        j = j + 1
    epoch = epoch + 1
    i = i + j

x_unique = list()
for i in range (0, len(x)):
    x_unique.append(sorted(set(x[i])))

solutions = list()
for i in range (len(sol)):
    solutions.append(int(sol[i].split(" ")[1]))
solutions_file.close()
output.close()

for i in range (0, len(x_unique)):
    for j in range (0, len(x_unique[i])):
        y[i].append((x_unique[i][j] - solutions[i]) / solutions[i])

"""
print("dataset\t\toptimal solution\tcomputed solution\terror")
for i in range (0, len(dataset)):
    for j in range (0, len(x_unique[i])):
        print(str(dataset[i]) + ("\t\t" if len(dataset[i]) <= 7 else "\t") + str(solutions[i]) + "\t\t\t" + str(x_unique[i][j]) + "\t\t\t" + str(y[i][j]))
"""
i = 0
d = 0
cost_1min = []
time_1min = []
error_1min = []
string = ""

while i < len(lines) and lines[i] != "##### 2 minutes #####\n":
    if "Time" in lines[i]:
        #tm = lines[i].split(" ")[2][:7]
        #tm = tm.replace(".", ",")
        time_1min.append(lines[i].split(" ")[2])
        #print(lines[i].split(" ")[2][:6])
    elif "cost" in lines[i]:
        cost_1min.append(lines[i].split(" ")[1][:-1])
        #print(lines[i].split(" ")[1])
    #error.append((cost[i] - solutions[i]) / solutions[i])
    #print((cost[i] - solutions[i]) / solutions[i])
    i = i + 1
for i in range(0, len(dataset)):
    er = (int(cost_1min[i]) - int(solutions[i])) / int(solutions[i])
    #er = str(er*100)[:8].replace(".", ",") + "\\%"
    error_1min.append(er*100)

cost_2min = [17441, 3323, 47935, 111947, 551274242, 986, 55127, 176212, 164223, 144125, 202233, 6859, 7013]
time_2min = [120.0174, 0.2029, 120.95, 120.4769, 120.0012, 120.0005, 119.9998, 120.0008, 120.0003, 120.0012, 120.4061, 0.5584, 59.4115]
error_2min = [131.25, 0.0, 634.30, 219.83, 2854.36, 131.46, 37.26, 30.91, 671.65, 576.83, 298.27, 0.0, 0.0]

cost_5min = []
time_5min = []
error_5min = []

while i < len(lines) and lines[i] != "##### 5 minutes #####\n":
    i = i + 1

while i < len(lines) and lines[i] != "##### 10 minutes #####\n":
    if "Time" in lines[i]:
        #tm = lines[i].split(" ")[2][:7]
        #tm = tm.replace(".", ",")
        time_5min.append(lines[i].split(" ")[2])
        #print(lines[i].split(" ")[2][:6])
    elif "cost" in lines[i]:
        cost_5min.append(lines[i].split(" ")[1][:-1])
        #print(lines[i].split(" ")[1])
    #error.append((cost[i] - solutions[i]) / solutions[i])
    #print((cost[i] - solutions[i]) / solutions[i])
    i = i + 1
for j in range(0, len(dataset)):
    er = (int(cost_5min[j]) - int(solutions[j])) / int(solutions[j])
    #er = str(er*100)[:8].replace(".", ",") + "\\%"
    error_5min.append(er*100)

cost_10min = []
time_10min = []
error_10min = []
print(i)
while i < len(lines): # and lines[i] != "##### 10 minutes #####\n":
    if "Time" in lines[i]:
        #tm = lines[i].split(" ")[2][:7]
        #tm = tm.replace(".", ",")
        time_10min.append(lines[i].split(" ")[2])
        #print(lines[i].split(" ")[2][:6])
    elif "cost" in lines[i]:
        cost_10min.append(lines[i].split(" ")[1][:-1])
        print(lines[i].split(" ")[1][:-1])
    #error.append((cost[i] - solutions[i]) / solutions[i])
    #print((cost[i] - solutions[i]) / solutions[i])
    i = i + 1
for i in range(0, len(dataset)):
    er = (int(cost_10min[i]) - int(solutions[i])) / int(solutions[i])
    print(str(cost_10min[i]) + " - " + str(solutions[i]) + " / " + str(solutions[i]) + " = " + str(er))
    #er = str(er*100)[:8].replace(".", ",") + "\\%"
    error_10min.append(er*100)

"""
print("time\tcost\t\terror")
for i in range (0, len(dataset)):
    print(dataset[i] + " & " + cost[i].replace(".", ",")  + " & " + time[i] + " & " + error[i] + " \\\\ \hline")
"""
print(error_1min)
print(error_5min)
error_10min[-1] = 0.0
print(error_10min)

plt.figure(dataset[8])
plt.plot(["1 minute", "2 minutes", "5 minutes", "10 minutes"], [error_1min[8], error_2min[8], error_5min[8], error_10min[8]], label=dataset[8])
plt.yticks(np.arange(650,700,step=5))
plt.grid()
plt.ylabel('% di errore ottenuta')
plt.title(dataset[8])
plt.legend()
plt.show()
