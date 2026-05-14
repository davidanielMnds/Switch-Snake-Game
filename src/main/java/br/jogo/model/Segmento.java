package br.jogo.model;

import java.awt.Point;

public class Segmento {
    private Point posicao;
    private String direcao;
    private TipoPersonagem tipoPersonagem;


    public Segmento(int x, int y, String direcao, TipoPersonagem tipoPersonagem) {
        this.posicao=new Point(x,y);
        this.direcao=direcao;
        this.tipoPersonagem=tipoPersonagem;
    }

    public Point getPosicao() {return posicao;}
    public String getDirecao() {return direcao;}
    public TipoPersonagem getTipoPersonagem() {return tipoPersonagem;}
    public void setPosicao(int x, int y) {this.posicao.setLocation(x,y);}
    public void setDirecao(String direcao) {this.direcao=direcao;}
    public void setTipoPersonagem(TipoPersonagem tipoPersonagem) {this.tipoPersonagem=tipoPersonagem;}
}
