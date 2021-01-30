package fr.redoishi.modGraphMenu.gui.graph;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModDependency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generate graph for cell position (render in another class)
 *
 * @author Redoishi
 */
public class GenerateGraph {

    public static final int SIZE = 20;

    private static List<Cell> modCellList = new ArrayList<>();
    private static mxGraph graph = new mxGraph();
    /**
     * map idmod -> lis id mod dep
     */
    private static HashMap<String, List<String>> modDep = new HashMap<>();

    private GenerateGraph() {
    }

    public static void loadGraph() {
        // clear
        modCellList = new ArrayList<>();
        graph = new mxGraph();
        modDep = new HashMap<>();
        graph.clearSelection();

        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();

        Collection<ModContainer> mods = FabricLoader.getInstance().getAllMods();
        HashMap<String, mxCell> modIdToCell = new HashMap<>();

        // add Vertex
        for (ModContainer mod : mods) {
            String modId = mod.getMetadata().getId();
            mxCell modCell = (mxCell) graph.insertVertex(parent, modId, modId, 0, 0, SIZE, SIZE);
            modIdToCell.put(modId, modCell);
            modCellList.add(new Cell(modCell, mod));
            modDep.put(modId, mod.getMetadata().getDepends().stream()
                    .map(ModDependency::getModId)
                    .collect(Collectors.toList())
            );
        }

        // add edge
        for (String modId : modDep.keySet()) {
            mxCell modCell = modIdToCell.get(modId);
            modDep.get(modId)
                    .stream()
                    .map(modIdToCell::get)
                    .forEach(modDepCell -> graph.insertEdge(parent, null, null, modCell, modDepCell));
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
     * @return new hashMap with map id => cell
     */
    public static List<Cell> getModCell() {
        return new ArrayList<>(modCellList);
    }

    /**
     * @return new hashMap with map id => lis dep id
     */
    public static HashMap<String, List<String>> getModDep() {
        return new HashMap<>(modDep);
    }
}
