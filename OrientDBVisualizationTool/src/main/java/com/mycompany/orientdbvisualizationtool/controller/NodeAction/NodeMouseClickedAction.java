package com.mycompany.orientdbvisualizationtool.controller.NodeAction;

import com.mycompany.orientdbvisualizationtool.View.Edge;
import com.mycompany.orientdbvisualizationtool.View.Node;
import com.mycompany.orientdbvisualizationtool.controller.MainController;
import com.mycompany.orientdbvisualizationtool.model.managers.PlaceManager;
import com.mycompany.orientdbvisualizationtool.model.places.Place;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Action taken when Node is mouse clicked
 */
public class NodeMouseClickedAction implements EventHandler<MouseEvent> {

    private Node node;
    private MainController controller;
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    /**
     * constructor
     *
     * @param node       node to be clicked on
     * @param controller main controller
     * @param nodes      list for tracking edges when node expanded/contracted
     * @param edges      list for tracking edges when edges expanded/contracted
     */
    public NodeMouseClickedAction(Node node, MainController controller, ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.node = node;
        this.controller = controller;
        this.nodes = nodes;
        this.edges = edges;
    }

    /**
     * expands a node to add/show the children of the node, if node is not
     * expanded and contracts a node to remove/hide the children of the node
     * otherwise.
     *
     * @param parentNode source node for expansion
     */
    private void expandContractNode(Node parentNode) {
        if (!parentNode.isExpanded()) {
            expandNode(parentNode);
            parentNode.setExpanded(true);
            clearEdgeLinks();
        } else {
            removeNodeAndChildren(parentNode);
            //if root node
            if (parentNode.getContainerPane().getParent().getParent() instanceof AnchorPane) {
                parentNode.setLayoutY(0);
                double recHeight = parentNode.getRectangle().getHeight() / 2;
                double vBoxLayoutY = parentNode.getContainerPane().getParent().getLayoutY();
                parentNode.getContainerPane().getParent().setLayoutY(vBoxLayoutY - recHeight);
            }
            parentNode.setExpanded(false);
        }
    }

    /**
     * expands a nodes to show the children of the node
     *
     * @param parentNode source node for expansion
     */
    private void expandNode(Node parentNode) {
        VBox childrenVBox = parentNode.getChildrenVBox();
        PlaceManager placeManager = PlaceManager.getInstance();
        Place sourcePlace = placeManager.getPlace(parentNode.getNodeId());
        ArrayList<Place> childrenPlaces = sourcePlace.getChildren();

        if (!childrenPlaces.isEmpty()) {
            for (Place place : childrenPlaces) {
                String id = place.getId();
                String type = place.getType().toString();
                String displayName = place.toString();
                Node childNode = new Node(id, type, displayName, controller);
                nodes.add(childNode);
                childrenVBox.getChildren().add(childNode.getContainerPane());
                childrenVBox.layout();
            }
        }
        redrawEdges(parentNode);
    }

    /**
     * clears and redraws the edge present in the central pane. used when nodes
     * are moved around (when a node is expanded).
     */
    private void clearEdgeLinks() {
        edges.clear();
        //keep whole tree to maintain its position when it is expanded (within scroll pane bounds)
        Node rootNode = nodes.get(0);
        VBox rootVBox = (VBox) rootNode.getContainerPane().getParent();
        rootVBox.layout();
        Bounds rootNodeChildrenVBoxBounds = rootNode.getChildrenVBox().getBoundsInLocal();
        rootVBox.setLayoutY(Math.abs(rootVBox.getLayoutY() - ((rootNodeChildrenVBoxBounds.getHeight()) - rootNode.getHeight()) / 2 + rootNode.getLayoutY()));
        rootVBox.layout();

        for (int i = 0; i < nodes.size(); i++) {
            Pane containerPane = nodes.get(i).getContainerPane();
            //reposition node to the center of its children
            ObservableList<javafx.scene.Node> genericNodes = nodes.get(i).getChildrenVBox().getChildren();
            if (!genericNodes.isEmpty()) {
                nodes.get(i).setLayoutY(((nodes.get(i).getChildrenVBox().getBoundsInLocal().getHeight()) - rootNode.getHeight()) / 2);
            }

            for (int j = 0; j < containerPane.getChildren().size(); j++) {
                if (containerPane.getChildren().get(j) instanceof Edge) {
                    containerPane.getChildren().remove(j);
                    j--;
                }
            }
        }
        redrawEdges(rootNode);
    }

    /**
     * recursively redraws the edges then nodes are moved around, (when a node
     * is expanded).
     *
     * @param startNode starting node of the subtree that needs to redraw edges.
     */
    private void redrawEdges(Node startNode) {
        if (startNode.getChildrenVBox().getChildren().isEmpty()) {
            return;
        }
        for (javafx.scene.Node childGenericNode : startNode.getChildrenVBox().getChildren()) {
            Pane containerPane = (Pane) childGenericNode;
            Node childNode = (Node) containerPane.getChildren().get(0);
            Edge edge = new Edge(startNode, childNode);
            startNode.getContainerPane().getChildren().add(edge);
            startNode.getContainerPane().layout();
            edges.add(edge);
            redrawEdges(childNode);
        }
    }

    /**
     * recursively removes the children in the subtree of given node. Used when
     * the user contracts a node
     *
     * @param node the node to be contracted
     */
    private void removeNodeAndChildren(Node node) {
        Pane container = node.getContainerPane();

        if (container.getChildren().size() < 2) {
            return;
        }

        Iterator<javafx.scene.Node> iterator = container.getChildren().iterator();
        while (iterator.hasNext()) {
            javafx.scene.Node nextNode = iterator.next();
            if (nextNode instanceof Edge) {
                edges.remove(nextNode);
                iterator.remove();
            }
        }

        for (javafx.scene.Node genericNode : node.getChildrenVBox().getChildren()) {
            Pane containerPane = (Pane) genericNode;
            Node childNode = (Node) containerPane.getChildren().get(0);
            nodes.remove(childNode);
            removeNodeAndChildren(childNode);
        }
        node.getChildrenVBox().getChildren().clear();
        clearEdgeLinks();
    }

    /**
     * Mouse click event handle for expanding/contracting node
     *
     * @param event for mouse clicked
     */
    @Override
    public void handle(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            //Double click
            if (event.getClickCount() == 2) {
                expandContractNode(node);
            }
        }
    }
}
