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

    return name_file, cost_file, time_file, error_file

def write_result(file, name, cost, time, error, algo):
    file.write("\\begin{table}[H]\n\centering\n\\begin{tabular}{|c|c|c|c|c|}\n")
    file.write("\\hline\n\\textbf{N.} & \\textbf{Name Graph} & \\textbf{TSP cost} & \\textbf{Time (s)} & \\textbf{Error (\%)}\\\\ \n")

    for i in range(1, len(name)):
        file.write(f"\\hline\n{i} & {name[i]} & {cost[i]} & {time[i]} & {error[i]}\\\\\n")

    file.write("\\hline\n\\end{tabular}\n\\caption{Risultati dell'algoritmo \\texttt{" + algo + "}}\n\end{table}")
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

    write_result(w_heldkarp, name_heldkarp, cost_heldkarp, time_heldkarp, error_heldkarp, "HeldKarp")
    write_result(w_heuristic, name_heuristic, cost_heuristic, time_heuristic, error_heuristic, "Heuristic")
    write_result(w_tree, name_tree, cost_tree, time_tree, error_tree, "2Approx")



heldkarp.close()
heuristic.close()
tree.close()
    