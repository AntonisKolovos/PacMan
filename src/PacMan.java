import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class PacMan extends JFrame implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final int numberOfLevels=2;
	float ghostValue;
	boolean  isRunning;
	boolean scared;
	double scaredTime;
	int scr;
	int highScr;
	double cookiesEaten;
	long lastTime = System.nanoTime();
	double deltaPlayer = 0;
	double deltaGhost =0;
	long timer = System.currentTimeMillis();
	int updatesPlayer = 0;
	int updatesGhost = 0;
	int frames = 0;
	String level[]={"level1.txt","level2.txt"};
	int levelNo;
	GameCanvas canvas;
	JLabel score = new JLabel("Score :"+scr);
	JLabel highScore =new JLabel("High Score :"+highScr);
	JPanel scorePanel =new JPanel();
	JPanel bottomPanel=new JPanel();
	JLabel life1 = new JLabel();
	JLabel life2 = new JLabel();
	JLabel life3 = new JLabel();
	JButton Start = new JButton("Start");
	BufferedImage lifeImage=null;
	ReadFile readFile;
	int lives;
	int delta;
	public PacMan(){
		ClassLoader classLoader = getClass().getClassLoader();
		try{
		    lifeImage = ImageIO.read(classLoader.getResourceAsStream("images/PMright1.gif"));
		  }
		  catch (IOException e1) {
		   e1.printStackTrace();
		  
		  }
		readFile=new ReadFile();
		
		ImageIcon img = new ImageIcon(lifeImage);
		
		life1.setIcon(img);
		life2.setIcon(img);
		life3.setIcon(img);
		this.initializeAllValues();
		this.canvas=new GameCanvas(level[levelNo]);
		this.setTitle("Medialab-PacMan");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.BLACK);
		this.setBackground(Color.BLACK);
		bottomPanel.setBackground(Color.BLACK);
	
		score.setForeground(Color.WHITE);
		highScore.setForeground(Color.WHITE);
		scorePanel.add(score);
		scorePanel.add(highScore);
		scorePanel.setBackground(Color.BLACK);
		bottomPanel.add(life1);
		bottomPanel.add(life2);
		bottomPanel.add(life3);
		bottomPanel.add(Start);
		this.add(scorePanel,BorderLayout.NORTH);
		this.add(canvas,BorderLayout.CENTER);
		this.add(bottomPanel,BorderLayout.SOUTH);
		this.setSize(600, 700);
		addKeyListener(this);
		this.createMenus();
		this.requestFocus();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setFocusable(true);
		HandlerClass handler = new HandlerClass();
		Start.addActionListener(handler);
	}
	class HandlerClass implements ActionListener{
	      
		  @Override
		  public void actionPerformed(ActionEvent Event) {
		    //TODO Auto-generated method stub
			isRunning=true;
		   Start.setEnabled(false);
		   
		  }
		     }
	
private void createMenus(){
	final JMenuBar menuBar = new JMenuBar();

    //create menus
    JMenu gameMenu = new JMenu("Game");
   
    //create menu items
    JMenuItem startMenuItem = new JMenuItem("Start");
    startMenuItem.setMnemonic(KeyEvent.VK_N);
    startMenuItem.setActionCommand("Start");
 

    JMenuItem loadMenuItem = new JMenuItem("Load");
    loadMenuItem.setActionCommand("Load");

    JMenuItem highMenuItem = new JMenuItem("High Scores");
    highMenuItem.setActionCommand("High Scores");

    JMenuItem exitMenuItem = new JMenuItem("Exit");
    exitMenuItem.setActionCommand("Exit");


    MenuItemListener menuItemListener = new MenuItemListener();

    startMenuItem.addActionListener(menuItemListener);
    loadMenuItem.addActionListener(menuItemListener);
    highMenuItem.addActionListener(menuItemListener);
    exitMenuItem.addActionListener(menuItemListener);
    gameMenu.add(startMenuItem);
    gameMenu.add(loadMenuItem);
    gameMenu.add(highMenuItem);   
    gameMenu.addSeparator();
    gameMenu.add(exitMenuItem);        
    menuBar.add(gameMenu);
    this.setJMenuBar(menuBar);
    this.setVisible(true);     
 }
 class MenuItemListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {            
    	// System.out.println(e.getActionCommand() + " JMenuItem clicked.");
    	 switch(e.getActionCommand()){
    	 case ("Start"):
    		 System.out.println("Start");
    		 if(!isRunning){
    			 isRunning=true;
    			 System.out.println(isRunning);
    		 }
    		 else{
    			 isRunning=false;
    			 System.out.println(isRunning);
    		 }
    	 	break;
    	 case ("Load"):
    		 System.out.println("Load");
    	 	break;
    	 case ("High Scores"):
    		 JOptionPane.showMessageDialog(null,readFile.print());
    	 	break;
    	 case ("Exit"):
    		 System.exit(0);
    	 	break;
    	 }
    }    
 }


