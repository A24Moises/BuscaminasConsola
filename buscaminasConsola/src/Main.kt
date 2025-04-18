fun main(){
    val juego=Buscaminas(filas = 5, columnas = 5, minas = 5)
    while (!juego.juegoFinalizado()){
        imprimirTablero(juego.getTableroVisible())
        println("Accion (d = destapar, f = bandera): ")
        val accion= readln()

        println("Fila: ")
        val fila= readln().toIntOrNull() ?:continue

        println("Columna: ")
        val columna= readln().toIntOrNull() ?:continue

        when(accion.lowercase()){
            "d"->juego.destapar(fila,columna)
            "f"->juego.colocarBandera(fila,columna)
        }
    }

}

fun imprimirTablero(tablero: List<List<String>>) {
    println("  " + tablero[0].indices.joinToString(" ") { it.toString() })
    tablero.forEachIndexed { i, fila ->
        println("$i ${fila.joinToString(" ")}")
    }
}
