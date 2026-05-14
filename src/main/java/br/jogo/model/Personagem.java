package br.jogo.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.Timer;

import javax.imageio.ImageIO;

public class Personagem {
    Map<String, BufferedImage[]> frames = new HashMap<>();
    private int frameAtual = 0;
    private int COLUNAS, LINHAS, escala;
    private LinkedList<Segmento> avatar;
    private int direcaoX = 1;
    private int direcaoY = 0;
    private String direcao = "direita";
    private Timer timer;

    public Personagem(int COLUNAS, int LINHAS, int escala) {
        this.COLUNAS = COLUNAS;
        this.LINHAS = LINHAS;
        this.escala = escala;
        this.avatar = new LinkedList<>();
        avatar.add(new Segmento(5, 5, "direita", TipoPersonagem.PINK));
        avatar.add(new Segmento(4, 5, "direita", TipoPersonagem.OWLET));
        avatar.add(new Segmento(3, 5, "direita", TipoPersonagem.OWLET));
        // ----------------------------------------------CARREGAR SPRITES DE ANIMAÇÃO--------------------------------------------
        try {
            // frames da direita e esquerda
            BufferedImage sheetOwlet_X = ImageIO.read(getClass().getResourceAsStream("/owlet_correndo.png"));

            BufferedImage[] framesOwlet_direita = new BufferedImage[6];
            for (int i = 0; i < 6; i++) {
                framesOwlet_direita[i] = sheetOwlet_X.getSubimage(i * 32, 0, 32, 32);
            }
            frames.put("owlet_direita", framesOwlet_direita);

            // frames de cima e baixo
            BufferedImage sheetOwlet_Y = ImageIO.read(getClass().getResourceAsStream("/owlet_y.png"));
            BufferedImage[] framesOwlet_cima = new BufferedImage[4];
            BufferedImage[] framesOwlet_baixo = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                framesOwlet_cima[i] = sheetOwlet_Y.getSubimage(i * 32, 0, 32, 32);
                framesOwlet_baixo[i] = sheetOwlet_Y.getSubimage((3-i) * 32, 0, 32, 32);
            }
            frames.put("owlet_cima", framesOwlet_cima);
            frames.put("owlet_baixo", framesOwlet_baixo);

            BufferedImage sheetPink_X = ImageIO.read(getClass().getResourceAsStream("/pink_correndo.png"));
            BufferedImage[] framesPink_direita = new BufferedImage[6];
            for (int i = 0; i<6; i++) {
                framesPink_direita[i] = sheetPink_X.getSubimage(i*32, 0, 32, 32);
            }
            frames.put("pink_direita", framesPink_direita);

            BufferedImage sheetPink_Y = ImageIO.read(getClass().getResourceAsStream("/pink_y.png"));
            BufferedImage[] framesPink_cima = new BufferedImage[4];
            BufferedImage[] framesPink_baixo = new BufferedImage[4];
            for(int i = 0; i < 4; i ++) {
                framesPink_cima[i] = sheetPink_Y.getSubimage(i*32, 0, 32, 32);
                framesPink_baixo[i] = sheetPink_Y.getSubimage((3-i) * 32, 0, 32, 32);
            }
            frames.put("pink_cima", framesPink_cima);
            frames.put("pink_baixo", framesPink_baixo);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // timer da animação
        timer = new Timer(180, e -> {
            String frameTamanho;
            if(direcao.equals("esquerda")) frameTamanho="owlet_direita";
            else if(direcao.equals("direita")) frameTamanho="owlet_direita";
            else if(direcao.equals("cima")) frameTamanho="owlet_cima";
            else frameTamanho="owlet_baixo";
            frameAtual = (frameAtual + 1) % frames.get(frameTamanho).length;

            Segmento cabeca = avatar.getFirst();
            Segmento novaCabecaPoint = new Segmento(cabeca.getPosicao().x + direcaoX, cabeca.getPosicao().y + direcaoY, direcao, TipoPersonagem.PINK);
            // colunas
            if (novaCabecaPoint.getPosicao().x >= COLUNAS) {
                novaCabecaPoint.setPosicao(0, novaCabecaPoint.getPosicao().y);
            }
            if (novaCabecaPoint.getPosicao().x < 0) {
                novaCabecaPoint.setPosicao(COLUNAS, novaCabecaPoint.getPosicao().y);
            }
            // linhas
            if (novaCabecaPoint.getPosicao().y >= LINHAS) {
                novaCabecaPoint.setPosicao(novaCabecaPoint.getPosicao().x, 0);
            }
            if (novaCabecaPoint.getPosicao().y < 0) {
                novaCabecaPoint.setPosicao(novaCabecaPoint.getPosicao().x, LINHAS);
            }
            avatar.getFirst().setTipoPersonagem(TipoPersonagem.OWLET); 
            avatar.addFirst(novaCabecaPoint);
            avatar.removeLast();

        });
        timer.start();

    }

    public void desenhar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for(Segmento seg : avatar) {
            String prefixo = (seg.getTipoPersonagem() == TipoPersonagem.PINK) ? "pink" : "owlet";
            String chave;
            if(seg.getDirecao().equals("direita") || seg.getDirecao().equals("esquerda")) chave = prefixo + "_direita";
            else if(seg.getDirecao().equals("cima")) chave = prefixo +"_cima";
            else chave = prefixo + "_baixo";
            int frame = frameAtual % frames.get(chave).length;

            if(seg.getDirecao().equals("esquerda")) {
                g2.drawImage(frames.get(chave)[frame],
                seg.getPosicao().x * 32 + 32 * escala,
                seg.getPosicao().y *32,
                -32 * escala,
                32 * escala,
                null
                );
            }
            else {
                g2.drawImage(frames.get(chave)[frame],
                seg.getPosicao().x *32,
                seg.getPosicao().y *32,
                32 * escala,
                32 * escala,
                null
                );

            }
            
        }
            
    }

    public void setDirecao(int dirX, int dirY) {
        if (dirX == -direcaoX && dirY == -direcaoY) {return;}
        this.direcaoX = dirX;
        this.direcaoY = dirY;
        if (dirX == 1) {this.direcao = "direita";}
        if (dirX == -1) {this.direcao = "esquerda";}
        if (dirY == -1) {this.direcao = "cima";}
        if (dirY == 1) {this.direcao = "baixo";}
        frameAtual = frameAtual % frames.get(chaveAtual()).length;
    }

    public void crescer() {
        Segmento cauda = avatar.getLast();
        avatar.addLast(new Segmento(cauda.getPosicao().x, cauda.getPosicao().y, direcao, TipoPersonagem.OWLET));
    }

    private String chaveAtual() {
        if(direcao.equals("esquerda")) return "owlet_direita";
        if(direcao.equals("direita")) return "owlet_direita";
        if(direcao.equals("baixo")) return "owlet_baixo";
        return "owlet_cima";
    }

    public void setX(int x) {this.COLUNAS = x;}
    public void setY(int y) {this.LINHAS = y;}
    public int getCOLUNAS() {return COLUNAS;}
    public int getLINHAS() {return LINHAS;}
    public String getDirecao() {return direcao;}
    public LinkedList<Segmento> getRastro() {return avatar;}
    public Segmento getCabeca() {return avatar.getFirst();}
}
