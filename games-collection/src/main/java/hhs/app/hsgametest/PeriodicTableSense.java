package hhs.app.hsgametest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.UBJsonReader;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.LazyBitmapFont;
import hhs.gdx.hsgame.tools.Resource;
import java.io.BufferedReader;

public class PeriodicTableSense extends ScreenAdapter{
  ModelBatch batch;
  ModelBuilder builder=new ModelBuilder();
  Environment environment=new Environment();
  PerspectiveCamera camera;
  Array<ModelInstance> array=new Array<>(),el=new Array<>();

  LazyBitmapFont font=Resource.font.newFont(64,Color.WHITE);
  SpriteBatch sbatch=new SpriteBatch();
  float s=4f;

  TextureAttribute ta=TextureAttribute.createDiffuse(genText(""));
  Material boxM=new Material(ta);
  int du=Usage.Position|Usage.Normal|Usage.TextureCoordinates;
  Model box=builder.createBox(5,5,5,boxM,du);

  public PeriodicTableSense() {
    batch=new ModelBatch();
    camera=new PerspectiveCamera(67,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    camera.far=300f;
    camera.near=1f;
    camera.position.set(-50,15,50);
    camera.lookAt(0,5,50);
    // camera.up.set(Vector3.Y);
    Gdx.input.setInputProcessor(new CameraControl(camera,el));

    environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.6f,0.6f,0.6f,1f));
    environment.add(new DirectionalLight().set(.8f,.8f,.8f,.4f,-.4f,.4f));
    environment.add(new PointLight().set(.8f,.8f,.8f,0,70,50,800));

    array.add(
      new ModelInstance(
        new G3dModelLoader(new UBJsonReader()).loadModel(Gdx.files.internal("skydome.g3db"))));
    builder.begin();
    builder.node().id="floor";
    MeshPartBuilder part=builder.part(
      "floor",
      GL20.GL_TRIANGLES,
      Usage.Position|Usage.TextureCoordinates|Usage.Normal,
      new Material(TextureAttribute.createDiffuse(new Texture("concrete.png"))));
    part.ensureVertices(4*1600);
    part.ensureRectangleIndices(1600);
    for(float x=-200f;x<200f;x+=10f) {
      for(float z=-200f;z<200f;z+=10f) {
        part.rect(x,0,z+10f,x+10f,0,z+10f,x+10f,0,z,x,0,z,0,1,0);
      }
    }
    var box=new ModelInstance(builder.end());
    sbatch=new SpriteBatch();
    array.add(box);
    FileHandle f=Gdx.files.internal("z2.txt");
    var reader=new BufferedReader(f.reader());
    String str;
    try {
      int index=0;
      while((str=reader.readLine())!=null) {
        index++;
        qadbox(str);
        if(index>=18) {
          index=0;
          cur.y-=7;
          cur.z=-25;
        }
      }
    }catch(Exception e) {
      e.printStackTrace();
    }
  }

  Vector3 cur=new Vector3(0,40,-25);

  @Override
  public void render(float delta) {
    camera.lookAt(0,camera.position.y-5*Math.abs(camera.position.x)/50,camera.position.z);
    Gdx.gl20.glClearColor(0,0,0,0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
    camera.update();
    batch.begin(camera);
    batch.render(array,environment);
    batch.render(el,environment);
    batch.end();
  }

  public void qadbox(String str) {
    adBox(str,cur);
  }

  public void adBox(String str,Vector3 pos) {
    var t=genText(str);
    TextureRegion tr;
    if(t!=null) {
      cur.z+=5+2;
      tr=new TextureRegion(t);
      tr.flip(false,true);
      box.materials.first().clear();
      box.materials.first().set(TextureAttribute.createDiffuse(tr));
    }else {
      cur.z+=5+2;
      box.materials.first().clear();
      box.materials.first().set(ColorAttribute.createDiffuse(ColorTool.明月珰));
      // box.materials.first().set(ColorAttribute.createDiffuse(0.15f, 0.15f, 0.15f, 1f));
    }
    add(box).transform.setToTranslation(pos).rotate(Vector3.X,90);
  }

  public ModelInstance add(final Model model) {
    var mi=new ModelInstance(model);
    el.add(mi);
    return mi;
  }

  public Texture genText(String str) {
    if(str.equals("")) {
      return null;
    }
    FrameBuffer fb=new FrameBuffer(Pixmap.Format.RGB888,1980,1080,false);
    fb.begin();
    Gdx.gl20.glViewport(0,0,fb.getWidth(),fb.getHeight());
    ScreenUtils.clear(ColorTool.石榴裙);
    if(sbatch==null) sbatch=new SpriteBatch();
    sbatch.begin();
    font.getData().setScale(s);
    font.drawText(sbatch,str,0,fb.getHeight()-5f*s,fb.getWidth(),64*(int)s);
    sbatch.end();
    fb.end();
    return fb.getColorBufferTexture();
  }
}
