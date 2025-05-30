import pandas as pd
import networkx as nx
import matplotlib.pyplot as plt
from typing import Set, Dict

def read_kill_map(filepath: str) -> pd.DataFrame:
    """Read the killMap.csv file and return as DataFrame"""
    return pd.read_csv(filepath, index_col=0)

def build_dmsg(kill_matrix: pd.DataFrame) -> nx.DiGraph:
    """Build Dynamic Mutant Subsumption Graph from kill matrix"""
    G = nx.DiGraph()
    mutants = kill_matrix.index
    
    # Add all mutants as nodes
    for mutant in mutants:
        G.add_node(mutant)
    
    # Check subsumption relationships
    for m1 in mutants:
        for m2 in mutants:
            if m1 != m2:
                kills_m1 = set(kill_matrix.columns[kill_matrix.loc[m1] == 1])
                kills_m2 = set(kill_matrix.columns[kill_matrix.loc[m2] == 1])
                
                # m1 subsumes m2 if m1 kills all tests that m2 kills
                if kills_m2.issubset(kills_m1) and kills_m1 != kills_m2:
                    G.add_edge(m1, m2)
    
    return G

def find_dominators(G: nx.DiGraph) -> Set[str]:
    """Find dominator mutants (nodes with no incoming edges)"""
    return {node for node in G.nodes() if G.in_degree(node) == 0}

def visualize_graph(G: nx.DiGraph, dominators: Set[str], output_file: str):
    """Visualize the DMSG and highlight dominator mutants"""
    plt.figure(figsize=(12, 8))
    pos = nx.spring_layout(G)
    
    # Draw regular nodes
    nx.draw_networkx_nodes(G, pos, 
                          node_color='lightblue',
                          node_size=500)
    
    # Draw dominator nodes
    nx.draw_networkx_nodes(G, pos,
                          nodelist=list(dominators),
                          node_color='red',
                          node_size=500)
    
    # Draw edges and labels
    nx.draw_networkx_edges(G, pos, edge_color='gray', arrows=True)
    nx.draw_networkx_labels(G, pos)
    
    plt.title("Dynamic Mutant Subsumption Graph")
    plt.savefig(output_file)
    plt.close()

def main():
    # Read kill matrix
    kill_matrix = read_kill_map('../mutation_results/covMap.csv')
    
    # Build DMSG
    graph = build_dmsg(kill_matrix)
    
    # Find dominators
    dominators = find_dominators(graph)
    
    # Visualize graph
    visualize_graph(graph, dominators, 'dmsg.png')
    
    # Print results
    print("Dominator mutants:")
    for mutant in sorted(dominators):
        print(f"- {mutant}")
    
    print(f"\nTotal mutants: {len(graph.nodes())}")
    print(f"Number of dominator mutants: {len(dominators)}")

if __name__ == "__main__":
    main()