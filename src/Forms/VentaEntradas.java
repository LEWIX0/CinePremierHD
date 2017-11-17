/*
 * Trabajo Final - Construccion De Software I  TdeA.
 * Hecho por: Juan Guillermo Diosa Muñoz | Docente: Sofia Gallo
 * Cine Premier HD - Control de usuario para un cine.
 */
package Forms;

import Clases.Conexion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class VentaEntradas extends javax.swing.JFrame {

    public VentaEntradas() {
        initComponents();

        setIconImage(new ImageIcon(getClass().getResource("../Imagenes/ICONO.png")).getImage()); //Cambio del icono del Form.

        mostrarDatos();             //Llenado de tabla de peliculas al abrirse la ventana.

        mostrarDisponibilidad();    //Llenado de tabla de disponibilidad al abrirse la ventana.
    }

    void mostrarDatos() {           //Metodo mostrarDatos de la tabla Peliculas de la Base de datos.
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 15) {
                    return true;
                } else {
                    return false;
                }
            }

        };;
        //Creacion del modelo de la tabla.
        modelo.addColumn("ID Pelicula");
        modelo.addColumn("Titulo");
        modelo.addColumn("Formato");
        modelo.addColumn("Sala");
        modelo.addColumn("Horario");
        tbPeliculas.setModel(modelo);  //Asignacion del modelo ya creado.

        String[] datos = new String[5];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM peliculas ");   //Sentencia para llenar la tabla con datos de la Base de datos.
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(6);
                datos[3] = rs.getString(7);
                datos[4] = rs.getString(8);
                modelo.addRow(datos);
            }
            tbPeliculas.setModel(modelo);
        } catch (SQLException ex) {

        }
    }

    void mostrarDisponibilidad() {   //Metodo mostrarDisponibilidad de la tabla Salas de la base de datos.
        DefaultTableModel modelo1 = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 15) {
                    return true;
                } else {
                    return false;
                }
            }

        };
        //Creacion del modelo de la tabla.
        modelo1.addColumn("ID Sala");
        modelo1.addColumn("Preferenciales");
        modelo1.addColumn("Generales");
        tbDisponibilidad.setModel(modelo1); //Sentencia para llenar la tabla con datos de la Base de datos.

        String[] datos = new String[3];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM salas");  //Sentencia para llenar la tabla con datos de la Base de datos.
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                modelo1.addRow(datos);
            }
            tbDisponibilidad.setModel(modelo1);
        } catch (SQLException ex) {

        }
    }

    //Atributos del Form.
    double valorFactura;
    double valorFinal;

    void facturar() {  //Metodo facturar, en el que se hace todo el proceso de facturacion.

        String formato = txtformato.getText();
        String asiento = (String) tipoAsiento.getSelectedItem();

        int x = Integer.parseInt(txtnumasientos.getText());  //convertir String en Int.

        if (formato.equals("3D") && asiento.equals("General")) {
            valorFactura = 8000;
        } else if (formato.equals("3D") && asiento.equals("Preferencial")) {
            valorFactura = 10000;
        } else if (formato.equals("2D") && asiento.equals("General")) {
            valorFactura = 6000;
        } else if (formato.equals("2D") && asiento.equals("Preferencial")) {
            valorFactura = 6000;
        }

        int hora = Integer.parseInt(txtHora.getText());        //convertir String en Itn.

        if (formato.equals("3D")) {                     //Si la factura es 3D, no aplica a promocion.
            valorFinal = valorFactura * x;
            String value = "";
            value = String.valueOf(valorFinal);
            txtvalorfactura.setText(value);
        } else {

            if (x % 2 == 0) {                           //Si la factura es 2D, aplica a promocion, pero %2 por si son boletas pares.
                if (formato.equals("2D") && hora <= 4) {
                    String botones[] = {"Activar", "No activar"};
                    int eleccion = JOptionPane.showOptionDialog(this, "Para peliculas 2D antes de las 5:00 PM \n existe la promocion 2x1 \n ¿Desea activar la Promocion para esta compra?", "Activar/Desactivar Promocion", 0, 0, null, botones, this);

                    if (eleccion == JOptionPane.YES_OPTION) {
                        valorFinal = valorFactura * x / 2;
                        String value = "";
                        value = String.valueOf(valorFinal);
                        txtvalorfactura.setText(value);
                    } else if (eleccion == JOptionPane.NO_OPTION) {
                        valorFinal = valorFactura * x;
                        String value = "";
                        value = String.valueOf(valorFinal);
                        txtvalorfactura.setText(value);
                    }
                } else {
                    valorFinal = valorFactura * x;
                    String value = "";
                    value = String.valueOf(valorFinal);
                    txtvalorfactura.setText(value);
                }

            } else {                                    //Si la factura es 2D, aplica a promocion, pero %1 por si son boletas impares.
                if (formato.equals("2D") && hora <= 4) {
                    String botones[] = {"Activar", "No activar"};
                    int eleccion = JOptionPane.showOptionDialog(this, "Para peliculas 2D antes de las 5:00 PM \n existe la promocion 2x1 \n ¿Desea activar la Promocion para esta compra?", "Activar/Desactivar Promocion", 0, 0, null, botones, this);

                    if (eleccion == JOptionPane.YES_OPTION) {

                        int x1 = x - 1;
                        valorFinal = (valorFactura * x1 / 2) + 6000;
                        String value = "";
                        value = String.valueOf(valorFinal);
                        txtvalorfactura.setText(value);
                    } else if (eleccion == JOptionPane.NO_OPTION) {
                        valorFinal = valorFactura * x;
                        String value = "";
                        value = String.valueOf(valorFinal);
                        txtvalorfactura.setText(value);
                    }
                } else {
                    valorFinal = valorFactura * x;
                    String value = "";
                    value = String.valueOf(valorFinal);
                    txtvalorfactura.setText(value);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbPeliculas = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbPeliculas1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txttitulo = new javax.swing.JTextField();
        txtformato = new javax.swing.JTextField();
        txtsala = new javax.swing.JTextField();
        tipoAsiento = new javax.swing.JComboBox<>();
        txtnumasientos = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txthorario = new javax.swing.JTextField();
        btnFacturar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDisponibilidad = new javax.swing.JTable();
        txtSegundos = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMinutos = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtHora = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtvalorfactura = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Venta De Entradas");
        setBackground(new java.awt.Color(153, 153, 153));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Peliculas En Cartelera"));

        tbPeliculas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbPeliculas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPeliculasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbPeliculas);

        tbPeliculas1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbPeliculas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPeliculas1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbPeliculas1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Pelicula");

        jLabel2.setText("Formato");

        jLabel3.setText("Sala");

        jLabel4.setText("ID Pelicula");

        jLabel5.setText("Tipo Ascientos");

        jLabel6.setText("Numero Ascientos");

        txtID.setEditable(false);

        txttitulo.setEditable(false);

        txtformato.setEditable(false);

        txtsala.setEditable(false);

        tipoAsiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "General", "Preferencial", " " }));

        jLabel7.setText("Horario");

        txthorario.setEditable(false);

        btnFacturar.setText("Facturar");
        btnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarActionPerformed(evt);
            }
        });

        jButton2.setText("Imprimir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Salir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Disponibilidad De Asientos"));

        tbDisponibilidad.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbDisponibilidad);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
        );

        txtSegundos.setText("00");

        jLabel8.setText(":");

        txtMinutos.setText("00");

        jLabel9.setText(":");

        txtHora.setText("00");

        jLabel10.setText("Valor Factura");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtID)
                                    .addComponent(txttitulo)
                                    .addComponent(txtsala, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                                .addGap(37, 37, 37)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtformato, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txthorario, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addComponent(txtHora)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMinutos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSegundos))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnFacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(tipoAsiento, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtvalorfactura, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtnumasientos, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHora)
                            .addComponent(txtMinutos)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(txtSegundos))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txttitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtformato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(txthorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(tipoAsiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnumasientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFacturar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtvalorfactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tbPeliculasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPeliculasMouseClicked
        //Llenamos los componentes del form, de acuerdo a la fila de la tabla que seleccionemos.
        int seleccion = tbPeliculas.getSelectedRow();

        txtID.setText(String.valueOf(tbPeliculas.getValueAt(seleccion, 0)));
        txttitulo.setText(String.valueOf(tbPeliculas.getValueAt(seleccion, 1)));
        txtformato.setText(String.valueOf(tbPeliculas.getValueAt(seleccion, 2)));
        txtsala.setText(String.valueOf(tbPeliculas.getValueAt(seleccion, 3)));
        txthorario.setText(String.valueOf(tbPeliculas.getValueAt(seleccion, 4)));


    }//GEN-LAST:event_tbPeliculasMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //Creacion de OptionDialog personalizado.
        String botones[] = {"Salir", "Cancelar"};
        int eleccion = JOptionPane.showOptionDialog(this, "¿Desea Salir de la aplicacion?", "Salir de la Aplicacion", 0, 0, null, botones, this);
        //Si la opcion es Si, sale del sistema, System.exit hace cierre total de la aplicacion.
        if (eleccion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //Creacion objeto Factura para abrir dicha ventana.
        Factura fac = new Factura();
        fac.setVisible(true);
        //Envio de los datos de la factura a la impresion de factura.
        Factura.txtTitulo1.setText(txttitulo.getText());
        Factura.txtFormato1.setText(txtformato.getText());
        Factura.txtSala1.setText(txtsala.getText());
        Factura.txtHorario1.setText(txthorario.getText());
        Factura.txtAsiento1.setText(tipoAsiento.getSelectedItem().toString());  //Convertir int en String.
        Factura.txtCantasientos1.setText(txtnumasientos.getText());
        Factura.txtTotal1.setText(txtvalorfactura.getText());

    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarActionPerformed
        facturar();
    }//GEN-LAST:event_btnFacturarActionPerformed

    private void tbPeliculas1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPeliculas1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPeliculas1MouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //Creacion de metodo Timer para controlar la hora en pantalla.
        timer = new Timer(1000, new cronometro());
        timer.start();
    }//GEN-LAST:event_formWindowOpened
    //Clase cronometro para controlar el manejo de la hora.
    public class cronometro implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            GregorianCalendar tiempo = new GregorianCalendar();
            int hora, minutos, segundos;
            hora = tiempo.get(Calendar.HOUR);
            minutos = tiempo.get(Calendar.MINUTE);
            segundos = tiempo.get(Calendar.SECOND);

            txtHora.setText(String.valueOf(hora));
            txtMinutos.setText(String.valueOf(minutos));
            txtSegundos.setText(String.valueOf(segundos));

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentaEntradas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentaEntradas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentaEntradas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentaEntradas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentaEntradas().setVisible(true);
            }
        });
    }
    private Timer timer;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFacturar;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbDisponibilidad;
    private javax.swing.JTable tbPeliculas;
    private javax.swing.JTable tbPeliculas1;
    private javax.swing.JComboBox<String> tipoAsiento;
    private javax.swing.JLabel txtHora;
    public static javax.swing.JTextField txtID;
    private javax.swing.JLabel txtMinutos;
    private javax.swing.JLabel txtSegundos;
    public static javax.swing.JTextField txtformato;
    public static javax.swing.JTextField txthorario;
    public static javax.swing.JTextField txtnumasientos;
    public static javax.swing.JTextField txtsala;
    public static javax.swing.JTextField txttitulo;
    private javax.swing.JTextField txtvalorfactura;
    // End of variables declaration//GEN-END:variables
Conexion con = new Conexion();
    Connection cn = con.conexion();
}
