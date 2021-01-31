package fr.redoishi.modGraphMenu.gui.graph;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Generate graph for cell position (render in another class)
 *
 * @author Redoishi
 */
public class GenerateGraph {

    public static final int SIZE = 20;

    private static HashMap<String, Cell> modCellList = new HashMap<>();
    private static mxGraph graph = new mxGraph();

    private GenerateGraph() {
    }

    public static void loadGraph() {
        // clear
        modCellList = new HashMap<>();
        graph = new mxGraph();

        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();

        Collection<ModContainer> mods = FabricLoader.getInstance().getAllMods();
        HashMap<String, Set<String>> modDep = new HashMap<>();

        // add Vertex
        for (ModContainer mod : mods) {
            String modId = mod.getMetadata().getId();
            // skip fabricmc sub API
            if (modId.startsWith("fabric") && !modId.equals("fabric") && !modId.equals("fabricloader")) {
                continue;
            }
            mxCell modCell = (mxCell) graph.insertVertex(parent, modId, modId, 0, 0, SIZE, SIZE);
            modCellList.put(modId, new Cell(modCell, mod));
            modDep.put(
                    modId,
                    mod.getMetadata().getDepends()
                            .stream()
                            .map(modDependency -> {
                                String modidDep = modDependency.getModId();
                                if (modidDep.startsWith("fabric") && !modidDep.equals("fabric") && !modidDep.equals("fabricloader")) {
                                    return "fabric";
                                }
                                return modidDep;
                            })
                            .collect(Collectors.toSet())
            );
        }

        // add edge
        for (String modId : modDep.keySet()) {
            Cell modCell = modCellList.get(modId);
            modDep.get(modId)
                    .stream()
                    .map(s -> modCellList.get(s))
                    .forEach(modDepCell -> {
                        modCell.modDep.add(modDepCell);
                        graph.insertEdge(parent, null, null, modCell.modMxCell, modDepCell.modMxCell);
                    });
        }

        graph.getModel().endUpdate();
        executeLayout();
    }

    public static void executeLayout() {
        // TODO user select (mxCircleLayout, mxFastOrganicLayout, mxHierarchicalLayout, mxOrganicLayout)
        mxGraphLayout layout = new mxFastOrganicLayout(graph);
        layout.execute(graph.getDefaultParent());
    }

    /**
     * @return list of {@link Cell}
     */
    public static ArrayList<Cell> getModCell() {
        return new ArrayList<>(modCellList.values());
    }

}
