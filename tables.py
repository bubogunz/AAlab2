import numpy as np
import os

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

def write_result(file, name, cost, time, error, algo):
    file.write("\\begin{table}[H]\n\centering\n\\begin{tabular}{|c|c|c|c|c|}\n")
    file.write("\\hline\n\\textbf{N.} & \\textbf{Name Graph} & \\textbf{TSP cost} & \\textbf{Time (s)} & \\textbf{Error (\%)}\\\\ \n")

    for i in range(len(name)):
        file.write(f"\\hline\n{i+1} & {name[i]} & {cost[i]} & {time[i]} & {error[i]}\\\\\n")

    file.write("\\hline\n\\end{tabular}\n\\caption{Risultati dell'algoritmo \\texttt{" + algo + "}}\n\end{table}")
    file.close()

def write_compare(file, name, h_time, h_error, t_time, t_error):
    file.write("\\begin{table}[H]\n\centering\n\\begin{tabular}{|c|c|c|c|}\n")
    file.write("\\hline\n\\textbf{N.} & \\textbf{Name Graph} & \\textbf{CheapestInsertion ratio} & \\textbf{Tree\_TSP ratio}\\\\ \n")
    
    h_rate=[]
    t_rate=[]
    for i in range(len(name)):
        h_rate.append(float(h_error[i])*float(h_time[i]))
        t_rate.append(float(t_error[i])*float(t_time[i]))

    for i in range(len(name)):
        file.write(f"\\hline\n{i+1} & {name[i]} & {round(h_rate[i], 4)} & {round(t_rate[i], 4)}\\\\\n")

    file.write("\\hline\n & \\textbf{Average} & " + f"{round(sum(h_rate)/len(h_rate), 2)} & {round(sum(t_rate)/len(t_rate), 2)}\\\\\n")
    file.write("\\hline\n\\end{tabular}\n\end{table}")
    file.close()


my_path = os.path.abspath(os.path.dirname(__file__))
path = os.path.join(my_path, "JavaLab2/HeldKarp.txt")
heldkarp = open(path, "r")
path = os.path.join(my_path, "JavaLab2/Heuristic.txt")
heuristic = open(path, "r")
path = os.path.join(my_path, "JavaLab2/2Approx.txt")
tree = open(path, "r")

if heldkarp.mode == "r" and heuristic.mode == "r" and tree.mode == "r":
    name_heldkarp, cost_heldkarp, time_heldkarp, error_heldkarp = return_result(heldkarp)

    name_heuristic, cost_heuristic, time_heuristic, error_heuristic = return_result(heuristic)

    name_tree, cost_tree, time_tree, error_tree = return_result(tree)

    w_heldkarp = open("table_heldkarp.txt", "w")
    w_heuristic = open("table_heuristic.txt", "w")
    w_tree = open("table_2approx.txt", "w")
    w_rate = open("rate.txt", "w")

    write_result(w_heldkarp, name_heldkarp, cost_heldkarp, time_heldkarp, error_heldkarp, "HeldKarp")
    write_result(w_heuristic, name_heuristic, cost_heuristic, time_heuristic, error_heuristic, "CheapestInsertion")
    write_result(w_tree, name_tree, cost_tree, time_tree, error_tree, "Tree\_TSP")

    write_compare(w_rate, name_heldkarp, time_heuristic, error_heuristic, time_tree, error_tree)

    print(round(sum([float(x) for x in error_heuristic])/len(error_heuristic), 0))
    print(round(sum([float(x) for x in error_tree])/len(error_tree), 0))



heldkarp.close()
heuristic.close()
tree.close()
    