package ui;

import com.google.common.base.Objects;
import environments.RoadNetwork;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.arakhne.afc.gis.mapelement.MapCircle;
import org.arakhne.afc.gis.mapelement.MapPolygon;
import org.arakhne.afc.gis.maplayer.ArrayMapElementLayer;
import org.arakhne.afc.gis.maplayer.MapElementLayer;
import org.arakhne.afc.gis.maplayer.MapLayer;
import org.arakhne.afc.gis.maplayer.MultiMapLayer;
import org.arakhne.afc.gis.primitive.GISContainer;
import org.arakhne.afc.nodefx.ZoomablePane;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Thomas Gredin
 * 
 * @description Class that handle all stuff about window that display the feedback of the
 * animation of the simulation.
 */
@SarlSpecification("0.7")
@SarlElementType(10)
@SuppressWarnings("all")
public class Application extends javafx.application.Application {
  /**
   * Variable to handle dragging events
   */
  private boolean dragging;
  
  /**
   * Create a singleton mimic to access instance outside
   */
  private static Application instance;
  
  private MapElementLayer<?> roadNetworkLayer;
  
  public Application() {
    Application.instance = this;
  }
  
  public void init() {
    RoadNetwork rd = new RoadNetwork();
    this.roadNetworkLayer = rd.loadShapeFile("asset/Ville.shp");
  }
  
  public void start(final Stage primaryStage) {
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 1024, 768);
    ArrayList<MapElementLayer> containers = new ArrayList<MapElementLayer>();
    boolean _notEquals = (!Objects.equal(this.roadNetworkLayer, null));
    if (_notEquals) {
      containers.add(this.roadNetworkLayer);
    }
    MapCircle circle = new MapCircle(940052, 2302886, 4);
    circle.setColor(255);
    MapPolygon polygon = new MapPolygon();
    polygon.addPoint(940052, 2302886);
    polygon.addPoint((940052 - 20), 2302886);
    polygon.addPoint((940052 - 20), (2302886 - 20));
    polygon.addPoint(940052, (2302886 - 20));
    ArrayMapElementLayer<MapPolygon> lay = new ArrayMapElementLayer<MapPolygon>();
    lay.addMapElement(polygon);
    containers.add(lay);
    GISContainer container = null;
    MultiMapLayer layer = null;
    int _size = containers.size();
    boolean _equals = (_size == 1);
    if (_equals) {
      container = containers.get(0);
    } else {
      MultiMapLayer<MapLayer> _multiMapLayer = new MultiMapLayer<MapLayer>();
      layer = _multiMapLayer;
      for (final MapElementLayer child : containers) {
        layer.addMapLayer(child);
      }
      container = layer;
    }
    ZoomablePane<GISContainer> scrollPane = new ZoomablePane<GISContainer>(container);
    root.setCenter(scrollPane);
    primaryStage.setTitle("Traffic simulation !");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  /**
   * @author Thomas Gredin
   * 
   * @description
   * Get the singleton instance of the application
   */
  @Pure
  public static Application getInstance() {
    return Application.instance;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Application other = (Application) obj;
    if (other.dragging != this.dragging)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + (this.dragging ? 1231 : 1237);
    return result;
  }
}
