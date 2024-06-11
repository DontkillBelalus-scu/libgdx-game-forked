package hhs.game.diffjourney.vfx;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.entities.EntityCenter;

public class VfxCenter extends EntityCenter<BasicEntity>{

  public VfxManager vfxManager;
  public Array<ChainVfxEffect> effects=new Array<>();
  ShaderProgram shaderProgram;
  public VfxCenter() {
    vfxManager=new VfxManager(Pixmap.Format.RGBA8888);
    //    String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
    //			+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
    //			+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
    //			+ "uniform mat4 u_projTrans;\n" //
    //			+ "varying vec4 v_color;\n" //
    //			+ "varying vec2 v_texCoords;\n" //
    //			+ "\n" //
    //			+ "void main()\n" //
    //			+ "{\n" //
    //			+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
    //			+ "   v_color.a = v_color.a * (255.0/254.0);\n" //
    //			+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
    //			+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
    //			+ "}\n";
    //    shaderProgram=new ShaderProgram(vertexShader,Gdx.files.internal("glsl/hdr_frag.glsl").readString());
    //    if(!shaderProgram.isCompiled()) {
    //      Gdx.app.log("ShaderTest",shaderProgram.getLog());
    //      FileHandle fh=Gdx.files.absolute("/storage/emulated/0/Android/data/hhs.game.diffjourney/files/log.log");
    //      fh.writeString(shaderProgram.getLog(),false);
    //      Gdx.app.exit();
    //    }
  }

  @Override
  public void dispose() {
    vfxManager.dispose();
    for(var e:effects) {
      e.dispose();
    }
    super.dispose();
  }
  public void addEffect(ChainVfxEffect cve) {
    vfxManager.addEffect(cve);
    effects.add(cve);
  }
  public void removeEffect(ChainVfxEffect cve) {
    vfxManager.removeEffect(cve);
    effects.removeValue(cve,false);
  }

  @Override
  public void render(SpriteBatch batch) {
    super.render(batch);
    endBuffer();
  }
  public void beginBuffer() {
    vfxManager.cleanUpBuffers();
    vfxManager.beginInputCapture();
  }
  public void endBuffer() {
    vfxManager.endInputCapture();
    vfxManager.applyEffects();
    vfxManager.renderToScreen();
  }

  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    super.UpdateAndRender(batch,delta);
    batch.end();
    endBuffer();
    batch.begin();
  }
}
