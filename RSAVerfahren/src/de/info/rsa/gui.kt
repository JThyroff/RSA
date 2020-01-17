package de.info.rsa

import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.math.BigInteger
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JTextPane
import javax.swing.UIManager

class GUI() : JFrame(),KeyListener {
    var prim1 = JLabel()
    var primzahl1 = JTextPane()
    var prim2 = JLabel()
    var primzahl2 = JTextPane()
    var text = JTextPane()
    /*
    var rsamodul = JLabel()
    var key1 = JLabel()
    var key2 = JLabel()
    var chiffrierterText = JLabel()*/

    val gen = "~ Generiert"

    var running = false

    init {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        this.layout = GridBagLayout()

        var c = GridBagConstraints()
        c.insets = Insets(10,10,10,10)

        prim1.text = "Primzahl 1"
        prim2.text = "Primzahl 2"

        primzahl1.addKeyListener(this)
        primzahl1.focusTraversalKeysEnabled = false
        primzahl1.text = gen

        primzahl2.addKeyListener(this)
        primzahl2.focusTraversalKeysEnabled = false
        primzahl2.text = gen

        text.addKeyListener(this)
        text.focusTraversalKeysEnabled = false
        text.text = "Nachricht"

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.add(prim1,c)

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        this.add(primzahl1,c)

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        this.add(prim2,c)

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        this.add(primzahl2,c)

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        this.add(text,c)

        this.pack()
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        this.setSize(400,300)
        this.setLocation(200,50)
        this.setVisible(true)
    }

    override fun keyPressed(p0: KeyEvent?) {
        if (running) return
        if (p0 != null) {
            //Tab
            if (p0.extendedKeyCode == 9){
                when (p0.source) {
                    primzahl1 -> primzahl2.requestFocus()
                    primzahl2 -> text.requestFocus()
                    text -> primzahl1.requestFocus()
                }
                p0.consume()
            }

            if (p0.extendedKeyCode == 10) {//Enter
                when (p0.source) {
                    primzahl1 -> primzahl2.requestFocus()
                    primzahl2 -> text.requestFocus()
                    text -> {
                        running = true
                        primzahl1.isEditable = false
                        primzahl2.isEditable = false
                        text.isEditable = false
                        if(isInteger(primzahl1.text) && isInteger(primzahl2.text)){
                            test(BigInteger(primzahl1.text),BigInteger(primzahl2.text),text.text)
                        }else if(isInteger(primzahl1.text)){
                            test(p = BigInteger(primzahl1.text), klartext = text.text)
                        }else if(isInteger(primzahl2.text)){
                            test(q = BigInteger(primzahl2.text), klartext = text.text)
                        }else{
                            test(klartext = text.text)
                        }
                        running = false
                        primzahl1.isEditable = true
                        primzahl2.isEditable = true
                        text.isEditable = true
                    }
                }
                p0.consume()
            }
        }
    }
    override fun keyReleased(p0: KeyEvent?) {
    }

    override fun keyTyped(p0: KeyEvent?) {
    }

    fun isInteger(s: String): Boolean {
        try {
            BigInteger(s)
        } catch (e: Exception) {
            return false
        }

        // only got here if we didn't return false
        return true
    }
}