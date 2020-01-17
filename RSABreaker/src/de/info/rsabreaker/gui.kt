package de.info.rsabreaker

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
    var publicKey = JLabel()
    var publicPane = JTextPane()
    var rsamodul = JLabel()
    var rsaPane = JTextPane()
    var chiffrat = JTextPane()
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

        publicKey.text = "Public Key"
        rsamodul.text = "RSA - Modul"

        publicPane.addKeyListener(this)
        publicPane.focusTraversalKeysEnabled = false
        publicPane.text = gen

        rsaPane.addKeyListener(this)
        rsaPane.focusTraversalKeysEnabled = false
        rsaPane.text = gen

        chiffrat.addKeyListener(this)
        chiffrat.focusTraversalKeysEnabled = false
        chiffrat.text = "Chiffrat"

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.add(publicKey,c)

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        this.add(publicPane,c)

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        this.add(rsamodul,c)

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        this.add(rsaPane,c)

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        this.add(chiffrat,c)

        this.pack()
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        this.setSize(400,300)
        this.setLocation(400,50)
        this.setVisible(true)
    }

    override fun keyPressed(p0: KeyEvent?) {
        if (running) return
        if (p0 != null) {
            //Tab
            if (p0.extendedKeyCode == 9){
                when (p0.source) {
                    publicPane -> rsaPane.requestFocus()
                    rsaPane -> chiffrat.requestFocus()
                    chiffrat -> publicPane.requestFocus()
                }
                p0.consume()
            }

            if (p0.extendedKeyCode == 10) {//Enter
                when (p0.source) {
                    publicPane -> rsaPane.requestFocus()
                    rsaPane -> chiffrat.requestFocus()
                    chiffrat -> {
                        running = true
                        publicPane.isEditable = false
                        rsaPane.isEditable = false
                        chiffrat.isEditable = false
                        if(isInteger(publicPane.text) && isInteger(rsaPane.text) && isInteger(chiffrat.text)){
                            breakRSA(BigInteger(publicPane.text),BigInteger(rsaPane.text),BigInteger(chiffrat.text))
                        }else {
                            println("Bitte alle Parameter korrekt angeben!")
                        }
                        running = false
                        publicPane.isEditable = true
                        rsaPane.isEditable = true
                        chiffrat.isEditable = true
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