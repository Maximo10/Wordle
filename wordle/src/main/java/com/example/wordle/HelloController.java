package com.example.wordle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class HelloController {

    @FXML
    private TextField entradaPalabra;
    @FXML
    private Label estado_juego,lblAciertos,lblFallos,lblIntentos,lblPalabraBuscada;

    //Ids_Tableros
    @FXML private TextField tf00,tf01,tf02,tf03,tf04;
    @FXML private TextField tf05,tf06,tf07,tf08,tf09;
    @FXML private TextField tf10,tf11,tf12,tf13,tf14;
    @FXML private TextField tf15,tf16,tf17,tf18,tf19;
    @FXML private TextField tf20,tf21,tf22,tf23,tf24;

    //Inten-Limite
    private final int limite_letras=5;
    private final int max_intentos=5;

    //array de palabras
    private String[] palabras={
            "CASAS", "ARBOL", "NUBES", "LUNAS", "PERRO", "GATOS", "HUEVO", "SUEÑO",
            "SOLAR", "LLAVE", "TIERRA", "RAPTO", "FLORO", "MARCO", "VIDAS", "RELOJ",
            "CAMPO", "PLANO", "BOSCO", "FRUTA", "CIELO", "SILLA", "GRADO", "PANAL",
            "MANGO", "PERLA", "TRAZO", "NIEVE", "RUIDO", "SONAR", "FUEGO", "LIBRO",
            "PLAYA", "ROCAS", "SALTO", "BRISA", "VIAJE", "MONTE", "TIGRE", "CABRA",
            "OJOSO", "LARGO", "BOMBA", "CORAL", "AGUAO", "REYES", "HUMOR", "FLOTA",
            "DULCE", "GRANO", "LOBOS", "TARDE", "NADAR", "MOTOR", "JUEGO", "MUEVE",
            "VELAR", "CANTO", "SALUD", "CLAVE","SABOR", "SALUD", "SUEÑO", "SUELO",
            "SUEVA","TACOS", "TALCO", "TAMBO", "TANGO", "TARDO", "ALABA", "ALCOS",
            "ALINE", "ALMOR", "ALUDA", "ASADO", "ASESO", "ASIST", "ASTRO", "ATADO",
            "GRIFO"
    };

    //Datos juego
    private String PalabraBuscada;
    private int intento_actual=0;
    private int aciertos=0;
    private int fallos=0;


    private TextField[][] Tablero;


    //inicio del tablero
    @FXML
    public void initialize() {
        PalabraBuscada=palabra_aleatoria();

        // Inicializar el tablero con referencias a los TextFields
        Tablero = new TextField[][]{
                {tf00, tf01, tf02, tf03, tf04},
                {tf05, tf06, tf07, tf08, tf09},
                {tf10, tf11, tf12, tf13, tf14},
                {tf15, tf16, tf17, tf18, tf19},
                {tf20, tf21, tf22, tf23, tf24}
        };

        limpiarTablero();
        estado_juego.setText("Intento 1 de " + max_intentos);
        System.out.println("La palabra secreta es: "+PalabraBuscada);
    }

    //funcion para limpiar el tablero
    private void limpiarTablero(){
        for(int i=0;i<max_intentos;i++){
            for(int j=0;j<limite_letras;j++){
                Tablero[i][j].setText("");
                Tablero[i][j].setStyle("-fx-text-fill:white;-fx-border-color:gray");
            }
        }
    }

    //funcio para obtener palabra aleatoria
    private String palabra_aleatoria(){
        Random aleatorio= new Random();
        return palabras[aleatorio.nextInt(palabras.length)];
    }

    //funcion de enviar
    @FXML
    private void Enviar(){
        //guardar la palabra introducida
        String palabra=entradaPalabra.getText().toUpperCase();
        //Comprobar que la palabra sea de 5 letras
        if(palabra.length()!=limite_letras){
            //En caso de que no sea mostrar mensaje
            Alert alerta= new Alert(AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error en la longitud de la palabra");
            alerta.setContentText("La palabra debe tener exactamente 5 letras.");
            alerta.showAndWait();
            return;
        }
        //comprobador de numero de intentos
        if(intento_actual>=max_intentos){
            //En caso de que se supere el limite mostrar mensaje
            Alert alerta= new Alert(AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("¡Juego Terminado!");
            alerta.setContentText("Ya no tienes más intentos disponibles.");
            alerta.showAndWait();
            return;
        }

        //actualizar tablero
        for(int i=0;i<limite_letras;i++){
            TextField cell=Tablero[intento_actual][i];
            char letra=palabra.charAt(i);
            cell.setText(String.valueOf(letra));

            if(letra==PalabraBuscada.charAt(i)){
                cell.setStyle("-fx-background-color: #6aaa64; -fx-text-fill:white;-fx-border-color:gray");
            }else if(PalabraBuscada.contains(String.valueOf(letra))){
                cell.setStyle("-fx-background-color: #c9b458; -fx-text-fill:white;-fx-border-color:gray");
            }else {
                cell.setStyle("-fx-background-color: #787c7e; -fx-text-fill:white;-fx-border-color:gray");
            }
        }

        //aumentar los inetentos realizados
        intento_actual++;

        //actualizar los datos de la partida
        lblIntentos.setText("Nº de intentos: " + intento_actual + " / "+ max_intentos);
        estado_juego.setText("Intento " + (intento_actual + 1 ) + " de " + max_intentos);

        //limpiar palabras
        entradaPalabra.clear();

        //controlar acierto obtenidos
        if(palabra.equals(PalabraBuscada)){

            aciertos++;
            //mostrar aciertos
            lblAciertos.setText("Acierto: " + aciertos);
            //mostra la palabra objetivo
            lblPalabraBuscada.setText("Palabra Buscada: " + PalabraBuscada);
            //notificar de acierto
            System.out.println("!Acertaste La palabra era: " + PalabraBuscada);
            //deshabilitar la palabra de volver a usarse
            entradaPalabra.setDisable(true);
            // Mostrar popup de victoria
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("¡Victoria!");
            alert.setHeaderText("¡Felicitaciones!");
            alert.setContentText("¡Has acertado la palabra '" + PalabraBuscada + "' en " + intento_actual + " intentos!");
            alert.showAndWait();
        }else if(intento_actual==max_intentos){
            //añadir fallos al contador
            fallos++;
            //actualizar info contador de fallos
            lblFallos.setText("Fallos:" + fallos);
            //mostra la palabra objetivo
            lblPalabraBuscada.setText("La palabra bucada era: " + PalabraBuscada);
            //notificar de fin del limite deintenos
            System.out.println("Se acabaron los intentos. La palabra ojetivo era: " + PalabraBuscada);
            // Mostrar popup de derrota
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("¡Game Over!");
            alert.setHeaderText("¡Has agotado los intentos!");
            alert.setContentText("La palabra correcta era '" + PalabraBuscada + "'. ¡Intentalo de nuevo!");
            alert.showAndWait();
        }
    }

    //funcion de volver a jugar
    @FXML
    public void Reiniciar(){
        //obtener nueva palabra aleatoria
        PalabraBuscada=palabra_aleatoria();
        System.out.println("La palabra secreta es: "+PalabraBuscada);
        //reiniciar intentos
        intento_actual=0;
        estado_juego.setText("Intento 1 de " + max_intentos);
        lblIntentos.setText("Nº de intentos 0 / " + max_intentos);
        lblPalabraBuscada.setText("Palabra buscada:_ _ _ _ _");
        entradaPalabra.clear();
        entradaPalabra.setDisable(false);
        limpiarTablero();
    }

    @FXML
    public void onLetraPresionada(ActionEvent event) {
        Button boton = (Button) event.getSource();
        String letra = boton.getText();
        // borrar última letra
        if (letra.equals("<=")) {
            String texto = entradaPalabra.getText();
            if (!texto.isEmpty()) {
                entradaPalabra.setText(texto.substring(0, texto.length() - 1));
            }
            return;
        }
        if (letra.equalsIgnoreCase("Enter")) { // enviar palabra
            Enviar();
            return;
        }
        // añadir letra solo si no superamos el límite
        String textoActual = entradaPalabra.getText();
        if (textoActual.length() < limite_letras) {
            entradaPalabra.setText((textoActual + letra).toUpperCase());
        }
    }

}