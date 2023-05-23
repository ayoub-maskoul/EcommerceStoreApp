package com.example.mystage.model


sealed class Category(val category: String) {

    object Clothes: Category("Clothes")
    object Jeans: Category("Jeans")
    object Pants: Category("Pants")
    object Accessory: Category("Accessory")
    object Shorts: Category("Shorts")
}