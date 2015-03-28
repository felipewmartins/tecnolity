package br.com.mentores.ui

import java.util.*
import java.awt.event.*
import javax.swing.*
import java.awt.*

class CampoCEP extends JTextField implements KeyListener
{
  private String numeros
    private String w
    private String n1
    private String n2
    private String n3
    private String n4
    private String n5
    private String n6
    private int i
    private int a
    private int j
    private String s
    private String d
    private String b
    private String c
    private String f
    private String g
    private String h
    private String k
    private String l
    private String m
    private String o
    private String p
    private String q
    private String r
    private String t
    private String v
    private String u
    private String x
    private String y
    private String z
    private Vector formatar

    public CampoCEP() {
      this.numeros = ""
        this.w = ""
        this.n1 = ""
        this.n2 = ""
        this.n3 = ""
        this.n4 = ""
        this.n5 = ""
        this.n6 = ""
        this.i = 0
        this.d = ""
        this.b = ""
        this.c = ""
        this.f = ""
        this.g = ""
        this.h = ""
        this.k = ""
        this.l = ""
        this.m = ""
        this.o = ""
        this.p = ""
        this.q = ""
        this.r = ""
        this.t = ""
        this.v = ""
        this.u = ""
        this.x = ""
        this.y = ""
        this.z = ""
        this.setHorizontalAlignment(4)
        this.addKeyListener(this)
    }

  void keyPressed(final KeyEvent e) {
    this.s = KeyEvent.getKeyText(e.getKeyCode())
  }

  void keyReleased(final KeyEvent e) {
    this.s = KeyEvent.getKeyText(e.getKeyCode())
      this.numeros = String.valueOf(this.numeros) + KeyEvent.getKeyText(e.getKeyCode())
      this.setFormatarNumero(e)
  }

  void keyTyped(final KeyEvent e) {
    this.s = "" + e.getKeyChar()
  }

  void setFormatarNumero(final KeyEvent e) {
    final String execao = "O CEP s\u00f3 possui 8 n\u00fameros."
      final Vector formatar = new Vector()
      final String valor = KeyEvent.getKeyText(e.getKeyCode())
      if (valor.equals("0") || valor.equals("1") || valor.equals("2") || valor.equals("3") || valor.equals("4") || valor.equals("5") || valor.equals("6") || valor.equals("7") || valor.equals("8") || valor.equals("9")) {
        if (this.numeros != null) {
          if (this.i < 2) {
            if (this.i < 1) {
              this.d = String.valueOf(this.d) + KeyEvent.getKeyText(e.getKeyCode())
            }
            if (this.i == 1) {
              this.w = String.valueOf(this.w) + KeyEvent.getKeyText(e.getKeyCode())
                this.f = String.valueOf(this.f) + KeyEvent.getKeyText(e.getKeyCode())
                this.g = String.valueOf(this.d) + this.f
            }
            this.setText(this.numeros)
              ++this.i
              formatar.addElement(this.s)
              this.b = KeyEvent.getKeyText(e.getKeyCode())
              this.c = String.valueOf(this.c) + KeyEvent.getKeyText(e.getKeyCode())
          }
          else if (this.i == 2) {
            this.numeros = String.valueOf(this.g) + KeyEvent.getKeyText(e.getKeyCode())
              this.n1 = this.numeros
              this.setText(this.numeros)
              this.b = KeyEvent.getKeyText(e.getKeyCode())
              this.h = String.valueOf(this.h) + this.b
              this.w = String.valueOf(this.w) + this.b
              ++this.i
          }
          else if (this.i == 3) {
            if (valor.equals("Backspace")) {
              this.setText(this.numeros)
                --this.i
            }
            this.numeros = String.valueOf(this.d) + "-" + this.w + KeyEvent.getKeyText(e.getKeyCode())
              this.n2 = this.numeros
              this.setText(this.numeros)
              this.c = String.valueOf(this.c) + this.b
              this.f = String.valueOf(this.f) + this.b
              this.b = KeyEvent.getKeyText(e.getKeyCode())
              this.w = String.valueOf(this.w) + this.b
              this.h = String.valueOf(this.h) + this.b
              this.k = String.valueOf(this.k) + this.b
              ++this.i
          }
          else if (this.i == 4) {
            this.numeros = String.valueOf(this.g) + "-" + this.h + KeyEvent.getKeyText(e.getKeyCode())
              this.n3 = this.numeros
              this.setText(this.numeros)
              this.b = KeyEvent.getKeyText(e.getKeyCode())
              this.k = String.valueOf(this.k) + this.b
              this.h = String.valueOf(this.h) + this.b
              this.m = String.valueOf(this.m) + this.b
              ++this.i
          }
          else if (this.i == 5) {
            this.numeros = String.valueOf(this.c) + "-" + this.k + KeyEvent.getKeyText(e.getKeyCode())
              this.n4 = this.numeros
              this.setText(this.numeros)
              this.b = KeyEvent.getKeyText(e.getKeyCode())
              this.m = String.valueOf(this.m) + this.b
              this.o = String.valueOf(this.o) + this.b
              this.p = String.valueOf(this.p) + this.b
              ++this.i
          }
          else if (this.i == 6) {
            this.numeros = String.valueOf(this.d) + "." + this.w + "-" + this.m + KeyEvent.getKeyText(e.getKeyCode())
              this.n5 = this.numeros
              this.setText(this.numeros)
              this.b = KeyEvent.getKeyText(e.getKeyCode())
              this.o = String.valueOf(this.o) + this.b
              this.p = String.valueOf(this.p) + this.b
              this.q = String.valueOf(this.q) + this.b
              ++this.i
          }
          else if (this.i == 7) {
            this.numeros = String.valueOf(this.g) + "." + this.h + "-" + this.o + KeyEvent.getKeyText(e.getKeyCode())
              this.n6 = this.numeros
              this.setText(this.numeros)
              this.b = KeyEvent.getKeyText(e.getKeyCode())
              this.p = String.valueOf(this.p) + this.b
              this.q = String.valueOf(this.q) + this.b
              this.r = String.valueOf(this.r) + this.b
              ++this.i
          }
          else if (this.i > 7) {
            JOptionPane.showMessageDialog(null, execao)
              this.setText(this.numeros = String.valueOf(this.g) + "." + this.h + "-" + this.o + this.b)
          }
        }
      }
      else if (!valor.equals("Backspace")) {
        final String valores = "O valor digitado n\u00e3o \u00e9 um n\u00famero."
          JOptionPane.showMessageDialog(null, valores, "Erro", 0)
      }
      else if (valor.equals("Backspace")) {
        final String valores = "Refazer?"
          JOptionPane.showMessageDialog(null, valores)
          this.numeros = ""
          this.w = ""
          this.d = ""
          this.b = ""
          this.c = ""
          this.f = ""
          this.g = ""
          this.h = ""
          this.k = ""
          this.l = ""
          this.m = ""
          this.o = ""
          this.p = ""
          this.q = ""
          this.r = ""
          this.t = ""
          this.v = ""
          this.u = ""
          this.x = ""
          this.y = ""
          this.z = ""
          this.i = 0
          this.setText(this.numeros)
      }
  }
}
