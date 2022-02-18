package gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import db.DbException;
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
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController  implements Initializable{
	
	private Department entity;	
	
	private DepartmentService service;
	
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
	
	
	public void setDepartment(Department entity) {
		this.entity = entity; 
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
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
			Utils.currentStage(event).close();
		}
		catch(DbException e) {
			Alerts.showAlerts("SQL EXCEPTION","ERRO", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private Department getFormData() {
		Department obj = new Department();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
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
	
	
	

}
