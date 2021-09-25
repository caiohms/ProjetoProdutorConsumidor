package Exercicio;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class LineChart extends JPanel {

    long startTime;

    ArrayList<Long> tFabricacaoLog, tEntregaLog;
    ArrayList<Long> updateTimestampHistory;
    ArrayList<Long> timeFabricacoesPlotadas;
    ArrayList<Long> timeEntregasPlotadas;
    ArrayList<Integer> xAxisDays;

    private Semaphore mutexLogFabricante;
    private Semaphore mutexLogEntregador;

    double yAxisRatio = 10.;
    int y = 620;
    int espacamentoX = 100;
    long lastDayTimestamp;
    int dayCounter = 0;
    int maxX;

    LineChart(long startTime, ArrayList<Long> tFabricacaoLog, ArrayList<Long> tEntregaLog, Semaphore mutexLogFabricante,
              Semaphore mutexLogEntregador) {
        this.startTime = startTime;
        this.tFabricacaoLog = tFabricacaoLog;
        this.tEntregaLog = tEntregaLog;
        this.mutexLogFabricante = mutexLogFabricante;
        this.mutexLogEntregador = mutexLogEntregador;

        this.updateTimestampHistory = new ArrayList<>();
        updateTimestampHistory.add(startTime);

        this.timeFabricacoesPlotadas = new ArrayList<>();
        timeFabricacoesPlotadas.add(0L);

        this.timeEntregasPlotadas = new ArrayList<>();
        timeEntregasPlotadas.add(0L);

        this.xAxisDays = new ArrayList<>();
        xAxisDays.add(0);

        this.setLayout(null);
        this.setPreferredSize(new Dimension(0, y));

        this.lastDayTimestamp = System.nanoTime();
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

        long currentTime;

        currentTime = System.nanoTime();

        // eixo X = updateTimestampHistory
        // eixo Y = valores dos tempos de fabricacao e transporte

        //g2.clearRect(0, 0, 2000, 2000);

        int timestampElements = updateTimestampHistory.size();

        if ((currentTime - lastDayTimestamp) > 14400000000L) {
            xAxisDays.add(maxX);
            lastDayTimestamp = currentTime;

            // media tempo de fabricacao
            try {
                mutexLogFabricante.acquire();
                mutexLogEntregador.acquire();


                updateTimestampHistory.add(currentTime);
                long mediaFab = calcularMediaEzerarArray(tFabricacaoLog);
                if (mediaFab > 0)
                    timeFabricacoesPlotadas.add(mediaFab);
                else
                    timeFabricacoesPlotadas.add(timeFabricacoesPlotadas.get(timeFabricacoesPlotadas.size()-1)); // se a media retornar zero repetimos o valor

                long mediaEnt = calcularMediaEzerarArray(tEntregaLog);
                if (mediaEnt > 0)
                    timeEntregasPlotadas.add(mediaEnt);
                else
                    timeEntregasPlotadas.add(timeEntregasPlotadas.get(timeEntregasPlotadas.size()-1)); // se a media retornar zero repetimos o valor

                mutexLogFabricante.release();
                mutexLogEntregador.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }

        dayCounter = 1;

        for (int i = 1; i < timestampElements; i++) {

            // desenhamos retas do ponto anterior ate a nova origem. 1px no eixo Y = 200ms
            // lembrando que o eixo Y esta invertido

            try {
                double oldY, newY;

                oldY = timeFabricacoesPlotadas.get(i - 1) / 1000000. / yAxisRatio;
                newY = timeFabricacoesPlotadas.get(i) / 1000000. / yAxisRatio;

                if (newY > getHeight() / 1.1) yAxisRatio += 5;

                g2.setPaint(Color.RED);
                g2.drawLine(drawingX, y + 1 - (int) oldY, drawingX + espacamentoX, y + 1 - (int) newY);

                if (i + 1 == updateTimestampHistory.size())
                    g2.drawString((timeFabricacoesPlotadas.get(i) / 10000000 + "min"), drawingX + 5, y - 10 - (int) newY);

                oldY = timeEntregasPlotadas.get(i - 1) / 1000000. / yAxisRatio;
                newY = timeEntregasPlotadas.get(i) / 1000000. / yAxisRatio;

                if (newY > getHeight() / 1.1) yAxisRatio += 5;

                g2.setPaint(Color.GREEN);
                g2.drawLine(drawingX, y - 1 - (int) oldY, drawingX + espacamentoX, y - 1 - (int) newY);

                if (i + 1 == updateTimestampHistory.size())
                    g2.drawString((timeEntregasPlotadas.get(i) / 10000000 + "min"), drawingX + 5, y - 10 - (int) newY);

                // desenha linha do dia
                if (xAxisDays.contains(drawingX)) {
                    g2.setPaint(Color.LIGHT_GRAY);
                    g2.drawLine(drawingX + espacamentoX, 0, drawingX + espacamentoX, y);
                    g2.drawString("Dia " + dayCounter++, drawingX + 3 , 25);
                }

                drawingX += espacamentoX; // avancamos 100 pixels para desenhar a proxima reta

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        maxX = drawingX;

        g2.dispose();
        this.setPreferredSize(new Dimension(100 + drawingX, y));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, this);
    }

    public long calcularMediaEzerarArray(ArrayList<Long> arrayList) {
        long sum = 0L;
        for (Long l : arrayList) {
            sum += l;
        }
        if (arrayList.size() > 0)
            sum = sum / arrayList.size();
        arrayList.clear();
        return sum;
    }

}


