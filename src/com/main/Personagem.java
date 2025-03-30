package com.main;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Personagem {
    protected String nome;
    protected String origem;
    protected String historia;
    protected List<Opcao> opcoes;
    protected JButton[] botoes;

    public Personagem(String nome, String origem, String historia, JButton[] botoes) {
        this.nome = nome;
        this.origem = origem;
        this.historia = historia;
        this.opcoes = new ArrayList<>();
        this.botoes = botoes;
    }

    public void apresentarHistoria(JTextArea areaTexto) {
        areaTexto.setText("");
        Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(areaTexto), 
                              "Você escolheu: " + nome + "\nOrigem: " + origem + "\nHistória: " + historia);
    }

    public void iniciarAventura(JTextArea areaTexto, JButton[] botoes) {
    	resetarEventos(botoes);
        GerenciadorBotoes.atualizarBotaoSair();

        for (int i = 0; i < Math.min(opcoes.size(), botoes.length); i++) {
            final int index = i;
            botoes[i].setText(opcoes.get(i).getTexto());
            botoes[i].setEnabled(true);
            
            // Remove listeners antigos antes de adicionar novos
            for (ActionListener al : botoes[i].getActionListeners()) {
                botoes[i].removeActionListener(al);
            }
            
            botoes[i].addActionListener(e -> {
                opcoes.get(index).executar(areaTexto);
//                Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(areaTexto), 
//                                      "Você escolheu: " + opcoes.get(index).getTexto());
            });
        }
    }

    protected void finalizarCiclo(JTextArea areaTexto, JButton[] botoes) {
        areaTexto.append("\nDeseja voltar ao menu inicial?\n");
        resetarEventos(botoes);

        // Configura botão "Voltar ao Menu"
        botoes[0].setText("Voltar ao Menu");
        botoes[0].setEnabled(true);
        botoes[0].addActionListener(e -> {
            limparOpcoes();
            Principal.menu(areaTexto, botoes);
        });

        // Configura todos os botões (incluindo Sair)
        GerenciadorBotoes.atualizarBotaoSair();

        // Desativa botões intermediários se existirem
        for (int i = 1; i < botoes.length - 1; i++) {
            botoes[i].setEnabled(false);
            botoes[i].setText("");
        }
    }

    protected void resetarEventos(JButton[] botoes) {
        for (JButton botao : botoes) {
            for (ActionListener al : botao.getActionListeners()) {
                botao.removeActionListener(al);
            }
        }
    }
    
    
    protected void adicionarOpcao(String texto, Consumer<JTextArea> acao) {
        opcoes.add(new Opcao(texto, acao));
    }

    protected void limparOpcoes(){
        opcoes.clear();
    }
}