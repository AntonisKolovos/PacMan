import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

public class Ghost {
	int x;
	int y;
	private int x2;
	private int y2;
	private int xDefault;
	private int yDefault;
	public static double speed=5.0;
	static double currentSpeed ;
	static public BufferedImage image =null;
	private boolean  isOut=false;
	static private BufferedImage imageScared =null;
	static private BufferedImage imageNormal =null;
	static boolean isScared=false;
	static private boolean firstTime=false;
	int direction=2 ;
	private int validDir[] = new int[4];
	GameCanvas canvas;
	Player player;
	public Ghost(int x, int y){
		this.x=x;
		this.y=y;
		this.xDefault=x;
		this.yDefault=y;
		currentSpeed=speed;
		ClassLoader classLoader = getClass().getClassLoader();
		try{
			 imageNormal = ImageIO.read(classLoader.getResourceAsStream("images/Ghost1.gif"));
		}
		catch (IOException e1) {
			e1.printStackTrace();
			
		}
		try{
			 imageScared =  ImageIO.read(classLoader.getResourceAsStream("images/GhostScared1.gif"));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		
		}
		image=imageNormal;
	}
public void tick(Player player, GameCanvas canvas){
	if(isScared&&firstTime){
		this.direction=reverse(this.direction);
		firstTime=false;
	}
	else{
		boolean found=this.findPacman(canvas, player);
		if(!found){
			this.chooseDirection(canvas,player);
		}else if(isScared){
			this.testMove(1, reverse(this.direction), this.x, this.y);
			if((canvas.getChar(this.x2, this.y2)=='#')||(canvas.getChar(this.x2, this.y2)=='_'&&this.isOut)){
				this.chooseDirection(canvas, player);
			}else{
				this.direction=this.reverse(this.direction);
			}
		}
	}
	this.move(1, this.direction);
if(canvas.getChar(this.x, this.y)=='_'){//an molis vghkame eksw
	this.isOut=true;
}
	
}
public void reset(){
	isScared=false;
	this.x=this.xDefault;
	this.y=this.yDefault;
	this.isOut=false;
	currentSpeed=speed;
	
}
public static void scared(boolean flag){
	if(flag){
		isScared=true;
		firstTime=true;
		image=imageScared;
		currentSpeed=currentSpeed*0.9;
	}else{
		isScared=false;
		image=imageNormal;
		currentSpeed=currentSpeed/0.9;
	}
}


private void chooseDirection( GameCanvas canvas,Player player){ //epilegei thn epomenh kateuthinsi pou tha kinithei
		//an den vrethei o Pacman se 3 blocks epilegw tuxaia
		int i=checkDirections(canvas);
		if(i==0){		//an den yparxei dunath epilogh
			this.direction=reverse(this.direction);
		}
		else{
			int random = ThreadLocalRandom.current().nextInt(1, i + 1);//tuxaios arithmos apo to 1 ews to plithos twn dunatwn kateuthinsewn
			this.direction=validDir[random-1];
		}
		
	
	
	
}
	
private void move(int delta,int direction){
	switch(direction){
	case 0:
		this.y-=delta;
		break;
	case 1:
		this.x+=delta;
		break;
	case 2:
		this.y+=delta;
		break;
	case 3:
		this.x-=delta;
		break;
	}
	
}
private void testMove(int delta,int direction,int x,int y){
	switch(direction){
	case 0:
		this.y2=y-delta;
		break;
	case 1:
		this.x2=x+delta;
		break;
	case 2:
		this.y2=y+delta;
		break;
	case 3:
		this.x2=x-delta;
		break;
	}
}
private boolean findPacman(GameCanvas canvas, Player player){
	for(int i=0;i<=3;i++){
		this.x2=this.x;
		this.y2=this.y;
		for(int j=1;j<=3;j++){
			testMove(1,i,this.x2,this.y2);
			if(canvas.getChar(this.x2, this.y2)=='#'){
				break;
			}else if((this.x2==player.x)&&(this.y2==player.y)){
				this.direction=i;
				return true;
			}
		}
	}
	return false;
}

private int reverse(int direction){
	if (direction>=2){							//vriskw poia einai i antitheth apo thn kateuthinsh pou dinetai
		return direction-2;
	}else{
		return direction+2;
	}
}

private int checkDirections(GameCanvas canvas){		//vriskei tis egures kateuthinseis pou borei na paei kai epistrefei to plithos tous
	
	int sum=0;
	
	
	for(int i=0;i<=3;i++){//gia kathe kateuthunsi
		if(i==reverse(this.direction)){
			continue;
		}
		move(1,i);								//paw ena brosta
		if((canvas.getChar(this.x,this.y)!='#')&&(canvas.getChar(this.x, this.y)!='_'||!this.isOut)){ //an den sunanthsw toixo 
			this.validDir[sum]=i;
			sum++;
		}
		move(-1,i);						//epistrefw pisw

		}
	return sum;
}


}
	
