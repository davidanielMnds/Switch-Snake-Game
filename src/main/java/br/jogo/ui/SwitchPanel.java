package br.jogo.ui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SwitchPanel extends JPanel implements KeyListener{

    private BufferedImage sprite;
    private static final int ESCALA = 5;
    private static final int SPRITE_W = 162;
    private static final int SPRITE_H = 73;
    private Map<String, Botao> botoes = new HashMap<>(); 
    private enum TelaAtual {INICIO, JOGO, GAMEOVER}
    private TelaAtual telaAtual = TelaAtual.INICIO;
    private TelaInicial telaInicial;
    private TelaGameOver telaGameOver;
    private TelaJogo telaJogo;
    private JPanel visor;
    private CardLayout cardLayout;

    public SwitchPanel() {
        addKeyListener(this);
        //----------------------------------- CRIANDO BOTOES----------------------------------------------------
        try {
//switchSheet pegar imagem
            BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream("/switch.png"));
            sprite = sheet.getSubimage(519, 3, SPRITE_W, SPRITE_H);
//botoesSheet pegar imagem
            BufferedImage botoesSheet = ImageIO.read(getClass().getResourceAsStream("/switch_botoes.png"));
//cima
            BufferedImage btnCimaN = botoesSheet.getSubimage(35, 7, 9, 10);
            BufferedImage btnCimaP = botoesSheet.getSubimage(35, 6, 9, 11);
//baixo
            BufferedImage btnBaixoN = botoesSheet.getSubimage(83, 7, 9 , 10);
            BufferedImage btnBaixoP = botoesSheet.getSubimage(83, 6, 9, 11);
//esquerda
            BufferedImage btnEsquerdaN = botoesSheet.getSubimage(10, 15, 9, 10);
            BufferedImage btnEsquerdaP = botoesSheet.getSubimage(10, 14, 9, 11);
//direita
            BufferedImage btnDireitaN = botoesSheet.getSubimage(60, 15, 9, 10);
            BufferedImage btnDireitaP = botoesSheet.getSubimage(60, 14, 9, 11);
//a
            BufferedImage btnAN = botoesSheet.getSubimage(84, 46, 9, 10);
            BufferedImage btnAP = botoesSheet.getSubimage(84, 45, 9, 11);
//b
            BufferedImage btnBN = botoesSheet.getSubimage(61, 38, 9, 10);
            BufferedImage btnBP = botoesSheet.getSubimage(61, 37, 9, 11);

//COLCOAR BOTOES NA TELA
            botoes.put("cima", new Botao(btnCimaN, btnCimaP, 60,  185, ESCALA));
            botoes.put("baixo", new Botao(btnBaixoN, btnBaixoP, 60,  265, ESCALA));
            botoes.put("esquerda", new Botao(btnEsquerdaN, btnEsquerdaP, 15,  225, ESCALA));
            botoes.put("direita", new Botao(btnDireitaN, btnDireitaP, 105,  225, ESCALA));
            botoes.put("a", new Botao(btnAN, btnAP, 750, 100, ESCALA));
            botoes.put("b", new Botao(btnBN, btnBP, 705, 140, ESCALA));
        //----------------------------------- BOTOES----------------------------------------------------

        } catch (IOException e) {}
//------------------------------------------- CONFIGURAÇÕES PAGINA ----------------------------------------------------
        setLayout(null);
//Configurando card layout
        this.cardLayout = new CardLayout();
//configurando visor
        this.visor = new JPanel(cardLayout);
        visor.setBackground(Color.BLACK);
        visor.setPreferredSize(new Dimension(450, 315));
        visor.setMaximumSize(new Dimension(450, 315));
        visor.setBounds(180, 20, 450, 315);

//configurando tela de jogo
        this.telaJogo = new TelaJogo(() -> {
            cardLayout.show(visor, "game-over");
            telaAtual=TelaAtual.GAMEOVER;
        });
        
        visor.add(telaJogo, "tela-jogo");
//------------------------------------------------------------

//configurando tela inicial
        this.telaInicial = new TelaInicial(() -> {});
        visor.add(telaInicial, "tela-inicial");
//------------------------------------------------------------

//configurando tela game over
        this.telaGameOver = new TelaGameOver(()-> {
            cardLayout.show(visor, "tela-inicial");
            telaAtual = TelaAtual.INICIO;
        });
        visor.add(telaGameOver, "game-over");
//------------------------------------------------------------

//mostrando tela inicial
        cardLayout.show(visor, "tela-inicial");
        add(visor);
//------------------------------------------------------------

//configurações da tela principal
        setPreferredSize(new Dimension(SPRITE_W * ESCALA, SPRITE_H * ESCALA));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        requestFocusInWindow();
        
//-----------------------------------------------MOUSE CLICAR--------------------------------------
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocus();
//se clicou em um dos botoes do switch
                botoes.values().forEach(b-> {
                    if(b.contemPonto(e.getX(), e.getY())) {b.setAtivado(true);}
                });
                repaint();

//se clicou no botao de começar jogo    
                if(telaAtual.equals(TelaAtual.INICIO) && TelaInicial.contemPonto(e.getX()-180, e.getY()-20)){
                    telaInicial.setAtivado(true);
                    telaInicial.repaint();
                }
                else if(telaAtual.equals(TelaAtual.GAMEOVER)) {
                    telaGameOver.setAtivado(true);
                    telaGameOver.repaint();
                }
            }

