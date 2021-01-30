package fr.redoishi.modGraphMenu;

import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Class for test mxGraph API
 * @author Redoishi
 */
public class TestMxgraph {

    public static void main(String[] args) {

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();

        mxCell test1 = (mxCell)graph.insertVertex(parent, "def", "def", 0, 0, 50, 50);
        for (int i = 0; i < 50; i++) {
            Object o = graph.insertVertex(parent, null, "test" + i, 0, 0, 50, 50);
            graph.insertEdge(parent, null, null, o, test1);
        }

        graph.getModel().endUpdate();

        System.out.println(Arrays.stream(graph.getChildCells(parent)).reduce((o, o2) -> o + "\n" + o2).get());
        System.out.println();

        // mxCircleLayout, mxFastOrganicLayout, mxHierarchicalLayout, mxOrganicLayout
        mxGraphLayout layout = new mxOrganicLayout(graph);

        layout.execute(parent);

        System.out.println(Arrays.stream(graph.getChildCells(parent)).reduce((o, o2) -> o + "\n" + o2).get());
        System.out.println(test1.getGeometry());

        BufferedImage image = mxCellRenderer.createBufferedImage(graph, null,
                1, Color.WHITE, true, null);
        JFrame frame = new JFrame();
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setVisible(true);
    }
}
