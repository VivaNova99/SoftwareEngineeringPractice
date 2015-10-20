/**
 * Created by veraivanova on 20.10.15.
 */
/*
Класс Clock - простая реализация цифровых часов.
Принцип работы :
После инициализации приложение "засыпает" и через каждую секунду "просыпается",
считывает текущее время и выводит его на экран.
 */
package clock;

/*
Clock - простая реализация цифровых часов.
 */
import java.awt.*;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Clock extends JFrame implements Runnable {

    Graphics g;
    Thread motor;
    Date date;
    Dimension d;
    //    JComponent comp = new JPanel();
    public static void main(String[] s) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Clock cl = new Clock();
                cl.init();
                cl.setVisible(true);
                cl.start();
                cl.run();
            }
        });
    }
    /*
    В мeтоде init происходит первичная инициализация (устанавливается
    необходимый шрифт для изображения времени, запоминаются размеры
    окна , устанавливается цвет фона.
     */

    public void init() {
        setSize(100, 100);
        g = getGraphics();
        date = new Date();
        Font fn = new Font("Helvetica", Font.PLAIN, 24);
        setFont(fn);
        d = this.size();
        setBackground(Color.white);
//        add(comp);
    }

    /*
    Создается объект класса Thread и запускается процесс , связанный с
    объектом motor
     */
    public void start() {
        if (motor == null) {
            motor = new Thread(this);
        }
        motor.start();
    }

    /*
    Выполнение программы приостанавливается
     */
    public void stop() {
        motor.stop();
        motor = null;
    }

    /*
    При запуске процесса , ассоциированного с объектом motor
    устанавливается минимальный приоритет для процесса
    затем, программа входит в бесконечный цикл
    где считывается текущее время , окно перерисовывается и
    процесс вновь засыпает на 1 секунду
    После чего все повторяется
     */
    public void run() {
        motor.setPriority(Thread.MIN_PRIORITY);
        while (motor != null) {
            date = new Date();
            paintComponent(getGraphics());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            ;
        }
    }

    /*
    В методе происходит перерисовка окна.
    Из объекта date вынимаются количество часов, минут и секунд
    и помещаются в строку ss , разделенные символом ':'
    Затем строка центрируется по высоте и ширине окна и
    выводится на экран
     */
    protected void paintComponent(Graphics g) {
        super.paintComponents(super.getGraphics());
        g.setColor(Color.black);
        FontMetrics fm = g.getFontMetrics();
        int hi = date.getHours();
        int mi = date.getMinutes();
        int si = date.getSeconds();
        String s, m, h;
        if (si < 10) {
            s = "0" + si;
        } else {
            s = "" + si;
        }
        if (mi < 10) {
            m = "0" + mi;
        } else {
            m = "" + mi;
        }
        if (hi < 10) {
            h = "0" + hi;
        } else {
            h = "" + hi;
        }
        String ss = h + ":" + m + ":" + s;
        int x = (d.width - fm.stringWidth(ss)) / 2;
        int y = fm.getAscent() + (d.height - (fm.getAscent() +
                fm.getDescent())) / 2;
        g.drawString(ss, x, y);
    }
}
