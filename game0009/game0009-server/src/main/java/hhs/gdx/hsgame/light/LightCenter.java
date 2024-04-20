package hhs.gdx.hsgame.light;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import hhs.gdx.hsgame.entities.EntityCenter;
import hhs.gdx.hsgame.entities.EntityLayers;

public class LightCenter extends EntityCenter<BasicLight> implements EntityLayers.Stackable{
  public ShaderProgram mLightShader;
  public LightCenter() {
    mLightShader=new ShaderProgram(
      """
           uniform mat4 u_projTrans;
           uniform vec4 l_color;
        varying vec4 v_color;
           varying vec2 f_coord;
           attribute vec2 coord;
           attribute vec4 a_position;
           void main(){
             v_color=l_color;
             f_coord=coord;
             gl_Position = u_projTrans*a_position;
           }
           """,
      """
        precision mediump float;
        varying vec4 v_color;
        varying vec2 f_coord;
        uniform float intensity;
        void main(){
          vec2 uv = f_coord;//(f_coord.xy*2.-vec2(1080.,1920.)) / 1080.;
          float d=length(uv*2.-1.);
          vec4 col=vec4(1.,1.,1.,.5);
          col*=1./(d+1.-intensity)-0.6*sqrt(d);
          gl_FragColor = col;
        }
        """);
    if(!mLightShader.isCompiled()) {
      Gdx.app.log("ShaderTest",mLightShader.getLog());
      FileHandle fh=Gdx.files.absolute("/storage/emulated/0/Android/data/hhs.game.diffjourney/files/log.log");
      fh.writeString(mLightShader.getLog(),false);
      Gdx.app.exit();
    }
  }
  @Override
  public EntityLayers.Layer getLayer() {
    return EntityLayers.Layer.FRONT;
  }

  void ready() {
    mLightShader.bind();
    mLightShader.setUniformMatrix("u_projTrans",cam.combined);
  }

  public void dispose() {
    mLightShader.dispose();
  }
  public void render(SpriteBatch batch) {
    ready();
    Gdx.gl20.glEnable(GL20.GL_BLEND);
    Gdx.gl20.glBlendFunc(GL20.GL_ONE,GL20.GL_ONE);
    for(var light:sons) {
      if(light instanceof PointLight pl) mLightShader.setUniformf("l_color",pl.lightColor.r,pl.lightColor.g,pl.lightColor.b,pl.lightColor.a);
      light.render(batch);
    }
  }
  public void add(BasicLight t) {
    t.lcenter=this;
    super.add(t);
  }
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    ready();
    Gdx.gl20.glEnable(GL20.GL_BLEND);
    Gdx.gl20.glBlendFunc(GL20.GL_ONE,GL20.GL_ONE);
    for(var light:sons) {
      if(light instanceof PointLight pl) mLightShader.setUniformf("l_color",pl.lightColor.r,pl.lightColor.g,pl.lightColor.b,pl.lightColor.a);
      light.update(delta);
      light.render(batch);
    }
  }
}
