package EditorPaquete;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Fuente extends JDialog implements ActionListener{
    Editor editor;
    JToggleButton negrita, cursiva, subrayado;
    JButton aceptar, cancelar;
    JComboBox tamano, familia;
    SimpleAttributeSet attrs;
    String fuentes[];
    JColorChooser colores;
    JPanel panelOpciones, panelBotones;
    
    public Fuente(Editor editor){
        this.setTitle("Fuente");
        this.editor = editor;
        this.setSize(500,500);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        panelOpciones=new JPanel (new FlowLayout());
        negrita = new JToggleButton("N");
        cursiva = new JToggleButton("C");
        subrayado= new JToggleButton("S");
        panelOpciones.add(negrita);
        panelOpciones.add(cursiva);
        panelOpciones.add(subrayado);
        tamano = new JComboBox();
        familia = new JComboBox();
        for (int i = 10; i <=48;i++){
            tamano.addItem(new Integer(i));
        }
        panelOpciones.add(tamano);
        
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fuentes = environment.getAvailableFontFamilyNames();
        familia = new JComboBox(fuentes);
        colores = new JColorChooser();
        panelOpciones.add(colores);
        this.add(panelOpciones, BorderLayout.CENTER);
        panelBotones = new JPanel(new FlowLayout());
        aceptar = new JButton("Aceptar");
        cancelar = new JButton("Cancelar");
        panelBotones.add(aceptar);
        panelBotones.add(cancelar);
        this.add(panelBotones, BorderLayout.SOUTH);
        attrs = new SimpleAttributeSet();
        aceptar.addActionListener(this);
        cancelar.addActionListener(this);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == aceptar){
            if (negrita.isSelected()){
                StyleConstants.setBold(attrs,true);
            }
            if(cursiva.isSelected()){
                StyleConstants.setItalic(attrs,true);
            }
            if(subrayado.isSelected()){
                StyleConstants.setUnderline(attrs,true);
            }
            StyleConstants.setForeground(attrs, colores.getColor());
            StyleConstants.setFontFamily(attrs, familia.getSelectedItem().toString());
            if(editor.getArea().getSelectedText() != null){
                editor.getArea().getStyledDocument().setCharacterAttributes(editor.getArea().getSelectionStart(),
                        editor.getArea().getSelectedText().length(),attrs,true);
            }
            this.dispose();
        }else if (e.getSource() == cancelar){
            this.dispose();
        }
    }
    
}
