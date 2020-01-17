package de.info.rsa

import java.math.BigInteger
import java.security.SecureRandom

fun main(args : Array<String>) {
    GUI()
}

fun test(p : BigInteger = BigInteger.valueOf(-1), q : BigInteger = BigInteger.valueOf(-1), klartext: String = "Nachricht"){
    val ascii = stringToAscii(klartext)
    var text = BigInteger(ascii)

    var p = p
    var q = q

    println("___________________________________________________________")

    if(p.equals(BigInteger.valueOf(-1))) {
        println("~ Primzahl p wird generiert...")
        p = generatePrim()
        println("~ Primzahl p wird generiert - abgeschlossen.")
    }
    if(q.equals(BigInteger.valueOf(-1))){
        println("~ Primzahl q wird generiert...")
        q = generatePrim()
        println("~ Primzahl q wird generiert - abgeschlossen.")
    }

    println("-> Primzahl p: "+p)
    println("-> Primzahl q: "+q)
    println("___________________________________________________________")

    println("Klartext: "+ klartext)
    println("Nachricht: "+text)

    var rsamodul = p * q
    println("RSA - Modul: "+rsamodul)
    if(rsamodul.subtract(text)< BigInteger.ZERO){
        println("RSA - Modul ist < als der Text!")
    }
    var eulerschePhiFunktion = (p.subtract(BigInteger.ONE)) * (q.subtract(BigInteger.ONE))
    println("Eulersche Phi Funktion: "+eulerschePhiFunktion)

    var publicKey = publicKeyGenerieren(eulerschePhiFunktion,p,q)
    println(""+publicKey+" ist teilerfremd zu "+eulerschePhiFunktion)
    println("Öffentlicher Schlüssel: { "+publicKey+" ; "+rsamodul+" }")

    var privateKey = euklidischerAlgorithmus(publicKey,eulerschePhiFunktion)
    if(privateKey.s < BigInteger.ZERO){
        println("key < 0")
        println("key = "+privateKey.s)
        privateKey.s = privateKey.s + eulerschePhiFunktion
        println("key = "+privateKey.s)
    }
    println("Privater Schlüssel: { "+privateKey.s+" ; "+rsamodul+" }")
    println("Output euklidischer Alg: {"+privateKey.d+"|"+privateKey.s+"|"+privateKey.t+"}")

    var verschluesselung = encodeAlt(text,rsamodul,publicKey)
    println("Verschlüsselung: "+verschluesselung)

    var entschluesselung = encodeAlt(verschluesselung,rsamodul,privateKey.s)
    println("Entschlüsselung: "+entschluesselung)

    var nachricht = entschluesselung.toString()
    nachricht = asciiToString(nachricht)
    println("Nachricht: "+nachricht)
}

fun encodeAlt(w: BigInteger, rsamodul: BigInteger, key: BigInteger) : BigInteger{
    return w.modPow(key,rsamodul)
}

fun encode(w: BigInteger, rsamodul: BigInteger, key: BigInteger) : BigInteger{
    if(key.equals(BigInteger.ZERO)) return BigInteger.ONE.remainder(rsamodul)
    var toReturn = encode(w,rsamodul, key.subtract(BigInteger.ZERO))
    toReturn = (w * toReturn).remainder(rsamodul)
    return toReturn
}

fun publicKeyGenerieren(zahl: BigInteger, p: BigInteger, q: BigInteger) : BigInteger {
    var i = BigInteger.valueOf(-1)
    var two = BigInteger.valueOf(2)
    if(p<q){
        i = zahl.divide(two).subtract(p)
    }else{
        i = zahl.divide(two).subtract(q)
    }

    return i
}

fun generatePrim():BigInteger{
    return BigInteger(2048,90,SecureRandom())
}

fun euklidischerAlgorithmus(a: BigInteger, b: BigInteger): dst {
    if(b.equals(BigInteger.ZERO))return dst(a,BigInteger.ONE,BigInteger.ZERO)
    var h = euklidischerAlgorithmus(b, a%b)
    var q = a/b
    return dst(h.d,h.t,h.s-q*h.t)
}