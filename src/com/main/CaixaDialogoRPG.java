package com.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class CaixaDialogoRPG {
    private JDialog dialogo;
    private JTextArea textoDialogo;
    private Timer temporizador;
    private int caracteresExibidos;
    private String mensagemAtual;
    private Queue<String> filaMensagens = new LinkedList<>();
    private boolean exibindoMensagem = false;

    public CaixaDialogoRPG(JFrame parent) {
        // Configuração do JDialog
        dialogo = new JDialog(parent, "", false);
        dialogo.setUndecorated(true);
        dialogo.setSize(600, 150);
        dialogo.setLocationRelativeTo(parent);
        
        // Configuração do painel principal
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(255, 255, 200));
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Inicialização do JTextArea
        textoDialogo = new JTextArea();
        textoDialogo.setEditable(false);
        textoDialogo.setOpaque(false);
        textoDialogo.setFont(new Font("Arial", Font.BOLD, 16));
        textoDialogo.setLineWrap(true);
        textoDialogo.setWrapStyleWord(true);
        
        painel.add(textoDialogo, BorderLayout.CENTER);
        
        JLabel indicador = new JLabel("▼ Clique em uma opção para continuar ▼");
        indicador.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(indicador, BorderLayout.SOUTH);
        
        dialogo.add(painel);
        
        // Configuração do Timer para efeito de digitação
        temporizador = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (caracteresExibidos < mensagemAtual.length()) {
                    textoDialogo.setText(mensagemAtual.substring(0, caracteresExibidos + 1));
                    caracteresExibidos++;
                } else {
                    temporizador.stop();
                    exibindoMensagem = false;
                    exibirProximaMensagem();
                }
            }
        });
        
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    public void adicionarMensagem(String mensagem) {
        filaMensagens.add(mensagem);
        if (!exibindoMensagem) {
            exibirProximaMensagem();
        }
    }
    
    private void exibirProximaMensagem() {
        if (!filaMensagens.isEmpty() && textoDialogo != null) {
            exibindoMensagem = true;
            this.mensagemAtual = filaMensagens.poll();
            this.caracteresExibidos = 0;
            textoDialogo.setText("");
            temporizador.start();
            dialogo.setVisible(true);
        }
    }
    
    public void fechar() {
        if (dialogo != null) {
            dialogo.dispose();
        }
    }
}