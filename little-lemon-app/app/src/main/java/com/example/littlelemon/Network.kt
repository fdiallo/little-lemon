package com.example.littlelemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuNetwork(
    @SerialName(value = "menu")
    val menu: MutableList<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    @SerialName(value = "id")
    val id : Int,
    @SerialName(value = "title")
    val title: String,
    @SerialName(value = "description")
    val description: String,
    @SerialName(value = "price")
    val price: Double,
    @SerialName(value = "image")
    val image: String,
    @SerialName(value = "category")
    val category: String,
){
    fun toMenuItemRoom() = MenuItemRoom(
        id,
        title,
        description,
        price,
        image,
        category,
    )
}