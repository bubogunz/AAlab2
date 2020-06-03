import numpy as np
import os
import matplotlib.pyplot as plt

def return_result(file):
    name_file = []
    cost_file = []
    time_file = []
    error_file = []

    for line in file:
        list_line = line.split("\t")
        name_file.append(list_line[0])
        cost_file.append(list_line[1])
        time_file.append(list_line[2])
        error_file.append(list_line[3])

    del name_file[0]
    del cost_file[0]
    del time_file[0]
    del error_file[0]

    return name_file, cost_file, time_file, error_file

my_path = os.path.abspath(os.path.dirname(__file__))
path = os.path.join(my_path, "JavaLab2/Heuristic.txt")
heuristic = open(path, "r")
path = os.path.join(my_path, "JavaLab2/2Approx.txt")
tree = open(path, "r")

if heuristic.mode == "r" and tree.mode == "r":

    name_heuristic, cost_heuristic, time_heuristic, error_heuristic = return_result(heuristic)

    name_tree, cost_tree, time_tree, error_tree = return_result(tree)

    error_heuristic=[float(x) for x in error_heuristic]
    error_tree=[float(x) for x in error_tree]
    time_heuristic=[float(x) for x in time_heuristic]
    time_tree=[float(x) for x in time_tree]

    x = np.arange(len(name_heuristic))
    width = 0.35
    degree=30
    figure, (ax1, ax2, ax3) = plt.subplots(3,1, figsize=(8,8))
    ax1.bar(x + width/2, time_tree, width, label="TriangleTSP")
    ax1.bar(x - width/2, time_heuristic, width, label="CheapestInsertion")
    ax1.set_yscale("log")
    ax1.set_xticks(x)
    ax1.set_xticklabels(name_heuristic, rotation=degree)
    ax1.set_ylabel("Time (s)")
    ax1.legend()
    ax1.set_title("Time of execution (log scale)")

    ax2.bar(x + width/2, time_tree, width, label="TriangleTST")
    ax2.bar(x - width/2, time_heuristic, width, label="CheapestInsertion")
    ax2.set_xticks(x)
    ax2.set_xticklabels(name_heuristic, rotation=degree)
    ax2.set_ylabel("Time (s)")
    ax2.legend()
    ax2.set_title("Time of execution")

    ax3.bar(x + width/2, error_tree, width, label="TriangleTSP")
    ax3.bar(x - width/2, error_heuristic, width, label="CheapestInsertion")
    ax3.set_xticks(x)
    ax3.set_xticklabels(name_heuristic, rotation=degree)
    ax3.set_ylabel("Error %")
    ax3.legend()
    ax3.set_title("Relative error")

    plt.subplots_adjust(hspace=0.5)
    plt.savefig(os.path.join(os.path.abspath(os.path.dirname(__file__)), f"relazioneAA/relazioneAA/imgs/confronto.png"))
    plt.show()


heuristic.close()
tree.close()
    