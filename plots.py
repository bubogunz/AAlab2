import matplotlib.pyplot as plt
import numpy as np
import os

def plot(x, y, title):
    plt.figure(title)
    plt.plot(x, y, 'k', x, y, 'ro')
    plt.xlabel("Size of graph (nodes)")
    plt.ylabel("Time (s)")
    plt.yscale("log")
    plt.savefig(os.path.join(os.path.abspath(os.path.dirname(__file__)), f"relazioneAA/relazioneAA/imgs/{title}.png"))

x=[14,16,22,51,52,100,150,202,229,442,493,1000]
approx_y=[2.226E-4,6.14E-5,9.15E-5,8.911E-4,0.007448,0.00171715,0.0234288,0.0188749,0.024916,0.090067,0.1063097,0.5357618]
heuristic_y=[2.702E-4,2.58E-5,5.29E-5,5.527E-4,0.0096012,0.00406745,0.0677773,0.0405677,0.0492992,0.3953886,0.4976069,7.5381809]

plot(x, approx_y, "Tree_TSP")
plot(x, heuristic_y, "CheapestInsertion")