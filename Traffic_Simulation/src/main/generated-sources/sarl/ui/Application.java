package ui;

import com.google.common.base.Objects;
import def.SimpleViewer;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.io.File;
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

@SarlSpecification("0.7")
@SarlElementType(10)
@SuppressWarnings("all")
public class Application extends javafx.application.Application {
  /**
   * Variable to handle dragging events
   */
  private boolean dragging;
  
  private ArrayMapElementLayer<MapPolygon> agentBodyLayer = new ArrayMapElementLayer<MapPolygon>();
  
  private ArrayMapElementLayer<MapPolygon> stopLayer = new ArrayMapElementLayer<MapPolygon>();
  
  private ArrayMapElementLayer<MapCircle> flashlightLayer = new ArrayMapElementLayer<MapCircle>();
  
  private boolean isReady = false;
  
  /**
   * Create a singleton mimic to access instance outside
   */
  private static Application instance;
  
  private MapElementLayer<?> roadNetworkLayer;
  
  public Application() {
    Application.instance = this;
  }
  
  public void init() {
    File _file = new File("asset/Ville.shp");
    this.roadNetworkLayer = SimpleViewer.loadShapeFile(_file);
  }
  
  public void start(final Stage primaryStage) {
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 1024, 768);
    ArrayList<MapElementLayer> containers = new ArrayList<MapElementLayer>();
    boolean _notEquals = (!Objects.equal(this.roadNetworkLayer, null));
    if (_notEquals) {
      containers.add(this.roadNetworkLayer);
    }
    containers.add(this.agentBodyLayer);
    containers.add(this.stopLayer);
    containers.add(this.flashlightLayer);
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
    this.isReady = true;
    primaryStage.show();
  }
  
  /**
   * @author Thomas Gredin
   * 
   * @description
   * Add an element to draw into the map elements layer of the
   * window.
   */
  public boolean addAgentBodyInLayer(final MapPolygon element) {
    return this.agentBodyLayer.addMapElement(element);
  }
  
  /**
   * @author Thomas Gredin
   * 
   * @description
   * Remove given map element from the map elements layer.
   */
  public boolean removeAgentBodyInLayer(final MapPolygon element) {
    return this.agentBodyLayer.removeMapElement(element);
  }
  
  public boolean addStopInLayer(final MapPolygon element) {
    return this.stopLayer.addMapElement(element);
  }
  
  public boolean removeStopInLayer(final MapPolygon element) {
    return this.stopLayer.removeMapElement(element);
  }
  
  public boolean addFlashlightInLayer(final MapCircle element) {
    return this.flashlightLayer.addMapElement(element);
  }
  
  public boolean removeFlashlightInLayer(final MapCircle element) {
    return this.flashlightLayer.removeMapElement(element);
  }
  
  @Pure
  public boolean getIsReady() {
    return this.isReady;
  }
  
  /**
   * @author Thomas Gredin
   * 
   * @description
   * Get the singleton instance of the application
   */
  @Pure
  public static Application getInstance() {
    boolean _equals = Objects.equal(Application.instance, null);
    if (_equals) {
      Application _application = new Application();
      Application.instance = _application;
    }
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
    if (other.isReady != this.isReady)
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
    result = prime * result + (this.isReady ? 1231 : 1237);
    return result;
  }
}
