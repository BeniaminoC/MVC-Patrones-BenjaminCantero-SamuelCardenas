**Sistema de Gestión de Tareas - TaskLink**

**Descripción del Proyecto**
TaskLink es un istema de gestión de tareas desarrollado, visualmente, en JavaFX que permite a los usuarios crear, organizar y administrar sus tareas de manera eficiente. La aplicación implementa un sistema completo de autenticación de usuarios y ofrece funcionalidades avanzadas como priorización de tareas, fechas límite, indicadores de urgencia y gestión visual del estado de las tareas.

**Características Principales**
Autenticación de usuarios: Sistema completo de registro e inicio de sesión
Gestión de tareas: Crear, editar, eliminar y completar tareas
Prioridades: Clasificación de tareas en Urgente, Importante y Opcional
Fechas límite: Asignación de fechas con indicadores visuales de urgencia
Interfaz intuitiva: Diseño moderno y responsivo con JavaFX
Estadísticas: Visualización de progreso y estado de tareas
Notificaciones visuales: Alertas para tareas vencidas o próximas a vencer


**Integrantes**
Benjamin Jose Cantero Quintana
Samuel David Cardenas Hoyos


**Lenguaje y Herramientas**
Lenguaje de Programación
Java - JDK 24

*Entorno de Desarrollo*
NetBeans - IDE principal para el desarrollo

*Framework y Librerías*
JavaFX 24/25 - Framework para la interfaz gráfica de usuario
FXML - Lenguaje de marcado para diseño de vistas
CSS - Estilos personalizados para la interfaz

*Persistencia de datos*
El sistema no utiliza una base de datos tradicional.
En su lugar, se implementa una capa de persistencia basada en archivos de texto (.txt), que simula el comportamiento de un sistema de almacenamiento:


**Patrones de Diseño Utilizados**
1. MVC (Model-View-Controller)
La arquitectura principal del proyecto sigue el patrón MVC para separar responsabilidades:
Model (Modelo)

Entidades: Task, User
Servicios: TaskService, UserService
Repositorios: TaskRepository, UserRepository
Rol: Gestiona la lógica de negocio y el acceso a datos

*View (Vista)*

Archivos FXML: LoginView.fxml, MainView.fxml, RegisterView.fxml
Diálogos: DialogTask, DialogDetails
Componentes visuales: Diseño de la interfaz de usuario
Rol: Presenta la información al usuario y captura sus acciones

*Controller (Controlador)*

Controladores: LoginViewController, MainViewController, RegisterViewController
Helpers: TaskStatusHelper, TaskStyleHelper, TaskUrgencyHelper
Rol: Coordina la comunicación entre Model y View, maneja eventos del usuario


2. Observer (Observador)
Implementado para el sistema de autenticación y notificaciones de cambios de estado.

Clases: AuthSubject, AuthObserver
Rol en MVC: Permite que los Controladores (Controllers) sean notificados automáticamente cuando ocurren eventos de autenticación (login, registro, logout), manteniendo sincronizada la información entre diferentes vistas sin acoplamiento directo.
Beneficio: Desacopla los componentes, permitiendo que múltiples controladores respondan a cambios de estado del usuario sin conocerse entre sí.

Ejemplo de uso:
java// El MainViewController se registra como observador
AuthSubject.getInstance().addObserver(mainViewController);

// Cuando el usuario inicia sesión, se notifica a todos los observadores
AuthSubject.getInstance().notifyLogin(user);

3. Decorator (Decorador)
Utilizado para agregar funcionalidades visuales a las tareas de forma dinámica.

Clases:

TaskDecorator (clase base abstracta)
UrgentTaskDecorator
ExpiringTaskDecorator
DeadlineTaskDecorator
CompletedTaskDecorator


Interfaz: TaskComponent
Factory: TaskDecoratorFactory
Rol en MVC: Extiende la funcionalidad de la Vista sin modificar el Modelo. Permite que una misma tarea se presente visualmente de diferentes formas (con iconos de urgencia, etiquetas de vencimiento, estilos CSS distintos) según su estado y prioridad.
Beneficio: Permite combinar múltiples decoradores en capas para agregar comportamiento visual complejo sin modificar la clase Task original.

Ejemplo de decoración:
javaTaskComponent baseTask = new BaseTask(task);
// Se aplican decoradores según las características de la tarea
TaskComponent decoratedTask = new UrgentTaskDecorator(
    new ExpiringTaskDecorator(baseTask)
);
// Resultado: "Completar informe [HOY] [URGENTE]"

4. Factory (Factoría)
Centraliza la lógica de creación de componentes decorados.

Clase: TaskDecoratorFactory
Rol en MVC: Actúa como intermediario entre el Controlador y la Vista, encapsulando la lógica compleja de decisión sobre qué decoradores aplicar a cada tarea. El controlador simplemente solicita un componente decorado sin preocuparse por los detalles de implementación.
Beneficio: Simplifica el código del controlador y centraliza la lógica de decoración en un solo lugar.

Ejemplo de uso:
java// El controlador delega la creación a la factory
TaskComponent component = TaskDecoratorFactory.createDecoratedTask(task);

5. Singleton
Garantiza una única instancia global de objetos clave.

Clase: AuthSubject
Rol en MVC: Proporciona un punto de acceso global al sistema de autenticación desde cualquier Controlador, asegurando que todos compartan el mismo estado de sesión del usuario y puedan recibir notificaciones consistentes.
Beneficio: Evita instancias múltiples del sujeto de autenticación y mantiene la coherencia del estado de sesión en toda la aplicación.

Ejemplo de uso:
javaAuthSubject subject = AuthSubject.getInstance();
subject.addObserver(this);

6. Repository (Repositorio)
Abstrae el acceso a datos y persistencia.

Clases: UserRepository, TaskRepository
Rol en MVC: Forma parte del Modelo, encapsulando toda la lógica de acceso a datos (CRUD operations) y aislando el resto de la aplicación de los detalles de implementación de la persistencia (JPA, base de datos).
Beneficio: Facilita el cambio de tecnología de persistencia sin afectar la lógica de negocio y proporciona una API clara para operaciones con datos.


