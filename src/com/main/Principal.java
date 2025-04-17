package com.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Principal {
    private static JScrollPane scroll;
    private static JPanel painelBotoes;
    public static CaixaDialogoRPG caixaDialogo;
    private static boolean telaCheia = true;
    private static JFrame janela;
    private static JTextArea areaTexto;
    private static JButton[] botoes;

    public static void main(String[] args) {
        EstadoJogo estadoSalvo = GerenciadorProgresso.carregarProgresso();

        janela = new JFrame("Jogo de Aventura");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setUndecorated(true);

        // Define o ícone personalizado
        Image icone = new ImageIcon("src/com/main/Resources/Imagens/ghostenvergonhadinho.png").getImage();
        janela.setIconImage(icone);

        configurarModoExecucao(janela);

        areaTexto = criarAreaTexto();
        botoes = criarBotoes();
        FundoPanel painelFundo = criarPainelFundo(areaTexto, botoes);

        janela.setContentPane(painelFundo);
        janela.setVisible(true);

        exibirLobby(estadoSalvo);
    }

    public static void exibirLobby(EstadoJogo estadoSalvo) {

        // Limpa a caixa de diálogo antes de configurar o lobby
    if (caixaDialogo != null) {
        caixaDialogo.limpar(); // Método para limpar as mensagens da caixa de diálogo
    }

        areaTexto.setText("Bem-vindo ao Jogo de Aventura!\nEscolha uma opção:");

        limparActionListeners(botoes);

        String[] opcoes = {"Jogar", "Opções", "Sair"};
        for (int i = 0; i < botoes.length; i++) {
            botoes[i].setText(opcoes[i]);
            botoes[i].setEnabled(true);
            botoes[i].setIcon(null);
        }

        botoes[0].addActionListener(e -> {
            if (estadoSalvo != null) {
                areaTexto.setText("Progresso encontrado. Deseja continuar?\n\n1. Sim\n2. Novo Jogo\n3. Sair");
        
                limparActionListeners(botoes);
                botoes[0].setText("Sim");
                botoes[1].setText("Novo Jogo");
                botoes[2].setText("Sair");
        
                botoes[0].addActionListener(ev -> {
                    if (caixaDialogo != null) {
                        caixaDialogo.adicionarMensagem("Continuando o jogo...");
                    }
                    continuarJogo(areaTexto, botoes, estadoSalvo);
                });
                botoes[1].addActionListener(ev -> {
                    if (caixaDialogo != null) {
                        caixaDialogo.adicionarMensagem("Iniciando um novo jogo...");
                    }
                    exibirMenuInicial(areaTexto, botoes, null);
                });
                botoes[2].addActionListener(ev -> System.exit(0));
            } else {
                if (caixaDialogo != null) {
                    caixaDialogo.adicionarMensagem("Iniciando o jogo...");
                }
                exibirMenuInicial(areaTexto, botoes, null);
            }
        });

        botoes[1].addActionListener(e -> {
            areaTexto.setText("Opções:\nEscolha o modo de tela:");
        
            limparActionListeners(botoes);
        
            botoes[0].setText("Tela Cheia");
            botoes[1].setText("Janela");
            botoes[2].setText("Voltar");
        
            // Opção Tela Cheia
            botoes[0].addActionListener(ev -> {
                telaCheia = false; // Atenção: false = fullscreen no seu método
                reiniciarJanela();
                exibirLobby(GerenciadorProgresso.carregarProgresso());
            });
        
            // Opção Janela
            botoes[1].addActionListener(ev -> {
                telaCheia = true; // true = modo janela no seu método
                reiniciarJanela();
                exibirLobby(GerenciadorProgresso.carregarProgresso());
            });
        
            // Voltar ao menu principal
            botoes[2].addActionListener(ev -> {
                exibirLobby(GerenciadorProgresso.carregarProgresso());
            });
        });

        botoes[2].addActionListener(e -> System.exit(0));
    }

    private static void continuarJogo(JTextArea areaTexto, JButton[] botoes, EstadoJogo estado) {
        String classe = estado.getPersonagem();
        Personagem personagem = null;

        if ("Mago".equals(classe)) {
            personagem = new Mago(botoes);
        } else if ("Bárbaro".equals(classe)) {
            personagem = new Barbaro(botoes);
        }

        if (personagem != null) {
            personagem.retomarProgresso(areaTexto, estado, botoes);
        } else {
            exibirMenuInicial(areaTexto, botoes, null);
        }
    }

    private static JTextArea criarAreaTexto() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setOpaque(false);
        area.setForeground(Color.WHITE);
        area.setFont(new Font("Arial", Font.BOLD, 18));
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        return area;
    }

    private static JButton[] criarBotoes() {
        JButton[] botoes = new JButton[3];
        for (int i = 0; i < botoes.length; i++) {
            botoes[i] = new JButton();
        }
        return botoes;
    }

    private static FundoPanel criarPainelFundo(JTextArea areaTexto, JButton[] botoes) {
        Image imagemFundo = new ImageIcon("src/com/main/Resources/Imagens/darkaether.png").getImage();
        
        FundoPanel painelFundo = new FundoPanel(imagemFundo) {
            @Override
            public void doLayout() {
                super.doLayout();
    
                int largura = getWidth();
                int altura = getHeight();
    
                // Tamanhos fixos
                int larguraTexto = 900;
                int alturaTexto = 200;
    
                int larguraDialogo = 600;
                int alturaDialogo = 150;
    
                int larguraBotoes = 500;
                int alturaBotoes = 50;
    
                // Posicionamento centralizado
                scroll.setBounds((largura - larguraTexto) / 2, altura / 5, larguraTexto, alturaTexto);
                caixaDialogo.setBounds((largura - larguraDialogo) / 2, altura / 2 + 100, larguraDialogo, alturaDialogo);
                painelBotoes.setBounds((largura - larguraBotoes) / 2, altura - 100, larguraBotoes, alturaBotoes);
            }
        };
    
        painelFundo.setLayout(null); // Layout absoluto com ajuste manual
    
        // Área de texto com scroll transparente
        scroll = new JScrollPane(areaTexto);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        painelFundo.add(scroll);
    
        // Caixa de diálogo
        caixaDialogo = new CaixaDialogoRPG();
        caixaDialogo.setOpaque(false);
        painelFundo.add(caixaDialogo);
    
        // Painel de botões
        painelBotoes = new JPanel(new GridLayout(1, botoes.length, 10, 10));
        painelBotoes.setOpaque(false);
        for (JButton botao : botoes) {
            painelBotoes.add(botao);
        }
        painelFundo.add(painelBotoes);
    
        return painelFundo;
    }      

    public static void exibirMenuInicial(JTextArea areaTexto, JButton[] botoes, EstadoJogo progressoSalvo) {
        if (caixaDialogo != null) {
            caixaDialogo.limpar(); // Método para limpar as mensagens da caixa de diálogo
        }

        String[] opcoes = {"Mago", "Bárbaro", "Opções"};

        areaTexto.setText("Escolha seu personagem:\n");

        ImageIcon iconeMago = new ImageIcon("src/com/main/Resources/Imagens/mago.png");
        ImageIcon iconeBarbaro = new ImageIcon("src/com/main/Resources/Imagens/cav.png");
        iconeMago = new ImageIcon(iconeMago.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        iconeBarbaro = new ImageIcon(iconeBarbaro.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));

        limparActionListeners(botoes);

        for (int i = 0; i < botoes.length; i++) {
            botoes[i].setText(opcoes[i]);
            botoes[i].setEnabled(true);
            botoes[i].setIcon(null);
        }

        botoes[0].setIcon(iconeMago);
        botoes[1].setIcon(iconeBarbaro);

        botoes[0].addActionListener(e -> iniciarComPersonagem(new Mago(botoes), "Mago", areaTexto, botoes));
        botoes[1].addActionListener(e -> iniciarComPersonagem(new Barbaro(botoes), "Bárbaro", areaTexto, botoes));
        botoes[2].addActionListener(e -> Principal.exibirLobby(GerenciadorProgresso.carregarProgresso()));
    }

    private static void iniciarComPersonagem(Personagem personagem, String classe, JTextArea areaTexto, JButton[] botoes) {
        for (JButton botao : botoes) botao.setIcon(null);

        personagem.apresentarHistoria(areaTexto);
        personagem.iniciarAventura(areaTexto, botoes);

        EstadoJogo estado = new EstadoJogo(classe + ":Inicio", classe);
        GerenciadorProgresso.salvarProgresso(estado);
    }

    private static void limparActionListeners(JButton[] botoes) {
        for (JButton botao : botoes) {
            for (ActionListener listener : botao.getActionListeners()) {
                botao.removeActionListener(listener);
            }
        }
    }

    public static void configurarModoExecucao(JFrame janela) {
        if (telaCheia) {
            janela.setSize(1280, 720);
            janela.setLocationRelativeTo(null);
        } else {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            janela.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
            gd.setFullScreenWindow(janela);
        }
    }

    private static void reiniciarJanela() {
        janela.dispose();
        janela = new JFrame("Jogo de Aventura");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setUndecorated(true);
    
        // Define ícone novamente
        Image icone = new ImageIcon("src/com/main/Resources/Imagens/ghostenvergonhadinho.png").getImage();
        janela.setIconImage(icone);
    
        configurarModoExecucao(janela);
    
        FundoPanel painelFundo = criarPainelFundo(areaTexto, botoes);
        janela.setContentPane(painelFundo);
        janela.setVisible(true);
    }

    public static void exibirDialogo(String mensagem) {
        if (caixaDialogo != null) {
            caixaDialogo.adicionarMensagem(mensagem);
        } else {
            areaTexto.setText(mensagem); // Fallback
        }
    }    
    

    public static class FundoPanel extends JPanel {
        private Image imagem;

        public FundoPanel(Image imagem) {
            this.imagem = imagem;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
