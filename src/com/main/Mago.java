package com.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Mago extends Personagem {

    public Mago(JButton[] botoes) {
        super("Mago", "Torre Arcana de Eldoria", 
             "Os magos da Torre Arcana passam décadas estudando os segredos do universo. Você foi escolhido para recuperar um artefato antigo.", 
             botoes);

        adicionarOpcao("Partir imediatamente para a missão", area -> {
        	Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(area), 
                    "Você enfrenta perigos e chega a uma encruzilhada...");
            adicionarOpcoesEncruzilhada(area, botoes);
        });
        
        adicionarOpcao("Consultar o Arquimago", area -> {
            Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(area), 
                                  "O Arquimago lhe conta sobre um perigo oculto...");
            
            new javax.swing.Timer(100, e -> {
                ((javax.swing.Timer)e.getSource()).stop();
                adicionarOpcoesArquimago(area, botoes);
            }).start();
        });
       }

    private void adicionarOpcoesEncruzilhada(JTextArea area, JButton[] botoes) {
        limparOpcoes();

        adicionarOpcao("Explorar a floresta sombria", areaTexto -> {
            Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(areaTexto), 
                                  "Você encontra criaturas sombrias e precisa lutar...");
            finalizarCiclo(areaTexto, botoes);
        });

        adicionarOpcao("Seguir pelo caminho das montanhas", areaTexto -> {
            Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(areaTexto), 
                                  "O caminho é perigoso, mas você avista o artefato ao longe...");
            finalizarCiclo(areaTexto, botoes);
        });

        iniciarAventura(area, botoes);
    }
    
    private void adicionarOpcoesArquimago(JTextArea area, JButton[] botoes) {
        limparOpcoes();
        
        adicionarOpcao("Pedir ajuda ao Arquimago", areaTexto -> {
            Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(areaTexto), 
                                  "O Arquimago concorda em ajudá-lo, mas exige algo em troca...");
            finalizarCiclo(areaTexto, botoes);
        });

        adicionarOpcao("Investigar o perigo sozinho", areaTexto -> {
            Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(areaTexto), 
                                  "Você parte em uma missão solitária e perigosa...");
            finalizarCiclo(areaTexto, botoes);
        });
        
        iniciarAventura(area, botoes);
    }
}