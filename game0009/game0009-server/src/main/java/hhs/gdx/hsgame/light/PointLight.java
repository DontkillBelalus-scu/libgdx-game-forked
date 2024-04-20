package hhs.gdx.hsgame.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PointLight extends BasicLight{

  Mesh lightMesh;
  float intensity=0.5f;
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

  public void setRadiu(float radiu) {
    size.set(radiu*2,radiu*2);
  }
  public void setIntensity(float intensity) {
    this.intensity=intensity;
  }

  @Override
  public void dispose() {}

  @Override
  public void render(SpriteBatch batch) {
    int idx=0;
    vertices[idx++]=pos.x;
    vertices[idx++]=pos.y;
    vertices[idx++]=0;
    vertices[idx++]=0;
    vertices[idx++]=0;

    vertices[idx++]=pos.x+size.x;
    vertices[idx++]=pos.y;
    vertices[idx++]=0;
    vertices[idx++]=1;
    vertices[idx++]=0;

    vertices[idx++]=pos.x+size.x;
    vertices[idx++]=pos.y+size.y;
    vertices[idx++]=0;
    vertices[idx++]=1;
    vertices[idx++]=1;

    vertices[idx++]=pos.x;
    vertices[idx++]=pos.y+size.y;
    vertices[idx++]=0;
    vertices[idx++]=0;
    vertices[idx++]=1;
    idx=0;
    //    vertices[idx++]=-1;
    //    vertices[idx++]=-1;
    //    vertices[idx++]=0;
    //    vertices[idx++]=0;
    //    vertices[idx++]=0;
    //
    //    vertices[idx++]=1;
    //    vertices[idx++]=-1;
    //    vertices[idx++]=0;
    //    vertices[idx++]=1;
    //    vertices[idx++]=0;
    //
    //    vertices[idx++]=1;
    //    vertices[idx++]=1;
    //    vertices[idx++]=0;
    //    vertices[idx++]=1;
    //    vertices[idx++]=1;
    //
    //    vertices[idx++]=-1;
    //    vertices[idx++]=1;
    //    vertices[idx++]=0;
    //    vertices[idx++]=0;
    //    vertices[idx++]=1;
    lcenter.mLightShader.setUniformf("intensity",intensity);
    lightMesh.setVertices(vertices);
    lightMesh.render(lcenter.mLightShader,GL20.GL_TRIANGLE_FAN);
  }
}
