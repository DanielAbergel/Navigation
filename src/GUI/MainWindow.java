package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Maps.Map;
import Maps.Pixel;


public class MainWindow extends JFrame implements MouseListener
{
	public Map GameMap ; 
	ArrayList<Pixel> Circles ; 
	public BufferedImage myLATERimage;
	public LinesComponent lines = new LinesComponent(GameMap.myImage);

	public MainWindow() 
	{

		initGUI();		
		this.addMouseListener(this); 

		this.addComponentListener(new ComponentAdapter() 
		{
			public void componentResized(ComponentEvent componentEvent)
			{


				scaledImage(myLATERimage,componentEvent.getComponent().getWidth(), componentEvent.getComponent().getHeight());


			}
		});

	}

	private void initGUI() 
	{
//		MenuBar menuBar = new MenuBar();
//		Menu menu = new Menu("Menu"); 
//		MenuItem item1 = new MenuItem("menu item 1");
//		MenuItem item2 = new MenuItem("menu item 2");
//
//		menuBar.add(menu);
//		menu.add(item1);
//		menu.add(item2);
//		this.setMenuBar(menuBar);
		GameMap = new Map();
	    Circles = new ArrayList<Pixel>();

		try {
			GameMap.myImage = ImageIO.read(new File("Ariel1.PNG"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			myLATERimage = ImageIO.read(new File("Ariel1.PNG"));
		} catch (IOException e) {
			e.printStackTrace();
		}



	}
	private void scaledImage(Image img,int w, int h)
	{
		BufferedImage resizedImage=new BufferedImage(w,h ,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2=resizedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(img, 0, 0, w, h, null);
		g2.dispose();

		GameMap.myImage=resizedImage;
		GameMap.ChangeFrameSize(new Pixel(GameMap.myImage.getWidth(), GameMap.myImage.getHeight()), Circles);
		this.repaint();
		
	}

	int x = -1;
	int y = -1;

	public void paint(Graphics g)
	{
		g.drawImage(GameMap.myImage, 0, 0, this);
	
			if(x!=-1 && y!=-1)
			{
				
				for (int i = 0; i < Circles.size(); i++) {
					g.fillOval((int)Circles.get(i).get_PixelX(), (int)Circles.get(i).get_PixelY(), 10	, 10);
				}
			
			}
	
		
	}
	public double getXsize()
	{
		return GameMap.myImage.getWidth();
	}
	
	public double getYsize()
	{
		return GameMap.myImage.getHeight();
	}

	@Override
	public void mouseClicked(MouseEvent arg) {
		System.out.println("mouse Clicked");
		System.out.println("("+ arg.getX() + "," + arg.getY() +")");
		x = arg.getX();
		y = arg.getY();
		Circles.add(new Pixel(x, y));
		repaint();
///////////should be deletd after figure the main purpose
		Color randomColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
		lines.addLine((int)Circles.get(i).get_PixelX(),(int)Circles.get(i).get_PixelY(),(int)Circles.get(i+1).get_PixelX(),(int)Circles.get(i+1).get_PixelY(),randomColor);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		System.out.println("mouse entered");

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
