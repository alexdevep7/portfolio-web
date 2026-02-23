package org.artisancode.mywebkotlin.models

data class NavItem(
    val label: String,
    val targetSection: String
)

object NavigationItems {
    val items = listOf(
        NavItem("Inicio", "home"),
        NavItem("Sobre mi", "about"),
        NavItem("Habilidades", "skills"),
        NavItem("Proyectos", "projects")
    )
}
