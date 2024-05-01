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
      new VertexAttribute(Usage.Position,2,"a_position"),
      new VertexAttribute(Usage.TextureCoordinates,2,"coord"));
  }

  public void setRadiu(float radiu) {
    size.set(radiu*2,radiu*2);
  }

  public void setIntensity(float intensity) {
    this.intensity=intensity;
  }

  @Override
  public void dispose() {
    if(lightMesh!=null) lightMesh.dispose();
  }

  @Override
  public void render(SpriteBatch batch) {
    int idx=0;
    vertices[idx++]=pos.x;
    vertices[idx++]=pos.y;
    // vertices[idx++]=0;
    vertices[idx++]=0;
    vertices[idx++]=0;

    vertices[idx++]=pos.x+size.x;
    vertices[idx++]=pos.y;
    // vertices[idx++]=0;
    vertices[idx++]=1;
    vertices[idx++]=0;

    vertices[idx++]=pos.x+size.x;
    vertices[idx++]=pos.y+size.y;
    // vertices[idx++]=0;
    vertices[idx++]=1;
    vertices[idx++]=1;

    vertices[idx++]=pos.x;
    vertices[idx++]=pos.y+size.y;
    // vertices[idx++]=0;
    vertices[idx++]=0;
    vertices[idx++]=1;
    // idx=0;
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
    lcenter.mLightShader.setUniformf(
      "l_color",lightColor.r,lightColor.g,lightColor.b,lightColor.a);
    lightMesh.setVertices(vertices,0,idx);
    lightMesh.render(lcenter.mLightShader,GL20.GL_TRIANGLE_FAN);
  }

  public Color getLightColor() {
    return this.lightColor;
  }

  public void setLightColor(Color lightColor) {
    this.lightColor=lightColor;
  }
}
