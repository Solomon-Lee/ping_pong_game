import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class score extends Rectangle {
	
	static int game_width;
	static int game_height;
	int player1;
	int player2;
	
	score(int game_width, int game_height) {
		score.game_width = game_width;
		score.game_height = game_height;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.PLAIN, 60));
		
		g.drawLine(game_width / 2, 0, game_width / 2, game_height);
		
		g.drawString(String.valueOf(player1 / 10) + String.valueOf(player1 % 10), (game_width / 2) - 85, 50);
		g.drawString(String.valueOf(player2 / 10) + String.valueOf(player2 % 10), (game_width / 2) + 20, 50); 
	}
}

