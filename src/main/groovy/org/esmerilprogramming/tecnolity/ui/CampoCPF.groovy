package org.esmerilprogramming.ui;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class CampoCPF extends JTextField implements KeyListener
{
    private String numeros;
    private String w;
    private int i;
    private int a;
    private int j;
    private String s;
    private String d;
    private String b;
    private String c;
    private String f;
    private String g;
    private String h;
    private String k;
    private String l;
    private String m;
    private String o;
    private String p;
    private String q;
    private String r;
    private String t;
    private String v;
    private String u;
    private String x;
    private String y;
    private String z;
    private Vector formatar;
    
    public CampoCPF() {
        this.numeros = "";
        this.w = "";
        this.i = 0;
        this.d = "";
        this.b = "";
        this.c = "";
        this.f = "";
        this.g = "";
        this.h = "";
        this.k = "";
        this.l = "";
        this.m = "";
        this.o = "";
        this.p = "";
        this.q = "";
        this.r = "";
        this.t = "";
        this.v = "";
        this.u = "";
        this.x = "";
        this.y = "";
        this.z = "";
        this.setHorizontalAlignment(4);
        this.addKeyListener(this);
    }
    
    public void keyPressed(final KeyEvent e) {
        this.s = KeyEvent.getKeyText(e.getKeyCode());
    }
    
    public void keyReleased(final KeyEvent e) {
        this.s = KeyEvent.getKeyText(e.getKeyCode());
        this.numeros = String.valueOf(this.numeros) + KeyEvent.getKeyText(e.getKeyCode());
        this.setFormatarNumero(e);
    }
    
    public void keyTyped(final KeyEvent e) {
        this.s = "" + e.getKeyChar();
    }
    
    public void setFormatarNumero(final KeyEvent e) {
        final String execao = "O CPF s\u00f3 possui 11 n\u00fameros.";
        final Vector formatar = new Vector();
        final String valor = KeyEvent.getKeyText(e.getKeyCode());
        if (valor.equals("0") || valor.equals("1") || valor.equals("2") || valor.equals("3") || valor.equals("4") || valor.equals("5") || valor.equals("6") || valor.equals("7") || valor.equals("8") || valor.equals("9")) {
            if (this.numeros != null) {
                if (this.i < 2) {
                    if (this.i < 1) {
                        this.d = String.valueOf(this.d) + KeyEvent.getKeyText(e.getKeyCode());
                    }
                    if (this.i == 1) {
                        this.f = String.valueOf(this.f) + KeyEvent.getKeyText(e.getKeyCode());
                        this.g = String.valueOf(this.d) + this.f;
                    }
                    this.setText(this.numeros);
                    ++this.i;
                    formatar.addElement(this.s);
                    this.b = KeyEvent.getKeyText(e.getKeyCode());
                    this.c = String.valueOf(this.c) + KeyEvent.getKeyText(e.getKeyCode());
                }
                else if (this.i == 2) {
                    this.setText(this.numeros = String.valueOf(this.d) + "-" + this.b + KeyEvent.getKeyText(e.getKeyCode()));
                    this.b = KeyEvent.getKeyText(e.getKeyCode());
                    this.h = String.valueOf(this.h) + this.b;
                    this.l = String.valueOf(this.l) + this.g + this.b;
                    ++this.i;
                }
                else if (this.i >= 3 && this.i < 5) {
                    formatar.addElement(this.s);
                    this.a = 0;
                    this.j = 0;
                    while (this.a < formatar.size()) {
                        if (this.a == this.j) {
                            this.setText(this.numeros = String.valueOf(this.c) + "-" + this.b + KeyEvent.getKeyText(e.getKeyCode()));
                            ++this.i;
                            this.c = String.valueOf(this.c) + this.b;
                            this.f = String.valueOf(this.f) + this.b;
                            ++this.j;
                            this.b = KeyEvent.getKeyText(e.getKeyCode());
                            this.h = String.valueOf(this.h) + this.b;
                            this.k = String.valueOf(this.k) + this.b;
                        }
                        ++this.a;
                    }
                }
                else if (this.i == 5) {
                    this.setText(this.numeros = String.valueOf(this.d) + "." + this.f + "-" + this.b + KeyEvent.getKeyText(e.getKeyCode()));
                    this.m = String.valueOf(this.m) + this.b;
                    this.b = KeyEvent.getKeyText(e.getKeyCode());
                    this.k = String.valueOf(this.k) + this.b;
                    this.m = String.valueOf(this.m) + this.b;
                    this.o = String.valueOf(this.o) + this.b;
                    ++this.i;
                }
                else if (this.i == 6) {
                    this.setText(this.numeros = String.valueOf(this.g) + "." + this.h + "-" + this.b + KeyEvent.getKeyText(e.getKeyCode()));
                    this.b = KeyEvent.getKeyText(e.getKeyCode());
                    this.m = String.valueOf(this.m) + this.b;
                    this.o = String.valueOf(this.o) + this.b;
                    this.p = String.valueOf(this.p) + this.b;
                    ++this.i;
                }
                else if (this.i == 7) {
                    this.setText(this.numeros = String.valueOf(this.l) + "." + this.k + "-" + this.b + KeyEvent.getKeyText(e.getKeyCode()));
                    this.b = KeyEvent.getKeyText(e.getKeyCode());
                    this.o = String.valueOf(this.o) + this.b;
                    this.p = String.valueOf(this.p) + this.b;
                    this.q = String.valueOf(this.q) + this.b;
                    ++this.i;
                }
                else if (this.i == 8) {
                    this.setText(this.numeros = String.valueOf(this.d) + "." + this.f + "." + this.m + "-" + this.b + KeyEvent.getKeyText(e.getKeyCode()));
                    this.b = KeyEvent.getKeyText(e.getKeyCode());
                    this.p = String.valueOf(this.p) + this.b;
                    this.q = String.valueOf(this.q) + this.b;
                    this.r = String.valueOf(this.r) + this.b;
                    ++this.i;
                }
                else if (this.i == 9) {
                    this.setText(this.numeros = String.valueOf(this.g) + "." + this.h + "." + this.o + "-" + this.b + KeyEvent.getKeyText(e.getKeyCode()));
                    this.b = KeyEvent.getKeyText(e.getKeyCode());
                    this.q = String.valueOf(this.q) + this.b;
                    this.r = String.valueOf(this.r) + this.b;
                    this.t = String.valueOf(this.t) + this.b;
                    ++this.i;
                }
                else if (this.i == 10) {
                    this.setText(this.numeros = String.valueOf(this.l) + "." + this.k + "." + this.p + "-" + this.b + KeyEvent.getKeyText(e.getKeyCode()));
                    this.b = String.valueOf(this.b) + KeyEvent.getKeyText(e.getKeyCode());
                    this.r = String.valueOf(this.r) + this.b;
                    this.t = String.valueOf(this.t) + this.b;
                    this.u = String.valueOf(this.u) + this.b;
                    ++this.i;
                }
                else if (this.i > 10) {
                    JOptionPane.showMessageDialog(null, execao);
                    this.setText(this.numeros = String.valueOf(this.l) + "." + this.k + "." + this.p + "-" + this.b);
                }
            }
        }
        else if (!valor.equals("Backspace")) {
            final String valores = "O valor digitado n\u00e3o \u00e9 um n\u00famero.";
            JOptionPane.showMessageDialog(null, valores, "Erro", 0);
        }
        else if (valor.equals("Backspace")) {
            final String valores = "Refazer?";
            JOptionPane.showMessageDialog(null, valores);
            this.numeros = "";
            this.w = "";
            this.d = "";
            this.b = "";
            this.c = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.k = "";
            this.l = "";
            this.m = "";
            this.o = "";
            this.p = "";
            this.q = "";
            this.r = "";
            this.t = "";
            this.v = "";
            this.u = "";
            this.x = "";
            this.y = "";
            this.z = "";
            this.i = 0;
            this.setText(this.numeros);
        }
    }
}
