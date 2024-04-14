package hhs.gdx.hsgame.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PointLight extends BasicLight{

  Mesh lightMesh;
  float[] vertices=new float[20];
  Color lightColor=Color.WHITE;

  public PointLight() {
    pos.set(0,0);
    size.set(1,1);
    lightMesh=new Mesh(
      false,
      4,
      0,
      new VertexAttribute(Usage.Position,3,"a_position"),
      new VertexAttribute(Usage.TextureCoordinates,2,"coord"));
  }

  @Override
  public void dispose() {}

  @Override
  public void render(SpriteBatch batch) {
    int idx=0;
    //    vertices[idx++]=pos.x;
    //    vertices[idx++]=pos.y;
    //    vertices[idx++]=0;
    //    vertices[idx++]=0;
    //    vertices[idx++]=0;
    //    
    //    vertices[idx++]=pos.x;
    //    vertices[idx++]=pos.y+size.y;
    //    vertices[idx++]=0;
    //    vertices[idx++]=0;
    //    vertices[idx++]=1;
    //    
    //    vertices[idx++]=pos.x+size.x;
    //    vertices[idx++]=pos.y+size.y;
    //    vertices[idx++]=0;
    //    vertices[idx++]=1;
    //    vertices[idx++]=0;
    //    
    //    vertices[idx++]=pos.x+size.x;
    //    vertices[idx++]=pos.y;
    //    vertices[idx++]=0;
    //    vertices[idx++]=1;
    //    vertices[idx++]=1;
    batch.end();
    idx=0;
    vertices[idx++]=-1;
    vertices[idx++]=-1;
    vertices[idx++]=0;
    vertices[idx++]=0;
    vertices[idx++]=0;

    vertices[idx++]=1;
    vertices[idx++]=-1;
    vertices[idx++]=0;
    vertices[idx++]=1;
    vertices[idx++]=0;

    vertices[idx++]=1;
    vertices[idx++]=1;
    vertices[idx++]=0;
    vertices[idx++]=1;
    vertices[idx++]=1;

    vertices[idx++]=-1;
    vertices[idx++]=1;
    vertices[idx++]=0;
    vertices[idx++]=0;
    vertices[idx++]=1;
    lightMesh.setVertices(vertices);
    lcenter.ready();
    lightMesh.render(lcenter.mLightShader,GL20.GL_TRIANGLE_FAN);
    batch.begin();
  }
}
