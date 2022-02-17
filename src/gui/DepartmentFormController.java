package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Restricoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController  implements Initializable{
	
	private Department entity;	
	
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
	
	public void onBtSaveAction() {
		System.out.println("salvou");
		
	}
	
	public void onBtCancelAction() {
		System.out.println("Cancelou");
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
