package hhs.gdx.hsgame.light;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import hhs.gdx.hsgame.entities.EntityCenter;
import hhs.gdx.hsgame.entities.EntityLayers;
import hhs.gdx.hsgame.tools.Resource;

public class LightCenter extends EntityCenter<BasicLight> implements EntityLayers.Stackable{
  public ShaderProgram mLightShader;
  final SpriteBatch lightBatch=new SpriteBatch();
  private final FrameBuffer lightsBuffer=new FrameBuffer(Pixmap.Format.RGBA8888,Resource.width,Resource.height,false);
  private final TextureRegion lightsBufferRegion=new TextureRegion();
  public LightCenter() {
    mLightShader=new ShaderProgram(
      Gdx.files.internal("glsl/vert.glsl").readString(),
      """
        precision mediump float;
        uniform vec4 l_color;
        varying vec2 f_coord;
        uniform float intensity;
        void main(){
          vec2 uv = f_coord;//(f_coord.xy*2.-vec2(1080.,1920.)) / 1080.;
          float d=length(uv*2.-1.);
          vec4 col=l_color;
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

    lightsBufferRegion.setRegion(lightsBuffer.getColorBufferTexture());
    lightsBufferRegion.flip(false,true);
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
    super.dispose();
  }
  public void render(SpriteBatch batch) {
    ready();
    Gdx.gl20.glEnable(GL20.GL_BLEND);
    //Gdx.gl20.glBlendFunc(GL20.GL_ONE,GL20.GL_ONE);
    Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
    for(var light:sons) {
      light.render(batch);
    }
  }
  public void add(BasicLight t) {
    t.lcenter=this;
    super.add(t);
  }
  Color cc=new Color(0,0,0,0);
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    ready();

    lightsBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest,Texture.TextureFilter.Nearest);
    lightsBuffer.begin();
    Gdx.gl.glClearColor(0.2f,0.2f,0.2f,1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    Gdx.gl20.glEnable(GL20.GL_BLEND);
    Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE);
    super.UpdateAndRender(batch,delta);
    Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE);
    lightsBuffer.end();

    lightBatch.getProjectionMatrix().setToOrtho2D(0,lightsBuffer.getHeight(),lightsBuffer.getWidth(),lightsBuffer.getHeight());
    lightBatch.begin();
    lightBatch.setBlendFunction(GL20.GL_DST_COLOR,GL20.GL_ZERO);
    lightBatch.draw(lightsBufferRegion,0,lightsBuffer.getHeight());
    lightBatch.end();
  }
}
