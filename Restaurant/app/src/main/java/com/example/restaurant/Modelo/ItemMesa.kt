package com.example.restaurant.Modelo

class ItemMesa constructor(val cantidad: Int, val itemMenu:ItemMenu){

    fun calcularSubtotal():Int{
        return cantidad * itemMenu.precio.toInt()
    }
}