package frs.gui.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GraphicViewController {

    private final AnchorPane canvas_panel;
    private final GraphicsContext gc;
    private final int imgMarginX, imgMarginY, mWidth, mHeight;
    private final Image anlageimg;
    boolean firstRunFlag = true;

    private Map<Integer, double[]> componentPosition = new HashMap<>();
    private final JSONObject mTables;

    public GraphicViewController(AnchorPane canvas_panel, Canvas canvas, JSONObject mTables) {
        this.mHeight = 582;
        this.imgMarginX = 200;
        this.mWidth = 820;
        this.imgMarginY = 50;
        this.anlageimg = new Image("/resources/AnlageDiagramm.png", mWidth, mHeight, false, true);
        this.canvas_panel = canvas_panel;
        this.gc = canvas.getGraphicsContext2D();
        this.componentPosition = generateComponentPosition();
        this.mTables = mTables;
    }

    private Map<Integer, double[]> generateComponentPosition() {
        Map<Integer, double[]> componentPosition = new HashMap<>();
        double[] MC = {0.0, 0.0, 0.0, 0.0};
        double[] E104 = {0.633, 0.611, 0.093, 0.047};
        double[] B104 = {0.589, 0.530, 0.053, 0.074};
        double[] T101 = {0.638, 0.428, 0.239, 0.281};
        double[] V102 = {0.534, 0.448, 0.077, 0.059};
        double[] S115 = {0.545, 0.378, 0.054, 0.06};
        double[] S116 = {0.545, 0.378, 0.054, 0.06};
        double[] B101 = {0.722, 0.334, 0.053, 0.074};
        double[] S117 = {0.947, 0.530, 0.053, 0.074};
        double[] B114 = {0.872, 0.458, 0.053, 0.074};
        double[] M106 = {0.209, 0.813, 0.083, 0.086};
        double[] M101 = {0.493, 0.884, 0.062, 0.082};
        double[] P101 = {0.572, 0.860, 0.073, 0.099};
        double[] B103 = {0.100, 0.908, 0.053, 0.074};
        double[] B102 = {0.365, 0.908, 0.053, 0.074};
        double[] S111 = {0.257, 0.182, 0.053, 0.074};
        double[] S112 = {0.111, 0.374, 0.053, 0.074};
        double[] B113 = {0.872, 0.615, 0.053, 0.074};
        double[] V109 = {0.800, 0.738, 0.056, 0.102};
        double[] Vair = {0.221, 0.036, 0.077, 0.104};
        double[] Air101 = {0.347, 0.003, 0.053, 0.074};
        double[] Air202 = {0.478, 0.0023, 0.053, 0.074};
        double[] KOMP = {0.082, 0.085, 0.072, 0.101};
        double[] V104 = {0.001, 0.569, 0.055, 0.101};
        double[] V112 = {0.433, 0.418, 0.073, 0.077};
        double[] T102 = {0.171, 0.271, 0.238, 0.281};
        componentPosition.put(1, MC);
        componentPosition.put(2, E104);
        componentPosition.put(3, B104);
        componentPosition.put(4, T101);
        componentPosition.put(5, V102);
        componentPosition.put(6, S115);
        componentPosition.put(7, S116);
        componentPosition.put(8, B101);
        componentPosition.put(9, S117);
        componentPosition.put(10, B114);
        componentPosition.put(11, M106);
        componentPosition.put(12, M101);
        componentPosition.put(13, P101);
        componentPosition.put(14, B103);
        componentPosition.put(15, B102);
        componentPosition.put(16, S111);
        componentPosition.put(17, S112);
        componentPosition.put(18, B113);
        componentPosition.put(19, V109);
        componentPosition.put(20, Vair);
        componentPosition.put(21, Air101);
        componentPosition.put(22, Air202);
        componentPosition.put(23, KOMP);
        componentPosition.put(24, V104);
        componentPosition.put(25, V112);
        componentPosition.put(26, T102);
        return componentPosition;
    }

    private void drawErrorRecht() {
        gc.setGlobalAlpha(0.3);
        List<Integer> effectedComponentIds = new ArrayList<>();
        List<Integer> faultComponentIds = new ArrayList<>();
        JSONArray components = mTables.getJSONArray("Components");
        JSONArray components_subsystem_rel = mTables.getJSONArray("SubsystemComponentRel");
        for (int i = 0; i < components.length(); i++) {
            if (components.getJSONObject(i).getString("status").equals("inactive")) {
                faultComponentIds.add(components.getJSONObject(i).getInt("component_id"));
                for (int j = 0; j < components_subsystem_rel.length(); j++) {
                    if (components_subsystem_rel.getJSONObject(j).getInt("component_id") == components.getJSONObject(i)
                            .getInt("component_id")) {
                        int faultSubsystemId = components_subsystem_rel.getJSONObject(j).getInt("subsystem_id");
                        for (int k = 0; k < components_subsystem_rel.length(); k++) {
                            if (components_subsystem_rel.getJSONObject(k).getInt("subsystem_id") == faultSubsystemId
                                    && components_subsystem_rel.getJSONObject(k).getInt("component_id") != components
                                    .getJSONObject(i).getInt("component_id")) {
                                effectedComponentIds
                                        .add(components_subsystem_rel.getJSONObject(k).getInt("component_id"));
                            }
                        }
                        break;
                    }
                }
            }
        }
        faultComponentIds.forEach(faultComponentId -> {
            if (faultComponentId != 1) {
                Image componentImg = new Image("/resources/" + faultComponentId + "f.png");
                gc.drawImage(componentImg, componentPosition.get(faultComponentId)[0] * mWidth + imgMarginX,
                        componentPosition.get(faultComponentId)[1] * mHeight + imgMarginY);
            }

        });

        effectedComponentIds.forEach(effectedComponentId -> {
            if (effectedComponentId != 1) {
                Image componentImg = new Image("/resources/" + effectedComponentId + "w.png");
                gc.drawImage(componentImg, componentPosition.get(effectedComponentId)[0] * mWidth + imgMarginX,
                        componentPosition.get(effectedComponentId)[1] * mHeight + imgMarginY);
            }
        });
    }

    public void draw() {
        gc.setGlobalAlpha(1);
        gc.drawImage(anlageimg, imgMarginX, imgMarginY);
        drawErrorRecht();
        if (firstRunFlag) {
            generateTooltip();
            firstRunFlag = false;
        }

    }

    private void generateTooltip() {
        Platform.runLater(() -> {
            for (Map.Entry<Integer, double[]> componentPosition1 : componentPosition.entrySet()) {
                int x = (int) (componentPosition1.getValue()[0] * mWidth + imgMarginX);
                int y = (int) (componentPosition1.getValue()[1] * mHeight + imgMarginY);
                int w = (int) (componentPosition1.getValue()[2] * mWidth);
                int h = (int) (componentPosition1.getValue()[3] * mHeight);
                Rectangle rect = new Rectangle(x, y, w, h);
                rect.setFill(Color.TRANSPARENT);
                JSONArray components = mTables.getJSONArray("Components");
                JSONObject obj = components.getJSONObject(componentPosition1.getKey() - 1);
                canvas_panel.getChildren().add(rect);
                Tooltip tooltip = new Tooltip();
                tooltip.setText("Component ID: " + obj.getInt("component_id") + "\nComponent Name: "
                        + obj.getString("component_name") + "\nComponent Desc: " + obj.getString("component_desc")
                        + "\nSeries: " + obj.getString("series") + "\nType: " + obj.getString("type")
                        + "\nActivition: " + obj.getString("activition") + "\nStatus: " + obj.getString("status"));
                Tooltip.install(rect, tooltip);
            }
        });
    }
}
