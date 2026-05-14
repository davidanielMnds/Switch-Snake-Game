package br.jogo.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TelaGameOver extends JPanel{
    private BufferedImage background;
    private BufferedImage painel;
    private BufferedImage btnNormal;
    private BufferedImage btnPressionado;
    private static final int PAINEL_W = 200;
    private static final int PAINEL_H = 150;
    private static final int PAINEL_X = (450 - PAINEL_W) / 2;
    private static final int PAINEL_Y = (315 - PAINEL_H) / 2;
    private static final int BTN_W = 128;
    private static final int BTN_H = 32;
    private static final int BTN_X = PAINEL_X + (PAINEL_W - BTN_W) / 2;
    private static final int BTN_Y = PAINEL_Y + (PAINEL_H - BTN_H) / 2;
    private static Font fonte;
    private Runnable onJogarDeNovo;
    private boolean pressionado;
    
    public TelaGameOver(Runnable onJogarDeNovo)
    {
        this.pressionado=false;
        this.onJogarDeNovo=onJogarDeNovo;
        setOpaque(true);
        setPreferredSize(new Dimension(450, 315));
        try {
            this.background = ImageIO.read(getClass().getResourceAsStream("/menu_gameover_background.png"));
            BufferedImage painelsheet = ImageIO.read(getClass().getResourceAsStream("/painel.png"));
            this.painel= painelsheet.getSubimage(1, 165, 93, 86);
            BufferedImage sheetBotoes = ImageIO.read(getClass().getResourceAsStream("/painel_botoes.png"));
            this.btnNormal = sheetBotoes.getSubimage(0, 0, 128, 32);
            this.btnPressionado = sheetBotoes.getSubimage(0, 32, 128, 32);
            TelaGameOver.fonte = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/StarCrush.ttf"));
            fonte = fonte.deriveFont(Font.PLAIN, 15);
        } catch (IOException e) {e.printStackTrace();} catch(FontFormatException ex) {ex.printStackTrace();}

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(painel, PAINEL_X, PAINEL_Y, PAINEL_W, PAINEL_H, this);
        g.setFont(fonte);
        g.setColor(Color.WHITE);
        g.drawString("A = Z   X = B", PAINEL_X +10, PAINEL_Y + PAINEL_H -10);
        //pintando botão
        if(!pressionado) {g.drawImage(btnNormal, BTN_X, BTN_Y, BTN_W, BTN_H, this);}
        else {g.drawImage(btnPressionado, BTN_X +1, BTN_Y +1, BTN_W -2, BTN_H -2, this);}
        g.drawString("REINICIAR", (BTN_X + BTN_W + 43 ) /2, (BTN_Y + BTN_H + PAINEL_H) /2);
    }

    public void setAtivado(Boolean ativado) {this.pressionado=ativado;}
}
