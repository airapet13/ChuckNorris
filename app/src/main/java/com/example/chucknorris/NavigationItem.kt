package com.example.chucknorris

sealed class NavigationItem(var route: String, var icon: Int, var title: String){
    object Jokes : NavigationItem("jokes", R.drawable.ic_jokes, "Jokes")
    object Web : NavigationItem("web", R.drawable.ic_web, "Web")
}
