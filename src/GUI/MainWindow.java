package GUI;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.plaf.FileChooserUI;


import Algorithms.ShortPathAlgorithm;
import File_format.CSV2Game;
import File_format.Game2CSV;
import File_format.Game2KML;
import Game_objects.Fruit;
import Game_objects.Game;
import Game_objects.Packman;
import Game_objects.Packmans;
import Game_objects.Path;
import Maps.Map;
import Maps.Pixel;
import Threads.TestThreads;

/**
 * 
 *@author Netanel Ben-Isahar
 *@author daniel abargel
 *
 *this class is for the gui interface and include all of the graphic items inside.
 */
public class MainWindow extends JFrame implements MouseListener
{



	Game game ; 
	boolean PacOrFruit  ;  // false = packman , true = fruit

	int x = -1;
	int y = -1;
	public BufferedImage PackManImage;
	public BufferedImage FruitImage;
	ArrayList<Path> PackmansPath;
	MyThread thread ; 

	public MainWindow() 
	{
		initGUI();		
		this.addMouseListener(this); 
	}


	private void InitMenu() {

		MenuBar  menubar = new MenuBar ();
		Menu file = new Menu("File"); 
		Menu gameM = new Menu("Game");
		MenuItem load = new MenuItem("Load");
		MenuItem save = new MenuItem("Save");
		MenuItem clear = new MenuItem("Clear");
		MenuItem packmanM = new MenuItem("PackMan");
		MenuItem fruitM = new MenuItem("Fruit");
		MenuItem run = new MenuItem("Run");
		MenuItem KML = new MenuItem("KmlRun");
		menubar.add(file);
		menubar.add(gameM);
		file.add(load);
		file.add(save);
		file.add(clear);
		file.add(KML);
		gameM.add(packmanM);
		gameM.add(fruitM);
		gameM.add(run);
		this.setMenuBar(menubar);

		KML.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Game2KML G = new Game2KML(game); 
				game.saving=new Game2CSV(game.packmans,game.fruits);
				game.algo=new ShortPathAlgorithm(game);
				PackmansPath=game.algo.Short(game, game.GameMap);
				try {
					G.Game2KMLfunction();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(thread != null ) thread.stop();
				game.fruits.clear();
				game.packmans.clear();
				PackmansPath.clear();
				String direction="";
				JButton open=new JButton();
				JFileChooser fc =new JFileChooser();
				fc.setDialogTitle("chose your game file");
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if(fc.showOpenDialog(open)==JFileChooser.APPROVE_OPTION)
				{
					direction=fc.getSelectedFile().getAbsolutePath();
					System.out.println(direction);
					direction=direction.replaceAll("\\\\", "\\\\\\\\");
					System.out.println(direction);
					;
				}


				try {
					
					CSV2Game S=new CSV2Game(game.GameMap, game, direction);
					
					

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				repaint();
			}

		});
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				game.saving=new Game2CSV(game.packmans,game.fruits);

			}
		});
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(game.fruits!=null)
					game.fruits.clear();
				if(game.packmans!=null)
					game.packmans.clear();
				if(PackmansPath!=null)
					PackmansPath.clear();
				if(thread != null ) thread.stop();
				repaint();


			}
		});
		packmanM.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PacOrFruit = false;

			}
		});
		fruitM.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PacOrFruit = true;

			}
		});
		run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PackmansPath=new ArrayList<Path>();
				game.saving=new Game2CSV(game.packmans,game.fruits);
				game.algo=new ShortPathAlgorithm(game);
				PackmansPath=game.algo.Short(game, game.GameMap);
				
				move();
				repaint();
			}
		});


	}
	public void move()
	{
		thread = new MyThread();
		thread.start();
	}
	private void initGUI() 
	{	
		InitMenu();
		PackmansPath = new ArrayList<Path>();
		Map GameMap = new Map();
		try {
			FruitImage = ImageIO.read(new File("Fruit.PNG"));
			PackManImage = ImageIO.read(new File("PackMan.PNG"));
			GameMap.myImage = ImageIO.read(new File("Ariel1.PNG"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		game = new Game(GameMap);
	}



	public void paint(Graphics g)
	{

		
		g.drawImage(game.GameMap.myImage, -10, -10,this.getWidth(),this.getHeight(), this);
		game.GameMap.ChangeFrameSizePacman(new Pixel(this.getWidth(), this.getHeight()), game.packmans,game.fruits);
		if(true)
		{

			for (int i = 0; i < game.packmans.size(); i++) 
			{
				
				g.drawImage(PackManImage,(int)game.packmans.get(i).getPixelLocation().get_PixelX()-20,(int)game.packmans.get(i).getPixelLocation().get_PixelY()-10,this);
			}
			for (int i = 0; i < game.fruits.size(); i++) 
			{

				g.drawImage(FruitImage,(int)game.fruits.get(i).getPixelLocation().get_PixelX()-20,(int)game.fruits.get(i).getPixelLocation().get_PixelY()-10,this);
			}



			if(PackmansPath!=null)
			{
				for(int i=0;i<game.packmans.size();++i)
				{
					if(game.packmans.get(i).path!=null)
					{
						for (int j=0;j<game.packmans.get(i).path.Lines.size();++j)
						{
							g.setColor(Color.YELLOW);
							g.drawLine((int)game.packmans.get(i).path.Lines.get(j).start.get_PixelX(),(int)game.packmans.get(i).path.Lines.get(j).start.get_PixelY(),(int)game.packmans.get(i).path.Lines.get(j).end.get_PixelX(),(int)game.packmans.get(i).path.Lines.get(j).end.get_PixelY());
						}
					}
				}
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg) {
		System.out.println("mouse Clicked");
		System.out.println("("+ arg.getX() + "," + arg.getY() +")");
		x = arg.getX();
		y = arg.getY();
		if(!PacOrFruit) 
		{
			game.packmans.add(new Packman(0, new Pixel(x, y), 1, 0, game.GameMap));
		}
		else
		{
			game.fruits.add(new Fruit(0, new Pixel(x, y), 0, game.GameMap));
		}

		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {


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

	public double getXsize()
	{
		return game.GameMap.myImage.getWidth();
	}

	public double getYsize()
	{
		return game.GameMap.myImage.getHeight();
	}

	public class MyThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < 400; i++) {
				for (int j = 0; j < game.packmans.size(); j++) {
					game.packmans.get(j).WhereInTime(i, game.GameMap);
					
				}
				for (int o = 0; o < game.fruits.size(); o++) {
					if(game.fruits.get(o).EatenTime < i) game.fruits.remove(o);
				}
				try {
					this.sleep(400);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				repaint();
			}
			repaint();

		}

	}

}
