/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxStati"
    private ComboBox<String> cmbBoxStati; // Value injected by FXMLLoader

    @FXML // fx:id="btnVisualizzaVelivoli"
    private Button btnVisualizzaVelivoli; // Value injected by FXMLLoader

    @FXML // fx:id="txtT"
    private TextField txtT; // Value injected by FXMLLoader

    @FXML // fx:id="txtG"
    private TextField txtG; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.model.creagrafo();
    	this.cmbBoxStati.getItems().addAll();
    	List<String> stati = model.getStati();
    	cmbBoxStati.getItems().addAll(stati);
    	this.btnVisualizzaVelivoli.setDisable(false);
    	this.btnSimula.setDisable(false);
    
    }

    @FXML
    void doSimula(ActionEvent event) {
    	String g = txtG.getText();
    	String t = txtT.getText();
    	String stato = this.cmbBoxStati.getValue();
    	if(g==null) {
    		txtResult.setText("Devi inserire il numero di giorni!!!");
    		return;
    	}
    	if(t==null) {
    		txtResult.setText("Devi inserire il numero di viaggiatori!!!");
    		return;
    	}
    	if(stato==null) {
    		txtResult.setText("Devi selezionare uno stato di partenza!!!");
    		return;
    	}
    	int giorni = Integer.parseInt(g);
    	int persone = Integer.parseInt(t);
    	if(giorni==0 || persone==0) {
    		txtResult.appendText("i numeri inseriti devono essere maggiori di zero!!!");
    		return;
    	}
    	txtResult.setText(this.model.simula(persone, giorni, stato));
    	
    }

    @FXML
    void doVisualizzaVelivoli(ActionEvent event) {
    	
    	String stato = this.cmbBoxStati.getValue();    	

    	if(stato==null) {
    		txtResult.setText("Devi selezionare uno stato!");
    		return;
    	}
    	String s = model.getVeicoli(stato);
    	txtResult.setText(s);
    	
    }
    
    public void setModel(Model model) {
		this.model = model;
		
	}

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxStati != null : "fx:id=\"cmbBoxStati\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnVisualizzaVelivoli != null : "fx:id=\"btnVisualizzaVelivoli\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert txtT != null : "fx:id=\"txtT\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert txtG != null : "fx:id=\"txtG\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        this.btnVisualizzaVelivoli.setDisable(true);
        this.btnSimula.setDisable(true);
    }
}