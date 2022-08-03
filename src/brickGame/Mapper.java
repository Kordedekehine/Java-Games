package brickGame;

import java.awt.*;

public class Mapper {

    public int[][] map;

    public int brickBreakerWidth;
    public int brickBreakerHeight;

    public Mapper(int row,int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length;i++){
            for (int j = 0; j < map[0].length;j++){
                map[i][j] = 1;
            }
        }
        brickBreakerWidth = 540/col;
        brickBreakerHeight=150/col;
    }

    public void draw(Graphics2D graphics2D){
        for (int i = 0;i < map.length;i++){
            for (int j = 0;j < map[0].length;j++){
                if (map[i][j] > 0){
                    graphics2D.setColor(Color.white);//HERE WE DESIGNING THE BRICKS
                    graphics2D.fillRect(j * brickBreakerWidth + 80,i * brickBreakerHeight + 50,brickBreakerWidth,
                            brickBreakerHeight);

                    graphics2D.setStroke(new BasicStroke(3));
                    graphics2D.setColor(Color.green);
                    graphics2D.drawRect(j * brickBreakerWidth + 80,i * brickBreakerHeight + 50,brickBreakerWidth,
                            brickBreakerHeight);
                }
            }
        }
    }

    public void setBrickValue(int value,int row, int col){
        map[row][col] = value;
    }
}
