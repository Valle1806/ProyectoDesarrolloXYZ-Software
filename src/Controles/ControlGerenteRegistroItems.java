package Controles;


import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.RequiredFieldValidator;

import BaseDatos.DaoInventario;
import BaseDatos.DaoSede;

import com.jfoenix.validation.IntegerValidator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class ControlGerenteRegistroItems {
	
	@FXML
    private JFXTextField costofabItem;

    @FXML
    private JFXTextField nombreItem;

    @FXML
    private JFXTextField cantidadItems;

    @FXML
    private DatePicker fechaRegistroItem;

    @FXML
    private JFXTextField valorVentaItem;

    @FXML
    private JFXTextField colorItem;

    @FXML
    private JFXTextField materialItem;
    
    @FXML
    private JFXComboBox<String> referenciaItem;

    @FXML
    private JFXButton botonRegistroItems;
    

    @FXML
    private JFXComboBox<String> comboBoxSede;
    
    private boolean nuevo;
    private DaoSede cBox;
    
   
    private String nombre;
    private String cantidad;
    private String color;
    private String fecha = "now()";
    private String material;
    private String costofab;
    private String costoVenta;
    private String referencia;
    private String Sede;
    
    public void initialize() {
    	referenciaItem.valueProperty().addListener((o,oldVal,newVal)->{
   	     if(newVal != "Nuevo" && newVal != null){
   	    	 switchCampos(false);
   	    	 } else if(newVal == "Nuevo" && oldVal != "Nuevo") {
   	    		switchCampos(true); 
   	    	 }
   	     }
   	);
    	addFieldListener(nombreItem);
    	addFieldListener(colorItem);
    	addFieldListener(materialItem);
    	addNumberFieldListener(cantidadItems);
    	addNumberFieldListener(costofabItem);
    	addNumberFieldListener(valorVentaItem);
    	
    	
    }
    public void addFieldListener(Object itm) {// should be a reference
    	//a�adira validadores y escuchas para validacion al campo dado in real time.
    	
    	RequiredFieldValidator validator = new RequiredFieldValidator();
    	validator.setMessage("Datos Requeridos");
    	((IFXValidatableControl) itm).getValidators().add(validator);
    	
    }
    //a�ade validadores tanto para campo como para numeros.
    public void addNumberFieldListener(Object itm) {
    	IntegerValidator validador = new IntegerValidator();
    	validador.setMessage("Debe ser un numero");
    	((IFXValidatableControl) itm).getValidators().add(validador);
    	addFieldListener(itm);
    	
    }
    
    @FXML
    void validacion(KeyEvent e) {
 	((IFXValidatableControl) e.getTarget()).validate();

    }
   
public void limpiarCampos() {
	cantidadItems.clear();
    	costofabItem.clear();
    	colorItem.clear();
    	materialItem.clear();
    	valorVentaItem.clear();
    	comboBoxSede.setValue(null);
    	nombreItem.clear();
    	referenciaItem.setValue(null);
}
	
public void mostrarMensajeInformativo(String titulo, String mensaje) {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle(titulo);
	alert.setHeaderText(null);
	alert.setContentText(mensaje);
	alert.showAndWait();
}

public void mostrarMensaje(String titulo, String mensaje) {
    	Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
    }
	
    @FXML
    //se ejecuta al clickear el ComboBox que muestra las Sedes.
    void onClickedSedes() {
    	cBox = new DaoSede();
    	 List<String> listaSedes = cBox.obtenerNombresDeSedes();
    	 ObservableList<String> sedes = FXCollections.observableArrayList(listaSedes);
    	 comboBoxSede.setItems(sedes);
    }
    
    @FXML
    void switchCampos(boolean bit) {
    	
    	cantidadItems.setDisable(bit);
        costofabItem.setDisable(bit);
        colorItem.setDisable(bit);
        materialItem.setDisable(bit);
        valorVentaItem.setDisable(bit);
        comboBoxSede.setDisable(bit);
	nombreItem.setDisable(!bit);
        
    }
    @FXML 
    //se ejecuta al clickear el combobox que muestra las referencias.
    void onClickedReferencias() {
    	DaoInventario inventario = new DaoInventario();
    	List<String> listaReferencias = inventario.obtenerReferencias();
    	listaReferencias.add(0, "Nuevo");
    	ObservableList<String> referencias = FXCollections.observableArrayList(listaReferencias);
    	referenciaItem.setItems(referencias);
    	
    	
    }
    
    
    boolean verificarCampos(){
    	//se verifican que los campos cumplan condiciones de aceptacion.
    	if(
    verificarReferencia()
    &&
    verificarNombre()
    &&
    verificarColor()
    &&
    verificarMaterial()
    &&
    verificarCostoFabricacion()
    &&
    verificarCostoVenta()
    &&
    verificarCantidad()
    && 
    verificarSede()
    ) {return true;}
    	return false;
    }
    @FXML
    private boolean verificarSede() {
		// TODO Auto-generated method stub
    	 // por definir, consultas a la base de datos.
    	if(nuevo) {
    		return true;
    	}else if(comboBoxSede.getValue() != null) {
    		this.Sede= comboBoxSede.getValue();
    		return true;
    	}
	    mostrarMensaje("Error Sede", "Porfavor seleccionar una sede.");
		return false;
	}
    @FXML
	private boolean verificarFecha() {
		// TODO Auto-generated method stub
    	if(nuevo) {
    		return true;
    	}else if(fechaRegistroItem.getValue() != null) {
    		String date=  fechaRegistroItem.getValue().toString();
    		this.fecha = date;
    		return true;}
    	System.out.println("fecha vacia");
		return false;
	}
	@FXML
    private boolean verificarCostoVenta() {
    	
    	//validation request for data base.
		// TODO Auto-generated method stub
    	String valorVenta= valorVentaItem.getText();
		if(nuevo) {
			return true;
		}else if(valorVentaItem.getText().matches("[0-9]*") && valorVenta.matches("[^\t]*") && valorVenta.length()!=0){
			this.costoVenta= valorVenta;
			return true;}
	    mostrarMensaje("Error Valor Venta", "valor venta vacio o incorrecto");
		System.out.println("valor venta incorrecto");
		return false;
	}
    
    @FXML
	private boolean verificarCostoFabricacion() {
		
    	//validation request for data base.
		String costofab = costofabItem.getText();
		if(nuevo) {
			return true;
		}else if(costofab.matches("[0-9]*") && costofab.matches("[^\t]*") && costofab.length()!=0) {
			this.costofab = costofab;
			return true;}
		System.out.println("Costo fab incorrecto");
		mostrarMensaje("Error Costo Fabricacion", "Costo Fabricacion vacio o incorrecto");
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarMaterial() {
		
    	//validation request for data base.
		String materialSeleccionado = materialItem.getText().toLowerCase();
		if(nuevo) {
			return true;
		}else if(materialSeleccionado.matches("[a-z]*") && materialSeleccionado.matches("[^\t]*") && materialSeleccionado.length()!=0) {
			this.material = materialSeleccionado;
			return true;}
		mostrarMensaje("Error Material", "Material vacio o incorrecto");
		System.out.println("material incorrecto");
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarColor() {
		
    	//Validation for data base request.
		String color = colorItem.getText().toLowerCase();
		if(nuevo){
    	return true;
    	}else if(color.matches("[a-z]*") && color.matches("[^\t]*") && color.length()!=0) {
			this.color = color;
			return true;}
		mostrarMensaje("Error Color", "Color vacio o incorrecto");
		System.out.println("Color incorrecto");
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarCantidad() {
		
    	//validation for data base request.
		String cantidad = cantidadItems.getText();
		if(nuevo) {
			return true;
		}else if(cantidad.matches("[0-9]*") && cantidad.matches("[^\t]*") && cantidad.length() != 0) {
			this.cantidad=cantidad;
			return true;}
		mostrarMensaje("Error Cantidad", "Cantidad vacia o incorrecto");
		System.out.println("cantidad incorrecta");
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarNombre() {
		
    	//validation for data base request.
		String nombre = nombreItem.getText().toLowerCase();
		if(nombreItem.isDisable()) {
		this.nombre= referenciaItem.getValue();
			return true;
		}else if(nombre.matches("[a-z]*") && nombre.matches("[^\t]*") && nombre.length() != 0) {
			this.nombre = nombre;
			return true;}
		
		//feedback for user.
		mostrarMensaje("Error Nombre", "Nombre vacio o incorrecto");
		System.out.println("nombre incorrecto");
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarReferencia() {
		
		
		//validation for data base request.
		String referenciaSeleccionada = referenciaItem.getValue();
		if(referenciaSeleccionada=="Nuevo") {
			nuevo = true;
			return true;
		}else if (referenciaSeleccionada != null) {
				this.referencia=referenciaSeleccionada;
				nuevo=false;
				return true;
			}
		mostrarMensaje("Error Referencia", "Porfavor Seleccionar una referencia");
		System.out.println("referencia incorrecta");
		return false;
	}

	@FXML
    void registrarItem(ActionEvent event) {
    if(verificarCampos()) {
    	//shoul be :peticion a la base de datos.
    	//conexion mediante instancia de inventario.
    	DaoInventario inventario = new DaoInventario();
    	//salida de todos los datos a la base de datos.
    	if(nuevo) {
    		inventario.registrarItemEnInventaro(nombre);
    		mostrarMensajeInformativo("ENVIADO", "Envio de registro completado");
    		limpiarCampos();
    	}else {
    		inventario.registrarItemEnEjemplares(referencia, color, costofab, costoVenta, fecha, cBox.getId(Sede), cantidad );
    		mostrarMensajeInformativo("ENVIADO", "Envio de registro completado");
    		limpiarCampos();
    	}
    	
    	System.out.println("se envio registro.");
	
    	}
	
    }


}
