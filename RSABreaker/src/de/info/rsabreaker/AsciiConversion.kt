package de.info.rsabreaker

fun stringToAscii(s : String):String{
    var b = StringBuilder()
    for(c in  s.toCharArray()){
        var ascii : String = c.toInt().toString()
        while(ascii.length<3){
            ascii = "0"+ascii
        }
        b.append(ascii)
    }
    return b.toString()
}

fun asciiToString(s : String): String {
    var s = s
    val toAdd = 3 - (s.length % 3)
    if(toAdd != 3){
        var n = 0
        while (n<toAdd){
            n++
            s = "0"+s
        }
    }

    var word = StringBuilder()
    var b = StringBuilder()
    var i = 0
    for(c : Char in s.toCharArray()){
        i++
        b.append(c)
        if(i>2){
            i = 0
            val char = Integer.valueOf(b.toString()).toChar()
            b = StringBuilder()
            word.append(char)
        }
    }
    return word.toString()
}