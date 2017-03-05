package frs.gui.views;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DemoViewGUI implements Initializable {
	@FXML
	Label title, content;
	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public DemoViewGUI() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void setContent(String _title, String _content) {
		title.setText(_title);
		content.setText(_content);
	}
}
