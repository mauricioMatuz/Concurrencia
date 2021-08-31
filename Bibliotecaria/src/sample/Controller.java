package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import Modelo.Bibliotecaria;
import Modelo.EntregarLibro;
import Modelo.RentarLibro;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import javafx.scene.shape.Rectangle;


public class Controller implements Observer{

    private  Semaphore rentaSemaphore = new Semaphore(0);
    private  Semaphore turnoSemaphore = new Semaphore(0);
    private  Semaphore entregaSemaphore = new Semaphore(0);
    private int numClientes;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Image entregaM;
    @FXML
    private Image prestarM;
    @FXML
    private Image entregaF;
    @FXML
    private Image prestarF;
    private Rectangle rentas[] = new Rectangle[10];
    private Rectangle entregas[] = new Rectangle[10];
    public int contador=0;
    public int contador2=-1;
    public int contador3=0;
    public int contador4=-1;
    public int contador5=-1;
    public int contador6=-1;
    public int test = -1;



    @FXML
    public void iniciar(){
        entregaM = new Image(getClass().getResourceAsStream("/Resources/munecoEntrega.png"));
        entregaF = new Image(getClass().getResourceAsStream("/Resources/entregaFeliz.png"));
        prestarF = new Image(getClass().getResourceAsStream("/Resources/prestamoFeliz.png"));
        prestarM= new Image(getClass().getResourceAsStream("/Resources/munecoPresta.png"));
        Bibliotecaria bibliotecaria = new Bibliotecaria(rentaSemaphore, turnoSemaphore, entregaSemaphore);
        new Thread(bibliotecaria).start();
        bibliotecaria.addObserver(this);

        numClientes = 8;
        Timer fall = new Timer();
        TimerTask task = new TimerTask(){
            int i=0;
            @Override
            public void run(){
                System.out.println("Numero de cliente: "+i);
                if(i%3!=0) {
                    RentarLibro renta = new RentarLibro(rentaSemaphore, turnoSemaphore,entregaSemaphore);
                    new Thread(renta).start();
                    renta.addObserver(Controller.this::update);
                }else{
                    EntregarLibro entrega = new EntregarLibro(entregaSemaphore,turnoSemaphore,rentaSemaphore);
                    new Thread(entrega).start();
                    entrega.addObserver(Controller.this::update);
                }
                i++;
                if (i > numClientes) {
                    fall.cancel();
                }
            }
        };
        fall.schedule(task, 0, 2000);

    }

    @Override
    public void update(Observable o, Object args) {
        System.out.println(args); //imprime el argumento que recibe
        if (o instanceof EntregarLibro){
            if(args.equals("Quieren entregar un libro")){
                Rectangle entregaN = new Rectangle(32,30);
                entregaN.setFill(new ImagePattern(entregaM));
                entregaN.setLayoutY(315);
                entregaN.setLayoutX(62);
                Platform.runLater(()->{
                    anchor.getChildren().add(entregaN);
                });
                for(int i=0; i<=5; i++){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        System.out.println("Error F1");
                    }
                    entregaN.setLayoutY(entregaN.getLayoutY()+10);
                }
                for(int j=0; j<=11; j++){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        System.out.println("Error F2");
                    }
                    entregaN.setLayoutX(entregaN.getLayoutX()+10);
                }
                entregas[contador]=entregaN;
                contador++;
            }
        }else{
            if(o instanceof RentarLibro){
                if(args.equals("Quieren rentar un libro")){
                    Rectangle prestarN = new Rectangle(32,30);
                    prestarN.setFill(new ImagePattern(prestarM));
                    prestarN.setLayoutY(315);
                    prestarN.setLayoutX(62);
                    Platform.runLater(()->{
                        anchor.getChildren().add(prestarN);
                    });
                    for(int i=0; i<=4; i++){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        prestarN.setLayoutY(prestarN.getLayoutY()+10);
                    }
                    for(int j=0; j<=11; j++){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        prestarN.setLayoutX(prestarN.getLayoutX()+10);
                    }
                    rentas[contador3]=prestarN;
                    contador3++;
                }
            }
        }

        if (o instanceof Bibliotecaria) {
            if (args.equals("Van a depositar un libro")) {
                contador2++;
                boolean x=false;
                while(!x){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        System.out.println("Error F1");
                    }
                    if(entregas[contador2].getLayoutY()<185){
                        entregas[contador2].setLayoutY(entregas[contador2].getLayoutY()+5);
                    }
                    if(entregas[contador2].getLayoutX()<450) {
                        entregas[contador2].setLayoutX(entregas[contador2].getLayoutX() + 5);
                    }
                    if(entregas[contador2].getLayoutX()>=450 && entregas[contador2].getLayoutY()>=185){
                        x=true;
                    }
                }
            }else{
                if(args.equals("Buscando libro")){
                    contador4++;
                    boolean x=false;
                    while(!x){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            System.out.println("Error F1");
                        }
                        if(rentas[contador4].getLayoutY()>185){
                            rentas[contador4].setLayoutY(rentas[contador4].getLayoutY()-5);
                        }
                        if(rentas[contador4].getLayoutX()<450) {
                            rentas[contador4].setLayoutX(rentas[contador4].getLayoutX() + 5);
                        }
                        if(rentas[contador4].getLayoutX()>=450 && rentas[contador4].getLayoutY()>=185){
                            x=true;
                        }
                    }
                }
            }


            if(args.equals("Se va despues de depositar")){
                contador5++;
                Platform.runLater(()->entregas[contador5].setFill(new ImagePattern(entregaF)));
                for(int i=0;i<5;i++){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        System.out.println("Error F1");
                    }
                    entregas[contador5].setLayoutY(entregas[contador5].getLayoutY()+10);
                }
                for(int i=0;i<20;i++){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        System.out.println("Error F1");
                    }
                    entregas[contador5].setLayoutX(entregas[contador5].getLayoutX()+10);
                }
            }else{
                if(args.equals("Se va con libro rentado")){
                    contador6++;
                    Platform.runLater(()->rentas[contador6].setFill(new ImagePattern(prestarF)));
                    for(int i=0;i<20;i++){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            System.out.println("Error F1");
                        }
                        rentas[contador6].setLayoutY(rentas[contador6].getLayoutY()+10);
                    }
                    for(int i=0;i<20;i++){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            System.out.println("Error F1");
                        }
                        rentas[contador6].setLayoutX(rentas[contador6].getLayoutX()+10);
                    }
                }
            }
        }
    }
}