package pers.wxp.applet;

import java.applet.Applet;
import java.awt.Graphics;

/**
* @author wuxiaopeng 
* @Description: TODO(������һ�仰��������������)  
* @date 2017��7��4�� ����8:49:02 
* 
*/
public class HelloWorldApplet extends Applet
{
   /**
	* @Description: TODO(������һ�仰��������������)  
	* @date 2017��7��4�� ����8:49:56 
	* 
	*/
	private static final long serialVersionUID = 1L;

public void paint (Graphics g)
   {
      g.drawString ("Hello World", 25, 50);
   }
}
