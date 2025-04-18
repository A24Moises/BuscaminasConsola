import kotlin.math.min

data class Celda(
    var tieneMina:Boolean=false,
    var descubierta:Boolean=false,
    var tieneBandera:Boolean=false,
    var minasAlrededor: Int=0
)

class Buscaminas(private val filas: Int, private val columnas: Int, private val minas:Int){
    private val tablero: Array<Array<Celda>> = Array(filas){ Array(columnas){ Celda()} }
    private var juegoTerminado=false

    init {
        require(filas>0 && columnas>0){"Dimensiones invalidas"}
        require(minas<filas*columnas){"Demasiadas minas"}
        colocarMinas()
        calcularMinasAlrededor()
    }

    private fun colocarMinas() {
        var colocadas= 0
        while (colocadas<minas){
            val f=(0 until filas).random()
            val c=(0 until columnas).random()
            if (!tablero[f][c].tieneMina){
                tablero[f][c].tieneMina=true
                colocadas++
            }
        }
    }

    private fun calcularMinasAlrededor() {
        for (f in 0 until filas){
            for (c in 0 until columnas){
                if (!tablero[f][c].tieneMina) tablero[f][c].minasAlrededor=contarMinasCerca(f,c)
            }
        }
    }

    private fun contarMinasCerca(f: Int, c: Int): Int {
        var total=0
        for (i in -1..1){
            for (j in -1..1){
                val nf=f+i
                val nc=c+j
                if (nf in 0 until filas && nc in 0 until columnas && tablero[nf][nc].tieneMina){
                    total++
                }
            }
        }
        return total
    }

    fun destapar(f:Int, c:Int){
        if (juegoTerminado) return
        val celda= tablero.getOrNull(f)?.getOrNull(c) ?:return
        if (celda.descubierta || celda.tieneBandera) return
        celda.descubierta=true
        if (celda.tieneMina){
            juegoTerminado=true
        } else if (celda.minasAlrededor==0){
            for (i in -1..1){
                for (j in -1..1){
                    if (i!=0 || j!=0) destapar(f+i, c+j)
                }
            }
        }
    }

    fun colocarBandera(f:Int, c:Int){
        val celda= tablero.getOrNull(f)?.getOrNull(c) ?:return
        if (!celda.descubierta) celda.tieneBandera=!celda.tieneBandera
    }

    fun getTableroVisible():List<List<String>>{
        return tablero.map { fila ->
            fila.map { celda ->
                when{
                    celda.tieneBandera->"F"
                    !celda.descubierta->"x"
                    celda.tieneMina->"*"
                    celda.minasAlrededor>0->celda.minasAlrededor.toString()
                    else->" "
                }
            }
        }
    }
    fun juegoFinalizado(): Boolean=juegoTerminado
}

























