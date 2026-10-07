# Nocturna Book Tracker (Mobile App)

[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-blue.svg?logo=kotlin)](https://kotlinlang.org/multiplatform)
[![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-brightgreen.svg?logo=jetpackcompose)](https://www.jetbrains.com/compose-multiplatform)
[![Firebase](https://img.shields.io/badge/Firebase-Auth%20%26%20Firestore-orange.svg?logo=firebase)](https://firebase.google.com)

**Nocturna Book Tracker** es una aplicación móvil multiplataforma desarrollada en **Kotlin Multiplatform (KMP)** y **Compose Multiplatform** que sirve como companion móvil y sistema de gestión personal ("Grimorio") para el ecosistema de comercio electrónico de libros y mangas **Nocturna**.

---

## 📖 De qué trata el proyecto
Nocturna nació como un santuario digital especializado en literatura oscura, fantasía gótica, terror, ciencia ficción y mangas (inspirado en [nocturna-web](https://github.com/MatiSotelo2004/nocturna-web)). 

Esta aplicación móvil permite a los lectores y lectores entusiastas:
1. **Explorar el catálogo global:** Búsqueda en tiempo real de obras y mangas utilizando la API pública de OpenLibrary con filtrado por géneros.
2. **Mi Grimorio (Biblioteca Personal):** Organizar lecturas en curso, pendientes y completadas.
3. **Soporte Híbrido (Modo Invitado + Nube):** Funciona perfectamente sin iniciar sesión guardando los datos en el **almacenamiento local** del dispositivo (`LocalStorage`), o sincronizando en tiempo real con **Firebase Firestore** al iniciar sesión.
4. **Autenticación Completa:** Inicio de sesión y registro de cuentas con nombre de usuario, confirmación de contraseña y opción de visibilidad de contraseña.
5. **Sección Acerca de Nocturna:** Información inmersiva de la marca y enlaces a la plataforma web.

---

## 🏛️ Arquitectura Elegida

El proyecto sigue una estructura basada en **Clean Architecture** y **MVVM (Model-View-ViewModel)** adaptada para Kotlin Multiplatform:

```
org.matias.nocturnatracker/
├── core/                  # Componentes UI reutilizables (NocturnaCard, NocturnaButton, etc.) y Tema M3 (Oscuro/Gótico)
├── data/                  # Fuentes de datos (Remota con Ktor + OpenLibrary, Local con SharedPreferences/NSUserDefaults, y Repositorios con Firebase)
├── domain/                # Modelos de dominio (Book, User) y Contratos de Repositorios (Interfaces)
├── navigation/            # Sistema de navegación con Jetpack Navigation Compose y BottomBar
└── presentation/          # Pantallas y ViewModels (Search, Library, Detail, Profile, Auth, About)
```

### ¿Por qué esta arquitectura?
- **Separación de responsabilidades:** La lógica de negocio y el acceso a datos están desacoplados de la interfaz de usuario.
- **Multiplataforma real:** Toda la lógica de negocio, repositorios, ViewModels y pantallas Compose se comparten en `commonMain`, reduciendo código duplicado a cero entre Android e iOS.
- **Resiliencia offline / Invitado:** El patrón de repositorio gestiona de manera transparente si debe leer/escribir en Firebase Firestore (cuando hay usuario autenticado) o en el almacenamiento local (en modo invitado).

---

## 🤖 Uso de Herramientas de Inteligencia Artificial
Este proyecto fue desarrollado usando **agentes y asistentes de Inteligencia Artificial** dentro de Android Studio:
- **Aceleración en KMP:** Generación y estructuración de la arquitectura multiplatform, módulos compartidos y adaptadores `expect/actual` para almacenamiento local (`SharedPreferences` en Android y `NSUserDefaults` en iOS).
- **Diseño de Interfaz Declarativa (Compose):** Creación rápida de componentes personalizados fieles al sistema de diseño gótico/nocturno (colores personalizados, tarjetas con bordes dorados y tipografías Serif/Sans-Serif).
- **Auditoría y QA:** Depuración de estados reactivos en tiempo real (como la sincronización asíncrona de perfiles con Firestore y el *debouncing* en la búsqueda para prevenir errores de red al escribir).

---

## 🚀 Cómo Compilar y Correr el Proyecto Localmente

### Prerrequisitos
- **Android Studio** (Koala o superior recomendado) con soporte para Kotlin Multiplatform.
- **JDK 11** o superior configurado.
- (Opcional para iOS) **Xcode** en macOS para compilar el objetivo iOS.

### Compilar y Ejecutar en Android
1. Clona el repositorio:
   ```bash
   git clone https://github.com/MatiSotelo2004/NocturnaBookTracker.git
   ```
2. Abre el proyecto en Android Studio.
3. Sincroniza el proyecto con Gradle (`File > Sync Project with Gradle Files`).
4. Ejecuta la aplicación seleccionando la configuración `:androidApp` y un emulador o dispositivo físico Android, o mediante comandos Gradle:
   ```bash
   ./gradlew :androidApp:assembleDebug
   ```

### Compilar y Ejecutar en iOS (macOS)
1. Abre la terminal en la raíz del proyecto.
2. Inicia la aplicación en el simulador de iOS desde Xcode abriendo `iosApp/iosApp.xcodeproj`, o compila la librería compartida:
   ```bash
   ./gradlew :shared:assembleSharedIosArm64FatFramework
   ```

---

## 🔥 Configuración de Firebase
Por motivos de seguridad , los archivos de configuración de credenciales de Firebase (`google-services.json` y `GoogleService-Info.plist`) no se incluyen en el repositorio público de Git.

Si deseas compilar la aplicación conectada a tu propio proyecto de Firebase (para probar Autenticación y Firestore en la nube):
1. **Crear Proyecto en Firebase:** Ve a [Firebase Console](https://console.firebase.google.com/) y crea un nuevo proyecto.
2. **Habilitar Servicios:**
   - **Authentication:** Activa el proveedor de acceso por *Correo electrónico / Contraseña*.
   - **Cloud Firestore:** Crea una base de datos en modo de prueba (*test mode*).
3. **Configurar Android:**
   - Añade una aplicación Android a tu proyecto de Firebase con el nombre de paquete: `org.matias.nocturnatracker`.
   - Descarga el archivo `google-services.json` y colócalo en la ruta:
     `androidApp/google-services.json`
4. **Configurar iOS:**
   - Añade una aplicación iOS a tu proyecto de Firebase con tu Bundle ID.
   - Descarga el archivo `GoogleService-Info.plist` y colócalo en la ruta:
     `iosApp/iosApp/GoogleService-Info.plist`

*(Nota: Si no configuras Firebase, la aplicación funcionará perfectamente en **Modo Invitado**).*