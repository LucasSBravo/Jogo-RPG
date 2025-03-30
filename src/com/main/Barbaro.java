package com.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Barbaro extends Personagem {

    public Barbaro(JButton[] botoes) {
        super("Bárbaro", "Tribo das Montanhas Uivantes",
                "Você é um guerreiro das montanhas, conhecido por sua força e fúria. Sua tribo foi atacada e você busca vingança.",
                botoes);
        
        adicionarOpcao("Seguir o rastro dos invasores", area -> {
            Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(area),
                                  "Você encontra um acampamento inimigo e se prepara para o ataque...");
            adicionarOpcoesAtaque(area, botoes);
        });

        adicionarOpcao("Buscar aliados nas aldeias próximas", area -> {
            Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(area),
                                  "Você viaja para a aldeia vizinha em busca de ajuda...");
            adicionarOpcoesAldeia(area, botoes);
        });
    }

    private void adicionarOpcoesAtaque(JTextArea area, JButton[] botoes) {
        limparOpcoes();
        
        adicionarOpcao("Atacar o acampamento diretamente", areaTexto -> {
            Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(areaTexto),
                                  "Você entra em fúria e ataca com toda a sua força!");
            finalizarCiclo(areaTexto, botoes);
        });

        adicionarOpcao("Infiltrar-se sorrateiramente", areaTexto -> {
            Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(areaTexto),
                                  "Você se move silenciosamente, eliminando os guardas um a um.");
            finalizarCiclo(areaTexto, botoes);
        });
        
        iniciarAventura(area, botoes);
    }

    private void adicionarOpcoesAldeia(JTextArea area, JButton[] botoes) {
        limparOpcoes();
        
        adicionarOpcao("Pedir ajuda ao líder da aldeia", areaTexto -> {
            Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(areaTexto),
                                  "O líder concorda em ajudar, mas exige uma prova de sua força.");
            finalizarCiclo(areaTexto, botoes);
        });
        
        adicionarOpcao("Buscar uma rede de informações para descobrir o motivo do ataque", areaTexto -> {
            Principal.exibirDialogo((JFrame)SwingUtilities.getWindowAncestor(areaTexto),
                                  "Você entra em uma casa aparentemente abandonada no fim da Aldeia");
            finalizarCiclo(areaTexto, botoes);
        });
        
        iniciarAventura(area, botoes);
    }
}