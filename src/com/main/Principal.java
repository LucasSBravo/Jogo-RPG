package com.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class Principal {
	private static CaixaDialogoRPG dialogoAtual;
	
	  public static CaixaDialogoRPG getDialogoAtual() {
	        return dialogoAtual;
	    }
    
    public static void main(String[] args) {
        JFrame janela = new JFrame("Jogo de Aventura");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setUndecorated(true); //
    
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        janela.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
        gd.setFullScreenWindow(janela); // Ativa modo tela cheia
    
        // Painel com imagem de fundo
        FundoPanel painelFundo = new FundoPanel("src/com/main/Imagens/darkaether.PNG");
        painelFundo.setLayout(new BorderLayout());
    
        // Criando e configurando a área de texto
        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setOpaque(false);
        areaTexto.setForeground(Color.WHITE);
        areaTexto.setFont(new Font("Arial", Font.BOLD, 18)); // Ajusta o tamanho da fonte para melhor visibilidade
        areaTexto.setWrapStyleWord(true);
        areaTexto.setLineWrap(true);
        
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        painelFundo.add(scrollPane, BorderLayout.CENTER);
    
        // Criando painel para os botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(1, 3));
        painelBotoes.setOpaque(false);
    
        JButton[] botoes = new JButton[3];
        for (int i = 0; i < 3; i++) {
            botoes[i] = new JButton();
            painelBotoes.add(botoes[i]);
        }
    
        painelFundo.add(painelBotoes, BorderLayout.SOUTH);
        janela.setContentPane(painelFundo);
        janela.setVisible(true);
    
        
        GerenciadorBotoes.configurarSistema(botoes[botoes.length - 1], janela);
        menu(areaTexto, botoes);
        
    }
    
    

    public static void menu(JTextArea areaTexto, JButton[] botoes) {
        areaTexto.setText("\n--------------------------------------------------------\n");
        areaTexto.append(" Escolha seu personagem:\n");
        areaTexto.append(" Mago\n");
        areaTexto.append(" Bárbaro\n");
        areaTexto.append(" Sair\n");
        areaTexto.append("--------------------------------------------------------\n");
    
        String[] opcoes = {"Mago", "Bárbaro", "Sair"};
    
        // Limpar ActionListeners antigos
        for (JButton botao : botoes) {
            for (ActionListener al : botao.getActionListeners()) {
                botao.removeActionListener(al);
            }
        }
    
        // Carregar imagem para o botão Mago
        ImageIcon iconeMago = new ImageIcon("src/com/main/Imagens/mago.png"); // Caminho da imagem
        Image imagemAjustadaMago = iconeMago.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        iconeMago = new ImageIcon(imagemAjustadaMago);
    
        // Carregar imagem para o botão Bárbaro
        ImageIcon iconeBarbaro = new ImageIcon("src/com/main/Imagens/cav.png"); // Caminho da imagem
        Image imagemAjustadaBarbaro = iconeBarbaro.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        iconeBarbaro = new ImageIcon(imagemAjustadaBarbaro);
    
        // Configurar botões
        for (int i = 0; i < 3; i++) {
            botoes[i].setText(opcoes[i]);
            botoes[i].setEnabled(true);
            botoes[i].setBackground(UIManager.getColor("Button.background")); // Restaurando cor padrão de fundo
            botoes[i].setForeground(UIManager.getColor("Button.foreground")); // Restaurando cor do texto
        }
    
        // Adicionar imagens aos botões
        botoes[0].setIcon(iconeMago);    // Ícone para o botão Mago
        botoes[1].setIcon(iconeBarbaro); // Ícone para o botão Bárbaro
        
    
        GerenciadorBotoes.atualizarBotaoSair();
    
        // Ação dos botões de personagens
        for (int i = 0; i < 2; i++) {
            final int escolhaFinal = i;
            botoes[i].addActionListener(e -> {
                // Remover ícone de todos os botões antes de prosseguir
                for (JButton botao : botoes) {
                    botao.setIcon(null);
                }
    
                // Criar personagem correspondente
                Personagem personagem = switch (escolhaFinal) {
                    case 0 -> new Mago(botoes);
                    case 1 -> new Barbaro(botoes);
                    default -> null;
                };
    
                if (personagem != null) {
                    personagem.apresentarHistoria(areaTexto);
                    personagem.iniciarAventura(areaTexto, botoes);
                }
            });
        }
    }
    
   
    public static void exibirDialogo(JFrame parent, String mensagem) {
        if (dialogoAtual == null) {
            dialogoAtual = new CaixaDialogoRPG(parent);
        }
        dialogoAtual.adicionarMensagem(mensagem);
    }
    
   
}

	
class FundoPanel extends JPanel {
 private Image imagemFundo;

 public FundoPanel(String caminhoImagem) {
     try {
         imagemFundo = new ImageIcon(caminhoImagem).getImage();
     } catch (Exception e) {
         System.out.println("Erro ao carregar imagem: " + e.getMessage());
     }
 }

 @Override
 protected void paintComponent(Graphics g) {
     super.paintComponent(g);
     if (imagemFundo != null) {
    	 
         g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
     }
 }
}

