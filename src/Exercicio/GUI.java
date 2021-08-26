package Exercicio;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class GUI extends JFrame implements Runnable {

    private FilaVenda filaVendas;
    private FilaEntrega filaEntregas;
    private long startTime;
    private ArrayList<Long> tFabricacaoLog;
    private ArrayList<Long> tEntregaLog;
    private int[] iFA; private int[] iFB; private int[] iFC; private int[] iFD; private int[] iTA; private int[] iTB;
    private int[] iLA; private int[] iLB; private int[] iLC; private int[] iLD; private int[] iLE; private int[] iLF;
    private int[] iLG; private int[] iLH;

    private Semaphore mutexLogFabricante;
    private Semaphore mutexLogEntregador;

    public GUI(FilaVenda filaVendas, FilaEntrega filaEntregas, long startTime, ArrayList<Long> tFabricacaoLog,
               ArrayList<Long> tEntregaLog, int[] iFA, int[] iFB, int[] iFC, int[] iFD, int[] iTA, int[] iTB, int[] iLA,
               int[] iLB, int[] iLC, int[] iLD, int[] iLE, int[] iLF, int[] iLG, int[] iLH, Semaphore mutexLogFabricante,
               Semaphore mutexLogEntregador) throws HeadlessException {
        this.filaVendas = filaVendas;
        this.filaEntregas = filaEntregas;
        this.startTime = startTime;
        this.tFabricacaoLog = tFabricacaoLog;
        this.tEntregaLog = tEntregaLog;
        this.iFA = iFA;
        this.iFB = iFB;
        this.iFC = iFC;
        this.iFD = iFD;
        this.iTA = iTA;
        this.iTB = iTB;
        this.iLA = iLA;
        this.iLB = iLB;
        this.iLC = iLC;
        this.iLD = iLD;
        this.iLE = iLE;
        this.iLF = iLF;
        this.iLG = iLG;
        this.iLH = iLH;
        this.mutexLogFabricante = mutexLogFabricante;
        this.mutexLogEntregador = mutexLogEntregador;
    }

    @Override
    public void run() {
        Font fonte20 = new Font("Consolas", Font.PLAIN, 20);
        Font fonte22 = new Font("Consolas", Font.PLAIN, 22);
        Font fonte26 = new Font("Consolas", Font.PLAIN, 26);

        Border border = BorderFactory.createLineBorder(Color.black, 1);

        JPanel filaVendasPanel = new JPanel();
        filaVendasPanel.setBounds(0, 0, 200, 660);
        filaVendasPanel.setBackground(new Color(230, 230, 230));
        filaVendasPanel.setBorder(border);

        JPanel filaEntregasPanel = new JPanel();
        filaEntregasPanel.setBounds(200, 0, 200, 660);
        filaEntregasPanel.setBackground(new Color(230, 230, 230));
        filaEntregasPanel.setBorder(border);

        JPanel statsPanel = new JPanel();
        statsPanel.setBounds(400, 0, 200, 660);
        statsPanel.setBackground(new Color(230, 230, 230));
        statsPanel.setBorder(border);

        JPanel chartPanel = new JPanel();
        chartPanel.setBounds(600, 0, 700, 660);
        chartPanel.setBackground(new Color(220, 220, 220));
        chartPanel.setBorder(border);

        // Fila vendas panel
        JLabel labelFilaVendas = new JLabel();
        labelFilaVendas.setText("Fila vendas");
        labelFilaVendas.setFont(fonte26);

        JLabel labelSizeFilaVendas = new JLabel();
        labelSizeFilaVendas.setText("Itens: ");
        labelSizeFilaVendas.setFont(fonte26);

        JLabel labelVendasCounter = new JLabel();
        labelVendasCounter.setText("0");
        labelVendasCounter.setFont(fonte26);

        JList<String> vList = new JList<>(filaVendas.getListModel());
        vList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vList.setLayoutOrientation(JList.VERTICAL);
        vList.setVisibleRowCount(-1);
        vList.setFont(fonte20);

        JScrollPane vListScrollPane = new JScrollPane(vList);
        vListScrollPane.setPreferredSize(new Dimension(100, 550));

        // Fila entregas panel
        JLabel labelFilaEntregas = new JLabel();
        labelFilaEntregas.setText("Fila entregas");
        labelFilaEntregas.setFont(fonte26);

        JLabel labelSizeFilaEntregas = new JLabel();
        labelSizeFilaEntregas.setText("Itens: ");
        labelSizeFilaEntregas.setFont(fonte26);

        JLabel labelEntregasCounter = new JLabel();
        labelEntregasCounter.setText("0");
        labelEntregasCounter.setFont(fonte26);

        JList<String> eList = new JList<>(filaEntregas.getListModel());
        eList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eList.setLayoutOrientation(JList.VERTICAL);
        eList.setVisibleRowCount(-1);
        eList.setFont(fonte20);

        JScrollPane eListScrollPane = new JScrollPane(eList);
        eListScrollPane.setPreferredSize(new Dimension(100, 550));

        // Stats panel ====================

        JLabel labelStats = new JLabel();
        labelStats.setText("Estatisticas");
        labelStats.setFont(fonte26);

        JLabel labelStatsFabricantes = new JLabel();
        labelStatsFabricantes.setFont(fonte22);

        // Chart Panel ====================

        LineChart chart = new LineChart(startTime, tFabricacaoLog, tEntregaLog, mutexLogFabricante, mutexLogEntregador);
        JScrollPane chartScrollPane = new JScrollPane(chart);
        chartScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        chartScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        chartScrollPane.setPreferredSize(new Dimension(660, 650));
        chartScrollPane.setBorder(border);

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


            // ajuste da velocidade de atualizacao do grafico (ajuste o numero do resto, quanto maior mais lento, 1=max)
            if (i % 1 == 0) {
                i = 0;
                chart.updateBuffer();
                chart.revalidate();
                chart.repaint();
            }
            i++;

            chartScrollPane.revalidate();
            chartScrollPane.repaint();
            if (!usuarioAjustandoScroll[0])
                chartScrollPane.getHorizontalScrollBar().setValue(chartScrollPane.getHorizontalScrollBar().getMaximum());
        }
    }
}

