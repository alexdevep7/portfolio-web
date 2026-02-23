package org.artisancode.mywebkotlin.models

import mywebkotlin.composeapp.generated.resources.Res
import mywebkotlin.composeapp.generated.resources.portfolio1
import mywebkotlin.composeapp.generated.resources.portfolio2
import mywebkotlin.composeapp.generated.resources.portfolio3
import org.jetbrains.compose.resources.DrawableResource

data class Project(
    val title: String,
    val description: String,
    val imageResource: DrawableResource
)

object ProjectsData {
    val projects = listOf(
        Project(
            title = "DrRecipe – SaaS Médico",
            description = "Producto multiplataforma desarrollada con Kotlin y Jetpack Compose para la generación segura de documentación médica. " +
                    "Incluye validación profesional, firma digital, generación de PDF con QR, modelo freemium y panel administrativo desktop.",
            imageResource = Res.drawable.portfolio1
        ),
        Project(
            title = "TP Scanner – Inventory App",
            description = "Aplicación Android para digitalización de inventario con escaneo QR utilizando CameraX y ML Kit. " +
                    "Arquitectura MVVM, persistencia local y uso en entorno productivo.",
            imageResource = Res.drawable.portfolio2
        ),
        Project(
            title = "Roalytics – Marketing Analytics",
            description = "Aplicación Android orientada al seguimiento de campañas y análisis de datos en tiempo real. " +
                    "Implementación con Clean Architecture, Firebase Suite, Room y gestión de procesos en segundo plano.",
            imageResource = Res.drawable.portfolio3
        )
    )
}
