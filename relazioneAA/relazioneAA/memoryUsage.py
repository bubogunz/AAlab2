import matplotlib.pyplot as plt
from matplotlib import colors
from matplotlib.ticker import PercentFormatter
from decimal import *
import random
import numpy as np

output   = open("../../JavaLab2/output.txt",    "r") 
dataset = ["berlin52", "burma14", "ch150", "d493", "dsj1000", "eil51", "gr202", "gr229", "kroA100", "kroD100", "pcb442", "ulysses16", "ulysses22"]

lines = output.readlines()

eof = 2566

mem = []
algorithms = ["HEURISTIC", "TRIANGLE_TSP", "HELD-KARP"]

for k in range (len(algorithms)):
    mem.append([])
    for i in range (len(dataset)):
        mem[k].append([])

epoch = 1
i = 0
k = -1
j = -1
for i in range(eof):
    if k < len(algorithms)-1 and algorithms[k+1] in lines[i]:
        #print(lines[i])
        k = k + 1
        #print()
        #print("computing now " + algorithms[k])
    if j < len(dataset)-1 and dataset[j+1] in lines[i]:
        #print(lines[i])
        j = j + 1
        #print("computing now " + dataset[j])
    elif j==12:
        j = -1
    #print(algorithms[k] + " " + dataset[j])
    if "usedHeap" in lines[i]:
        #print(lines[i][:-1])
        ar = lines[i].split(" ")[1:]
        #print("ar: " + ar[0] + " " + ar[1][:-1])
        mem[k][j].append(ar[0] + " " + ar[1][:-1])

mem_avg = []
for k in range (len(algorithms)):
    mem_avg.append([])

for i in range(len(algorithms)):
    print("algorithm: " + algorithms[i])
    for j in range(len(dataset)):
        print("dataset: " + dataset[j])
        st = ""
        avg = 0.0
        um = ""
        for k in range(len(mem[i][j])):
            st = st + mem[i][j][k] + "   "
            avg = avg + float(mem[i][j][k].split(" ")[0].replace(",", "."))
            um = mem[i][j][k].split(" ")[1]
        avg = avg / len(mem[i][j])
        mem_avg[i].append(avg)
        st = st + "       media: " + str(avg).replace(".", ",") + " " + um
        print(st)
    print()
#print(mem_avg)
"""
f = 0
offset = 3.9
offset_2 = 2 
plt.figure()
plt.bar([dataset[0], dataset[2], dataset[3], dataset[4], dataset[5], dataset[6], dataset[7], dataset[8], dataset[9], dataset[10], dataset[12]], height=[mem_avg[f][0]-offset, mem_avg[f][2]-offset, mem_avg[f][3]-offset, mem_avg[f][4]-offset, mem_avg[f][5]-offset, mem_avg[f][6]-offset, mem_avg[f][7]-offset, mem_avg[f][8]-offset, mem_avg[f][9]-offset, mem_avg[f][10]-offset, 0.02], bottom=offset)
plt.yticks([mem_avg[f][0], mem_avg[f][2], mem_avg[f][3], mem_avg[f][4], mem_avg[f][5], mem_avg[f][6], mem_avg[f][7], mem_avg[f][8], mem_avg[f][9], mem_avg[f][10], 3.92], labels=[mem_avg[f][0], 4.05, 4.05, mem_avg[f][4], mem_avg[f][5], 4.07, mem_avg[f][7], 3.98, 3.95, mem_avg[f][7], '3.06'])
plt.ylabel("memory usage (GiB)")
plt.grid(True)
plt.title("Held-Karp memory usage")
plt.show()

f = 2
offset = 3
offset_2 = 2 
plt.figure()
plt.bar([dataset[1], dataset[11]], height=[mem_avg[f][1]-offset, mem_avg[f][11]-offset], bottom=offset)
plt.yticks([mem_avg[f][1], mem_avg[f][11]])
plt.ylabel("memory usage (MiB)")
plt.grid(True)
plt.title("Held-Karp memory usage")
plt.show()
"""
f = 0
offset = 30
offset_2 = 2 
plt.figure()
plt.bar(dataset, height=[mem_avg[f][0]-offset, mem_avg[f][1]-offset, mem_avg[f][2]-offset, mem_avg[f][3]-offset, mem_avg[f][4]-offset, mem_avg[f][5]-offset, mem_avg[f][6]-offset, mem_avg[f][7]-offset, mem_avg[f][8]-offset, mem_avg[f][9]-offset, mem_avg[f][10]-offset, mem_avg[f][11]-offset, mem_avg[f][12]-offset], bottom=offset)
plt.yticks(mem_avg[f], labels=[103, 52.2, '', 113.3, 174.8, 52.2, 72.7, 62.4, 52.2, 52.2, 52.2, 52.2, 52.2])
plt.ylabel("memory usage (MiB)")
plt.grid(True)
plt.title("CheapestInsertion memory usage")
plt.show()