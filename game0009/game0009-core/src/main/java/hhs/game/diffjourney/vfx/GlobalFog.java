package hhs.game.diffjourney.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import hhs.gdx.hsgame.entities.BasicEntity;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import hhs.gdx.hsgame.entities.EntityLayers;

public class GlobalFog extends BasicEntity implements EntityLayers.Stackable{

  public final ShaderProgram shader=new ShaderProgram(Gdx.files.internal("glsl/nProjVert.glsl").readString(),Gdx.files.internal("glsl/fog.glsl").readString());
  Mesh fogMesh;
  float[] vertices=new float[16];
  float time=0;
  public GlobalFog() {
    if(!shader.isCompiled()) {
      Gdx.app.log("ShaderTest",shader.getLog());
      FileHandle fh=Gdx.files.absolute("/storage/emulated/0/Android/data/hhs.game.diffjourney/files/log.log");
      fh.writeString(shader.getLog(),false);
      Gdx.app.exit();
    }
    int idx=0;
    vertices[idx++]=-1;
    vertices[idx++]=-1;
    vertices[idx++]=0;
    vertices[idx++]=0;

    vertices[idx++]=1;
    vertices[idx++]=-1;
    vertices[idx++]=1;
    vertices[idx++]=0;

    vertices[idx++]=1;
    vertices[idx++]=1;
    vertices[idx++]=1;
    vertices[idx++]=1;

    vertices[idx++]=-1;
    vertices[idx++]=1;
    vertices[idx++]=0;
    vertices[idx++]=1;

    fogMesh=new Mesh(true,
      4,
      0,
      new VertexAttribute(Usage.Position,2,"a_position"),
      new VertexAttribute(Usage.TextureCoordinates,2,"coord"));

    fogMesh.setVertices(vertices,0,idx);
  }

  @Override
  public void update(float delta) {
    time+=delta;
  }

  @Override
  public void render(SpriteBatch batch) {
    //Gdx.gl20.glEnable(GL20.GL_BLEND);
    //Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE);
    shader.bind();
    shader.setUniformf("resolution",cam.viewportWidth,cam.viewportHeight);
    shader.setUniformf("time",time);
    shader.setUniformf("pos",cam.position.x,cam.position.y);
    fogMesh.render(shader,GL20.GL_TRIANGLE_FAN);
  }

  @Override
  public EntityLayers.Layer getLayer() {
    // TODO: Implement this method
    return EntityLayers.Layer.FRONT;
  }

  @Override
  public void dispose() {
    shader.dispose();
    fogMesh.dispose();
  }

}
