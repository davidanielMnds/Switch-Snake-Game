# 🎮 Switch Snake Game

Jogo estilo **Snake** desenvolvido em **Java Swing**, com interface inspirada no **Nintendo Switch**. O personagem se move pelo visor com animação por sprite sheet, come itens e cresce a cada coleta. A estrutura visual simula o console físico completo, com botões clicáveis e navegação entre telas.

---

## ✨ Funcionalidades

- Interface gráfica fiel ao estilo Nintendo Switch, com botões interativos renderizados via sprite sheet
- Jogo Snake com movimentação contínua, crescimento do personagem e detecção de colisão com o próprio corpo
- Animação por frames extraídos de sprite sheets (personagens **Pink** e **Owlet**), com espelhamento horizontal para o movimento à esquerda via `Graphics2D`
- Geração de comida em posição aleatória, evitando sobreposição com o corpo do personagem
- Navegação entre telas (Tela Inicial → Jogo → Game Over → reinício) via `CardLayout`
- Controle por **teclado** (setas + teclas Z/X) e por **clique nos botões** da interface
- Wrap de bordas: o personagem atravessa as extremidades do mapa e reaparece no lado oposto
- Fonte customizada **Star Crush** nas telas de menu

---

## 🗂️ Estrutura do Projeto

```
src/main/java/br/jogo/
├── Main.java                  # Ponto de entrada — cria o JFrame e inicializa o SwitchPanel
├── model/
│   ├── Personagem.java        # Lógica do snake: movimento, crescimento, animação por sprite
│   ├── Segmento.java          # Representa cada parte do corpo do personagem (posição + direção)
│   ├── Comida.java            # Geração aleatória de comida com colisão evitada
│   └── TipoPersonagem.java    # Enum: PINK (cabeça) / OWLET (corpo)
└── ui/
    ├── SwitchPanel.java       # Painel principal — renderiza o console, gerencia input e CardLayout
    ├── TelaJogo.java          # Tela do jogo — game loop via Timer, detecção de colisão
    ├── TelaInicial.java       # Tela de início com botão de entrada
    ├── TelaGameOver.java      # Tela de game over com botão de reinício
    └── Botao.java             # Componente de botão com estado normal/pressionado

src/main/resources/
├── switch.png                 # Sprite sheet do console Nintendo Switch
├── switch_botoes.png          # Sprite sheet dos botões do console
├── background.png             # Fundo da tela de jogo
├── menu_background.png        # Fundo da tela inicial
├── menu_gameover_background.png # Fundo da tela de game over
├── owlet_correndo.png         # Sprite sheet animação owlet (horizontal)
├── owlet_y.png                # Sprite sheet animação owlet (vertical)
├── pink_correndo.png          # Sprite sheet animação pink (horizontal)
├── pink_y.png                 # Sprite sheet animação pink (vertical)
├── sushi.png                  # Sprite da comida
├── painel.png                 # Sprite do painel de menu
├── painel_botoes.png          # Sprite dos botões de menu
└── Star_Crush.ttf             # Fonte customizada
```

---

## 🚀 Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.x

### Passos

```bash
mvn package
java -jar target/demo-1.0-SNAPSHOT.jar
```

---

## 🕹️ Controles

| Ação         | Teclado        | Botão na tela   |
|--------------|----------------|-----------------|
| Mover cima   | ↑              | D-pad ↑         |
| Mover baixo  | ↓              | D-pad ↓         |
| Mover esquerda | ←            | D-pad ←         |
| Mover direita | →             | D-pad →         |
| Confirmar / Iniciar | Z      | Botão A         |
| Cancelar     | X              | Botão B         |

> Na **Tela Inicial** e na **Tela de Game Over**, também é possível clicar diretamente nos botões da interface.

---

## 🏗️ Decisões Técnicas

- **`CardLayout`** gerencia as três telas dentro do visor sem recriar o `JFrame`
- **`javax.swing.Timer`** controla o game loop (~16ms) e a animação de sprites (~180ms) de forma segura na EDT
- **`LinkedList<Segmento>`** representa o corpo do snake, facilitando adição na cabeça e remoção na cauda a cada tick
- **`Map<String, BufferedImage[]>`** organiza os frames de animação por personagem e direção (`"pink_direita"`, `"owlet_cima"`, etc.)
- O espelhamento horizontal para o movimento à esquerda é feito via `Graphics2D.drawImage` com largura negativa, evitando a necessidade de sprites separados
- A geração de comida usa loop `do-while` para garantir que a posição não coincide com nenhum segmento do corpo

---

## 📸 Telas

| Tela Inicial | Jogo em andamento | Game Over |
|:---:|:---:|:---:|
| Menu com botão de início | Snake animado com fundo customizado | Painel de reinício |

---

## 🛠️ Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 17 | Linguagem principal |
| Java Swing | Interface gráfica |
| Maven | Build e gerenciamento de dependências |
| `javax.imageio.ImageIO` | Carregamento de sprites e backgrounds |
| `java.awt.CardLayout` | Navegação entre telas |
| `javax.swing.Timer` | Game loop e animação |

---

## 👤 Autor

Desenvolvido por **Davi** como projeto de portfólio para desenvolvimento Java.

- GitHub: https://github.com/davidanielMnds
- LinkedIn: https://www.linkedin.com/in/davi-daniel-2822b83b7/
