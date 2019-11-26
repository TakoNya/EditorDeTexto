package EditorPaquete;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JOptionPane;

public class Editor extends JFrame implements ActionListener {

    private JTextPane area;
    private JMenuBar barraMenu;
    private JMenu archivo, edicion;
    private JMenuItem abrir, salir, copiar, cortar, pegar, guardar, fuente, imagen, nuevo;
    private JScrollPane panel;
    private BorderLayout borderLayout;
    private File file;
    private String textoAntes = "";
    private String nombreArchivo = "";

    public Editor() {
        super("Editor de texto");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        barraMenu = new JMenuBar();
        archivo = new JMenu("Archivo");
        edicion = new JMenu("Edicion");
        barraMenu.add(archivo);
        barraMenu.add(edicion);
        abrir = new JMenuItem("Abrir");
        guardar = new JMenuItem("Guardar");
        salir = new JMenuItem("Salir");
        copiar = new JMenuItem("Copiar");
        cortar = new JMenuItem("Cortar");
        pegar = new JMenuItem("Pegar");
        fuente = new JMenuItem("Fuente");
        imagen = new JMenuItem("Imagen");
        nuevo = new JMenuItem("Nuevo");
        archivo.add(nuevo);
        archivo.add(abrir);
        archivo.add(guardar);
        archivo.addSeparator();
        archivo.add(salir);
        archivo.add(copiar);
        archivo.add(cortar);
        archivo.add(pegar);
        archivo.add(fuente);
        archivo.add(imagen);
        area = new JTextPane();
        panel = new JScrollPane(area);
        this.add(panel, borderLayout.CENTER);
        area.setSize(600, 600);
        this.setJMenuBar(barraMenu);
        nuevo.addActionListener(this);
        abrir.addActionListener(this);
        guardar.addActionListener(this);
        salir.addActionListener(this);
        copiar.addActionListener(this);
        cortar.addActionListener(this);
        pegar.addActionListener(this);
        fuente.addActionListener(this);
        imagen.addActionListener(this);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == copiar) {
            area.copy();
        } else if (e.getSource() == pegar) {
            area.paste();
        } else if (e.getSource() == cortar) {
            area.cut();
        } else if (e.getSource() == salir) {
            if (hayCambios()) {
                int input = JOptionPane.showConfirmDialog(null, "¿Guardar cambios?");
                switch (input) {
                    case 0:
                        guardar();
                        salir();
                        break;
                    case 1:
                        salir();
                        break;
                }
            } else {
                salir();
            }
            
        } else if (e.getSource() == abrir) {
            if (hayCambios()) {
                int input = JOptionPane.showConfirmDialog(null, "¿Guardar cambios?");
                switch (input) {
                    case 0:
                        guardar();
                        abrir();
                        break;
                    case 1:
                        abrir();
                        break;
                }
            } else {
                abrir();
            }
        } else if (e.getSource() == guardar) {
            guardar();
            setearTitulo();
        } else if (e.getSource()
                == fuente) {
            Fuente fuente = new Fuente(this);
        } else if (e.getSource() == imagen) {
            JFileChooser fileChooser = new JFileChooser();
            int resultado = fileChooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
            }
            this.area.insertIcon(new ImageIcon(file.getAbsolutePath()));
        } else if (e.getSource() == nuevo) {
            if (hayCambios()) {
                int input = JOptionPane.showConfirmDialog(null, "¿Guardar cambios?");
                switch (input) {
                    case 0:
                        guardar();
                        nuevo();
                        break;
                    case 1:
                        nuevo();
                        break;
                }
            } else {
                nuevo();
            } 
        }
    }

    public JTextPane getArea() {
        return area;
    }

    public void setArea(JTextPane area) {
        this.area = area;
    }

    public static void main(String[] args) {
        Editor principal = new Editor();

    }

    private void nuevo() {
        area.setText("");
        nombreArchivo = "";
        setearTitulo();
    }

    private void abrir() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            try {
                FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
                JTextPane textPaneAux = (JTextPane) inputStream.readObject();
                this.area.setStyledDocument(textPaneAux.getStyledDocument());
                fileInputStream.close();
               nombreArchivo = file.getName();
               setearTitulo();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    private void guardar() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showSaveDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath());
                ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
                //Esto es posible porque JTextPane implementa la clase Serializable
                outputStream.writeObject(area);
                outputStream.flush();
                outputStream.close();
                nombreArchivo = file.getName();
                setearTitulo();
                textoAntes = area.getText();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    private void salir() {
        this.dispose();
    }

    private boolean hayCambios() {
        return !textoAntes.equals(area.getText());
    }
    private void setearTitulo(){
    setTitle("Editor de texto - " + nombreArchivo);
}
}
