import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
	int x;
	int y;
	int xDefault;
	int yDefault;
	double speed =5 ;//tick rate
	double speedTime;
	boolean isFast= false;		//fast mode otan faie 60% twn cookies
	boolean speedBoost= false;  //7sec speed boost
	 char newDirection;			//h zhtoumenh kateuthinsh kinhshs
	 char direction;			//h trexousa kateuthunsh kinhshs
	 BufferedImage image =null;
	private BufferedImage imageU =null;
	private BufferedImage imageD =null;
	private BufferedImage imageL =null;
	private BufferedImage imageR =null;
	
	public Player(int x, int y){
		this.x=x;
		this.y=y;
		this.xDefault=x;
		this.yDefault=y;
		ClassLoader classLoader = getClass().getClassLoader();
		try{
			 image = ImageIO.read(classLoader.getResourceAsStream("images/PM0.gif"));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		
		}
		try{
			 imageU =  ImageIO.read(classLoader.getResourceAsStream("images/PMup1.gif"));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		
		}
		try{
			 imageD =  ImageIO.read(classLoader.getResourceAsStream("images/PMdown1.gif"));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		
		}
		try{
			 imageL =  ImageIO.read(classLoader.getResourceAsStream("images/PMleft1.gif"));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		
		}
		try{
			 imageR =  ImageIO.read(classLoader.getResourceAsStream("images/PMright1.gif"));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		
		}
	}

	private void move(int delta,char direction){
		switch(direction){
		case 'U':
			this.y-=delta;
			this.image=this.imageU;
			break;
		case 'D':
			this.y+=delta;
			this.image=this.imageD;
			break;
		case 'L':
			this.x-=delta;
			this.image=this.imageL;
			break;
		case 'R':
			this.x+=delta;
			this.image=this.imageR;
			break;
			
		}
	}
	
	public void pacManTick(PacMan game){
		this.movePacman( game.canvas);
		this.checkCookies(game);
		game.updateScore();
	}
	
	private void movePacman(GameCanvas canvas){				//elenxei an borei o pacman na kinithei sth nea kateuthinsh kai ton kinei
		this.move(1, this.newDirection);
		if(canvas.getChar(this.x, this.y)=='#'||canvas.getChar(this.x, this.y)=='_'){	//an sunanthsei toixo
			this.move(-1, this.newDirection);			// kanw pisw
			this.move(1, this.direction);				//proxwraw sth palia kateuthinsh
			if(canvas.getChar(this.x, this.y)=='#'||canvas.getChar(this.x, this.y)=='_'){
				this.move(-1, this.direction);			//an sunanthsw pali toixo stamataw
			}
		}
		else{
			this.direction=this.newDirection;			//an den sunanthsei toixo vazw thn nea kateuthinsh
		}
	}
	
	private void checkCookies(PacMan game){
		char c = game.canvas.getChar(this.x, this.y);
		if(c=='.' && game.canvas.valid[this.y][this.x]){
			game.canvas.clear(this.x,this.y);
			game.cookiesEaten++;
			if (game.cookiesEaten>game.canvas.totalCookies*0.6 && this.isFast==false){
				System.out.println("Speed increase!");
				this.speed=this.speed*1.3;
				Ghost.currentSpeed=Ghost.currentSpeed*1.3;
				Ghost.speed=Ghost.speed*1.3;
				
				this.isFast= true;
			}
			if(game.cookiesEaten==game.canvas.totalCookies){
				game.isRunning=false;
				game.won();
			}
			game.scr+=10;
		}
		else if(c=='O' && game.canvas.valid[this.y][this.x]){
			game.canvas.clear(this.x,this.y);
			game.scared(true);
			game.scr+=50;
		}	
	
	}
	
	public void reset(){
		this.x=this.xDefault;
		this.y=this.yDefault;
	}
	public void scared(boolean flag){
		if(flag){
			this.speed= this.speed*1.2;
		}else{
			this.speed=this.speed/1.2;
		}
	}

}
