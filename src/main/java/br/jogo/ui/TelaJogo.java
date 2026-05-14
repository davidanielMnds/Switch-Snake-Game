package br.jogo.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.Timer;
import br.jogo.model.Personagem;
import br.jogo.model.Comida;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import br.jogo.model.Segmento;

public class TelaJogo extends JPanel{
    private Personagem personagem;
    private Comida comida;
    private Runnable onGameOver;
    private BufferedImage background;
    private static int TAMANHO_PIXEL = 32;
    public static final int LARGURA = 448;
    public static final int ALTURA =288;
    private static final int COLUNAS = LARGURA / TAMANHO_PIXEL, LINHAS = ALTURA / TAMANHO_PIXEL;
    
//-----------------------------------------CONSTRUTOR------------------------------------------
    public TelaJogo(Runnable onGameOver) {
        this.onGameOver=onGameOver;
        setOpaque(false);
        personagem = new Personagem(COLUNAS, LINHAS, 1);
        try{this.comida = new Comida(COLUNAS, LINHAS, personagem.getRastro());} catch (Exception e) {e.printStackTrace();}
        
        new Timer(16, e-> {
            if(personagem.getCabeca().getPosicao().equals((comida.getPosicao()))) {
                personagem.crescer();
                comida.gerar(COLUNAS, LINHAS, personagem.getRastro());
            }
            Segmento cabeca = personagem.getCabeca();
            LinkedList<Segmento> rastro = personagem.getRastro();
            for(int i=1; i < rastro.size(); i++) {
                if(cabeca.getPosicao().equals(rastro.get(i).getPosicao())) {
                    onGameOver.run();
                    ((Timer)e.getSource()).stop();
                    return;
                }
            }
            
            
            repaint();
        }).start();

//---------------------------------------- CARREGAR BACKGROUND-------------------------------
        try{
            BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream("/background.png"));
            this.background = sheet.getSubimage(0,0, 448, 288);
        } catch(Exception e) {e.printStackTrace();}
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        personagem.desenhar(g);
        g.drawImage(comida.getImage(), comida.getPosicao().x * 32, comida.getPosicao().y * 32, 32, 32, this);
        
    }

    public Personagem getPersonagem() {return personagem;}

}
