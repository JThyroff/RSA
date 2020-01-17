package de.info.rsabreaker

import java.math.BigInteger

fun main(args : Array<String>){
    GUI()
}

fun breakRSA(publicKey: BigInteger, rsaModul: BigInteger, text: BigInteger) {
    val starttime = System.currentTimeMillis()
    var primzahl = getPrim(rsaModul)

    val prim2 = rsaModul.div(primzahl)
    println("Primzahlen gefunden: "+primzahl+" und "+prim2)
    var eulerschePhiFunktion = (primzahl.subtract(BigInteger.ONE)) * (prim2.subtract(BigInteger.ONE))
    var privateKey = euklidischerAlgorithmus(publicKey,eulerschePhiFunktion)
    if(privateKey.s < BigInteger.ZERO){
        println("key < 0")
        println("key = "+privateKey.s)
        privateKey.s = privateKey.s + eulerschePhiFunktion
        println("key = "+privateKey.s)
    }
    var klartext = text.modPow(privateKey.s,rsaModul).toString()
    klartext = asciiToString(klartext)
    println("Nachricht dechiffriert: "+klartext)
    val dauer = System.currentTimeMillis() - starttime
    println("Dauer: "+dauer+" millisekunden.")
}

fun getPrimAlt(rsaModul: BigInteger) : BigInteger{
//    val wurzel = rsaModul.
    var primzahl = BigInteger.valueOf(2)
    while(true){
        println("Checking "+primzahl)
        if(rsaModul.remainder(primzahl).equals(BigInteger.ZERO)){
            return primzahl
        }
        primzahl = primzahl.nextProbablePrime()
    }
}

fun getPrim(rsaModul: BigInteger) : BigInteger{
    var primzahl = BigInteger.valueOf(2)
    while(true){
        println("Checking "+primzahl)
        if(rsaModul.remainder(primzahl).equals(BigInteger.ZERO)){
            return primzahl
        }
        primzahl = primzahl.nextProbablePrime()
    }
}

fun euklidischerAlgorithmus(a: BigInteger, b: BigInteger): dst {
    if(b.equals(BigInteger.ZERO))return dst(a,BigInteger.ONE,BigInteger.ZERO)
    var h = euklidischerAlgorithmus(b, a%b)
    var q = a/b
    return dst(h.d,h.t,h.s-q*h.t)
}