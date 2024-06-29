package pama1234.gdx.game.state.state0002;

import static pama1234.gdx.game.duel.util.input.UiGenerator.getServerAttrText;
import static pama1234.gdx.game.duel.util.input.UiGenerator.getSkinText;

import pama1234.app.game.server.duel.ServerConfigData.ServerAttr;
import pama1234.gdx.MobileUtil;
import pama1234.gdx.Pama;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.State0002Util.StateEntity0002;
import pama1234.gdx.game.duel.util.input.UiGenerator;
import pama1234.gdx.game.duel.util.theme.ThemeData;
import pama1234.gdx.game.ui.element.Slider;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.gdx.launcher.MainApp;
import pama1234.gdx.util.android.AndroidCtrlBase;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.gdx.util.ui.editor.TextEditor;

public class Settings extends StateEntity0002{
  public TextEditor<?>[] textEditors;
  public TextButton<?>[] buttons;
  public TextButtonCam<?>[] camButtons;
  public Settings(Duel p,int id) {
    super(p,id);
    init();
  }
  @Override
  public void init() {
    textEditors=genUi_0002(p);
    buttons=UiGenerator.genReturnButton(p);
    camButtons=genButtons_0003(p);
  }
  @Override
  public void from(StateEntity0002 in) {
    p.camStrokeWeight=()->p.cam2d.pixelPerfect==CameraController2D.SMOOTH?p.cam2d.scale.pos:p.u/16*p.cam2d.scale.pos;
    p.cam2d.pixelPerfect=CameraController2D.SMOOTH;
    p.cam2d.scale.des=2;
    p.cam2d.point.des.y=40;
    for(TextEditor<?> i:textEditors) {
      p.centerCam.add.add(i);
      i.addTo(p.camStage);
    }
    for(TextButton<?> i:buttons) p.centerScreen.add.add(i);
    for(TextButtonCam<?> i:camButtons) p.centerCam.add.add(i);
  }
  @Override
  public void to(StateEntity0002 in) {
    p.camStrokeWeight=()->p.u/16*p.cam2d.scale.pos;
    p.cam2d.pixelPerfect=CameraController2D.NONE;
    for(TextEditor<?> i:textEditors) {
      p.centerCam.remove.add(i);
      i.removeFrom(p.camStage);
    }
    for(TextButton<?> i:buttons) p.centerScreen.remove.add(i);
    for(TextButtonCam<?> i:camButtons) p.centerCam.remove.add(i);
  }

  public static TextEditor<?>[] genUi_0002(Duel p) {
    TextEditor<?>[] out=new TextEditor[] {new TextEditor<>(p,p.theme().stroke,-160,-160,320,480) {
      @Override
      public void display() {
        super.display();
        p.textColor(p.theme().text);
        p.text("皮肤设置",rect.x(),rect.y()-20);
      }

      @Override
      public void keyboardHidden(TextField in) {
        if(in==textArea) try {
          p.config.customThemeData.data=Duel.localization.yaml.load(textArea.getText());
          p.theme(ThemeData.fromData(p.config.customThemeData));
        }catch(RuntimeException e) {
          textArea.setText(getSkinText(p));
        }
      }
    },new TextEditor<>(p,p.theme().stroke,180,-160,320,60) {
      @Override
      public void keyboardHidden(TextField in) {
        if(in==textArea) p.config.data.server=Duel.localization.yaml.loadAs(in.getText(),ServerAttr.class);
      }
    }};
    out[0].textArea.setText(getSkinText(p));
    out[0].textArea.setMessageText("皮肤设置");
    out[1].textArea.setText(getServerAttrText(p));
    out[1].textArea.setMessageText("联机设置");
    return p.debug?out:new TextEditor[] {out[0]};
  }

  public static TextButtonCam<?>[] genButtons_0003(Duel p) {
    return new TextButtonCam[] {
      new TextButtonCam<>(p,self->self.text="重启游戏")
        .allTextButtonEvent(self-> {},self-> {},self-> {
          MainApp.instance.restartScreen();
        })
        .rectAuto(()->-40,()->-250),
      new TextButtonCam<>(p,self->self.text="模式："+p.config.data.gameMode.toString(),()->p.debug,true)
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.config.data.gameMode=p.config.data.gameMode.next();
          self.updateText();
        })
        .rectAuto(()->-40,()->-230),
      new TextButtonCam<>(p,self->self.text="显示方向："+(p.config.data.orientation==MobileUtil.landscape?"横向":"竖向"))
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.config.data.orientation=(p.config.data.orientation+1)%2;
          if(p.isAndroid) {
            if(p.config.data.orientation==1) p.stateCenter.game.actrl.activeCondition=AndroidCtrlBase.portraitCondition;
            else p.stateCenter.game.actrl.activeCondition=AndroidCtrlBase.landscapeCondition;
            Pama.mobile.orientation(p.config.data.orientation);
          }
          self.updateText();
        })
        .rectAuto(()->-40,()->-210),
      new TextButtonCam<>(p,self->self.text="主题："+p.config.data.themeType.toString())
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.config.data.themeType=p.config.data.nextTheme(p.config.data.themeType);
          p.config.updateThemeFromType(p.config.data.themeType);
          self.updateText();
        })
        .rectAuto(()->-40,()->-190),
      // 音量控制条
      new Slider<>(p,true,()->true,self-> {
        p.config.data.volume=self.pos;
        self.updateText();
      },self-> {},self-> {},
        self->self.text="音量："+String.format("%6.2f",p.config.data.volume*100),()->18,()->-40,()->-270,1)
          .pos(p.config.data.volume),
      new TextButtonCam<>(p,self->self.text="FPS修复：（需重启游戏）"+(p.config.data.fpsFix?"是":"否"))
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.config.data.fpsFix=!p.config.data.fpsFix;
          self.updateText();
        })
        .rectAuto(()->-40,()->-290),
    };
  }
}
