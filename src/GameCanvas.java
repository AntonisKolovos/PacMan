import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import java.io.InputStream;

public class GameCanvas extends JComponent{
	ArrayList<String> rows = new ArrayList<String>();
	int pacManY;
	int pacManX;
	int height;
	int width;
	int ghosts=0;
	double totalCookies=0;
	Player player;
	Ghost ghost[]=new Ghost[4];
	 boolean[][] valid=new boolean[100][100];	//se auton ton pinaka shmeiwnetai an ena item(cookie i kuklos) exei fagwthei wste na mhn emfanizetai
	BufferedImage livesImage= null;
	public char getChar(int x, int y){
		return rows.get(y).charAt(x);
	}
	public void clear(int x, int y){
		valid[y][x]=false;
	}
	
	public GameCanvas(String fileName){
		for (boolean[] row: valid)
		    Arrays.fill(row, Boolean.TRUE);

		InputStream is = getClass().getResourceAsStream("levels/"+fileName);
		  InputStreamReader isr = new InputStreamReader(is);
		  BufferedReader br = new BufferedReader(isr);
		  String line;
		
		 
		
		
	try{
		int i=0;
		  while ((line = br.readLine()) != null) {
			rows.add(line);
			if(line.contains("P")){
				this.player=new Player(line.indexOf("P"),i);
			}
			if(line.contains("F")){
				
				for(int k=0;k<=line.length()-1;k++){
					if(line.charAt(k)=='F'){
						this.ghost[ghosts]=new Ghost(k,i);
						ghosts++;
					}
				}
				
				
			}
			i++;
			
		}
		  br.close();
		  isr.close();
		  is.close();
	}
	catch (IOException e){
		e.printStackTrace();
	}
		height= rows.size();
		width= rows.get(0).length();
		
		for(int y=0;y<height;y++){				//metrame ta sunolika cookies
			for(int x=0;x<width;x++){
				if(getChar(x,y)=='.'){
					this.totalCookies++;
				}
			}
		}
	}


	public void paintComponent(Graphics g){
		
		for(int y=0;y<height;y++){
			for(int x=0;x<width;x++){
				if(getChar(x,y)=='#'){
					g.setColor(Color.ORANGE);
					g.fillRect(x*24+55, y*24+55, 24, 24);
					
				}
				if(getChar(x,y)=='.' && valid[y][x]){	
					g.setColor(Color.WHITE);
					g.fillOval(x*24+55+8, y*24+55+6, 8, 8);
					
				}
				if(getChar(x,y)=='O' && valid[y][x]){
					g.setColor(Color.WHITE);
					g.fillOval(x*24+55+4, y*24+55+4, 16, 16);
					
				}
			}
		}
		g.drawImage(player.image,player.x*24+55, player.y*24+55, null);
		for(int k=0;k<=ghosts-1;k++){
			g.drawImage(Ghost.image,ghost[k].x*24+55, ghost[k].y*24+55, null);
		}
		
		
		
	
}
}