//-----------------------------------------------MOUSE SOLTAR--------------------------------------
            @Override
            public void mouseReleased(MouseEvent e) {
//se clicou em um dos botoes do switch
                botoes.values().forEach(b -> b.setAtivado(false));
                
                repaint();
//se soltou o botao de começar jogo
                if(telaAtual.equals(TelaAtual.INICIO) && TelaInicial.contemPonto(e.getX()-180, e.getY()-20) ||
                botoes.get("a").contemPonto(e.getX(),e.getY())){
                    telaInicial.setAtivado(false);
                    telaInicial.repaint();
                    cardLayout.show(visor, "tela-jogo");
                    telaAtual=TelaAtual.JOGO;
                    requestFocus();
                }
                else if(telaAtual.equals(TelaAtual.JOGO)) {
                    if(botoes.get("cima").contemPonto(e.getX(), e.getY())) {telaJogo.getPersonagem().setDirecao(0, -1);}
                    if(botoes.get("baixo").contemPonto(e.getX(), e.getY())) {telaJogo.getPersonagem().setDirecao(0, 1);}
                    if(botoes.get("esquerda").contemPonto(e.getX(), e.getY())) {telaJogo.getPersonagem().setDirecao(-1, 0);}
                    if(botoes.get("direita").contemPonto(e.getX(), e.getY())) {telaJogo.getPersonagem().setDirecao(1, 0);}
                }
                else if(telaAtual.equals(TelaAtual.GAMEOVER)) {
                    telaGameOver.setAtivado(false);
                    telaGameOver.repaint();
                    reiniciarJogo();
                }
            }
        });
    }
//-----------------------------------------------PINTAR TELA--------------------------------------
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int largura = SPRITE_W * ESCALA;
        int altura = SPRITE_H * ESCALA;
        g.drawImage(sprite, 0, 0, largura, altura, this);
        botoes.values().forEach(b -> b.desenhar(g));
    }
//-----------------------------------------------TECLADO CLICAR--------------------------------------
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
//cima
            case KeyEvent.VK_UP: {
                botoes.get("cima").setAtivado(true);
                if(telaAtual.equals(TelaAtual.JOGO)) {telaJogo.getPersonagem().setDirecao(0, -1);}
            } break;
//baixo
            case KeyEvent.VK_DOWN: {
                botoes.get("baixo").setAtivado(true);
                if (telaAtual == TelaAtual.JOGO) telaJogo.getPersonagem().setDirecao(0, 1);
            } break;
//esquerda
            case KeyEvent.VK_LEFT: {
                botoes.get("esquerda").setAtivado(true);
                if (telaAtual == TelaAtual.JOGO) telaJogo.getPersonagem().setDirecao(-1, 0);
            } break;
//direita
            case KeyEvent.VK_RIGHT: {
                botoes.get("direita").setAtivado(true);
                if (telaAtual == TelaAtual.JOGO) telaJogo.getPersonagem().setDirecao(1, 0);
            } break;
//z
            case KeyEvent.VK_Z:{
                botoes.get("a").setAtivado(true);
                if(telaAtual.equals(TelaAtual.INICIO)) {
                    telaInicial.setAtivado(true);
                    telaInicial.repaint();
                    requestFocus();
                }
                else if(telaAtual.equals(TelaAtual.GAMEOVER)) {
                    telaGameOver.setAtivado(true);
                    telaGameOver.repaint();
                }
            } break;
//x
            case KeyEvent.VK_X: {
                botoes.get("b").setAtivado(true);
            } break;
        }
        repaint();
    }
//-----------------------------------------------TECLADO SOLTAR--------------------------------------
    @Override 
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
//cima
            case KeyEvent.VK_UP -> botoes.get("cima").setAtivado(false);
//baixo
            case KeyEvent.VK_DOWN -> botoes.get("baixo").setAtivado(false);
//esquerda
            case KeyEvent.VK_LEFT -> botoes.get("esquerda").setAtivado(false);
//direita
            case KeyEvent.VK_RIGHT -> botoes.get("direita").setAtivado(false);
//z
            case KeyEvent.VK_Z ->
            {
                botoes.get("a").setAtivado(false);
                if(telaAtual.equals(TelaAtual.INICIO)) {
                    telaInicial.setAtivado(false);
                    telaInicial.repaint();
                    cardLayout.show(visor, "tela-jogo");
                    telaAtual=TelaAtual.JOGO;
                    requestFocus();
                    
                }
                if(telaAtual.equals(TelaAtual.GAMEOVER)) {
                    telaGameOver.setAtivado(false);
                    telaGameOver.repaint();
                    reiniciarJogo();
                }
            }
//x
            case KeyEvent.VK_X -> botoes.get("b").setAtivado(false);
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void reiniciarJogo(){
        visor.removeAll();
        telaJogo = new TelaJogo(() ->{
            cardLayout.show(visor, "game-over");
            telaAtual= TelaAtual.GAMEOVER;
        });
        visor.add(telaJogo, "tela-jogo");
        visor.add(telaInicial, "tela-inicial");
        visor.add(telaGameOver, "game-over");
        cardLayout.show(visor, "tela-jogo");
        telaAtual = TelaAtual.JOGO;
        visor.revalidate();
        visor.repaint();
        requestFocus();
    }
}
