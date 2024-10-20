import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;


public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile {
        int x,y;

        Tile(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardHeight;
    int boardWidth;
    int tileSize =25;
    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    //GameLogic

    Timer gameLoop;
    int velocityX, velocityY;
    boolean gameOver = false;

    SnakeGame(int boardHeight, int boardWidth){
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;

        setPreferredSize(new Dimension(this.boardHeight, this.boardWidth));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

   

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        //Gridlines
        // for(int i=0;i<boardWidth/tileSize;i++){
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
        //     g.drawLine(0, i*tileSize, boardWidth, i*tileSize
            
        //     );
        // }
        //Food
        g.setColor(Color.orange);
    
       // g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize,true);

        g.setColor(Color.red); 
        g.drawRect(food.x * tileSize+5, food.y * tileSize+5, tileSize-10, tileSize-10);

        //SnakeHead
        g.setColor(Color.green);
       // g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        g.fill3DRect( snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize,true);
        

        //snakeBody

        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
            //g.fillRect(snakePart.x*tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y * tileSize, tileSize, tileSize,true);
            
        }

        //Score
        g.setFont(new Font("Arial",Font.PLAIN, 20));
        if(gameOver){
            g.setColor(Color.yellow);
            g.drawString("Game Over"+ String.valueOf(snakeBody.size()*10), tileSize-6, tileSize);
        }
        else{
            g.drawString("Score"+ String.valueOf(snakeBody.size()*10),tileSize -16,tileSize );
        }
    }


    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
    }


    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
        
    }

    public void move(){

        //eat food

        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x,food.y));
            placeFood();
        }

        //snake Body

        for(int i= snakeBody.size()-1;i>=0;i--){
            Tile snakePart = snakeBody.get(i);
            if(i==0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //snakeHead
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //Game over conditions
        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }
      //Edge loop conditions

         if(snakeHead.x * tileSize <0){
            snakeHead.x = boardWidth/tileSize-1;
         }
         if(snakeHead.x == boardWidth/tileSize){
            snakeHead.x = 0;
         }
         if(snakeHead.y * tileSize <0){
            snakeHead.y = boardHeight/tileSize-1;
         }
         if(snakeHead.y == boardHeight/tileSize){
            snakeHead.y = 0;
         }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }

    }

    
    @Override
    public void keyPressed(KeyEvent e) {
         if(e.getKeyCode()== KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
         }
         else if(e.getKeyCode()== KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
         }
         else if(e.getKeyCode()== KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
         }
         else if(e.getKeyCode()== KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
         }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
    
}