private void removeLife(){	
	switch(this.lives){
	case (3):
		bottomPanel.remove(life3);
		revalidate();
		repaint();
		break;
	
	case (2):
		bottomPanel.remove(life2);
		revalidate();
		repaint();
		break;
	case(1):
		bottomPanel.remove(life1);
		revalidate();
		repaint();
		lost();
		break;
	}
	this.canvas.player.reset();
	for (int i=0;i<=3;i++){
		this.canvas.ghost[i].reset();
	}
	lives--;
}
private void initializeValues(){
	 this.lastTime = System.nanoTime();
	 this.deltaPlayer = 0;
	 this.deltaGhost =0;
	 this.timer = System.currentTimeMillis();
	 this.updatesPlayer = 0;
	 this.updatesGhost = 0;
	 this.frames = 0;
	 this.ghostValue=200;
	 this.isRunning=false;
	 this.scared=false;
	 this.cookiesEaten=0;
	 this.canvas.player.speed=5.0;
	 Ghost.speed=5;
	 Ghost.currentSpeed=5;
	 Start.setEnabled(true);
}
private void initializeAllValues(){
	 levelNo=0;
	 lives=3;
	 this.lastTime = System.nanoTime();
	 this.deltaPlayer = 0;
	 this.deltaGhost =0;
	 this.timer = System.currentTimeMillis();
	 this.updatesPlayer = 0;
	 this.updatesGhost = 0;
	 this.frames = 0;
	this.ghostValue=200;
	this.isRunning=false;
		this.scared=false;
		this.scr=0;
		this.highScr=Integer.parseInt(readFile.userInput[0][1]);
		this.cookiesEaten=0;
		
		Ghost.speed=5;
		Ghost.currentSpeed=5;
	}
	public void updateScore(){
		this.score.setText("Score :"+scr);
		this.highScore.setText("High Score :"+highScr);
	}
	
	public static void main(String[] args){
		
		PacMan game =new PacMan();
		while(!game.isRunning){
			System.out.println("Waiting...");
			if(game.isRunning){
				game.gameloop();
			}
		}
		
		
	}
		

	public void gameloop(){
		this.lastTime = System.nanoTime();
		while(this.isRunning){
			double nsPacMan = 1000000000 / this.canvas.player.speed;
			double nsGhost  =1000000000 / Ghost.currentSpeed;
			long now = System.nanoTime();
			this.deltaPlayer += (now - this.lastTime) / nsPacMan;
			this.deltaGhost  +=(now - this.lastTime) / nsGhost;
			this.lastTime = now;
			while(this.deltaPlayer >= 1){
				this.canvas.player.pacManTick(this);
				
				this.updatesPlayer++;
				this.deltaPlayer--;
				this.render();
				this.checkCollissions();
			}
			while(this.deltaGhost>= 1){
				for(int k=0;k<=3;k++){
					this.canvas.ghost[k].tick(this.canvas.player, this.canvas);
				}
				this.render();
				this.checkCollissions();
				this.deltaGhost--;
				this.updatesGhost++;
			}
			
			
			//game.render();
			this.frames++;
			if(this.scared){
				if(System.currentTimeMillis() - this.scaredTime>7000){
					this.scared=false;
					this.scared(false);
				}
			}
			if(System.currentTimeMillis() - this.timer > 1000){
				this.timer += 1000;
				System.out.println(" PacmanTicks: " + this.updatesPlayer);
				System.out.println("Ghost Ticks: "+this.updatesGhost);
				this.frames = 0;
				this.updatesPlayer = 0;
				this.updatesGhost=0;
			}
			
		}
		
	}

	public void checkCollissions(){
		if(this.isRunning){
			
			for(int k=0;k<=3;k++){
				if(canvas.player.x ==canvas.ghost[k].x && canvas.player.y==canvas.ghost[k].y){
					if(!scared){
						removeLife();
					}else{
						scr+=ghostValue;
						if(ghostValue<1600){
							ghostValue=ghostValue*2;
						}
						canvas.ghost[k].reset();
					}
						
				}
			}
			
			
		}
	}
	public void scared(boolean flag){
		if(flag){
			if(!this.scared){
				this.scared=true;
				Ghost.scared(true);
				this.canvas.player.scared(true);
			}
			this.scaredTime=System.currentTimeMillis();
		}else{
			this.scared=false;
			Ghost.scared(false);
			this.canvas.player.scared(false);
		}
	}
	
	public void won(){
		if (levelNo<numberOfLevels-1){
			levelNo++;
			initializeValues();
			//this.isRunning=true;
			this.remove(canvas);
			this.canvas=new GameCanvas(level[levelNo]);
			this.add(canvas,BorderLayout.CENTER);
		}
		else{
			this.readFile.checkScore(scr);
		
			Object[] options = {"Retry","Exit"};
			int n = JOptionPane.showOptionDialog(null,
			            "You won, what would you like to do?",
			            "YOU WON!",
			            JOptionPane.YES_NO_CANCEL_OPTION,
			            JOptionPane.DEFAULT_OPTION,
			            null,
			            options,
			            options[1]);  

			System.out.println(n);  
			if(n==0){  
				this.restart();
			}else if(n==1){
			    System.exit(0);
			}else{
			    System.out.println("no option choosen");
			}
		}
	}
	public void lost(){
			this.isRunning=false;
			//JOptionPane.showMessageDialog(null, "You Lost!");
			Object[] options = {"Retry","Exit"};
			int n = JOptionPane.showOptionDialog(null,
			            "You lost, what would you like to do?",
			            "GAME OVER",
			            JOptionPane.YES_NO_CANCEL_OPTION,
			            JOptionPane.DEFAULT_OPTION,
			            null,
			            options,
			            options[1]);  

			System.out.println(n);  
			if(n==0){  
				this.restart();
			}else if(n==1){
			    System.exit(0);
			}else{
			    System.out.println("no option choosen");
			}
		
		
	}
	public void restart(){
		this.initializeAllValues();
		this.isRunning=true;
		this.remove(canvas);
		this.canvas=new GameCanvas(level[levelNo]);
		this.add(canvas,BorderLayout.CENTER);
	}
	
	
	public void render(){
		this.canvas.repaint();
	}
	@Override
	public void keyPressed(KeyEvent e) {
	    int keyCode = e.getKeyCode();
	    if(this.isRunning){
	    	switch( keyCode ) { 
	        case KeyEvent.VK_UP:
	        	canvas.player.newDirection='U';
	            break;
	        case KeyEvent.VK_DOWN:
	        	canvas.player.newDirection='D'; 
	            break;
	        case KeyEvent.VK_LEFT:
	        	canvas.player.newDirection='L';
	            break;
	        case KeyEvent.VK_RIGHT :
	        	canvas.player.newDirection='R';
	            break;
	     }
	    }
	    
	} 
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	
	
	
	
}



