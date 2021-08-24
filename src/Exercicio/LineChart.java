package Exercicio;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LineChart extends JPanel {

    long startTime;


    ArrayList<Long> tFabricacaoLog, tEntregaLog;

    ArrayList<Long> updateTimestampHistory;
    ArrayList<Long> timeFabricacoesPlotadas;
    ArrayList<Long> timeEntregasPlotadas;

    double yAxisRatio = 10.;

    LineChart(long startTime, ArrayList<Long> tFabricacaoLog, ArrayList<Long> tEntregaLog) {
        this.startTime = startTime;
        this.tFabricacaoLog = tFabricacaoLog;
        this.tEntregaLog = tEntregaLog;

        this.updateTimestampHistory = new ArrayList<>();
        updateTimestampHistory.add(startTime);

        this.timeFabricacoesPlotadas = new ArrayList<>();
        timeFabricacoesPlotadas.add(0L);

        this.timeEntregasPlotadas = new ArrayList<>();
        timeEntregasPlotadas.add(0L);

        this.setPreferredSize(new Dimension(0, 660));
    }

    BufferedImage buffer; // this is an instance variable

    public void updateBuffer() {

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = buffer.getGraphics();

        int drawingX = 0; // valor sobre o eixo X onde vamos desenhar

        Graphics2D g2 = (Graphics2D) g;

        g2.setPaint(Color.RED);
        g2.setStroke(new BasicStroke(3));
        g2.setFont(new Font("Consolas", Font.PLAIN, 22));
        // g2.drawLine(0, 620, 2000, 0);

        long currentTime = System.nanoTime();

        updateTimestampHistory.add(currentTime);

        //utilizamos os ultimos valores calculados para desenhar o grafico... é o jeito

        // isso aqui embaixo eh um crime contra a humanidade
        // basicamente se eu ler um ArrayList enquanto uma thread modifica
        // o java lanca um ConcurrentModificationException
        // como nao acontece com tanta frequencia (algumas vezes por min)
        // cometi esse crime aqui embaixo.. e funcionou

        // funciona assim, a contagem de pontos calculados para o grafico eh baseada na quantidade de elementos no
        // ArrayList updateTimestampHistory. se alguma das leituras abaixo lancar uma Exception entao todos os valores
        // daquele frame lido sao ignorados.
        // o grafico atualiza mais ou menos 50 vezes por segundo (sem exceptions), em velocidade maxima

        // vou corrigir depois que conseguir obter a media dos valores para popular o grafico da forma certa
        // (uma reta no grafico pra cada dia)
        try {
            timeFabricacoesPlotadas.add(tFabricacaoLog.get(tFabricacaoLog.size() - 1));
            try {
                timeEntregasPlotadas.add(tEntregaLog.get(tEntregaLog.size() - 1));
            } catch (Exception e) {
                timeFabricacoesPlotadas.remove(timeFabricacoesPlotadas.get(timeFabricacoesPlotadas.size() - 1));
                updateTimestampHistory.remove(updateTimestampHistory.get(updateTimestampHistory.size() - 1));
            }
        } catch (Exception e) {
            updateTimestampHistory.remove(updateTimestampHistory.get(updateTimestampHistory.size() - 1));
        }


        // eixo X = updateTimestampHistory
        // eixo Y = valores dos tempos de fabricacao e transporte

        g2.clearRect(0, 0, 2000, 2000);

        for (int i = 1; i < updateTimestampHistory.size() - 1; i++) {

            // desenhamos retas do ponto anterior ate a nova origem. 1px no eixo Y = 200ms

            // lembrando que o eixo Y esta invertido

            try {
                double oldY, newY;

                oldY = timeFabricacoesPlotadas.get(i - 1) / 1000000. / yAxisRatio;
                newY = timeFabricacoesPlotadas.get(i) / 1000000. / yAxisRatio;

                if (newY > getHeight() / 1.2) yAxisRatio *= 2;

                g2.setPaint(Color.RED);
                g2.drawLine(drawingX, 620 - (int) oldY, drawingX + 2, 620 - (int) newY);

                if (i + 2 == updateTimestampHistory.size())
                    g2.drawString((timeFabricacoesPlotadas.get(i) / 10000000 + "min"), drawingX, 610 - (int) newY);

                oldY = timeEntregasPlotadas.get(i - 1) / 1000000. / yAxisRatio;
                newY = timeEntregasPlotadas.get(i) / 1000000. / yAxisRatio;

                if (newY > getHeight() / 1.2) yAxisRatio *= 2;

                g2.setPaint(Color.GREEN);
                g2.drawLine(drawingX, 620 - (int) oldY, drawingX + 2, 620 - (int) newY);

                if (i + 2 == updateTimestampHistory.size())
                    g2.drawString((timeEntregasPlotadas.get(i) / 10000000 + "min"), drawingX, 610 - (int) newY);

                drawingX += 1; // avancamos 2 pixels para desenhar a proxima reta

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        g2.dispose();
        this.setPreferredSize(new Dimension(100 + drawingX, 660));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, this);
    }

}


