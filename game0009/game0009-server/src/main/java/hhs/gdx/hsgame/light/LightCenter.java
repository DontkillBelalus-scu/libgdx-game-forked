package hhs.gdx.hsgame.light;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import hhs.gdx.hsgame.entities.EntityCenter;

public class LightCenter extends EntityCenter<BasicLight>{
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
        void main(){
          vec2 uv = (gl_FragCoord.xy*2.-vec2(1080.,1920.)) / 1080.;
          float d=length(uv)*1.;
          vec4 col=vec4(0.5,0.6,0.7,.5);
          col*=1./(d+.5);
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
  void ready() {
    mLightShader.setUniformMatrix("u_projTrans",cam.combined);
    mLightShader.bind();
  }

  public void dispose() {
    mLightShader.dispose();
  }
  public void render(SpriteBatch batch) {
    ready();
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
    //ready();
    for(var light:sons) {
      if(light instanceof PointLight pl) mLightShader.setUniformf("l_color",pl.lightColor.r,pl.lightColor.g,pl.lightColor.b,pl.lightColor.a);
      light.update(delta);
      light.render(batch);
    }
  }
}
