# Manejo De Imagenes
Microservicio para cargar y descargar imagenes con Spring

# Upload
La subida de la imagen se hace mensajes REST. 
Se puede subir de dos formas: Como File o en base 64

# Download
Hay expuesto un mensaje REST tipo GET que retorna un HTTP de tipo JPG

# Resize
Como subir se puede subir imagenes de cualquier tamaño, realmente hay que bajarlas de resolucion para guardarlas de forma mas prudente. Juego con una libreria para hacer eso.

