package Exercicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;

public class GUI extends JFrame {

    public GUI(FilaVenda filaVendas, FilaEntrega filaEntregas, long startTime, ArrayList<Long> tFabricacaoLog,
               ArrayList<Long> tEntregaLog, int[] iFA, int[] iFB, int[] iFC, int[] iFD, int[] iTA, int[] iTB,
               int[] iLA, int[] iLB, int[] iLC, int[] iLD, int[] iLE, int[] iLF, int[] iLG, int[] iLH) {
        // lol esse construtor

        JPanel filaVendasPanel = new JPanel();
        filaVendasPanel.setBounds(0, 0, 200, 660);
        filaVendasPanel.setBackground(new Color(230, 230, 230));
        filaVendasPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        JPanel filaEntregasPanel = new JPanel();
        filaEntregasPanel.setBounds(200, 0, 200, 660);
        filaEntregasPanel.setBackground(new Color(230, 230, 230));
        filaEntregasPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        JPanel statsPanel = new JPanel();
        statsPanel.setBounds(400, 0, 200, 660);
        statsPanel.setBackground(new Color(230, 230, 230));
        statsPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        JPanel chartPanel = new JPanel();
        chartPanel.setBounds(600, 0, 700, 660);
        chartPanel.setBackground(new Color(220, 220, 220));
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        // Fila vendas panel
        JLabel labelFilaVendas = new JLabel();
        labelFilaVendas.setText("Fila vendas");
        labelFilaVendas.setFont(new Font("Consolas", Font.PLAIN, 26));

        JLabel labelSizeFilaVendas = new JLabel();
        labelSizeFilaVendas.setText("Itens: ");
        labelSizeFilaVendas.setFont(new Font("Consolas", Font.PLAIN, 26));

        JLabel labelVendasCounter = new JLabel();
        labelVendasCounter.setText("0");
        labelVendasCounter.setFont(new Font("Consolas", Font.PLAIN, 26));

        JList<String> vList = new JList<>(filaVendas.getListModel());
        vList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vList.setLayoutOrientation(JList.VERTICAL);
        vList.setVisibleRowCount(-1);
        vList.setFont(new Font("Consolas", Font.PLAIN, 20));

        JScrollPane vListScrollPane = new JScrollPane(vList);
        vListScrollPane.setPreferredSize(new Dimension(100, 550));

        // Fila entregas panel
        JLabel labelFilaEntregas = new JLabel();
        labelFilaEntregas.setText("Fila entregas");
        labelFilaEntregas.setFont(new Font("Consolas", Font.PLAIN, 26));

        JLabel labelSizeFilaEntregas = new JLabel();
        labelSizeFilaEntregas.setText("Itens: ");
        labelSizeFilaEntregas.setFont(new Font("Consolas", Font.PLAIN, 26));

        JLabel labelEntregasCounter = new JLabel();
        labelEntregasCounter.setText("0");
        labelEntregasCounter.setFont(new Font("Consolas", Font.PLAIN, 26));

        JList<String> eList = new JList<>(filaEntregas.getListModel());
        eList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eList.setLayoutOrientation(JList.VERTICAL);
        eList.setVisibleRowCount(-1);
        eList.setFont(new Font("Consolas", Font.PLAIN, 20));

        JScrollPane eListScrollPane = new JScrollPane(eList);
        eListScrollPane.setPreferredSize(new Dimension(100, 550));

        // Stats panel ====================

        JLabel labelStats = new JLabel();
        labelStats.setText("Estatisticas");
        labelStats.setFont(new Font("Consolas", Font.PLAIN, 26));

        JLabel labelStatsFabricantes = new JLabel();
        labelStatsFabricantes.setFont(new Font("Consolas", Font.PLAIN, 22));

        // Chart Panel ====================

        LineChart chart = new LineChart(startTime, tFabricacaoLog, tEntregaLog);
        JScrollPane chartScrollPane = new JScrollPane(chart);
        chartScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chartScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        chartScrollPane.setPreferredSize(new Dimension(660, 640));
        chartScrollPane.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        // sem isso caso o usuario mexa no scroll, entra em conflito com o
        // chartScrollPane.getHorizontalScrollBar().setValue(chartScrollPane.getHorizontalScrollBar().getMaximum());
        boolean[] usuarioAjustandoScroll = {false};
        chartScrollPane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                usuarioAjustandoScroll[0] = e.getValueIsAdjusting();
            }
        });

        //=========================================

        filaVendasPanel.add(labelFilaVendas);
        filaVendasPanel.add(labelSizeFilaVendas);
        filaVendasPanel.add(labelVendasCounter);
        filaVendasPanel.add(vListScrollPane);

        filaEntregasPanel.add(labelFilaEntregas);
        filaEntregasPanel.add(labelSizeFilaEntregas);
        filaEntregasPanel.add(labelEntregasCounter);
        filaEntregasPanel.add(eListScrollPane);

        statsPanel.add(labelStats);
        statsPanel.add(labelStatsFabricantes);

        chartPanel.add(chartScrollPane);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Exercicio Produtor-Consumidor");
        this.setSize(1300, 700);
        this.setResizable(false);
        this.setLayout(null);
        this.add(filaVendasPanel);
        this.add(filaEntregasPanel);
        this.add(statsPanel);
        this.add(chartPanel);
        this.setVisible(true);

        //atualiza GUI a cada 20ms ~50fps
        int i = 0;
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            labelStatsFabricantes.setText("" +
                    "<html>" +
                    "Lojas<br/>" +
                    "A vendeu: " + iLA[0] + "<br/>" +
                    "B vendeu: " + iLB[0] + "<br/>" +
                    "C vendeu: " + iLC[0] + "<br/>" +
                    "D vendeu: " + iLD[0] + "<br/>" +
                    "E vendeu: " + iLE[0] + "<br/>" +
                    "F vendeu: " + iLF[0] + "<br/>" +
                    "G vendeu: " + iLG[0] + "<br/>" +
                    "H vendeu: " + iLH[0] + "<br/><br/>" +
                    "Fabricantes<br/>" +
                    "A fabricou: " + iFA[0] + "<br/>" +
                    "B fabricou: " + iFB[0] + "<br/>" +
                    "C fabricou: " + iFC[0] + "<br/>" +
                    "D fabricou: " + iFD[0] + "<br/><br/>" +
                    "Transportadoras<br/>" +
                    "A entregou: " + iTA[0] + "<br/>" +
                    "B entregou: " + iTB[0] + "<br/>" +
                    "</html>");

            vList.setModel(filaVendas.getListModel());
            labelVendasCounter.setText(String.valueOf(filaVendas.getSize()));

            eList.setModel(filaEntregas.getListModel());
            labelEntregasCounter.setText(String.valueOf(filaEntregas.getSize()));

            chartScrollPane.revalidate();
            chartScrollPane.repaint();
            if (!usuarioAjustandoScroll[0])
                chartScrollPane.getHorizontalScrollBar().setValue(chartScrollPane.getHorizontalScrollBar().getMaximum());

            // ajuste da velocidade de atualizacao do grafico (ajuste o numero do resto, quanto maior mais lento, 1=max)
            if (i % 1 == 0) {
                i = 0;
                chart.updateBuffer();
                chart.revalidate();
                chart.repaint();
            }
            i++;
        }
    }

}
