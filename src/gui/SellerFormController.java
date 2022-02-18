package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listners.DataChangeListner;
import gui.util.Alerts;
import gui.util.Restricoes;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController  implements Initializable{
	
	private Seller entity;	
	
	private SellerService service;
	
	private List<DataChangeListner> dataChangeListners = new ArrayList<>();
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private Label labelErroName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	
	public void setSeller(Seller entity) {
		this.entity = entity; 
	}
	
	public void setSellerService(SellerService service) {
		this.service = service;
	}
	
	
	public void subscribeDataChangeListner(DataChangeListner listner) {
		dataChangeListners.add(listner);
	}
	
	public void onBtSaveAction(ActionEvent event) {
		
		if(entity == null) {
			throw new IllegalStateException("Erro entity vazio");
		}
		if(service == null) {
			throw new IllegalStateException("Erro service vazio");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListner();
			Utils.currentStage(event).close();
		}
		catch(ValidationException e) {
			setErrorMessage(e.getErrors());
		}
		catch(DbException e) {
			Alerts.showAlerts("SQL EXCEPTION","ERRO", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private void notifyDataChangeListner() {
		for(DataChangeListner listner : dataChangeListners) {
			listner.onDataChanged();
		}
		
	}

	private Seller getFormData() {
		Seller obj = new Seller();
		ValidationException exception = new ValidationException("Erro na validação");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "O campo não pode ser vazio");
		}
		obj.setName(txtName.getText());
		
		if(exception.getErrors( ).size() > 0) {
			throw exception;
		}
		return obj;
	}

	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
		
	}
	
	private void initializeNodes() {
		Restricoes.setTextFieldInteger(txtId);
		Restricoes.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Erro: entity null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
	
	private void setErrorMessage(Map<String,String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("name")) {
			labelErroName.setText(errors.get("name"));
			
		}
		
		
	}
	

}
