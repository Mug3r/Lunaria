package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Main.GamePanel;
import TileMap.Tile;
import TileMap.TileMap;

public abstract class MapObject {
	
	
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	//position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	//Dimensions - "visible" dimensions
	protected int width;
	protected int height;
	
	// collision box - "real" dimensions
	protected int cwidth;
	protected int cheight;
	
	//collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;	
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	//animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	//movement
	public boolean left;
	public boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	// movement attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	//constructor sets which tilemap to use
	public MapObject(TileMap tm){
		tileMap = tm;
		tileSize = tm.getTileSize();
	}
	//Collision detection checks if rectangle hitboxes are intersecting
	public boolean intersects(MapObject o){
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	//returns the hitbox of the specific mapobject calling the method
	public Rectangle getRectangle(){
		return new Rectangle((int)x - cwidth, (int)y - cheight, cwidth, cheight);
	}
	//Breaks each hitbox into a smaller collection of 4 hitboxes each 1 tile large and uses these for collisions of larger composite objects(player, and sprites of enemies)
	public void calculateCorners(double x, double y){
		
		 int leftTile = (int)(x - cwidth / 2) / tileSize;
	     int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
	     int topTile = (int)(y - cheight / 2) / tileSize;
	     int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
	     if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
	    		 leftTile < 0 || rightTile >= tileMap.getNumCols()) {
	             topLeft = topRight = bottomLeft = bottomRight = false;
	             return;
	        }
	        int tl = tileMap.getType(topTile, leftTile);
	        int tr = tileMap.getType(topTile, rightTile);
	        int bl = tileMap.getType(bottomTile, leftTile);
	        int br = tileMap.getType(bottomTile, rightTile);
	        topLeft = tl == Tile.BLOCKED;
	        topRight = tr == Tile.BLOCKED;
	        bottomLeft = bl == Tile.BLOCKED;
	        bottomRight = br == Tile.BLOCKED;
		
		
		
	}
	//Collision detection with the map and and tiles
	public void checkTileMapCollision(){
		
		currCol = (int) x / tileSize;
		currRow = (int) y / tileSize;
		
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		calculateCorners(x, ydest);
		if(dy < 0){
			if(topLeft || topRight){
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			}
			else{
				ytemp += dy;
				}
			}
			
		
		if(dy > 0){
			if(bottomLeft || bottomRight){
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			}
			else{
				ytemp += dy;
			}
		
		}
		
		calculateCorners(xdest, y);
		if(dx < 0){
			if(topLeft || bottomLeft){
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			}
			else{
				xtemp += dx;
			}
		}
		if(dx > 0){
			if(topRight || bottomRight){
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else{
				xtemp += dx;
			}
		}
		
		if(!falling){
			calculateCorners(x, ydest + 1);
			if(!bottomLeft && !bottomRight){
				falling = true;
				}
		}
	}
	//Returns the x position(right edge of the collsion box)
	public int getx(){return (int) x;}
	//Returns the y position(bottom edge of the collison box)
	public int gety(){return (int) y;}
	//Returns the width of the Actual object
	public int getWidth(){return width;}
	//Returns the height of the Actual object
	public int getHeight(){return height;}
	//Returns the width of the collision box
	public int getCWidth(){return cwidth;}
	//returns the height of the collision box
	public int getCHeight(){return cheight;}
	//Sets the position of the collision box
	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
	}
	//Sets the vector(direction) of the collision box(decides the rate and direction of its movement)
	public void setVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	//Sets the position of the map
	public void setMapPosition(){
		xmap = tileMap.getX();
		ymap = tileMap.getY();
	}
	//Sets if moving left
	public void setLeft(boolean b){left = b;}
	//Sets if moving right
	public void setRight(boolean b){right = b;}
	//Sets if moving up
	public void setUp(boolean b){up = b;}
	//Sets if moving down
	public void setDown(boolean b){down = b;}
	//Sets if moving jumping
	public void setJumping(boolean b){jumping = b;}
	//Used to detect if the object is off the screen/game window
	public boolean offScreen(){
		return x + xmap + width < 0 || x + xmap - width > GamePanel.WIDTH || y + ymap + height < 0 || y + ymap - height > GamePanel.HEIGHT;
	}
	//Draws the map object
	public void draw(Graphics2D g){
		if(facingRight){
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int) (y + ymap - height / 2), null);
		}
		else{
			g.drawImage(animation.getImage(), (int)(x + xmap + width / 2), (int)(y + ymap - height / 2), -width, height, null);
		}
	}
}
