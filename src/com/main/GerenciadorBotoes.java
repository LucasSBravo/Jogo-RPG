package com.main;

import javax.swing.*;
import java.awt.event.*;

public class GerenciadorBotoes {
    private static JButton botaoSair;
    private static JFrame janelaPrincipal;
    
    public static void configurarSistema(JButton botao, JFrame janela) {
        botaoSair = botao;
        janelaPrincipal = janela;
        atualizarBotaoSair();
    }
    
    public static void atualizarBotaoSair() {
        if (botaoSair != null) {
            // Limpeza completa
            botaoSair.removeActionListener(null);
            for (ActionListener al : botaoSair.getActionListeners()) {
                botaoSair.removeActionListener(al);
            }
            
            // Nova configuração
            botaoSair.setText("Sair");
            botaoSair.setEnabled(true);
            botaoSair.addActionListener(e -> sairJogo());
        }
    }
    
    private static void sairJogo() {
        if (Principal.getDialogoAtual() != null) {
            Principal.getDialogoAtual().fechar();
        }
        if (janelaPrincipal != null) {
            janelaPrincipal.dispose();
        }
        System.exit(0);
    }
}