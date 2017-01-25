import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class PacMan extends JPanel {
	ArrayList<String> rows = new ArrayList<String>();
	int pacManY;
	int pacManX;
	int scr=0;
	int highScr=1000;
	int height;
	int width;
	BufferedImage pacman =null;
	
	
	public void paintComponent(Graphics g){
		
		for(int y=0;y<height;y++){
			for(int x=0;x<width;x++){
				if(rows.get(y).charAt(x)=='#'){
					g.setColor(Color.ORANGE);
					g.fillRect(x*24+55, y*24+55, 24, 24);
					
				}
				if(rows.get(y).charAt(x)=='.'){
					g.setColor(Color.WHITE);
					g.fillOval(x*24+55+8, y*24+55+6, 8, 8);
					
				}
				if(rows.get(y).charAt(x)=='O'){
					g.setColor(Color.WHITE);
					g.fillOval(x*24+55+4, y*24+55+4, 16, 16);
					
				}
			}
		}
		g.drawImage(pacman,pacManX*24+55, pacManY*24+55, null);
	}
	public PacMan(){
		
		try {
			Scanner s = new Scanner(new File("level1.txt"));
			int i=0;
			while(s.hasNextLine()){
				String line= s.nextLine();
				rows.add(line);
				if(line.contains("P")){
					pacManX=line.indexOf("P");
					pacManY= i;
					
				}
				i++;
				
			}
			s.close();
			height= rows.size();
			width= rows.get(0).length();
		}
		catch(FileNotFoundException e1){
			e1.printStackTrace();
		}
		try{
			 pacman = ImageIO.read(new File("PM0.gif"));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		JFrame frame = new JFrame() ;
		frame.setTitle("Medialab-PacMan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 700);
		frame.setLocationRelativeTo(null);
		JLabel score = new JLabel("Score :"+scr);
		JLabel highScore =new JLabel("High Score:"+highScr);
		score.setForeground(Color.WHITE);
		highScore.setForeground(Color.WHITE);
		this.add(score);
		this.add(highScore);
		frame.add(this);
		frame.setBackground(Color.BLACK);
		frame.setVisible(true);
		
		
	}
	
	public static void main(String[] args){
		boolean isRunning=true;
		PacMan game =new PacMan();
		Thread gameLoop= new Thread(){
			public void run(){
				System.out.println("Thread Starting...");
				int frame=0;
				while(isRunning){
					System.out.println("Frame="+frame);
					System.out.println("Coor"+game.pacManX);
					game.pacManX=game.pacManX+ 24;
					//game.pacManY=+24;
					game.highScr++;
					game.repaint();
					frame++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					
				}
				
			}
		};
		gameLoop.start(); 
	
		
	}
}



