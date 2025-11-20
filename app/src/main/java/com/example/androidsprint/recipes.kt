package com.example.androidsprint


object stub {

    val burger = Category(
        0,
        "Бургеры",
        "Рецепты всех популярных видов бургеров",
        "burger.png"
    )
    val dessert = Category(
        1,
        "Десерты",
        "Самые вкусные рецепты десертов специально для вас",
        "dessert.png"
    )
    val pizza = Category(
        2,
        "Пицца",
        "Пицца на любой вкус и цвет. Лучшая подборка для тебя",
        "pizza.png"
    )
    val fish = Category(
        3,
        "Рыба",
        "Печеная, жареная, сушеная, любая рыба на твой вкус",
        "fish.png"
    )
    val soup = Category(
        4,
        "Супы",
        "От классики до экзотики: мир в одной тарелке",
        "soup.png"
    )
    val salad = Category(
        5,
        "Салаты",
        "Хрустящий калейдоскоп под соусом вдохновения",
        "salad.png"
    )

    private val categories: List<Category> = listOf(burger, dessert, pizza, fish, soup, salad)
}

public fun  getCategories(category: List<Category>): List<Category> {
    return category.take(1)
}
