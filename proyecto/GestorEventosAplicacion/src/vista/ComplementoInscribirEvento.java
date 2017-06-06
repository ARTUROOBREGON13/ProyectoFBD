package vista;

import control.ControlAsociado;
import control.ControlFuncionario;
import control.ControlMultivalor;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import modelo.Evento;
import modelo.Familiar;
import modelo.Inscripcion;

public class ComplementoInscribirEvento extends javax.swing.JFrame {

    private String asociado;
    private InscribirEvent inscribir;
    private ArrayList<Familiar> pAsistentes;
    private Inscripcion inscripcion;

    public ComplementoInscribirEvento(InscribirEvent previous, String tipo, String asociado) {
        inscripcion = new Inscripcion();
        this.inscribir = previous;
        this.asociado = asociado;
        inscribir.disable();
        initComponents();
        cargar();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inscripcion a evento");

        jLabel1.setText("Nombre Evento");

        jLabel3.setText("Valor Unitario Copago");

        jLabel4.setText("Tipo de pago");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Nomina" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel5.setText("Cuotas");

        jTextField5.setEditable(false);
        jTextField5.setText("1");

        jLabel6.setText("Posibles Asistentes");

        jScrollPane1.setViewportView(jList1);

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel8.setText("Para seleccionar varios, oprimir la tecla Ctrl y seleccionar los asistentes");

        jLabel9.setText("Lugar");

        jLabel10.setText("Fecha Evento");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jButton1)
                        .addGap(92, 92, 92)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(46, 46, 46)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(34, 34, 34)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(39, 39, 39))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if (jComboBox1.getSelectedIndex() == 0) {
            jTextField5.setText("1");
            jTextField5.setEditable(false);
        } else {
            jTextField5.setText("");
            jTextField5.setEditable(true);
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        getInscribir().enable();
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Evento e;
        ControlAsociado cA;
        try {
            if (jList1.isSelectionEmpty()) {
                throw new Exception("No hay ningun asistente para esta inscripción.");
            } else if(jTextField5.getText().equals("") || Integer.parseInt(jTextField5.getText())<1 ){
                throw new Exception("No se ha definido un numero de cuotas valido");
            }
            else {
                e = getInscribir().getEvento();
                String message = "La actual inscripcion cuenta con la siguiente informacion:\n"
                        + "Evento: " + e.getNombre() + "\n "
                        + "Lugar: " + e.getLugar() + "\n"
                        + "Fecha de Inicio: " + e.getfInicio() + "\n"
                        + "Asistentes: \n";
                int[] indices = jList1.getSelectedIndices();
                ArrayList<String> seleccion = (ArrayList<String>) jList1.getSelectedValuesList();
                for (String f : seleccion) {
                    message += " - " + f + "\n";
                }
                double valorTotal = e.getCopago() * seleccion.size();
                message += "Valor Inscripcion: " + String.valueOf(valorTotal) + "\n"
                        + "¿Esta seguro?";
                int opc = JOptionPane.showConfirmDialog(null, message, "Confirmacion", YES_NO_OPTION);
                if (opc == 0) {
                    ArrayList<Familiar> asistentes = new ArrayList<Familiar>();
                    for (int i : indices) {
                        asistentes.add(pAsistentes.get(i));
                    }
                    inscripcion.setUser(asociado);
                    inscripcion.setAsistentes(asistentes);
                    inscripcion.setValorTotal(valorTotal);
                    String tipoPago = "";
                    String estado = "";
                    if (jComboBox1.getSelectedIndex() == 0) {
                        tipoPago = "V";
                        estado = "I";
                    } else {
                        tipoPago = "N";
                        estado = "P";
                    }
                    inscripcion.setEstado(estado);
                    inscripcion.setTipoPago(tipoPago);
                    inscripcion.setCantCuotas(Integer.parseInt(jTextField5.getText()));
                    try {
                        cA = getInscribir().getMenu().getControlA();
                        cA.InsertarInscripcion(e.getCodigo(), inscripcion);
                        getInscribir().enable();
                        this.dispose();
                    } catch (Exception ex) {
                        throw ex;
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables

    private void cargar() {
        ControlAsociado cA;
        ControlFuncionario cF;
        ControlMultivalor cM;
        Evento e;
        jTextField5.setText("1");
        jTextField5.setEditable(false);
        try {
            e = getInscribir().getEvento();
            cM = getInscribir().getMenu().getControlM();
            jTextField1.setText(e.getNombre());
            jTextField1.disable();
            jTextField3.setText(String.valueOf(e.getCopago()));
            jTextField3.disable();
            jTextField4.setText(e.getLugar());
            jTextField4.disable();
            jTextField6.setText(e.getfInicio() + " - " + e.getfFin());
            jTextField6.disable();
            pAsistentes = new ArrayList<Familiar>();
            pAsistentes = cM.consultarFamiliares(asociado, e.getCodigo());
            if (!pAsistentes.isEmpty()) {
                ArrayList<String> lista = new ArrayList<String>();
                for (Familiar f : pAsistentes) {
                    lista.add(f.getNombre() + " " + f.getApellido() + " - " + f.getParentezco());
                }
                FillList(lista);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void FillList(ArrayList<String> l) throws Exception {
        DefaultListModel<String> lista;
        lista = new DefaultListModel<String>();
        for (String s : l) {
            lista.addElement(s);
        }
        jList1.setModel((lista));
    }

    /**
     * @return the inscribir
     */
    public InscribirEvent getInscribir() {
        return inscribir;
    }
}
