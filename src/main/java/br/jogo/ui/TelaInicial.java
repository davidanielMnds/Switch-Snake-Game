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

public class TelaInicial extends JPanel{
    private BufferedImage background, painel, btnNormal, btnPressionado;
    private Runnable onJogar;
    private boolean pressionado;
    private static Font fonte;
    private static final int PAINEL_W = 200, PAINEL_H = 150;
    private static final int PAINEL_X = (450 - PAINEL_W) / 2, PAINEL_Y = (315 - PAINEL_H) / 2;
    private static final int BTN_W = 128, BTN_H = 32;
    private static final int BTN_X = PAINEL_X + (PAINEL_W - BTN_W) /2, BTN_Y = PAINEL_Y + (PAINEL_H - BTN_H) / 2;

    public TelaInicial(Runnable onJogar) {
        setOpaque(true);
        setPreferredSize(new Dimension(450, 315));
        this.onJogar = onJogar;
        try {
            //background
            this.background = ImageIO.read(getClass().getResourceAsStream("/menu_background.png"));
            //painel sheet
            BufferedImage painelSheet = ImageIO.read(getClass().getResourceAsStream("/painel.png"));
            painel = painelSheet.getSubimage(1, 165, 93, 86);
            //botoes sheet
            BufferedImage botoesSheet = ImageIO.read(getClass().getResourceAsStream("/painel_botoes.png"));
                //botao normal
            btnNormal = botoesSheet.getSubimage(0, 0, 128, 32);
                //botao pressionado
            btnPressionado = botoesSheet.getSubimage(0, 32, 128, 33);
            TelaInicial.fonte = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Star_Crush.ttf"));
            fonte = fonte.deriveFont(Font.PLAIN, 15);
        }
        catch (IOException e) {e.printStackTrace();}    
        catch (FontFormatException ex) {ex.printStackTrace();}
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //pintando background
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        //pintando painel
        g.drawImage(painel, PAINEL_X, PAINEL_Y, PAINEL_W, PAINEL_H, this);
        g.setFont(fonte);
        g.setColor(Color.WHITE);
        g.drawString("A = Z   X = B", PAINEL_X +10, PAINEL_Y + PAINEL_H -10);
        //pintando botão
        if(!pressionado) {g.drawImage(btnNormal, BTN_X, BTN_Y, BTN_W, BTN_H, this);}
        else {g.drawImage(btnPressionado, BTN_X +1, BTN_Y +1, BTN_W -2, BTN_H -2, this);}
        g.drawString("iNICIAR JOGO", (BTN_X + BTN_W + 43 ) /2, (BTN_Y + BTN_H + PAINEL_H) /2);

    }

    public void setAtivado(boolean ativar) {
        this.pressionado=ativar;
    }
    public boolean getAtivado() {return pressionado;}

    public static boolean contemPonto(int mx, int my)
    {return mx >= BTN_X && mx <= BTN_X + BTN_W && my >= BTN_Y && my <= BTN_Y + BTN_H;}
}

