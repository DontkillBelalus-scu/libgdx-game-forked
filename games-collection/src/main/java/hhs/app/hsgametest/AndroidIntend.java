package hhs.app.hsgametest;

public interface AndroidIntend{
  public void toast(String str,int time);
  public void toast(String str);
  public void dialog(String title,String h1,String h2,gData ok,Runnable cancel);
  public void dialog(String title,String h1,gData ok,Runnable cancel);
  public static interface gData{
    public void whenStart(String str,String str2);
  }
}
