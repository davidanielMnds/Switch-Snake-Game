package br.jogo.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Botao {
    private BufferedImage normal;
    private BufferedImage pressionado;
    private int x, y, largura, altura;
    private boolean ativado = false;
    //Construtor
    public Botao(BufferedImage normal, BufferedImage pressionado, int x, int y, int escala) {
        this.normal=normal;
        this.pressionado=pressionado;
        this.x=x;
        this.y=y;
        this.largura = normal.getWidth() * escala;
        this.altura = normal.getHeight() * escala;
    }

    public void desenhar(Graphics g) {
        if(!ativado){g.drawImage(normal, x, y, largura, altura, null);}
        else {g.drawImage(pressionado, x, y, largura, altura, null);}
    }

    public void setAtivado(boolean ativar) {this.ativado=ativar;}

    public boolean contemPonto(int mx, int my) {
        return mx >= x && mx <= x + largura && my  >= y && my <= y + altura;
    }

}
