package br.jogo.model;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Comida {
    private Point posicao;
    private Random random = new Random();
    private BufferedImage sheetComida;

    public Comida(int colunas, int linhas, LinkedList<Segmento> avatar) throws IOException {
        gerar(colunas, linhas, avatar);
        this.sheetComida = ImageIO.read(getClass().getResourceAsStream("/sushi.png"));
    }
    public Point getPosicao(){return posicao;}
    public BufferedImage getImage(){return sheetComida;}

    public void gerar(int colunas, int linhas, LinkedList<Segmento> avatar) {
        Point posicao;
        boolean ocupado;
        do {
            posicao = new Point(random.nextInt(colunas -2) + 1, random.nextInt(linhas -2) +1);
            ocupado = false;
            for (Segmento seg : avatar) {
                if(posicao.equals(seg.getPosicao())) {ocupado = true; break;}
            }
        } while(ocupado);
        this.posicao=posicao;
    }
    
}
