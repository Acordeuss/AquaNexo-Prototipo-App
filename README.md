# ⚓ AquaNexo — Gestión Preventiva para Buceo Acuícola

Aplicación móvil Android desarrollada como proyecto académico para la asignatura **Desarrollo de Aplicaciones Móviles (DSY1105)**.

AquaNexo busca apoyar la gestión preventiva de operaciones de buceo acuícola mediante el registro de pre-chequeos, listas de verificación, información de salud del buzo, evidencias y seguimiento de resultados.

El proyecto se desarrolla en el contexto de un proyecto de Vinculación con el Medio (VcM).

> **Nota académica:** esta aplicación corresponde a un MVP desarrollado con fines educativos. Los datos utilizados durante el desarrollo y las demostraciones son ficticios o de prueba.

---

## 👤 Integrante

**Carlos Leal**  
Ingeniería Informática — Duoc UC

Desarrollo individual.

El proyecto académico permite equipos de hasta tres integrantes, pero este desarrollo ha sido realizado de forma individual.

---

## 🎯 Objetivo

Centralizar en una aplicación móvil el proceso preventivo previo y posterior a una operación de buceo, permitiendo registrar información relevante, validar condiciones de seguridad y mantener trazabilidad local de los chequeos realizados.

El flujo principal busca cubrir:

**Login → Pre-chequeo → Checklist → Salud del buzo → Resultado → Detalle → Post-chequeo → Historial**

---

## 📱 Funcionalidades implementadas

### Inicio de sesión

- Acceso mediante credenciales simuladas.
- Perfiles de usuario utilizados para controlar determinadas acciones de la interfaz.

### Pre-chequeo

- Registro de centro y buzo.
- Fecha y hora del dispositivo.
- Validación de campos obligatorios.
- Creación del registro inicial del proceso.

### Checklist preventivo

- Lista de elementos asociados al chequeo.
- Validación de elementos pendientes.
- Registro de observaciones.
- Asociación de evidencia fotográfica.

### Salud del buzo

Registro y validación de parámetros preventivos, incluyendo:

- Presión sistólica.
- Presión diastólica.
- Saturación de oxígeno.
- Pulso.
- Validaciones de rangos y coherencia entre valores.

### Resultado

Clasificación del pre-chequeo según los datos registrados.

Los resultados permiten identificar condiciones como:

- Cumple.
- Observado.
- Requiere revisión.

### Detalle

Visualización de la información asociada al pre-chequeo:

- Datos generales.
- Checklist.
- Datos de salud.
- Resultado.
- Observaciones.
- Evidencias asociadas.

### Post-chequeo

Registro de información posterior al proceso preventivo.

### Historial

- Consulta de registros almacenados.
- Búsqueda por buzo, centro o fecha.
- Filtros por resultado.
- Acceso al detalle de cada registro.

---

## 🧭 Navegación

La aplicación utiliza **Navigation Compose** para conectar las pantallas del flujo principal.

Flujo general:

```text
Login
  ↓
Inicio
  ↓
Pre-chequeo
  ↓
Checklist
  ↓
Salud del buzo
  ↓
Resultado
  ↓
Detalle
  ↓
Post-chequeo
  ↓
Historial
```

---

## 🏗️ Arquitectura

El proyecto utiliza una estructura basada en **MVVM (Model–View–ViewModel)**.

```text
UI / Screens
      ↓
ViewModel
      ↓
Repository
      ↓
DAO
      ↓
Room Database
```

Esto permite separar:

- Interfaz.
- Estado de la aplicación.
- Reglas de negocio.
- Acceso a datos.
- Persistencia local.

---

## 🔄 Estado reactivo

Los ViewModel utilizan **StateFlow** para exponer el estado de la aplicación a las pantallas desarrolladas con Jetpack Compose.

La UI observa estos estados y se actualiza automáticamente cuando los datos cambian.

---

## 💾 Persistencia local

La aplicación utiliza **Room sobre SQLite** para almacenar información localmente.

La estructura incluye:

- Entity.
- DAO.
- Database.
- Repository.
- ViewModel.

Los registros pueden ser almacenados y posteriormente recuperados desde el historial.

---

## 📷 Recursos del dispositivo

AquaNexo integra recursos del dispositivo relacionados con el proceso preventivo.

### Cámara

Permite capturar evidencia fotográfica durante el checklist utilizando los contratos de actividad de Android.

### Galería

Permite seleccionar una imagen existente del dispositivo y asociarla como evidencia.

### Fecha y hora

La aplicación obtiene automáticamente la fecha y hora actual del dispositivo al iniciar determinados registros.

---

## ✅ Validaciones

La aplicación incorpora reglas de validación para evitar registros incompletos o incoherentes.

Entre ellas:

- Campos obligatorios.
- Fecha válida.
- Hora válida.
- Rangos de parámetros de salud.
- Relación entre presión sistólica y diastólica.
- Checklist completo.
- Observaciones obligatorias según resultado.

Los errores se muestran visualmente al usuario antes de continuar con el flujo.

---

## 🔎 Búsqueda y filtros

El historial permite:

- Buscar por nombre del buzo.
- Buscar por centro.
- Buscar por fecha.
- Filtrar registros por resultado.
- Acceder al detalle de cada registro.

---

## 🛠️ Tecnologías utilizadas

- Kotlin.
- Android Studio.
- Jetpack Compose.
- Material 3.
- Navigation Compose.
- ViewModel.
- StateFlow.
- Room.
- SQLite.
- Coroutines.
- Activity Result API.
- FileProvider.
- Coil.

---

## ▶️ Cómo ejecutar el proyecto

### Requisitos

- Android Studio.
- SDK de Android configurado.
- Emulador Android o dispositivo físico.

### Ejecución

1. Clonar o descargar el repositorio.
2. Abrir la carpeta del proyecto en Android Studio.
3. Esperar que finalice la sincronización de Gradle.
4. Seleccionar un emulador o dispositivo físico.
5. Ejecutar la aplicación desde **Run**.

Antes de ejecutar se recomienda comprobar que la sincronización de Gradle finalice sin errores.

---

## 📂 Organización general

```text
app/
└── src/main/java/
    └── .../
        ├── data/
        ├── model/
        ├── repository/
        ├── ui/
        ├── viewmodel/
        └── utils/
```

La organización busca mantener separadas las responsabilidades de interfaz, lógica y persistencia.

---

## 📚 Documentación

La documentación académica y de análisis del proyecto se encuentra dentro de:

```text
docs/
```

Esta carpeta incluye los documentos desarrollados durante las distintas etapas del proyecto.

---

## 🚧 Alcance actual

Para la **Evaluación Parcial 2** se implementó el avance funcional del frontend móvil y persistencia local correspondiente a los contenidos trabajados durante el curso.

Actualmente están implementados:

- Kotlin + Android Studio.
- Jetpack Compose.
- Material 3.
- Navegación.
- Formularios.
- Validaciones.
- ViewModel.
- StateFlow.
- Listas.
- Búsqueda.
- Filtros.
- Room.
- Persistencia local.
- Cámara.
- Galería.
- Fecha y hora del dispositivo.

---

## 🔜 Próximos pasos

- Realizar pruebas finales en emulador y dispositivo físico.
- Continuar refinando la experiencia de usuario.
- Ampliar pruebas del proyecto.
- Preparar la demostración y defensa individual.
- Implementar las funcionalidades correspondientes a las siguientes etapas de la asignatura.

---

## 📌 Estado del proyecto

**Evaluación Parcial 2 — avance funcional hasta contenidos clase 11.**

Proyecto académico en desarrollo.
