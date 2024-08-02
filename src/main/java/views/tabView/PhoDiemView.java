/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views.tabView;

import entities.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javax.swing.*;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import utilities.others.Others;
import utilities.ui.Ui;

public class PhoDiemView extends javax.swing.JPanel {
    List<DiemGraphView> graph = new ArrayList<>();

    /**
     * Creates new form PhoDiemView
     */
    public PhoDiemView() {
        initComponents();
        for (String x : Others.MON) {
            DiemGraphView temp = new DiemGraphView(x);
            graph.add(temp);
            monGraph.addTab(x.toUpperCase(), temp);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pvF = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tinhF = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        truongF = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        monGraph = new javax.swing.JTabbedPane();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pvF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Tỉnh", "Trường" }));
        pvF.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pvFItemStateChanged(evt);
            }
        });
        pvF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pvFActionPerformed(evt);
            }
        });
        add(pvF, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 110, 30));
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 760, 90, 40));

        jLabel2.setText("Phạm vi");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 20, 66, -1));

        tinhF.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tinhFItemStateChanged(evt);
            }
        });
        add(tinhF, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 110, 30));

        jLabel3.setText("Tỉnh");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 66, -1));

        truongF.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                truongFItemStateChanged(evt);
            }
        });
        add(truongF, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 170, 30));

        jLabel4.setText("Trường");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 66, -1));
        add(monGraph, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 870, 570));
    }// </editor-fold>//GEN-END:initComponents

    private void tinhFItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_tinhFItemStateChanged
        // TODO add your handling code here:
        if (pvF.getSelectedIndex() == 2) updateTruongF();
        updateFilter();
    }//GEN-LAST:event_tinhFItemStateChanged
    void updateFilter() {
        int choice = pvF.getSelectedIndex();

        String tinh = (String) tinhF.getSelectedItem();
        String truong = (String) truongF.getSelectedItem();
        Predicate<ThiSinh> x;
        switch (choice) {
            case 1 -> x = i -> i.getTruong().getTinh().getID().equals(tinh);
            case 2 -> x = i -> i.getTruong().getTenTruong().equals(truong) && i.getTruong().getTinh().getID().equals(tinh);
            default -> x = i -> true;
        }
        for (DiemGraphView g : graph) g.setFunc(x);

    }
    private void pvFItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_pvFItemStateChanged
        // TODO add your handling code here:
        int choice = pvF.getSelectedIndex();
        updateField(choice);
    }//GEN-LAST:event_pvFItemStateChanged

    private void truongFItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_truongFItemStateChanged
        // TODO add your handling code here:
        updateFilter();
    }//GEN-LAST:event_truongFItemStateChanged

    private void pvFActionPerformed(ActionEvent evt) {//GEN-FIRST:event_pvFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pvFActionPerformed

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        if (!aFlag) return;
        resetState();
    }

    void updateField(int choice) {
        switch (choice) {
            case 0 -> {
                tinhF.setSelectedIndex(-1);
                truongF.setSelectedIndex(-1);
                tinhF.setEnabled(false);
                truongF.setEnabled(false);
                break;
            }
            case 1 -> {
                updateTinhF();
                truongF.setSelectedIndex(-1);
                tinhF.setEnabled(true);
                truongF.setEnabled(false);
                break;
            }
            case 2 -> {
                updateTinhF();
                updateTruongF();
                tinhF.setEnabled(true);
                truongF.setEnabled(true);
                break;
            }
        }
        updateFilter();
    }

    void updateTruongF() {
        String tinh = (String) tinhF.getSelectedItem();
        Ui.updateComboBox(truongF, TruongHoc.getDB().QUERY(i -> i.getTinh().getID().equals(tinh)).stream().map(i -> i.getTenTruong()).toList());
    }

    void updateTinhF() {
        Ui.updateComboBox(tinhF, TinhThanh.getDB().MAP(i -> i.getID()).toList());
    }

    void resetState() {
        pvF.setSelectedIndex(0);
        updateField(0);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTabbedPane monGraph;
    private javax.swing.JComboBox<String> pvF;
    private javax.swing.JComboBox<String> tinhF;
    private javax.swing.JComboBox<String> truongF;
    // End of variables declaration//GEN-END:variables
}